package com.magus.trainingfirstapp.module.customviews;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.module.customviews.charting.CusstomScrollView;

public class MyOwnCusstomViewActivity extends BaseActivity {

    private CusstomScrollView mScrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_my_own_cusstom_view);
        mScrollView = (CusstomScrollView) findViewById(R.id.mCusstomScrollview);
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(R.drawable.loading);
            mScrollView.addView(imageView);
        }
    }
}
