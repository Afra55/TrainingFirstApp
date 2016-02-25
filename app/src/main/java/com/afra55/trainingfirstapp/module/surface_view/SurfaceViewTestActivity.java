package com.afra55.trainingfirstapp.module.surface_view;

import android.os.Bundle;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseActivity;

public class SurfaceViewTestActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("SurfaceView");
        setContentLayout(R.layout.activity_surface_view_test);
    }
}
