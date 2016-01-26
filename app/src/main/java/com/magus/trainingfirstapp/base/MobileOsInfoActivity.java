package com.magus.trainingfirstapp.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.utils.DisplayUtil;
import com.magus.trainingfirstapp.utils.SystemInfoTools;

public class MobileOsInfoActivity extends BaseActivity {

    private final int END_ANIM = 1001;
    private long animDuration;
    private TextView osInfo;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case END_ANIM:
                    View v = (View) msg.obj;
                    DisplayUtil.circularRevealAnim(v, v.getWidth() / 2, v.getHeight() / 2, 0, (float) Math.hypot(v.getWidth(), v.getHeight()));
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_mobile_os_info);
        osInfo = (TextView) findViewById(R.id.base_mobile_os_info_tv);
        String systemInfoStr = SystemInfoTools.getBuildInfo(this);
        systemInfoStr += "-------------------------------------\r\n";
        systemInfoStr += SystemInfoTools.getSystemPropertyInfo(this);
        osInfo.setText(systemInfoStr);
        osInfo.setOnClickListener(this);

        animDuration = getResources().getInteger(android.R.integer.config_longAnimTime);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            DisplayUtil.circularRevealAnim(v, v.getWidth() / 2, v.getHeight() / 2, (float) Math.hypot(v.getWidth(), v.getHeight()), 0, animDuration);
            Message message = new Message();
            message.obj = v;
            message.what = END_ANIM;
            handler.sendMessageDelayed(message, animDuration);
        }
    }
}
