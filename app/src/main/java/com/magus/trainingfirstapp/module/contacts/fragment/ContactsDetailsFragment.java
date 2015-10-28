package com.magus.trainingfirstapp.module.contacts.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseFragment;

/**
 * 联系人详情
 */
public class ContactsDetailsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    /* 使用了ContactsContract.Data类中定义的列名字，去获取ContactsContract.Data表中一行的所有数据列 */
    private static final String[] PROJECTION =
            new String[]{
                    ContactsContract.Contacts.Data._ID,
                    ContactsContract.Contacts.Data.MIMETYPE,
                    ContactsContract.Contacts.Data.DATA1,
                    ContactsContract.Contacts.Data.DATA2,
                    ContactsContract.Contacts.Data.DATA3,
                    ContactsContract.Contacts.Data.DATA4,
                    ContactsContract.Contacts.Data.DATA5,
                    ContactsContract.Contacts.Data.DATA6,
                    ContactsContract.Contacts.Data.DATA7,
                    ContactsContract.Contacts.Data.DATA8,
                    ContactsContract.Contacts.Data.DATA9,
                    ContactsContract.Contacts.Data.DATA10,
                    ContactsContract.Contacts.Data.DATA11,
                    ContactsContract.Contacts.Data.DATA12,
                    ContactsContract.Contacts.Data.DATA13,
                    ContactsContract.Contacts.Data.DATA14,
                    ContactsContract.Contacts.Data.DATA15,
            };

    /* 默认的筛选条件,
    * 在查询选择表达式中使用 “?”占位符，确保了搜索是由绑定生成而不是由SQL编译生成。这种方法消除了恶意SQL注入的可能性*/
    private static final String SELECTION =
            ContactsContract.Data.LOOKUP_KEY + " = ?";

    /* 定义数组以保存搜索条件 */
    private String[] mSelectionArgs = { "" };

    private String mLookupKey;

    /**
     * 设置 mLookupKey
     * @param message
     */
    public void setLookupKey(String message){
        mLookupKey = message;
    }

    private String mContactName;

    public void setContactName(String mContactName) {
        this.mContactName = mContactName;
    }

    private String mContactId;

    public void setContactId(String mContactId){
        this.mContactId = mContactId;
    }

    private Uri mContactUri;
    public void setContactUri(Uri mContactUri){
        this.mContactUri = mContactUri;
    }

    /* 定义一个字符串,指定MIME类型的排序顺序,
     * 可以让特定数据类型的所有行排列在一起 */
    private static final String SORT_ORDER = ContactsContract.Contacts.Data.MIMETYPE;

    /* 定义了一个常量,标识加载程序 */
    private final int DETAILS_QUERY_ID = 1;

    /* 存储结果 */
    private Cursor resultCursor;

    private TextView resultShow;

    public static ContactsDetailsFragment newInstance(String param1, String param2) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        ContactsDetailsFragment fragment = new ContactsDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ContactsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_details, container, false);
        resultShow = (TextView) view.findViewById(R.id.fragment_contacts_details_tv);
        view.findViewById(R.id.fragment_contacts_details_edit_btn).setOnClickListener(this);
        return initLayoutView(inflater, container, view);
    }

    @Override
    protected void initData() {
        hideFragmentTitleBar();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(DETAILS_QUERY_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (id != DETAILS_QUERY_ID) return null;

        mSelectionArgs[0] = mLookupKey;
        return new android.support.v4.content.CursorLoader(getActivity(), ContactsContract.Data.CONTENT_URI, PROJECTION, SELECTION, mSelectionArgs, SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() != DETAILS_QUERY_ID) return;
        /* 获取到结果 */
        resultCursor =data;
        notifyResult(resultCursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() != DETAILS_QUERY_ID) return;
        /* remove */
        resultCursor = null;
    }

    private void notifyResult(Cursor data){

        String showText = mContactName + "\n";
        Cursor cursor = data;

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            showText += cursor.getString(i) + "\n";
        }

        resultShow.setText(showText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_contacts_details_edit_btn:       // 编辑联系人
                Intent editIntent = new Intent(Intent.ACTION_EDIT);
                editIntent.setDataAndType(mContactUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE);

                /* 在Android 4.0（API版本14）和更高的版本，Contacts应用中的一个问题会导致错误的页面导航。
                我们的应用发送一个编辑联系人的Intent到Contacts应用，用户编辑并保存这个联系人，当用户点击Back键的时候会看到联系人列表页面。
                用户需要点击最近使用的应用，然后选择我们的应用，才能返回到我们自己的应用. */
                editIntent.putExtra("finishActivityOnSaveCompleted", true);
                startActivity(editIntent);
                break;
        }
        super.onClick(v);
    }
}
