package com.afra55.trainingfirstapp.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Victor Yang on 2016/10/14.
 * SingleBatchCustomView
 */

public class SingleBatchCustomView extends HorizontalScrollView {

    private final String TAG = SingleBatchCustomView.class.getSimpleName();

    private LinearLayout mContainer;

    private int mWidth;

    private int mContainerStartPadding;

    private Paint mPaint;

    private LinearLayout.LayoutParams defaultChildLayoutParams;

    private TextView mSingleTv;

    private TextView mBatchTv;

    private int mCenterCircleColor;
    private int mSelectColor;
    private int mDefaultColor;

    private float density;

    private SwitchListener mSwitchListener;

    private ValueAnimator mSingleColorChangeVAnim;
    private ValueAnimator mBatchColorChangeVAnim;
    private int mStartCenter;

    public SingleBatchCustomView(Context context) {
        this(context, null);
    }

    public SingleBatchCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleBatchCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        density = dm.density;

        setFillViewport(true);
        setWillNotDraw(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setNestedScrollingEnabled(false);
        }

        setHorizontalScrollBarEnabled(false);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.SingleBatchCustomView, defStyleAttr, defStyleAttr);

        try {
            mCenterCircleColor = typedArray.getColor(R.styleable.SingleBatchCustomView_centerCircleColor
                    , Color.parseColor("#24c3a3"));
            mSelectColor = typedArray.getColor(R.styleable.SingleBatchCustomView_selectColor
                    , Color.parseColor("#24c3a3"));
            mDefaultColor = typedArray.getColor(R.styleable.SingleBatchCustomView_defaultColor
                    , Color.parseColor("#bfc2c5"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mCenterCircleColor);

        mContainer = new LinearLayout(context);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        mContainer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT
                        , LinearLayout.LayoutParams.MATCH_PARENT)
        );
        addView(mContainer);

        defaultChildLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        initSwitchAndBatch();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        setStartAndEndPadding();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG, "single w = " + mSingleTv.getMeasuredWidth());
        Log.i(TAG, "batch w = " + mBatchTv.getMeasuredWidth());
        int animDur = (mBatchTv.getMeasuredWidth() + mSingleTv.getMeasuredWidth()) / 2 - 1;
        Log.i(TAG, "animDur = " + (animDur));
        mSingleColorChangeVAnim.setDuration(animDur);
        mBatchColorChangeVAnim.setDuration(animDur);

        mStartCenter = getScrollX() + getMeasuredWidth() / 2;
        Log.i(TAG, mStartCenter + "");
        Log.i(TAG, mSingleColorChangeVAnim.getDuration() + "");
    }

    private void setStartAndEndPadding() {
        int mContainerChildCount = mContainer.getChildCount();
        if (mContainerChildCount <= 0) {
            return;
        }
        if (mContainerChildCount == 1) {
            mContainer.setGravity(Gravity.CENTER);
        } else {
            View childAtStart = mContainer.getChildAt(0);
            mContainerStartPadding = (mWidth - childAtStart.getMeasuredWidth()) / 2;

            View childAtEnd = mContainer.getChildAt(mContainerChildCount - 1);
            int mContainerEndPadding = (mWidth - childAtEnd.getMeasuredWidth()) / 2;
            mContainer.setPadding(mContainerStartPadding, 0, mContainerEndPadding, 0);

            Log.d(TAG, "mContainerStartPadding = " + mContainerStartPadding);
            Log.d(TAG, "mContainerEndPadding = " + mContainerEndPadding);
        }
    }


    private void initSwitchAndBatch() {
        mContainer.removeAllViews();

        mSingleTv = addChild(0, "Single");
        mBatchTv = addChild(1, "Batch");

        setSingleSelectColor();

        mSingleTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSingle();
            }
        });

        mBatchTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               selectBatch();
            }
        });

        mSingleColorChangeVAnim = ObjectAnimator.ofInt(
                mSingleTv, "textColor", mSelectColor, mDefaultColor);
        mSingleColorChangeVAnim.setEvaluator(new ArgbEvaluator());

        mBatchColorChangeVAnim = ObjectAnimator.ofInt(
                mBatchTv, "textColor", mDefaultColor, mSelectColor);
        mBatchColorChangeVAnim.setEvaluator(new ArgbEvaluator());
    }

    private TextView addChild(final int position, String title) {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(dip2px(6), 0, dip2px(6), 0);
        textView.setText(title);
        mContainer.addView(textView, position, defaultChildLayoutParams);
        return textView;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mCenter = getScrollX() + getMeasuredWidth() / 2;
        canvas.drawCircle(mCenter
                , dip2px(5), dip2px(5), mPaint);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            notificationLocation();
            return false;
        }
    });

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int mCenter = getScrollX() + getMeasuredWidth() / 2;
        Log.d(TAG, "single left = " + mSingleTv.getLeft()
                + " | single width = " + mSingleTv.getMeasuredWidth()
                + "\n"
                + " | center = " + mCenter  + "\n"
                + " | mStartCenter = " + mStartCenter  + "\n"
                + " | mBatch left " + mBatchTv.getLeft()
                + " | mBatch width " + mBatchTv.getMeasuredWidth()
                );
        int playTime = mCenter - mStartCenter;
        Log.i(TAG, "playTime" + playTime);
        mSingleColorChangeVAnim.setCurrentPlayTime(playTime);
        mBatchColorChangeVAnim.setCurrentPlayTime(playTime);
    }

    private boolean hasMove = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                hasMove = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (hasMove) {
                    handler.removeMessages(0);
                    handler.sendEmptyMessageDelayed(0, 50);
                }
                hasMove = false;
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void notificationLocation() {
        int mCenter = getScrollX() + getMeasuredWidth() / 2;
        if (mCenter <= (mSingleTv.getLeft() + mSingleTv.getMeasuredWidth())) {
            selectSingle();
        } else {
            selectBatch();
        }
    }

    private void selectBatch() {
        smoothScrollTo(mBatchTv.getLeft(), 0);
        if (mSwitchListener != null) {
            mSwitchListener.check(false);
        }
    }

    private void setBatchSelectColor() {
        mSingleTv.setTextColor(mDefaultColor);
        mBatchTv.setTextColor(mSelectColor);
    }

    private void selectSingle() {
        smoothScrollTo(0, 0);
        if (mSwitchListener != null) {
            mSwitchListener.check(true);
        }
    }

    private void setSingleSelectColor() {
        mSingleTv.setTextColor(mSelectColor);
        mBatchTv.setTextColor(mDefaultColor);
    }

    @Override
    public void fling(int velocityX) {
//        super.fling(velocityX);
    }

    public void setSwitchListener(SwitchListener listener) {
        mSwitchListener = listener;
    }

    public interface SwitchListener{
        void check(boolean isSingleMode);
    }

    public int dip2px(float dipValue) {
        return (int) (dipValue * density + 0.5f);
    }

    public int px2dip(float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }
}
