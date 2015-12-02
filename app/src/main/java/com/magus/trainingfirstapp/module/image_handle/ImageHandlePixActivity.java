package com.magus.trainingfirstapp.module.image_handle;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.base.BaseFragment;
import com.magus.trainingfirstapp.module.image_handle.fragment.MatrixColorFragment;
import com.magus.trainingfirstapp.module.image_handle.fragment.PixHandleFragment;
import com.magus.trainingfirstapp.view.CategoryTabStrip;

public class ImageHandlePixActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {

    private CategoryTabStrip tabs;
    private ViewPager pager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(getString(R.string.handle_img));
        setContentLayout(R.layout.activity_image_handle_pix);
        tabs = (CategoryTabStrip) findViewById(R.id.category_strip);
        pager = (ViewPager) findViewById(R.id.category_view_pager);

        final Fragment[] fragments = new Fragment[]{
                PixHandleFragment.newInstance("image_handle", "pix"),
                MatrixColorFragment.newInstance("image_handle", "matrix")};

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
                }
                return "图像处理";
            }
        });
        tabs.setViewPager(pager);
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
}
