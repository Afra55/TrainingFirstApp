package com.magus.trainingfirstapp.design_mode.strategy_pattern_mode;

import android.util.Log;

/**
 * Created by yangshuai in the 11:37 of 2016.01.29 .
 */
public class StrategyTwo implements Strategy {
    @Override
    public int strategy() {
        Log.d("StrategyTwo", "TwoStrategy");
        /* 算法操作 */
        return 2;
    }
}
