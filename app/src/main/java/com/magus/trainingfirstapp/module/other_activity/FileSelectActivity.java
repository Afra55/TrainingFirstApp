package com.magus.trainingfirstapp.module.other_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.adapter.FileSelectorAdapter;

import java.io.File;
import java.io.IOException;

public class FileSelectActivity extends BaseActivity {

    private Intent mResultIntent;
    private File mPrivateRootDir;
    private File mImageDir;
    File[] mImageFiles;
    private ListView file_listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_file_select);

        file_listview = (ListView) findViewById(R.id.file_listview);

        mResultIntent = new Intent("com.magus.trainingfirstapp.ACTION_RETURN_FILE");
//        mPrivateRootDir = getFilesDir();
        mPrivateRootDir = Environment.getExternalStorageDirectory();
        mImageDir = new File(mPrivateRootDir, "images");
        if (!mImageDir.exists()){
            try {
                mImageDir.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mImageDir.getPath();
        mImageFiles = mImageDir.listFiles();
        setResult(Activity.RESULT_CANCELED, null);

        if (mImageFiles == null){
            return;
        }
        FileSelectorAdapter adapter = new FileSelectorAdapter(this, mImageFiles);
        file_listview.setAdapter(adapter);

    }
}
