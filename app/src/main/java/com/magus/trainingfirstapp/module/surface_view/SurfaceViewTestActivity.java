package com.magus.trainingfirstapp.module.surface_view;

import android.os.Bundle;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;

public class SurfaceViewTestActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("SurfaceView");
        setContentLayout(R.layout.activity_surface_view_test);
    }
}
