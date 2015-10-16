package com.magus.trainingfirstapp.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.magus.trainingfirstapp.BaseActivity;
import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.adapter.SwipeMenuAdapter;
import com.magus.trainingfirstapp.swipemenu.SwipeMenu;
import com.magus.trainingfirstapp.swipemenu.SwipeMenuCreator;
import com.magus.trainingfirstapp.swipemenu.SwipeMenuItem;
import com.magus.trainingfirstapp.swipemenu.SwipeMenuListView;

public class SwipeMenuDemoActvity extends BaseActivity {

    private SwipeMenuAdapter adapter;
    private SwipeMenuListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_swipe_menu_demo_actvity);

        listView = (SwipeMenuListView) findViewById(R.id.swipe_menu_list_view);
        adapter = new SwipeMenuAdapter(this);

        adapter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus){

                    showToast(((EditText) v).getText().toString());
                }

            }
        });

        listView.setAdapter(adapter);
        listView.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        });
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Toast.makeText(SwipeMenuDemoActvity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                Toast.makeText(SwipeMenuDemoActvity.this, "index:" + index, Toast.LENGTH_SHORT).show();

                switch (index) {
                    case 0:
                        showToast("index 0");
                        adapter.getmAppList().remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        showToast("index 1");
                        break;
                }
                return false;
            }
        });
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
