package com.afra55.trainingfirstapp.module.myanim.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by yangshuai on 2015/10/15 0015 14:11.
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    /**
     * 属性变换应用于给定的页面
     *
     * @param page     转换应用到这个页面
     * @param position Position of page relative to the current front-and-center position of the pager. 0 is front and center. 1 is one full
     */
    @Override
    public void transformPage(View page, float position) {

        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1){  // [-无穷,-1)

            // 这一页是屏幕左边
            page.setAlpha(0);

        }else if (position <= 1){  // [-1,1]

            // 修改默认的滑动过渡到缩小页面

            // 缩放比例
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));

            // 获取垂直margin
            float verMargin = pageHeight * (1 - scaleFactor) / 2;

            // 获取水平margin
            float horzmargin = pageWidth * (1 - scaleFactor) / 2;

            // 在潜藏过程中，默认动画（屏幕滑动）是仍旧发生的，所以必须用负的X平移来抵消它
            if (position < 0){
                page.setTranslationX(horzmargin  - verMargin / 2);
            }else {
                page.setTranslationX(-horzmargin + verMargin / 2);
            }

            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        }else{  // (1,+无穷]
            page.setAlpha(0);
        }
    }
}
