package com.afra55.trainingfirstapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.module.contacts.fragment.ContactsFragment;
import com.afra55.trainingfirstapp.utils.CommontUtils;

/**
 * Created by yangshuai in the 9:54 of 2015.10.29 .
 */
public class ContactsAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public ContactsAdapter(Context context) {
        super(context, null, 0);
        mInflater = LayoutInflater.from(context);
    }

    private class ViewHolder{
        TextView displayName;
        QuickContactBadge quickContactBadge;
    }

    /* 新建布局 */
    /* 填充一个View对象去持有列表项布局。在重写这个方法的过程中，需要保存这个布局的子View的handles，包括QuickContactBadge的handles。
    通过采用这种方法，避免了每次在填充新的布局时都去获取子View的handles。 我们必须重写这个方法以便能够获取每个子View对象的handles。
    这种方法允许我们控制这些子View对象在CursorAdapter.bindView()方法中的绑定 */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.fragment_contacts_list_item, null, false);
        final ViewHolder viewHolder = new ViewHolder();
        viewHolder.displayName = (TextView) view.findViewById(R.id.fragment_contacts_list_item_displayname_tv);
        viewHolder.quickContactBadge = (QuickContactBadge) view.findViewById(R.id.fragment_contacts_list_item_quickcontact);
        view.setTag(viewHolder);
        return view;
    }

    /* 绑定数据到布局中 */
    /* 将数据从当前Cursor行绑定到列表项布局的子View对象中。
    必须重写这个方法以便能够将联系人的URI和缩略图信息绑定到QuickContactBadge。
    这个方法的默认实现仅仅允许在数据列和View之间的一对一映射。 */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        final String photoData = cursor.getString(ContactsFragment.PHOTO_THUMBNAIL_URI_INDEX);
        final String displayName = cursor.getString(ContactsFragment.CONATACT_NAME_INDEX);
        viewHolder.displayName.setText(displayName);
        final Uri contactUri = ContactsContract.Contacts.getLookupUri(
                cursor.getLong(ContactsFragment.CONTACT_ID_INDEX),
                cursor.getString(ContactsFragment.LOOKUP_KEY_INDEX));
        viewHolder.quickContactBadge.assignContactUri(contactUri);
        if (photoData != null) {
            Bitmap thumbnailBitmap = CommontUtils.loadContactPhotoThumbnail((Activity) context, photoData);
            viewHolder.quickContactBadge.setImageBitmap(thumbnailBitmap);
        }else {
            viewHolder.quickContactBadge.setImageResource(R.mipmap.ic_launcher);
        }

    }

}
