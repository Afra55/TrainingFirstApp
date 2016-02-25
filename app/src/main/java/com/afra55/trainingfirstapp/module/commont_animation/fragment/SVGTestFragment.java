package com.afra55.trainingfirstapp.module.commont_animation.fragment;


import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afra55.trainingfirstapp.R;
import com.afra55.trainingfirstapp.base.BaseFragment;

/**
 * SVG
 */
public class SVGTestFragment extends BaseFragment {

    public static SVGTestFragment newInstance(String arg1, String arg2) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, arg1);
        args.putString(ARG_PARAM2, arg2);
        SVGTestFragment fragment = new SVGTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SVGTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_svgtest, container, false);
        getChildViewForm(view);
        return initLayoutView(inflater, container, view);
    }

    @Override
    protected void initData() {
        hideFragmentTitleBar();
    }

    private void getChildViewForm(View view){
        try {
            if (view instanceof ViewGroup){
                ViewGroup g = (ViewGroup) view;
                for (int i = 0 ; i < g.getChildCount() ; i++) {
                    getChildViewForm(g.getChildAt(i));
                }
            }else if(view instanceof ImageView) {
                view.setOnClickListener(this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v instanceof ImageView) {
            animate((ImageView) v);
        }
    }

    private void animate(ImageView view) {
        Drawable drawable = view.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}
