package com.afra55.trainingfirstapp.module.image_handle;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseActivity;
import com.afra55.trainingfirstapp.base.BaseFragment;
import com.afra55.trainingfirstapp.module.image_handle.fragment.MatrixColorFragment;
import com.afra55.trainingfirstapp.module.image_handle.fragment.PixHandleFragment;
import com.afra55.trainingfirstapp.module.image_handle.fragment.PorterDuffXfermodeTestFragment;
import com.afra55.trainingfirstapp.module.image_handle.fragment.ShaderTestFragment;
import com.afra55.trainingfirstapp.view.CategoryTabStrip;

public class ImageHandlePixActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {

    private CategoryTabStrip tabs;
    private ViewPager pager;
    private int fragmentShownPosition = 0;
    private BaseFragment[] fragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(getString(R.string.handle_img));
        setActionBarRightBtnText(getString(R.string.reset));
        setContentLayout(R.layout.activity_image_handle_pix);
        tabs = (CategoryTabStrip) findViewById(R.id.category_strip);
        pager = (ViewPager) findViewById(R.id.category_view_pager);

        fragments = new BaseFragment[]{
                PixHandleFragment.newInstance("image_handle", "pix"),
                MatrixColorFragment.newInstance("image_handle", "matrix"),
                PorterDuffXfermodeTestFragment.newInstance("image_handle", "xfermode"),
                ShaderTestFragment.newInstance("image_handle", "shader")};

        pager.setOffscreenPageLimit(fragments.length);
        pager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "图像像素点处理";
                    case 1:
                        return "色彩矩阵处理";
                    case 2:
                        return "画笔特效处理";
                }
                return "图像处理";
            }
        });
        tabs.setViewPager(pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragmentShownPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String message) {

    }

    @Override
    public void onFragmentChildViewOnClick(View view) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.actionBar_right_btn:
                fragments[fragmentShownPosition].reset();
                break;
        }
    }
}
