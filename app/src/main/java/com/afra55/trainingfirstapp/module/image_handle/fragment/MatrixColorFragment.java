package com.afra55.trainingfirstapp.module.image_handle.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseFragment;

public class MatrixColorFragment extends BaseFragment {

    private ImageView img;
    private Bitmap orientationBitmap;

    public static MatrixColorFragment newInstance(String param1, String param2) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        MatrixColorFragment fragment = new MatrixColorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MatrixColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matrix_color, container, false);
        img = (ImageView) view.findViewById(R.id.fragment_shader_img);
        return initLayoutView(inflater, container, view);
    }

    @Override
    protected void initData() {
        hideFragmentTitleBar();
        orientationBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_xfermode_roundcorner_btn:
                colorEfectMethod(ColorMatrixUtils.grayColorMatrix());
                break;
            case R.id.fragment_xfermode_guaguale_btn:
                colorEfectMethod(ColorMatrixUtils.reversalMatrix());
                break;
            case R.id.fragment_matrix_reminiscence_btn:
                colorEfectMethod(ColorMatrixUtils.reminiscenceMatrix());
                break;
            case R.id.fragment_matrix_discoloration_btn:
                colorEfectMethod(ColorMatrixUtils.discolorationMatrix());
                break;
            case R.id.fragment_matrix_high_sat_btn:
                colorEfectMethod(ColorMatrixUtils.highSatMatrix());
                break;
        }
    }

    private void colorEfectMethod(float color[]) {
        Bitmap bitmap = Bitmap.createBitmap(orientationBitmap.getWidth(), orientationBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(color);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(orientationBitmap, 0, 0, paint);
        img.setImageBitmap(bitmap);
    }

    @Override
    public void reset() {
        super.reset();
        img.setImageBitmap(orientationBitmap);
    }

}
