package com.magus.trainingfirstapp.base.field;

import android.content.Context;

import com.magus.trainingfirstapp.R;

/**
 * Created by yangshuai on 2015/9/28 0028.
 */
public class G {
    public class HostConst{
        public static final boolean isQA = true;
    }

    public interface UrlConst{
        public static final String  CSDN_BLOG = "http://blog.csdn.net/yang786654260";
    }

    public interface MessageConst{
        public static final String MESSAGE = "message";
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
        public static final int FIRST_ACTIVITY_MODULE_BUTTON_COUNT = 16;
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
                return context.getResources().getString(R.string.fragment);
            case 1:
                return context.getResources().getString(R.string.intent_test);
            case 2:
                return context.getResources().getString(R.string.map);
            case 3:
                return context.getResources().getString(R.string.web);
            case 4:
                return context.getResources().getString(R.string.email);
            case 5:
                return context.getResources().getString(R.string.opennew);
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
        }
        return "";
    }

}
