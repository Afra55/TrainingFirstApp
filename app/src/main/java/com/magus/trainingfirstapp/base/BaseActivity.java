package com.magus.trainingfirstapp.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.field.G;
import com.magus.trainingfirstapp.utils.DisplayUtil;
import com.magus.trainingfirstapp.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;

/**
 * Created by yangshuai on 2015/9/25 0025.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener {

    private RelativeLayout actionBarRlt;
    private Button actionBarLeftBtn;
    private Button actionBarRightBtn;
    private TextView actionBarTitle;
    private LinearLayout contentFatherLly;
    private LinearLayout actionBarRightContainerLly;

    private int mTouchSlop;
    private Animator mAnimator;
    private float mFirstY;
    private boolean actionBarIsShown = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        /* 初始化Bomb*/
        Bmob.initialize(this, G.KeyConst.BOMB_APPLICATION_KEY);

        setContentView(R.layout.activity_base);

        /* 获取用户滑动的最短距离 */
        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        SharedPreferenceUtil.init(this);

        actionBarLeftBtn = (Button) findViewById(R.id.actionBar_left_btn);
        actionBarRightBtn = (Button) findViewById(R.id.actionBar_right_btn);
        actionBarTitle = (TextView) findViewById(R.id.actionBar_title_tv);
        actionBarRlt = (RelativeLayout) findViewById(R.id.action_bar_rlt);

        contentFatherLly = (LinearLayout) findViewById(R.id.content_father_lly);
        actionBarRightContainerLly = (LinearLayout) findViewById(R.id.actionBar_right_contenter_lly);
//        fillLayoutView();   不推荐自动添加布局
    }

    private void fillLayoutView() {
        String layoutName = getLayoutName();
        int layoutId = getFieldValue("layout", layoutName, this);
        if (layoutId != -1) {
            setContentLayout(layoutId);
        }

    }

    private String getLayoutName() {
        String className = this.getClass().getSimpleName();
        className = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
        Pattern p = Pattern.compile("\\p{Upper}");
        Matcher m = p.matcher(className);

        ArrayList<String> names = new ArrayList<String>();

        int index = 0;
        int lastIndex = index;
        while (m.find()) {
            index = className.indexOf(m.group());
            names.add(className.substring(lastIndex, index));
            lastIndex = index;
        }
        names.add(className.substring(lastIndex, className.length()));

//        Collections.reverse(names);
        className = names.get(names.size() - 1);
        for (int i = 0; i < names.size() - 1; i++) {
            className += "_" + names.get(i);
        }
        return className.toLowerCase();
    }

    /**
     * 根据给定的类型名和字段名，返回R文件中的字段的值
     *
     * @param typeName  属于哪个类别的属性 （id,layout,drawable,string,color,attr......）
     * @param fieldName 字段名
     * @return 字段的值
     * @throws Exception
     */
    public static int getFieldValue(String typeName, String fieldName, Context context) {
        int i = -1;
        try {
            Class<?> clazz = Class.forName(context.getPackageName() + ".R$" + typeName);
            i = clazz.getField(fieldName).getInt(null);
        } catch (Exception e) {
            Log.d("" + context.getClass(), "没有找到" + context.getPackageName() + ".R$" + typeName + "类型资源 " + fieldName + "请copy相应文件到对应的目录.");
            return -1;
        }
        return i;
    }

    protected void setContentLayout(int layoutId) {
        View sonView = LayoutInflater.from(this).inflate(layoutId, null);
        contentFatherLly.addView(sonView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        getChildViewForm(getWindow().getDecorView());
    }

    protected void setContentLayout(View view) {
        contentFatherLly.addView(view);
        getChildViewForm(getWindow().getDecorView());
    }

    private void getChildViewForm(View view) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup g = (ViewGroup) view;
                for (int i = 0; i < g.getChildCount(); i++) {
                    getChildViewForm(g.getChildAt(i));
                }
            } else if (view instanceof Button) {
                view.setOnClickListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected View getActionBarView(){
        return actionBarRlt;
    }

    protected void showActionBar() {
        actionBarRlt.setVisibility(View.VISIBLE);
    }

    protected void hideActionBar() {
        actionBarRlt.setVisibility(View.GONE);
    }

    protected void showActionBarLeftBtn() {
        actionBarLeftBtn.setVisibility(View.VISIBLE);
    }

    protected void hideActionBarLeftBtn() {
        actionBarLeftBtn.setVisibility(View.GONE);
    }

    protected void showActionBarRightBtn() {
        actionBarRightBtn.setVisibility(View.VISIBLE);
    }

    protected void hideActionBarRightBtn() {
        actionBarRightBtn.setVisibility(View.GONE);
    }

    protected void showActionBarTitle() {
        actionBarTitle.setVisibility(View.VISIBLE);
    }

    protected void hideActionBarTitle() {
        actionBarTitle.setVisibility(View.GONE);
    }

    //设置中间标题的文本
    protected void setActionBarTitle(String title) {
        actionBarTitle.setText(title);
    }

    //设置toast文本
    protected void changeToastState(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    //设置左侧按钮的文本
    protected void setActionBarLeftBtnText(String text) {
        actionBarLeftBtn.setText(text);
    }

    //设置左侧按钮的文本
    protected void setActionBarRightBtnText(String text) {
        actionBarRightBtn.setText(text);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.actionBar_left_btn:
                if (this instanceof TrainingFirstActivity) {
                    exit();
                } else {
                    finish();
                }

                break;
        }
    }

    //双击退出
    private long exitTime = -1;

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            changeToastState("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    //双击返回键退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((this instanceof TrainingFirstActivity) && event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //Toast公共方法
    private Toast toast = null;

    protected void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    protected void actionBarAddViewToRight(View view) {
        actionBarRightContainerLly.addView(view);
    }


    private void actionBarAnim(int flag) {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }

        switch (flag) {
            case 0:  // show
                mAnimator = ObjectAnimator.ofFloat(getActionBarView(), "translationY", getActionBarView().getTranslationY(), 0);
                break;
            case 1:  // hide
                mAnimator = ObjectAnimator.ofFloat(getActionBarView(), "translationY", getActionBarView().getTranslationY(), -getActionBarView().getHeight());
                break;
        }
        mAnimator.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        mAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFirstY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float mCurrentY = event.getY();
                float dimension = getResources().getDimension(R.dimen.action_bar_size);
                if (mFirstY - mCurrentY > mTouchSlop && actionBarIsShown) {  // 向上 hide
//                    actionBarAnim(1);
                    DisplayUtil.hideDropView(this, getActionBarView(), dimension);
                    actionBarIsShown = false;
                } else if (mCurrentY - mFirstY > mTouchSlop && !actionBarIsShown) { // 向下 show
//                    actionBarAnim(0);
                    DisplayUtil.showDropView(this, getActionBarView(), dimension);
                    actionBarIsShown = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
