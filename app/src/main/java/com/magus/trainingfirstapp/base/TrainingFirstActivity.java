package com.magus.trainingfirstapp.base;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.field.G;
import com.magus.trainingfirstapp.module.DialogThemeActivity;
import com.magus.trainingfirstapp.module.alert.AlerTestActivity;
import com.magus.trainingfirstapp.utils.accessibility_service.MAcessibilityService;
import com.magus.trainingfirstapp.module.activity_life.ActivityA;
import com.magus.trainingfirstapp.utils.broadcast_receiver.BroadcastTestReceiver;
import com.magus.trainingfirstapp.module.circle_menu.CircleMenuActivity;
import com.magus.trainingfirstapp.module.commont_animation.CommontAnimationActivity;
import com.magus.trainingfirstapp.module.contacts.ContactsActivity;
import com.magus.trainingfirstapp.module.customviews.CusstomViewActivity;
import com.magus.trainingfirstapp.module.customviews.CusstomViewTestActivity;
import com.magus.trainingfirstapp.module.customviews.ViewDragHelperTestActivity;
import com.magus.trainingfirstapp.module.effectivenavigation.EffectiveNavigationActivity;
import com.magus.trainingfirstapp.module.image_handle.ImageHandlePixActivity;
import com.magus.trainingfirstapp.module.images.DisplayingBitmapsActivity;
import com.magus.trainingfirstapp.module.myanim.MyAnimActivity;
import com.magus.trainingfirstapp.module.networkusage_demo.NetworkActivity;
import com.magus.trainingfirstapp.module.newsreader.NewsReaderActivity;
import com.magus.trainingfirstapp.module.notification.NotificationTestActivity;
import com.magus.trainingfirstapp.module.pagerSlidingTabStrip.PagerSlidingTabStripActivity;
import com.magus.trainingfirstapp.module.photobyintent.PhotoIntentActivity;
import com.magus.trainingfirstapp.module.pingme.PingMeActivity;
import com.magus.trainingfirstapp.module.surface_view.SurfaceViewTestActivity;
import com.magus.trainingfirstapp.module.swipe_menu.SwipeMenuDemoActvity;
import com.magus.trainingfirstapp.utils.CommontUtils;
import com.magus.trainingfirstapp.utils.DisplayUtil;
import com.magus.trainingfirstapp.utils.SharedPreferenceUtil;
import com.magus.trainingfirstapp.utils.alert_utils.AlertUtils;
import com.magus.trainingfirstapp.utils.download_utils.DownLoadService;
import com.magus.trainingfirstapp.view.AutoDisplayChildViewContainer;
import com.networkbench.agent.impl.NBSAppAgent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TrainingFirstActivity extends BaseActivity {

    private ImageView takePhotoThenToShowImg;
    private AutoDisplayChildViewContainer first_module_content_lly;
    private final int MODULE_BUTTON_ITEM_ID = G.FlagsConst.BUTTON_ITEM_ID;
    private ScrollView scrollView;
    private boolean topProgressContentIsShown = true;
    private RelativeLayout topProgressContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化听云
        // 采集地理位置信息
        NBSAppAgent.setLicenseKey(G.KeyConst.tingyunKey).withLocationServiceEnabled(true).start(this);
        // 不需要采集地理位置信息 NBSAppAgent.setLicenseKey(G.KeyConst.tingyunKey).start(this);

        setContentLayout(R.layout.activity_training_first);
        setActionBarLeftBtnText("Exit");
        setActionBarTitle("主界面");
        setActionBarRightBtnText("Lover");
        initWidget();
        initModule();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initWidget() {
        topProgressContent = (RelativeLayout) findViewById(R.id.first_relativeLayout);

        addTopBackgroundAnim();

        first_module_content_lly = (AutoDisplayChildViewContainer) findViewById(R.id.first_module_content_lly);
        takePhotoThenToShowImg = (ImageView) findViewById(R.id.first_image_content);
        ((TextView) findViewById(R.id.first_show_tv)).setText(Build.MODEL);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        childModuleShowAnim();
    }

    private void addTopBackgroundAnim() {
        ValueAnimator colorAnim = ObjectAnimator.ofInt(topProgressContent, "backgroundColor", /*Red*/0xFFFF8080, /*Blue*/0xFF8080FF);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }

    private void childModuleShowAnim() {
        ScaleAnimation animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(300);
        LayoutAnimationController animationController = new LayoutAnimationController(animation, 0.1f);
        animationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        first_module_content_lly.setLayoutAnimation(animationController);
    }

    private void initModule() {
        // 添加所有的按钮模块
        for (int i = 0; i <= G.ModuleConst.FIRST_ACTIVITY_MODULE_BUTTON_COUNT; i++) {
            addButton(i);
        }
    }

    /**
     * 添加模块按钮
     *
     * @param tagId
     */
    private void addButton(int tagId) {
        Button button = (Button) LayoutInflater.from(this).inflate(R.layout.view_button, null);
        button.setGravity(Gravity.CENTER);
        button.setText(G.getModuleBtnName(this, tagId));
        Log.d("TrainingFirstActivity", G.getModuleBtnName(this, tagId));
        button.setId(MODULE_BUTTON_ITEM_ID);
        button.setTag(tagId);
        button.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setStateListAnimator(AnimatorInflater.loadStateListAnimator(this, R.drawable.state_list_anim_rotate));
        }
        first_module_content_lly.addView(button);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_training_fisrt_acitivity, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ShareActionProvider shareActionProvider = (ShareActionProvider) (menu.findItem(R.id.menu_item_share)).getActionProvider();
            shareActionProvider.setShareIntent(shareText());
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showToast("action_settings");
                break;
            case R.id.menu_item_share:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    startActivity(shareText());
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case MODULE_BUTTON_ITEM_ID:
                if (CommontUtils.isFastDoubleClick()) {
                    showToast("正在加载中，请耐心等待哦~");
                    return;
                }
                intent = onModuleBtnClick((Integer) v.getTag());
                break;
            case R.id.actionBar_right_btn:
                if (topProgressContent.getTag() != null) {
                    showToast("oh, my lover!");
                } else if (topProgressContentIsShown) {
                    DisplayUtil.hideDropView(this, topProgressContent, DisplayUtil.dip2px(this, 80));
                    topProgressContentIsShown = false;
                } else {
                    DisplayUtil.showDropView(this, topProgressContent, DisplayUtil.dip2px(this, 80));
                    topProgressContentIsShown = true;
                }
                break;
        }
        if (intent != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(
                                this,
                                Pair.create(v, "share")).toBundle());
            } else {
                startActivity(intent);
            }
        }
    }

    /**
     * 模块的点击事件
     *
     * @param tagId
     */
    private Intent onModuleBtnClick(int tagId) {
        switch (tagId) {
            case 0:
                return new Intent(this, ImageHandlePixActivity.class);
            case 1:
                Uri number = Uri.parse("tel:10086");
                Intent intent = new Intent(Intent.ACTION_DIAL, number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                startActivity(intent);
                break;
            case 2:
                try {
                    Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
                    startActivity(new Intent(Intent.ACTION_VIEW, location));
                } catch (Exception e) {
                    showToast("没有地图");
                }
                return null;
            case 3:
                Uri webPage = Uri.parse(G.UrlConst.CSDN_BLOG);
                return new Intent(Intent.ACTION_VIEW, webPage);
            case 4:
                return new Intent(TrainingFirstActivity.this, CommontAnimationActivity.class);
            case 5:
                Intent otherintent = new Intent(TrainingFirstActivity.this, SurfaceViewTestActivity.class);
                startActivity(otherintent);
                return null;
            case 6:
                return Intent.createChooser(shareText(), "选啊你");
            case 10:
                return new Intent(TrainingFirstActivity.this, SwipeMenuDemoActvity.class);
            case 7:
                return new Intent(TrainingFirstActivity.this, PhotoIntentActivity.class);
            case 8:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, G.FlagsConst.REQUEST_IMAGE_CAPTURE);
                }
                return null;
            case 9:   //保存拍摄到的原图
                savePhoto();
                return null;
            case 11:
                return new Intent(TrainingFirstActivity.this, CircleMenuActivity.class);
            case 12:
                showToast("大家好");
                return new Intent(TrainingFirstActivity.this, ActivityA.class);
            case 13:
                return new Intent(TrainingFirstActivity.this, DisplayingBitmapsActivity.class);
            case 14:
                return new Intent(TrainingFirstActivity.this, MyAnimActivity.class);
            case 15:
                return new Intent(TrainingFirstActivity.this, NetworkActivity.class);
            case 16:
                downLoadTemp();
                break;
            case 17:
//                openYouXiClient();
                showPopAddContact(G.getModuleBtnName(this, 17));
                break;
            case 18:
                return new Intent(TrainingFirstActivity.this, ContactsActivity.class);
            case 19:
                return new Intent(TrainingFirstActivity.this, EffectiveNavigationActivity.class);
            case 20:
                return new Intent(TrainingFirstActivity.this, PingMeActivity.class);
            case 21:
                return new Intent(TrainingFirstActivity.this, NewsReaderActivity.class);
            case 22:
                return new Intent(TrainingFirstActivity.this, CusstomViewActivity.class);
            case 23:
                return new Intent(TrainingFirstActivity.this, PagerSlidingTabStripActivity.class);
            case 24:
                return new Intent(TrainingFirstActivity.this, CusstomViewTestActivity.class);
            case 25:
                return new Intent(TrainingFirstActivity.this, ViewDragHelperTestActivity.class);
            case 26:
                return new Intent(TrainingFirstActivity.this, NotificationTestActivity.class);
            case 27:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return null;
                }
                Intent callDialogIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"));
                callDialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                startActivity(callDialogIntent);
                break;
            case 28:
                Intent touchDialerIntent = new Intent("com.android.phone.action.TOUCH_DIALER");
                touchDialerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                startActivity(touchDialerIntent);
                break;
            case 29:
                Intent contactsIntent = new Intent("com.android.contacts.action.LIST_CONTACTS");
                contactsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                startActivity(contactsIntent);
                break;
            case 30:
                Intent settingIntent = new Intent("android.settings.SETTINGS");
                settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                startActivity(settingIntent);
                break;
            case 31:
                Intent wifiSettingIntent = new Intent("android.settings.WIFI_SETTINGS");
                wifiSettingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                startActivity(wifiSettingIntent);
                break;
            case 32:
                return new Intent(this, DialogThemeActivity.class);
            case 33:
                if (!SharedPreferenceUtil.getBooleanData(G.KeyConst.MONITORING_SMS)) {
                    SharedPreferenceUtil.saveBooleanData(G.KeyConst.MONITORING_SMS, true);
                    showToast("开启短信拦截" + SharedPreferenceUtil.getBooleanData(G.KeyConst.MONITORING_SMS));
                } else {
                    SharedPreferenceUtil.saveBooleanData(G.KeyConst.MONITORING_SMS, false);
                    showToast("关闭短信拦截"+SharedPreferenceUtil.getBooleanData(G.KeyConst.MONITORING_SMS));
                }
                break;
            case 34:
                if (!SharedPreferenceUtil.getBooleanData(G.KeyConst.MONITORING_CALL)) {
                    showPopAddContact(G.getModuleBtnName(this, 34));
                } else {
                    SharedPreferenceUtil.saveBooleanData(G.KeyConst.MONITORING_CALL, false);
                    showToast("关闭电话拦截" + SharedPreferenceUtil.getBooleanData(G.KeyConst.MONITORING_CALL));
                }
                break;
            case 35:
                return new Intent(this, AlerTestActivity.class);
        }
        return null;
    }

    private void downLoadTemp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && !SharedPreferenceUtil.getBooleanData(G.FlagsConst.AUTO_INSTALL)) {
            startActivity(G.IntentConst.ACCESSIBILITY_SETTINGS_INTENT);
            showToast("开启省心装自动安装应用包");
        }
        AlertUtils.showAlert(this, "提示", "确认下载阳光e客户端？", "是的", "不要", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MAcessibilityService.setEnable(true);
                DownLoadService youxiDown = new DownLoadService(TrainingFirstActivity.this, G.UrlConst.E_APK);
                youxiDown.setDescribeText("正在下载elife...");
                youxiDown.startDownLoad();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void savePhoto() {
        Intent takePictureOintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureOintent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                takePictureOintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureOintent, G.FlagsConst.REQUEST_IMAGE_CAPTURE_O);
            }
        }
    }

    private void openYouXiClient(final String path) {

        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(G.MessageConst.YOUXI_PACKGAE_NAME, 0);
        } catch (PackageManager.NameNotFoundException e) {
            showToast("没有有戏客户端，请选择下载");
            AlertUtils.showAlert(this, "提示", "确认下载有戏客户端？", "是的", "不要", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    MAcessibilityService.setEnable(true);
                    DownLoadService youxiDown = new DownLoadService(TrainingFirstActivity.this, path);
                    youxiDown.setDescribeText("正在下载有戏客户端...");
                    youxiDown.startDownLoad();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                startActivity(G.IntentConst.ACCESSIBILITY_SETTINGS_INTENT);
//                showToast("开启省心装自动安装应用包");
//            }
            return;
        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageInfo.packageName);
        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);
        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String className = ri.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packageInfo.packageName, className);
            intent.setComponent(cn);
            this.startActivity(intent);
        }
    }

    /* 弹出框 */
    private PopupWindow popupWindow;
    private EditText youxiPath;

    /* 显示弹出框用来填写有戏的下载地址信息 */
    private void showPopAddContact(final String module) {
        if (popupWindow == null) {

            View view = LayoutInflater.from(this).inflate(R.layout.pop_youxi_down_path_view, null);
            TextView title = (TextView) view.findViewById(R.id.pop_contact_title);
            if (module.equals(G.getModuleBtnName(this, 34))) {
                title.setText("请输入要拦截的电话号码");
            }
            youxiPath = (EditText) view.findViewById(R.id.pop_youxi_path_et);
            if (module.equals(G.getModuleBtnName(this, 34))) {
                youxiPath.setInputType(InputType.TYPE_CLASS_PHONE);
            } else {
                youxiPath.setInputType(InputType.TYPE_CLASS_TEXT);
            }
            popupWindow = new PopupWindow(view,
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(false);
            popupWindow.setAnimationStyle(R.style.pop_out_in);
            view.findViewById(R.id.pop_contact_ok_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    if (module.equals(G.getModuleBtnName(TrainingFirstActivity.this, 17))) {
                        openYouXiClient(youxiPath.getText().toString());
                    } else if (module.equals(G.getModuleBtnName(TrainingFirstActivity.this, 34))) {
                        SharedPreferenceUtil.saveBooleanData(G.KeyConst.MONITORING_CALL, true);
                        BroadcastTestReceiver.MONITORING_CALL_NUM = youxiPath.getText().toString().trim();
                        showToast("开启电话拦截"+ SharedPreferenceUtil.getBooleanData(G.KeyConst.MONITORING_CALL)
                                +"\n黑名单手机号是：" + BroadcastTestReceiver.MONITORING_CALL_NUM);
                    }
                }
            });
            view.findViewById(R.id.pop_contact_cancle_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    if (module.equals(G.getModuleBtnName(TrainingFirstActivity.this, 17))) {
                        openYouXiClient(G.UrlConst.YOUXI_APK);
                    }
                }
            });
        }
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    public void showOsInfo(View view) {
        startActivity(new Intent(this, MobileOsInfoActivity.class));
    }

    private Intent shareText() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.hellowShuaiYang));
        shareIntent.setType("text/plain");
        return shareIntent;
    }

    public void sendMsg(String content) {
        SmsManager smsManager = SmsManager.getDefault();
        List<String> divideContents = smsManager.divideMessage(content);
        for (String text : divideContents) {
            smsManager.sendTextMessage("1000", null, text, null, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == G.FlagsConst.REQUEST_IMAGE_CAPTURE_O && resultCode == RESULT_OK) {
            int targetW = takePhotoThenToShowImg.getWidth();
            int targetH = takePhotoThenToShowImg.getHeight();

		/* Get the size of the image */
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
            int scaleFactor = 1;
            if ((targetW > 0) || (targetH > 0)) {
                scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            }

		/* Set bitmap options to scale the image decode target */
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
            takePhotoThenToShowImg.setImageBitmap(bitmap);
            galleryAddPic();
        }
        if (requestCode == G.FlagsConst.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {//展示图片
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            takePhotoThenToShowImg.setImageBitmap(imageBitmap);
            showToast("图片已经显示在蓝色布景上");
            scrollView.smoothScrollTo(0, 0);
        }
    }

    private String mCurrentPhotoPath;

    /**
     * 创建拍摄的图片的存储路径及文件名
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.d("TrainingFirstActivity", "storageDir:" + storageDir);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("image.getAbsolutePath()", image.getAbsolutePath() + "");
        return image;
    }

    /**
     * 将拍摄到的照片添加到Media Provider的数据库中
     */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}
