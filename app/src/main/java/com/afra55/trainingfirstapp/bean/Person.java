package com.afra55.trainingfirstapp.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by yangshuai in the 16:13 of 2015.12.08 .
 * Bomb 初始化测试
 */
public class Person  extends BmobObject {
    private String name;
    private String address;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}