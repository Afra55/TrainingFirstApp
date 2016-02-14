package com.magus.trainingfirstapp.module.alert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;

public class AlerTestActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_alert);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.alert_one_btn:
                one();
                break;

            case R.id.alert_two_btn:
                two();
                break;

            case R.id.alert_three_btn:
                three();
                break;

            case R.id.alert_four_btn:
                four();
                break;

            case R.id.alert_five_btn:
                five();
                break;
        }
    }

    private void five() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("右上角").setPositiveButton("ok", null).create();
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.END | Gravity.TOP);
        alertDialog.show();
    }

    private void four() {
        final String[] strings = new String[]{"以上", "难", "qq", "谁啊哥", "帅哥", "鸡鸡"};
        new AlertDialog.Builder(this).setTitle("Muti选择")
                .setMultiChoiceItems(strings, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        showToast(which + " " + isChecked);
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("" + which);
                    }
                }).show();
    }

    private void three() {
        final String[] strings = new String[]{"以上", "难", "qq", "谁啊哥", "帅哥", "鸡鸡"};
        new AlertDialog.Builder(this).setTitle("选择帅哥")
                .setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast(strings[which]);
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("你选择了睡个：" + which
                        );
                    }
                }).show();
    }

    private void two() {
        final String[] strings = new String[]{"以上", "难", "qq", "谁啊哥", "帅哥", "鸡鸡"};
        new AlertDialog.Builder(this).setTitle("选择")
                .setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast(strings[which]);
                    }
                }).show();
    }

    private void one() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("普通").setMessage("你好")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("ok");
                    }
                })
                .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("cancle");
                    }
                }).create();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.7f;
        window.setAttributes(layoutParams);
        alertDialog.show();

    }
}
