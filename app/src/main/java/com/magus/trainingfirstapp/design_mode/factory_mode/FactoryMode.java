package com.magus.trainingfirstapp.design_mode.factory_mode;

/**
 * Created by yangshuai in the 16:04 of 2016.01.11 .
 */
public class FactoryMode {

    /**
     * 获取工厂实例
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T extends BaseFacory> T getInstatnce(Class<T> tClass) {
        BaseFacory baseFacory = null;
        try {
            baseFacory = (BaseFacory) Class.forName(tClass.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return (T) baseFacory;
    }

    /**
     * 使用方法
     */
    public void useFactorMode() {
        BaseFacory factoryMode = FactoryMode.getInstatnce(MyFacory.class);
        if (factoryMode != null) {
            factoryMode.start();
        }
    }
}
