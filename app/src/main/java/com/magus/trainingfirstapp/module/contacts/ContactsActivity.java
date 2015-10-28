package com.magus.trainingfirstapp.module.contacts;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.base.BaseFragment;
import com.magus.trainingfirstapp.module.contacts.fragment.ContactsFragment;

public class ContactsActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener{

    private RelativeLayout content;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_contacts);

        if (savedInstanceState != null){
            return;
        }

        content = (RelativeLayout) findViewById(R.id.activity_contacts_content_rly);
        ContactsFragment contactsFragment = ContactsFragment.newInstance("contacts", "fragment");
        getSupportFragmentManager().beginTransaction().add(R.id.activity_contacts_content_rly, contactsFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentChildViewOnClick(View view) {

    }
}
