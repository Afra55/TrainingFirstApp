package com.magus.trainingfirstapp.module.customviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;

public class ViewDragHelperTestActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_view_drag_helper_test);
        setActionBarTitle(getString(R.string.slide_menu));
        setActionBarRightBtnText(getString(R.string.clock_s));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.actionBar_right_btn:
                startActivity(new Intent(this, CusstomViewTestActivity.class));
                break;
        }
    }
}
