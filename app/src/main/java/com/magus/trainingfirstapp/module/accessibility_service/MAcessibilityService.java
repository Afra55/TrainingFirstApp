package com.magus.trainingfirstapp.module.accessibility_service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

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
//
//        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//        nodeInfo.recycle();

    }

    /**
     * Callback for interrupting the accessibility feedback.
     */
    @Override
    public void onInterrupt() {

    }
}
