package com.magus.trainingfirstapp.module.contacts;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.base.BaseFragment;
import com.magus.trainingfirstapp.module.contacts.fragment.ContactsDetailsFragment;
import com.magus.trainingfirstapp.module.contacts.fragment.ContactsFragment;

public class ContactsActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener{

    private RelativeLayout content;
    private ContactsDetailsFragment contactsDetailsFragment;


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

        contactsDetailsFragment = ContactsDetailsFragment.newInstance("details", "fragment");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onFragmentInteraction(String message) {
        String[] msg = message.split(",");
        initDetailFragment();
        contactsDetailsFragment.setContactName(msg[0]);
        contactsDetailsFragment.setLookupKey(msg[1]);
    }

    private boolean detailfragmentIsShowing = false;
    private void initDetailFragment() {
        if (contactsDetailsFragment == null){
            contactsDetailsFragment = ContactsDetailsFragment.newInstance("details", "fragment");
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_contacts_content_rly, contactsDetailsFragment)
                .addToBackStack(null)
                .commit();
        detailfragmentIsShowing = true;
    }

    @Override
    public void onFragmentChildViewOnClick(View view) {

    }


    @Override
    public void finish() {
        if (detailfragmentIsShowing){
            getSupportFragmentManager().popBackStack();
            detailfragmentIsShowing = false;
            return;
        }
        super.finish();
    }
}
