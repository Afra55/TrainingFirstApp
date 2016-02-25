package com.afra55.trainingfirstapp.module.image_handle.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseFragment;
import com.afra55.trainingfirstapp.module.image_handle.view.XfermodeView;

public class PorterDuffXfermodeTestFragment extends BaseFragment {

    private ImageView imageView;
    private Bitmap oldBitmap;
    private XfermodeView guagualeView;

    public static PorterDuffXfermodeTestFragment newInstance(String arg1, String arg2) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, arg1);
        args.putString(ARG_PARAM2, arg2);
        PorterDuffXfermodeTestFragment fragment = new PorterDuffXfermodeTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void reset() {
        super.reset();
        if (guagualeView.isShown()) {
            guagualeView.reset();
        } else
            imageView.setImageBitmap(oldBitmap);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_porter_duff_xfermode_test, null);
        imageView = (ImageView) view.findViewById(R.id.fragment_xfermode_img);
        guagualeView = (XfermodeView) view.findViewById(R.id.fragment_xfermode_guaguale_view);
        return initLayoutView(inflater, container, view);
    }

    @Override
    protected void initData() {
        hideFragmentTitleBar();
        oldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_xfermode_roundcorner_btn:
                imageView.setVisibility(View.VISIBLE);
                guagualeView.setVisibility(View.GONE);
                roundCorner();
                break;
            case R.id.fragment_xfermode_guaguale_btn:
                imageView.setVisibility(View.GONE);
                guagualeView.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void roundCorner() {
        Bitmap bitmap = Bitmap.createBitmap(oldBitmap.getWidth(), oldBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(0, 0,
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    80, 80,
                    paint);
        } else {
            Toast.makeText(getContext(), "仅支持api 21及以上的系统", Toast.LENGTH_SHORT).show();
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(oldBitmap, 0, 0, paint);
        imageView.setImageBitmap(bitmap);
    }
}
