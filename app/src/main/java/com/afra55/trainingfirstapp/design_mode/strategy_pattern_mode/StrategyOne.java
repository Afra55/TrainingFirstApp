package com.afra55.trainingfirstapp.design_mode.strategy_pattern_mode;

import android.util.Log;

/**
 * Created by yangshuai in the 11:37 of 2016.01.29 .
 */
public class StrategyOne implements Strategy {
    @Override
    public int strategy() {
        Log.d("StrategyOne", "OneStrategy");
        /* 算法操作 */
        return 1;
    }
}
