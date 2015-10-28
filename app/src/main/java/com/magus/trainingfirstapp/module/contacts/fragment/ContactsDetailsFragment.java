package com.magus.trainingfirstapp.module.contacts.fragment;


import android.database.Cursor;
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
                    ContactsContract.Contacts.Data.DATA15
            };

    /* 默认的筛选条件,
    * 在查询选择表达式中使用 “?”占位符，确保了搜索是由绑定生成而不是由SQL编译生成。这种方法消除了恶意SQL注入的可能性*/
    private static final String SELECTION = ContactsContract.Data.LOOKUP_KEY + " = ?";

    /* 定义数组以保存搜索条件 */
    private String[] mSelectionArgs = { "" };

    private String mLookupKey;

    /**
     * 设置 mLookupKey
     * @param message
     */
    public void setLookupKey(String message){
        mLookupKey = message;
        getLoaderManager().restartLoader(DETAILS_QUERY_ID, null, this);
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
        return initLayoutView(inflater, container, view);
    }

    @Override
    protected void initData() {
        hideFragmentTitle();
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

        String showText = "";
        Cursor cursor = data;

        for (int i = 0; i < PROJECTION.length; i++){
            cursor.moveToPosition(i);
            showText += cursor.getString(i);
        }

        resultShow.setText(showText);
    }
}
