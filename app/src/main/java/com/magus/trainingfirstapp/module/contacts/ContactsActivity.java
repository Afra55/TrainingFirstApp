package com.magus.trainingfirstapp.module.contacts;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.base.BaseFragment;

public class ContactsActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener{

    private ListView contactsListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_contacts);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentChildViewOnClick(View view) {

    }
}
