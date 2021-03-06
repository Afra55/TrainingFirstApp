package com.afra55.trainingfirstapp.module.contacts;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseActivity;
import com.afra55.trainingfirstapp.base.BaseFragment;
import com.afra55.trainingfirstapp.module.contacts.fragment.ContactsDetailsFragment;
import com.afra55.trainingfirstapp.module.contacts.fragment.ContactsFragment;

public class ContactsActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener{

    private RelativeLayout content;
    private ContactsDetailsFragment contactsDetailsFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_contacts);
        setActionBarTitle(getResources().getString(R.string.contacts));

        if (savedInstanceState != null){
            return;
        }

        content = (RelativeLayout) findViewById(R.id.activity_contacts_content_rly);
        ContactsFragment contactsFragment = ContactsFragment.newInstance("contacts", "fragment");
        getSupportFragmentManager().beginTransaction().add(R.id.activity_contacts_content_rly, contactsFragment).commit();

        contactsDetailsFragment = ContactsDetailsFragment.newInstance("details", "fragment");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_contacts, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_contacts_add:
                showToast(getResources().getString(R.string.add_contact));
                showPopAddContact();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        if (contactsDetailsFragment == null){
            contactsDetailsFragment = ContactsDetailsFragment.newInstance("details", "fragment");
        }
        contactsDetailsFragment.setContactUri(uri);
    }

    @Override
    public void onFragmentInteraction(String message) {
        String[] msg = message.split(",");
        initDetailFragment();
        contactsDetailsFragment.setContactName(msg[ContactsFragment.CONATACT_NAME_INDEX]);
        contactsDetailsFragment.setLookupKey(msg[ContactsFragment.LOOKUP_KEY_INDEX]);
        contactsDetailsFragment.setContactId(msg[ContactsFragment.CONTACT_ID_INDEX]);
        contactsDetailsFragment.setThumbnailUri(msg[ContactsFragment.PHOTO_THUMBNAIL_URI_INDEX]);
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

    /* 弹出框 */
    private PopupWindow popupWindow;

    /* 名字 */
    private EditText addContactName;

    /* 邮箱 */
    private EditText addContactEmail;

    /* 电话号码 */
    private EditText addContactPhone;


    /* 显示弹出框用来填写联系人信息 */
    private void showPopAddContact(){
        if (popupWindow == null) {

            View view = LayoutInflater.from(this).inflate(R.layout.pop_contacts_add_view, null);
            popupWindow = new PopupWindow(view,
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(false);
            popupWindow.setAnimationStyle(R.style.pop_out_in);
            view.findViewById(R.id.pop_contact_ok_btn).setOnClickListener(this);
            view.findViewById(R.id.pop_contact_cancle_btn).setOnClickListener(this);
            addContactName = (EditText) view.findViewById(R.id.pop_contact_name_et);
            addContactEmail = (EditText) view.findViewById(R.id.pop_contact_email_et);
            addContactPhone = (EditText) view.findViewById(R.id.pop_contact_phone_et);
        }
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.pop_contact_cancle_btn:
                if (popupWindow != null && popupWindow.isShowing())
                    popupWindow.dismiss();
                break;
            case R.id.pop_contact_ok_btn:
                String name = addContactName.getText().toString().trim();
                if (name.equals("")) {
                    showToast("please input name~");
                    return;
                }

                String phone = addContactPhone.getText().toString().trim();
                if (phone.equals("")) {
                    showToast("please input phone~");
                    return;
                }

                String email = addContactEmail.getText().toString().trim();

                // 创建一个新的意图插入一个联系人
                Intent addContactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                addContactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE)
                        .putExtra(ContactsContract.Intents.Insert.NAME, name)
                        .putExtra(ContactsContract.Intents.Insert.PHONE, phone)
                        .putExtra(ContactsContract.Intents.Insert.EMAIL, email)
                        .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .putExtra("finishActivityOnSaveCompleted", true);
                startActivity(addContactIntent);

                popupWindow.dismiss();
                break;
        }
    }
}
