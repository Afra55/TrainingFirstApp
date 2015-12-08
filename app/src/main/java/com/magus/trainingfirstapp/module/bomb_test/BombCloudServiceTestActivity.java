package com.magus.trainingfirstapp.module.bomb_test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.bean.Person;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

public class BombCloudServiceTestActivity extends BaseActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //查找Person表里面id为6b6c11c537的数据
            BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
            bmobQuery.getObject(BombCloudServiceTestActivity.this, "091414edad", new GetListener<Person>() {
                @Override
                public void onSuccess(Person object) {
                    // TODO Auto-generated method stub
                    showToast("查询成功");
                }

                @Override
                public void onFailure(int code, String msg) {
                    // TODO Auto-generated method stub
                    showToast("查询失败：" + msg);
                }
            });
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_bomb_cloud_service_test);
//        final Person p2 = new Person();
//        p2.setName("lucky");
//        p2.setAddress("北京海淀");
//        p2.save(this, new SaveListener() {
//            @Override
//            public void onSuccess() {
//                // TODO Auto-generated method stub
//                showToast("添加数据成功，返回objectId为：" + p2.getObjectId());
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                // TODO Auto-generated method stub
//                showToast("创建数据失败：" + msg);
//            }
//        });


        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.getObject(BombCloudServiceTestActivity.this, "091414edad", new GetListener<Person>() {
            @Override
            public void onSuccess(Person object) {
                // TODO Auto-generated method stub
                showToast("查询成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                showToast("查询失败：" + msg);
            }
        });
    }
}
