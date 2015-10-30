package com.magus.trainingfirstapp.module.other_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.R;


import java.util.ArrayList;


public class OtherActivity extends BaseActivity {


    private TextView hellowTv;
    private LinearLayout firstFatherContentRlt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);

        }


        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        scrollView.addView(linearLayout);

        Intent intent = getIntent();

        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null){
            if ("text/plain".equals(type)){
                TextView textView = new TextView(this);
                textView.setTextSize(40);
                textView.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
                linearLayout.addView(textView);
            }else if (type.startsWith("image/")){
                Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (imageUri != null){
                    ImageView imageView = new ImageView(this);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setImageURI(imageUri);
                    linearLayout.addView(imageView);
                }
            }

        }else if(Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null){
            if (type.startsWith("image/")){
                ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                if (imageUris != null){
                    for (int i = 0; i < imageUris.size(); i++){
                        ImageView imageView = new ImageView(this);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        imageView.setImageURI(imageUris.get(i));
                        linearLayout.addView(imageView);
                    }
                }
            }
        }

        setContentLayout(scrollView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_other, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // 这个activity不是这个app任务的一部分, 所以当向上导航时创建
                    // 用合成后退栈(synthesized back stack)创建一个新任务。
                    TaskStackBuilder.create(this)
                            // 添加这个activity的所有父activity到后退栈中
                            .addNextIntentWithParentStack(upIntent)
                                    // 向上导航到最近的一个父activity
                            .startActivities();
                } else {
                    // 这个activity是这个app任务的一部分, 所以
                    // 向上导航至逻辑父activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
