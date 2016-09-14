package com.tinytinybites.android.pvzquiz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.activity.QuizActivity;


public class DashboardFragment extends Fragment {
    //Tag
    private static final String TAG = DashboardFragment.class.getName();

    //Variables
    @BindView(R.id.startButton) CardView mStartButton;
    private Unbinder mUnbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //For element binding
        mUnbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.startButton)
    public void startButtonClicked() {
        //TODO:
        getActivity().startActivity(new Intent(getActivity(), QuizActivity.class));
    }



}
