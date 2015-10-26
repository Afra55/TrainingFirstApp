package com.magus.trainingfirstapp.module.accessibility_service;

import android.accessibilityservice.AccessibilityService;
import android.media.JetPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.magus.trainingfirstapp.base.field.G;
import com.magus.trainingfirstapp.utils.Log;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yangshuai in the 11:43 of 2015.10.22 .
 * https://developer.android.com/intl/zh-cn/guide/topics/ui/accessibility/services.html
 */
public class MAcessibilityService extends AccessibilityService{

    private final static int  FINISH_INSTALL = 1009;
    private final static int  CHECK_INSTALL = 1008;
    private static final String PARAM_1 = "param1";
    private static final String PARAM_2 = "param1";



    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CHECK_INSTALL:
                    HashMap<String, Object> hash = (HashMap<String, Object>) msg.obj;

                    btnPerformClick((List<AccessibilityNodeInfo>) hash.get(PARAM_2));
                    break;
                case FINISH_INSTALL:
                    AccessibilityService service = (AccessibilityService) msg.obj;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                    }
                    break;
            }
        }
    };


    /**
     * Callback for {@link AccessibilityEvent}s.
     *
     * @param event An event.
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }


        AccessibilityNodeInfo nodeInfo = event.getSource();
        if (nodeInfo == null) {
            return;
        }

//        for (int i = 0 ; i < nodeInfo.getChildCount() ; i++) {
//            AccessibilityNodeInfo tmp = nodeInfo.getChild(i);
//            Log.d("MAcessibilityService", i + "nodeInfo.getChild(i).getText():" + tmp.getText());
//        }


        List<AccessibilityNodeInfo> nextBtn = null;
        if ((nextBtn = nodeInfo.findAccessibilityNodeInfosByText("正在安装...")) != null && (nextBtn.size() > 0)){
            Log.d("wahaha", "正在安装");
            nextBtn.get(0).recycle();

        }else if(((nextBtn = nodeInfo.findAccessibilityNodeInfosByText("下一步"))!=null && nextBtn.size() > 0) ||
                ((nextBtn = nodeInfo.findAccessibilityNodeInfosByText("安装")) !=null && nextBtn.size() > 0) ||
                ((nextBtn = nodeInfo.findAccessibilityNodeInfosByText("完成")) !=null && nextBtn.size() > 0)) {
            HashMap<String, Object> hash = new HashMap<>();
            hash.put(PARAM_1, nextBtn);
            Message message = new Message();
            message.obj = hash;
            message.what = CHECK_INSTALL;
            handler.sendMessageDelayed(message, 300);
        }

        handler.removeMessages(FINISH_INSTALL);
        Message message = new Message();
        message.what = FINISH_INSTALL;
        message.obj = this;
        handler.sendMessageDelayed(message, 1000);

        nodeInfo.recycle();

    }

    private boolean btnPerformClick(List<AccessibilityNodeInfo> nextBtn) {
        AccessibilityNodeInfo nextInfo = nextBtn.get(nextBtn.size() - 1);
        if (nextInfo == null) {
            return true;
        }
        nextInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        nextInfo.recycle();
        return false;
    }

    /**
     * Callback for interrupting the accessibility feedback.
     */
    @Override
    public void onInterrupt() {
    }
}
