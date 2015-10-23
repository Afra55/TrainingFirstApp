package com.magus.trainingfirstapp.module.accessibility_service;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.os.Message;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.magus.trainingfirstapp.base.field.G;
import com.magus.trainingfirstapp.utils.Log;

import java.util.List;

/**
 * Created by yangshuai in the 11:43 of 2015.10.22 .
 */
public class MAcessibilityService extends AccessibilityService{

    private AccessibilityNodeInfo nodeInfo;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            btnPerformClick(nodeInfo, (List<AccessibilityNodeInfo>) msg.obj);
        }
    };


    /**
     * Callback for {@link AccessibilityEvent}s.
     *
     * @param event An event.
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {


        nodeInfo = event.getSource();
        if (nodeInfo == null) {
            return;
        }

        for (int i = 0 ; i < nodeInfo.getChildCount() ; i++) {
            AccessibilityNodeInfo tmp = nodeInfo.getChild(i);
            Log.d("MAcessibilityService", i + "nodeInfo.getChild(i).getText():" + tmp.getText());
            tmp.recycle();
        }

        if (nodeInfo.getChildCount() > 2) {
            AccessibilityNodeInfo Flag = nodeInfo.getChild(2);

            if (Flag != null) {
                if (Flag.getText() != null && Flag.getText().equals("正在安装...")) {
                    Flag.recycle();
                    nodeInfo.recycle();
                    return;
                }
                Flag.recycle();
            }
        }

        List<AccessibilityNodeInfo> nextBtn = null;

        if(((nextBtn = nodeInfo.findAccessibilityNodeInfosByText("下一步"))!=null && nextBtn.size() > 0) ||
                ((nextBtn = nodeInfo.findAccessibilityNodeInfosByText("安装")) !=null && nextBtn.size() > 0) ||
                ((nextBtn = nodeInfo.findAccessibilityNodeInfosByText("完成")) !=null && nextBtn.size() > 0)){
            Message message = new Message();
            message.obj = nextBtn;
            handler.sendMessageDelayed(message, 1000);
        }

        nodeInfo.recycle();
    }

    private boolean btnPerformClick(AccessibilityNodeInfo nodeInfo, List<AccessibilityNodeInfo> nextBtn) {
        AccessibilityNodeInfo nextInfo = nextBtn.get(nextBtn.size() - 1);
        if (nextInfo == null) {
            nodeInfo.recycle();
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
