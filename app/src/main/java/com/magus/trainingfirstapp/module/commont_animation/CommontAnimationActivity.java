package com.magus.trainingfirstapp.module.commont_animation;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.base.BaseFragment;
import com.magus.trainingfirstapp.module.commont_animation.fragment.PropertyAnimatorFragment;
import com.magus.trainingfirstapp.module.commont_animation.fragment.SVGTestFragment;
import com.magus.trainingfirstapp.module.commont_animation.fragment.ViewAnimationFragment;
import com.magus.trainingfirstapp.view.CategoryTabStrip;

public class CommontAnimationActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener{

    private CategoryTabStrip tabs;
    private ViewPager pager;
    private BaseFragment[] fragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(getString(R.string.anim_development));
        setContentLayout(R.layout.activity_commont_animation);
        tabs = (CategoryTabStrip) findViewById(R.id.category_strip);
        pager = (ViewPager) findViewById(R.id.category_view_pager);

        fragments = new BaseFragment[]{
                ViewAnimationFragment.newInstance("anim", "view"),
                PropertyAnimatorFragment.newInstance("anim", "Object"),
                SVGTestFragment.newInstance("anim", "SVG")};

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
                        return "视图动画";
                    case 1:
                        return "属性动画";
                    case 2:
                        return "SVG";
                }
                return "动画";
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
