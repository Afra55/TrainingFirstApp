package com.magus.trainingfirstapp.module.gesture_view;

import android.os.Bundle;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.view.GestureView;

public class GestureViewActivity extends BaseActivity {

    private GestureView gestureView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_view);
        gestureView = (GestureView) findViewById(R.id.view_gesture);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
