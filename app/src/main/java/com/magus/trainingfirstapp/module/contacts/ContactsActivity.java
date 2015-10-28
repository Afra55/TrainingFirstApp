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

        initDetailFragment();
        contactsDetailsFragment.setLookupKey(message);
    }

    private boolean detailfragmentIsShowing = false;
    private void initDetailFragment() {
        if (contactsDetailsFragment == null){
            contactsDetailsFragment = ContactsDetailsFragment.newInstance("details", "fragment");
        }
        getSupportFragmentManager()
                .beginTransaction()

                        // Replace the default fragment animations with animator resources representing
                        // rotations when switching to the back of the card, as well as animator
                        // resources representing rotations when flipping back to the front (e.g. when
                        // the system Back button is pressed).
                .setCustomAnimations(
                        R.anim.card_flip_right_in, R.anim.card_flip_right_out,
                        R.anim.card_flip_left_in, R.anim.card_flip_left_out)

                        // Replace any fragments currently in the container view with a fragment
                        // representing the next page (indicated by the just-incremented currentPage
                        // variable).
                .replace(R.id.activity_contacts_content_rly, contactsDetailsFragment)

                        // Add this transaction to the back stack, allowing users to press Back
                        // to get to the front of the card.
                .addToBackStack(null)

                        // Commit the transaction.
                .commit();
        detailfragmentIsShowing = true;
    }

    @Override
    public void onFragmentChildViewOnClick(View view) {

    }

    @Override
    public void onBackPressed() {

        if (detailfragmentIsShowing){
            getSupportFragmentManager().popBackStack();
            detailfragmentIsShowing = false;
            return;
        }
        super.onBackPressed();
    }
}
