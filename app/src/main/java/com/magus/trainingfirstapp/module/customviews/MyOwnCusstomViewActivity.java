package com.magus.trainingfirstapp.module.customviews;

import android.app.Activity;
import android.os.Bundle;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;

public class MyOwnCusstomViewActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_my_own_cusstom_view);
    }
}
