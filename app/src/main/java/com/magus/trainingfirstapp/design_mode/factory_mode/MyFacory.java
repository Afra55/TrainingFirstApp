package com.magus.trainingfirstapp.design_mode.factory_mode;

import com.magus.trainingfirstapp.utils.Log;

/**
 * Created by yangshuai in the 16:09 of 2016.01.11 .
 * 具体工厂类
 */
public class MyFacory extends BaseFacory {
    @Override
    public void start() {
        Log.d("MyFactory", "start");
    }
}
