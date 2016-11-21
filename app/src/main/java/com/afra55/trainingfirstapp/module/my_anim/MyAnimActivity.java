package com.afra55.trainingfirstapp.module.my_anim;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseActivity;
import com.afra55.trainingfirstapp.base.BaseFragment;
import com.afra55.trainingfirstapp.module.my_anim.fragment.CardBackFragment;
import com.afra55.trainingfirstapp.module.my_anim.fragment.CardFrontFragment;
import com.afra55.trainingfirstapp.module.my_anim.fragment.LessonFiveFragment;
import com.afra55.trainingfirstapp.module.my_anim.fragment.LessonFourFragment;
import com.afra55.trainingfirstapp.module.my_anim.fragment.LessonOnwFragment;
import com.afra55.trainingfirstapp.module.my_anim.fragment.LessonThreeFragment;
import com.afra55.trainingfirstapp.module.my_anim.fragment.LessonTwoFragment;

public class MyAnimActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {

    private FrameLayout fragmentContainerFlt;
    private ViewPager fragmentContainerVP;
    private RadioGroup fragmentMenuRg;
    private BaseFragment[] fragments;

    /**
     * 动画延迟时间
     */
    private int mShortAnimationDuration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_my_anim);

        // 获取动画延迟时间
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);


        fragmentMenuRg = (RadioGroup) findViewById(R.id.my_anim_bottom__menu_rg);
        fragmentContainerFlt = (FrameLayout) findViewById(R.id.my_anim_fragment_contaniner_flt);
        fragmentContainerVP = (ViewPager) findViewById(R.id.my_anim_fragment_contaniner_vp);

        fragments  = new BaseFragment[]{
                LessonOnwFragment.newInstance("", ""),
                LessonTwoFragment.newInstance("", ""),
                LessonThreeFragment.newInstance("", ""),
                LessonFourFragment.newInstance("", ""),
                LessonFiveFragment.newInstance("", "")};

        setActionBarTitle(fragments[0].getFragmentTitle());

        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public BaseFragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };

        fragmentContainerVP.setAdapter(adapter);

        fragmentContainerVP.setOffscreenPageLimit(fragments.length / 2);

        fragmentContainerVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setActionBarTitle(fragments[position].getFragmentTitle());
                switch (position) {
                    case 0:
                        changeBottomRbState(R.id.my_anim_bottom_lesson_one_tb);
                        break;
                    case 1:
                        changeBottomRbState(R.id.my_anim_bottom_lesson_two_tb);
                        break;
                    case 2:
                        changeBottomRbState(R.id.my_anim_bottom_lesson_three_tb);
                        break;
                    case 3:
                        changeBottomRbState(R.id.my_anim_bottom_lesson_four_tb);
                        break;
                    case 4:
                        changeBottomRbState(R.id.my_anim_bottom_lesson_five_tb);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fragmentMenuRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.my_anim_bottom_lesson_one_tb:
                        fragmentContainerVP.setCurrentItem(0, true);
                        break;
                    case R.id.my_anim_bottom_lesson_two_tb:
                        fragmentContainerVP.setCurrentItem(1, true);
                        break;
                    case R.id.my_anim_bottom_lesson_three_tb:
                        fragmentContainerVP.setCurrentItem(2, true);
                        break;
                    case R.id.my_anim_bottom_lesson_four_tb:
                        fragmentContainerVP.setCurrentItem(3, true);

                        break;
                    case R.id.my_anim_bottom_lesson_five_tb:
                        fragmentContainerVP.setCurrentItem(4, true);

                        break;
                }
            }
        });

    }

    /**
     * 设置底部Rb的state
     * @param id
     */
    private void changeBottomRbState(int id) {
        if (id == fragmentMenuRg.getCheckedRadioButtonId()) return;
        fragmentMenuRg.clearCheck();
        RadioButton radioButton = (RadioButton)findViewById(id);
        radioButton.setChecked(true);
    }

    /**
     * fragment与activity交互
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String message) {
        getFragmentManager().beginTransaction().add(R.id.lesson_three_card_container_flt, new CardFrontFragment()).commit();
    }

    @Override
    public void onFragmentChildViewOnClick(View view) {
        if (fragmentContainerVP.getCurrentItem() == 2){
            if (view.getId() == R.id.fragment_actionBar_right_btn){

                lessonThreeAnim();
            }
        }
    }




    @Override
    public void onClick(View v) {
        if (fragmentContainerVP.getCurrentItem() == 2)
            if (v.getId() == R.id.actionBar_right_btn){

                lessonThreeAnim();
            }
        super.onClick(v);
    }

    @Override
    public void onBackPressed() {
        if (fragmentContainerVP.getCurrentItem() == 1){
            if(fragments[1].onBackPressed()){
                super.onBackPressed();
            }

        }else if (fragmentContainerVP.getCurrentItem() == 2){
            if (mLessonThreeShowingBack) {
                getFragmentManager().popBackStack();
                mLessonThreeShowingBack = false;
                return;
            }
        }else
        super.onBackPressed();
    }

    //=====================================================leson 3 start
    /**
     * lesson 3 back Flags
     */
    private boolean mLessonThreeShowingBack = false;
    /**
     * lesson 3 动画
     */
    private void lessonThreeAnim() {
        if (mLessonThreeShowingBack) {
            mLessonThreeShowingBack =false;
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

        mLessonThreeShowingBack = true;

        CardBackFragment cardBackFragment = new CardBackFragment();
        getFragmentManager()
                .beginTransaction()

                        // Replace the default fragment animations with animator resources representing
                        // rotations when switching to the back of the card, as well as animator
                        // resources representing rotations when flipping back to the front (e.g. when
                        // the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                        // Replace any fragments currently in the container view with a fragment
                        // representing the next page (indicated by the just-incremented currentPage
                        // variable).
                .replace(R.id.lesson_three_card_container_flt, cardBackFragment)

                        // Add this transaction to the back stack, allowing users to press Back
                        // to get to the front of the card.
                .addToBackStack(null)

                        // Commit the transaction.
                .commit();
    }

    //=====================================================leson 3 end

}
