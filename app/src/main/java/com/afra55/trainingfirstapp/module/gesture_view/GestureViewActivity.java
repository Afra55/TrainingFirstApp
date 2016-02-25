package com.afra55.trainingfirstapp.module.gesture_view;

import android.os.Bundle;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseActivity;
import com.afra55.trainingfirstapp.view.GestureView;

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
