package com.magus.trainingfirstapp.module.image_handle.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseFragment;

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
        img = (ImageView) view.findViewById(R.id.fragment_matrix_img);
        return initLayoutView(inflater, container, view);
    }

    @Override
    protected void initData() {
        hideFragmentTitleBar();
        orientationBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.loading);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_matrix_gray_btn:

                break;
            case R.id.fragment_matrix_reversal_btn:

                break;
            case R.id.fragment_matrix_reminiscence_btn:

                break;
            case R.id.fragment_matrix_discoloration_btn:

                break;
            case R.id.fragment_matrix_high_sat_btn:

                break;
        }
    }
}
