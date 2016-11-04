package com.afra55.trainingfirstapp.module.snackbar;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseActivity;

public class SnackbarActivity extends BaseActivity {

    private CoordinatorLayout mSnackbarContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_snackbar);

        mSnackbarContainer = (CoordinatorLayout) findViewById(R.id.activity_snackbar);

        findViewById(R.id.snackbar_1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackerbar1();
            }
        });
    }

    private void showSnackerbar1() {
        Snackbar.make(
                mSnackbarContainer
                , "xxx", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(mSnackbarContainer, "Message is restored!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                })

                .show();
    }
}
