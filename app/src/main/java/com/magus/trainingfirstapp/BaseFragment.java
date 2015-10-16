package com.magus.trainingfirstapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yangshuai on 2015/10/14 0014 16:58.
 * 
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{



    public String FragmentTitle = "BaseFragment";

    public String getFragmentTitle() {
        return FragmentTitle;
    }

    public void setFragmentTitle(String fragmentTitle) {
        FragmentTitle = fragmentTitle;
    }



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    protected static final String ARG_PARAM1 = "param1";
    protected static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    protected String mParam1;
    protected String mParam2;



    protected OnFragmentInteractionListener mListener;


    private Button fragmentActionBarLeftBtn;
    private Button fragmentActionBarRightBtn;
    private TextView fragmentMiddleTv;
    private LinearLayout fragmentContentFatherLly;
    private RelativeLayout fragmentActionBarRlt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * onCreateView必调初始化方法
     * @param inflater
     * @param container
     * @param subView
     * @return
     */
    protected View initLayoutView(LayoutInflater inflater, ViewGroup container, int subView){
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        fragmentActionBarLeftBtn = (Button) view.findViewById(R.id.fragment_actionBar_left_btn);
        fragmentActionBarRightBtn = (Button) view.findViewById(R.id.fragment_actionBar_right_btn);
        fragmentMiddleTv = (TextView) view.findViewById(R.id.fragment_actionBar_title_tv);
        fragmentContentFatherLly = (LinearLayout) view.findViewById(R.id.fragment_content_father_lly);
        fragmentActionBarRlt = (RelativeLayout) view.findViewById(R.id.fragment_action_bar_rlt);
        fragmentContentFatherLly.addView(LayoutInflater.from(getContext()).inflate(subView, null));
        getChildViewForm(getView());

        return view;
    }
    /**
     * onCreateView必调初始化方法
     * @param inflater
     * @param container
     * @param subView
     * @return
     */
    protected View initLayoutView(LayoutInflater inflater, ViewGroup container,
                                   View subView){
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        fragmentActionBarLeftBtn = (Button) view.findViewById(R.id.fragment_actionBar_left_btn);
        fragmentActionBarRightBtn = (Button) view.findViewById(R.id.fragment_actionBar_right_btn);
        fragmentMiddleTv = (TextView) view.findViewById(R.id.fragment_actionBar_title_tv);
        fragmentContentFatherLly = (LinearLayout) view.findViewById(R.id.fragment_content_father_lly);
        fragmentActionBarRlt = (RelativeLayout) view.findViewById(R.id.fragment_action_bar_rlt);
        fragmentContentFatherLly.addView(subView);
        getChildViewForm(fragmentActionBarLeftBtn);
        getChildViewForm(fragmentActionBarRightBtn);
        initData();
        return view;
    }

    protected abstract void initData();

    protected void setRightFragmentTitleBtnText(String s){
        fragmentActionBarRightBtn.setText(s);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_actionBar_left_btn:
                showToast("left");
                break;
            case R.id.fragment_actionBar_right_btn:
                showToast("right");
                break;
        }
    }

    private Toast toast = null;
    protected void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(getContext(),message,Toast.LENGTH_SHORT);
        }else{
            toast.setText(message);
        }
        toast.show();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
        public void onFragmentChildViewOnClick(View view);
    }

    protected void showFragmentTitle(){
        fragmentActionBarRlt.setVisibility(View.VISIBLE);
    }

    protected void hideFragmentTitle(){
        fragmentActionBarRlt.setVisibility(View.GONE);
    }

    protected void setFragmentTitleSting(String titleText){
        fragmentMiddleTv.setText(titleText);
    }

    private void getChildViewForm(View view){
        try {
            if (view instanceof ViewGroup){
                ViewGroup g = (ViewGroup) view;
                for (int i = 0 ; i < g.getChildCount() ; i++) {
                    getChildViewForm(g.getChildAt(i));
                }
            }else if(view instanceof Button){
                view.setOnClickListener(this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean onBackPressed(){
        return true;
    }
}
