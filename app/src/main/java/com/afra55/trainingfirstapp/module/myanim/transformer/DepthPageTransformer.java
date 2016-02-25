package com.afra55.trainingfirstapp.module.myanim.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by yangshuai on 2015/10/15 0015 15:04.
 */
public class DepthPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();

        if (position < -1){
            page.setAlpha(0);

        }else if(position <= 0){ // [-1,0]

            // 使用默认的滑动过渡当向左移动页面
            page.setAlpha(1);
            page.setTranslationX(0);
            page.setScaleX(1);
            page.setScaleY(1);

        }else if(position <= 1){ // (0,1]

            // 向右滑动时淡出页面
            page.setAlpha(1 - position);

            // 抵消默认的滑动过度
            page.setTranslationX(pageWidth * -position);

            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));

            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }else {
            page.setAlpha(0);
        }

    }
}
