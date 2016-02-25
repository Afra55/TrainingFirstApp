package com.afra55.trainingfirstapp.module.customviews;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseActivity;
import com.afra55.trainingfirstapp.view.CusstomScrollView;

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
            imageView.setImageResource(R.drawable.beauty);
            mScrollView.addView(imageView);
        }
    }
}
