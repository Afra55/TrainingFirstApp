package com.magus.trainingfirstapp.module.contacts.fragment;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseFragment;

/**
 * Created by yangshuai in the 11:43 of 2015.10.22 .
 */
public class ContactsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener{

    /* 光标去查询的联系人的列名,
    * 由于Contacts.DISPLAY_NAME_PRIMARY需要在Android 3.0（API版本11）或者更高的版本才能使用，
    * 如果应用的minSdkVersion是10或者更低，会在eclipse中产生警告信息。
    * 为了关闭这个警告，我们可以在FROM_COLUMNS定义之前加上@SuppressLint("InlinedApi")注解。*/
    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };

    /* 光标列内容的布局Id */
    private final static int[] TO_IDS = {
            android.R.id.text1
    };

    /* 定义查询映射 */
    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };

    // _ID列的列索引
    private static final int CONTACT_ID_INDEX = 0;
    // LOOKUP_KEY列的列索引
    private static final int LOOKUP_KEY_INDEX = 1;

    /* 定义了文本表达, 去告诉provider我们需要的数据列和想要的值,
    * 对于文本表达式，定义一个常量，列出所有搜索到的列。尽管这个表达式可以包含变量值，但是建议用"?"占位符来替代这个值。
    * 在搜索的时候，占位符里的值会被数组里的值所取代。使用"?"占位符确保了搜索条件是由绑定产生而不是由SQL编译产生。
    * 这个方法消除了恶意SQL注入的可能*/
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";

    /* 定义了一个搜索字符串的变量 */
    private String mSearchString = "yangshuai";

    /* 定义了数组保存值用来替换 "?" */
    private String[] mSelectionArgs = { mSearchString };

    private ListView mContactsListView;

    /* 联系人Id */
    private long mContactId;

    /* 联系人的 LOOKUP_KEY */
    private String mContactKey;

    /* 选定联系人的内容 Uri */
    private Uri mContactUri;

    /* 光标的查询结果绑定到 ListView */
    private SimpleCursorAdapter mCursorAdapter;

    public static ContactsFragment newInstance(String param1, String param2) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        mContactsListView = (ListView) view.findViewById(R.id.fragment_contacts_listView);
        return initLayoutView(inflater, container, view);
    }


    @Override
    protected void initData() {
        hideFragmentTitle();

        mCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.fragment_contacts_list_item, null, FROM_COLUMNS, TO_IDS, 0);
        mContactsListView.setAdapter(mCursorAdapter);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContactsListView.setOnItemClickListener(this);

        /* 初始化加载程序 */
        getLoaderManager().initLoader(0, null, this);

    }

    /* 这个方法是在调用initLoader()后马上被loader框架调用,
    * 设置搜索字符串模式。为了让一个字符串符合一个模式，插入"%"字符代表0个或多个字符，插入"_"代表一个字符,
     * 例如，模式%Jefferson%将会匹配“Thomas Jefferson”和“Jefferson Davis”。*/
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        /* 存储搜索的字符串 */
        mSelectionArgs[0] = "%" + mSearchString + "%";
        return new CursorLoader(getActivity(), ContactsContract.Contacts.CONTENT_URI, PROJECTION, SELECTION, mSelectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        /* 在listView 中展示结果 */
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        /* 删除现有的光标 */
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Cursor cursor = ((SimpleCursorAdapter)parent.getAdapter()).getCursor();
        cursor.moveToPosition(position);
        mContactId = cursor.getLong(CONTACT_ID_INDEX);
        mContactKey = cursor.getString(LOOKUP_KEY_INDEX);
        mContactUri = ContactsContract.Contacts.getLookupUri(mContactId, mContactKey);

        /* my test */

    }
}
