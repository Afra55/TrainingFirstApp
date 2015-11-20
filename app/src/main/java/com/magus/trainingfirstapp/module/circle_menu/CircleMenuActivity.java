package com.magus.trainingfirstapp.module.circle_menu;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.view.CircleMenuLayout;

/**
 * Created by guanjun on 2015/10/19.
 */
public class CircleMenuActivity extends BaseActivity {
    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts = new String[]{"安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "信用卡"};
    private int[] mItemImgs = new int[]{R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        setActionBarTitle("圆形菜单");
        setActionBarLeftBtnText("Return");
      //  hideActionBarRightBtn();

        //    getActionBar().setDisplayHomeAsUpEnabled(true);

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);

        mCircleMenuLayout
                .setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {

                    @Override
                    public void itemClick(View view, int pos) {
                        Toast.makeText(CircleMenuActivity.this, mItemTexts[pos],
                                Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void itemCenterClick(View view) {
                        Toast.makeText(CircleMenuActivity.this,
                                "you can do something just like ccb  ",
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
