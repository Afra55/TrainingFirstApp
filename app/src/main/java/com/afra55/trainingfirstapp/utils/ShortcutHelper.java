/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afra55.trainingfirstapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.afra55.trainingfirstapp.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N_MR1)
public class ShortcutHelper {
    private static final String TAG = ShortcutHelper.class.getName();

    private static final String EXTRA_LAST_REFRESH =
            "com.afra55.trainingfirstapp.EXTRA_LAST_REFRESH";

    private static final long REFRESH_INTERVAL_MS = 60 * 60 * 1000;

    private final Context mContext;

    private final ShortcutManager mShortcutManager;

    public ShortcutHelper(Context context) {
        mContext = context;
        mShortcutManager = mContext.getSystemService(ShortcutManager.class);
    }

    public void maybeRestoreAllDynamicShortcuts() {
        try {
            if (mShortcutManager.getDynamicShortcuts().size() == 0) {
                // NOTE: 如果应用总是有动态的快捷方式, 在这里重新发布他们。
                // 当应用在另一个设备 被还原时，所有的动态的快捷方式都不会被还原，只有放在桌面的快捷方式才会被还原.
                if (mShortcutManager.getPinnedShortcuts().size() > 0) {
                    // 桌面的快捷方式已经被还原了 使用 updateShortcuts(List) 方法确保他们是最新的内容.
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportShortcutUsed(String id) {
        mShortcutManager.reportShortcutUsed(id);
    }

    /**
     * 调用 ShortcutManager 的方法后，判断是否被限制.
     */
    private void callShortcutManager(boolean r) {
        if (!r) {
            showToast(mContext, "Call to ShortcutManager is rate-limited");
        }
    }

    /**
     * 获取所有的可变的快捷方式.
     */
    public List<ShortcutInfo> getShortcuts() {
        // 加载可变的动态快捷方式和加载在桌面的快捷方式，
        // 放在一个 list 里并移除重复的（因为动态的也可能被拖放在桌面上）。

        final List<ShortcutInfo> ret = new ArrayList<>();
        final HashSet<String> seenKeys = new HashSet<>();

        // 检查存在的快捷方式
        for (ShortcutInfo shortcut : mShortcutManager.getDynamicShortcuts()) {
            if (!shortcut.isImmutable()) {
                // 只拿去可更改的快捷方式
                ret.add(shortcut);
                seenKeys.add(shortcut.getId());
            }
        }

        // 检查所有的固定在桌面上的快捷方式
        for (ShortcutInfo shortcut : mShortcutManager.getPinnedShortcuts()) {
            if (!shortcut.isImmutable() && !seenKeys.contains(shortcut.getId())) {
                // 只拿去可更改 和 不重复的快捷方式
                ret.add(shortcut);
                seenKeys.add(shortcut.getId());
            }
        }
        return ret;
    }

    /**
     * 当 activity 开始的时候调用.  查找已经发布的快捷方式并刷新他们，异步操作(but the refresh part isn't implemented yet...).
     */
    public void refreshShortcuts(final boolean force) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Log.i(TAG, "refreshingShortcuts...");

                final long now = System.currentTimeMillis();
                final long staleThreshold = force ? now : now - REFRESH_INTERVAL_MS;

                // 检测所有存在的动态快捷方式和放在桌面的快捷方式。
                // 如果上次刷新的时间与当前时间间隔超过了阖值 （REFRESH_INTERVAL_MS), 再更新他们。

                final List<ShortcutInfo> updateList = new ArrayList<>();

                for (ShortcutInfo shortcut : getShortcuts()) {
                    if (shortcut.isImmutable()) {
                        continue;
                    }
                    // 遍历所有可更改的快捷方式

                    final PersistableBundle extras = shortcut.getExtras();
                    if (extras != null && extras.getLong(EXTRA_LAST_REFRESH) >= staleThreshold) {
                        // Shortcut 是最新的，就不再刷新它。
                        continue;
                    }
                    Log.i(TAG, "Refreshing shortcut: " + shortcut.getId());

                    final ShortcutInfo.Builder b = new ShortcutInfo.Builder(
                            mContext, shortcut.getId());

                    setSiteInformation(b, shortcut.getIntent().getData());
                    setExtras(b);

                    updateList.add(b.build());
                }
                // Call update.
                if (updateList.size() > 0) {
                    try {
                        callShortcutManager(mShortcutManager.updateShortcuts(updateList));
                    } catch (Exception e) {
                        Log.e(TAG, "Caught Exception", e);
                        showToast(mContext, "Error while calling ShortcutManager: " + e.toString());
                    }
                }

                return null;
            }
        }.execute();
    }

    /**
     * 通过 url 创建快捷方式
     * @param urlAsString String
     * @return ShortcutInfo
     */
    private ShortcutInfo createShortcutForUrl(String urlAsString) {
        Log.i(TAG, "createShortcutForUrl: " + urlAsString);

        final ShortcutInfo.Builder b = new ShortcutInfo.Builder(mContext, urlAsString);

        final Uri uri = Uri.parse(urlAsString);

        // 设置这个快捷方式的对应的 Intent.
        b.setIntent(new Intent(Intent.ACTION_VIEW, uri));

        setSiteInformation(b, uri);
        setExtras(b);

        return b.build();
    }

    /**
     * 设置站点信息 icon 和 title
     *
     * @param b   ShortcutInfo.Builder
     * @param uri Uri
     * @return ShortcutInfo.Builder
     */
    private ShortcutInfo.Builder setSiteInformation(ShortcutInfo.Builder b, Uri uri) {
        // TODO Get the actual site <title> and use it.
        // TODO Set the current locale to accept-language to get localized title.
        b.setShortLabel(uri.getHost());
        b.setLongLabel(uri.toString());

        Bitmap bmp = fetchFavicon(uri);
        if (bmp != null) {
            b.setIcon(Icon.createWithBitmap(bmp));
        } else {
            b.setIcon(Icon.createWithResource(mContext, R.drawable.link));
        }

        return b;
    }

    /**
     * 存储快捷方式的刷新时间
     *
     * @param b ShortcutInfo.Builder
     * @return ShortcutInfo.Builder
     */
    private ShortcutInfo.Builder setExtras(ShortcutInfo.Builder b) {
        final PersistableBundle extras = new PersistableBundle();
        extras.putLong(EXTRA_LAST_REFRESH, System.currentTimeMillis());
        b.setExtras(extras);
        return b;
    }

    private String normalizeUrl(String urlAsString) {
        if (urlAsString.startsWith("http://") || urlAsString.startsWith("https://")) {
            return urlAsString;
        } else {
            return "http://" + urlAsString;
        }
    }

    /**
     * 添加站点快捷方式
     * @param urlAsString String
     */
    public void addWebSiteShortcut(String urlAsString) {
        final ShortcutInfo shortcut = createShortcutForUrl(normalizeUrl(urlAsString));
        try {
            // 添加动态快捷方式
            callShortcutManager(mShortcutManager.addDynamicShortcuts(Collections.singletonList(shortcut)));
        } catch (Exception e) {
            Log.e(TAG, "Caught Exception", e);
            showToast(mContext, "Error while calling ShortcutManager: " + e.toString());
        }
    }

    public void removeShortcut(ShortcutInfo shortcut) {
        mShortcutManager.removeDynamicShortcuts(Collections.singletonList(shortcut.getId()));
    }

    public void disableShortcut(ShortcutInfo shortcut) {
        mShortcutManager.disableShortcuts(Collections.singletonList(shortcut.getId()));
    }

    public void enableShortcut(ShortcutInfo shortcut) {
        mShortcutManager.enableShortcuts(Collections.singletonList(shortcut.getId()));
    }

    /**
     * 获取站点的 favicon.ico 图标
     *
     * @param uri uri
     * @return Bitmap
     */
    private Bitmap fetchFavicon(Uri uri) {
        final Uri iconUri = uri.buildUpon().path("favicon.ico").build();
        Log.i(TAG, "Fetching favicon from: " + iconUri);

        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(iconUri.toString()).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            return BitmapFactory.decodeStream(bis);
        } catch (IOException e) {
            Log.w(TAG, "Failed to fetch favicon from " + iconUri, e);
            return null;
        }
    }

    public static void showToast(final Context context, final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
