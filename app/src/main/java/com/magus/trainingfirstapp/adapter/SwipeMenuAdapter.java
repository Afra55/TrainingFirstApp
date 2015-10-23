package com.magus.trainingfirstapp.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.magus.trainingfirstapp.R;

import java.util.List;

/**
 * Created by yangshuai on 2015/10/9 0009 10:44.
 */
public class SwipeMenuAdapter extends BaseAdapter {

    public List<ApplicationInfo> getmAppList() {
        return mAppList;
    }

    private List<ApplicationInfo> mAppList;
    private Context context;

    public void setOnFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    private View.OnFocusChangeListener onFocusChangeListener = null;

    public SwipeMenuAdapter(Context context){
        this.context = context;
        mAppList = context.getPackageManager().getInstalledApplications(0);
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context.getApplicationContext(),
                    R.layout.item_list_app, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ApplicationInfo item = getItem(position);
        holder.iv_icon.setImageDrawable(item.loadIcon(context.getPackageManager()));
        holder.tv_name.setText(item.loadLabel(context.getPackageManager()));
        holder.tv_sub.setText(item.packageName);
        return convertView;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_sub;

        public ViewHolder(View view) {
            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_sub = (TextView) view.findViewById(R.id.tv_sub);
            view.setTag(this);
        }
    }
}
