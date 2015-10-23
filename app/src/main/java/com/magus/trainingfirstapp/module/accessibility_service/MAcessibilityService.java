package com.magus.trainingfirstapp.module.accessibility_service;

import android.accessibilityservice.AccessibilityService;
import android.content.pm.PackageInstaller;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by yangshuai in the 11:43 of 2015.10.22 .
 */
public class MAcessibilityService extends AccessibilityService{



    /**
     * Callback for {@link AccessibilityEvent}s.
     *
     * @param event An event.
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        AccessibilityNodeInfo nodeInfo = event.getSource();
        if (nodeInfo == null) {
            return;
        }

        for (int i = 0 ; i < nodeInfo.getChildCount() ; i++) {
            Log.d("MAcessibilityService", i + "nodeInfo.getChild(i).getText():" + nodeInfo.getChild(i).getText() + " id: " + nodeInfo.getChild(i).getViewIdResourceName());
        }


        List<AccessibilityNodeInfo> nextBtn = nodeInfo.findAccessibilityNodeInfosByText("下一步");

        if (nextBtn != null && nextBtn.size()!=0) {
            AccessibilityNodeInfo ok = nextBtn.get(0);
            if (ok == null) {
                nodeInfo.recycle();
                return;
            }
            ok.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            ok.recycle();
        }
        List<AccessibilityNodeInfo> okBtn = nodeInfo.findAccessibilityNodeInfosByText("安装");

        if (okBtn != null && okBtn.size()!=0) {
            AccessibilityNodeInfo ok2 = okBtn.get(0);
            if (ok2 == null) {
                nodeInfo.recycle();
                return;
            }
            ok2.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            ok2.recycle();
        }
        List<AccessibilityNodeInfo> ok2Btn = nodeInfo.findAccessibilityNodeInfosByText("完成");

        if (ok2Btn != null && ok2Btn.size()!=0) {
            AccessibilityNodeInfo ok3 = ok2Btn.get(0);
            if (ok3 == null) {
                nodeInfo.recycle();
                return;
            }
            ok3.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            ok3.recycle();
        }




//        Log.d("click", "i have click ");
//
//
//        for (int i = 0 ; i < nodeInfo.getChildCount() ; i++) {
//            Log.d("MAcessibilityService", i + "nodeInfo.getChild(i).getText():" + nodeInfo.getChild(i).getText());
//        }

        nodeInfo.recycle();
    }

    /**
     * Callback for interrupting the accessibility feedback.
     */
    @Override
    public void onInterrupt() {

    }
}
