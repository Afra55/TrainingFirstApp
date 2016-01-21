package com.magus.trainingfirstapp.base.field;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.magus.trainingfirstapp.R;

/**
 * Created by yangshuai on 2015/9/28 0028.
 */
public class G {
    public class HostConst{
        public static final boolean isQA = true;
    }

    public interface IntentConst{
        /** An intent for launching the system settings. */
        public static final Intent ACCESSIBILITY_SETTINGS_INTENT = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
    }

    public interface KeyConst{
        public static final String tingyunKey = "b112b0d9829f4eaba156a579b2bb9084";
        public static final String BOMB_APPLICATION_KEY = "f460268ceabe7f86553e5d9b5eefd724";
    }

    public interface UrlConst{
        public static final String E_APK = "http://www.yangguangeshequ.com/apk/app.apk";
        public static final String GAME_APK = "http://down.androidgame-store.com/201510211741/4C67B53DD0FBDBB0D75E0D07E0B1768A/new/game1/33/91133/minecraftstorymode_1444916920122.dpk?f=web_1";
        public static final String YOUXI_APK = "http://117.23.51.43/apk.r1.market.hiapk.com/data/upload/apkres/2015/10_24/14/com.example.youxiclient_020506.apk?wsiphost=local";
        public static final String  CSDN_BLOG = "http://blog.csdn.net/yang786654260";
    }

    public interface MessageConst{
        public static final String MESSAGE = "message";
        public static final String YOUXI_PACKGAE_NAME = "com.magus.youxiclient";
    }
    public interface FlagsConst{
        public static final int REQUEST_IMAGE_CAPTURE = 1;
        /**
         * 拍照flag
         */
        public static final int REQUEST_IMAGE_CAPTURE_O = 2;
        public static final int BUTTON_ITEM_ID = 1001;

    }

    /**
     * 模块常量
     */
    public interface ModuleConst{

        /**
         * 主界面模块个数(按钮点击相关)
         */
        public static final int FIRST_ACTIVITY_MODULE_BUTTON_COUNT = 35;
    }

    /**
     * 通过tagId获取模块名
     * @param context
     * @param tagId
     * @return
     */
    public static String getModuleBtnName(Context context, int tagId){
        switch (tagId){
            case 0:
                return context.getResources().getString(R.string.picture_processing);
            case 1:
                return context.getResources().getString(R.string.intent_test);
            case 2:
                return context.getResources().getString(R.string.map);
            case 3:
                return context.getResources().getString(R.string.web);
            case 4:
                return context.getString(R.string.commont_anim);
            case 5:
                return context.getResources().getString(R.string.yangshuai);
            case 6:
                return context.getResources().getString(R.string.sharebutton);
            case 7:
                return context.getResources().getString(R.string.take_photo_s);
            case 8:
                return context.getResources().getString(R.string.take_photo_demo);
            case 9:
                return context.getResources().getString(R.string.take_photo_o);
            case 10:
                return context.getResources().getString(R.string.open_swipe_demo);
            case 11:
                return context.getResources().getString(R.string.imitate_ccb_menu);
            case 12:
                return context.getResources().getString(R.string.activity_life);
            case 13:
                return context.getResources().getString(R.string.img_anim_more);
            case 14:
                return context.getResources().getString(R.string.my_anim);
            case 15:
                return context.getResources().getString(R.string.net_work_demo);
            case 16:
                return context.getResources().getString(R.string.down_load);
            case 17:
                return context.getResources().getString(R.string.open_youxi_client);
            case 18:
                return context.getResources().getString(R.string.contacts);
            case 19:
                return context.getString(R.string.effective_navigation);
            case 20:
                return context.getString(R.string.notification);
            case 21:
                return context.getString(R.string.news_reader);
            case 22:
                return context.getString(R.string.cusstom_chart);
            case 23:
                return "PagerSlidingTabStrip";
            case 24:
                return context.getString(R.string.flash_view);
            case 25:
                return context.getString(R.string.slide_menu);
            case 26:
                return context.getString(R.string.notification);
            case 27:
                return context.getString(R.string.call_phone);
            case 28:
                return context.getString(R.string.dialer);
            case 29:
                return context.getString(R.string.see_contacts);
            case 30:
                return context.getString(R.string.show_settings);
            case 31:
                return context.getString(R.string.show_wifi_setting);
            case 32:
                return context.getString(R.string.dialog_activity);
            case 33:
                return context.getString(R.string.monitoring_sms);
            case 34:
                return context.getString(R.string.monitoring_call);
        }
        return "Lover";
    }

}
