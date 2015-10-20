package com.magus.trainingfirstapp.base;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.EditText;

import com.magus.trainingfirstapp.R;

/**
 * Created by yangshuai on 2015/9/30 0030 16:56.
 */
public class TrainingFirstActivityTest extends ActivityInstrumentationTestCase2<TrainingFirstActivity> {

    private TrainingFirstActivity trainingFirstActivity;
    private Button openOtherActivityBtn;
    private EditText messageEt;

    public TrainingFirstActivityTest(){
        super(TrainingFirstActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        trainingFirstActivity = getActivity();
        openOtherActivityBtn = (Button) trainingFirstActivity.findViewById(R.id.first_open_new_activity_btn);
        messageEt = (EditText) trainingFirstActivity.findViewById(R.id.message_et);
    }

    /**
     * 测试前提
     */
    @MediumTest
    public void testPreconditions() {
        assertNotNull("trainingFirstActivity is null", trainingFirstActivity);
        assertNotNull("openOtherActivityBtn is null", openOtherActivityBtn);
        assertNotNull("messageEt is null", messageEt);
    }

    @MediumTest
    public void testInsertTextToMessageEt(){
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                messageEt.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("ni hao a");
        getInstrumentation().waitForIdleSync();
    }




}
