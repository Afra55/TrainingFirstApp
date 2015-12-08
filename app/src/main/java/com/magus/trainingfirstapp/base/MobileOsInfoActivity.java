package com.magus.trainingfirstapp.base;

import android.os.Bundle;
import android.widget.TextView;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.utils.SystemInfoTools;

public class MobileOsInfoActivity extends BaseActivity {

    private TextView osInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_mobile_os_info);
        osInfo = (TextView) findViewById(R.id.base_mobile_os_info_tv);
        String systemInfoStr = SystemInfoTools.getBuildInfo();
        systemInfoStr += "-------------------------------------\r\n";
        systemInfoStr += SystemInfoTools.getSystemPropertyInfo();
        osInfo.setText(systemInfoStr);
    }
}
