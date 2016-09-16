package com.tinytinybites.android.pvzquiz.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.databinding.FragmentDashboardBinding;
import com.tinytinybites.android.pvzquiz.intent.IntentUtil;
import com.tinytinybites.android.pvzquiz.viewmodel.DashboardViewModel;


public class DashboardFragment extends Fragment {
    //Tag
    private static final String TAG = DashboardFragment.class.getName();

    //Variables
    private FragmentDashboardBinding mBinding;
    private DashboardViewModel mDashboardViewModel; //Not really needed now as there are no real state info needed

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if(savedInstanceState != null &&
                savedInstanceState.containsKey(IntentUtil.DASHBOARD_VM)){
            mDashboardViewModel = savedInstanceState.getParcelable(IntentUtil.DASHBOARD_VM);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(IntentUtil.DASHBOARD_VM, mDashboardViewModel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Data binding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        mDashboardViewModel = new DashboardViewModel();
        mBinding.setDashboardViewModel(mDashboardViewModel);

        bindClickListeners();

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbindClickListeners();

        mBinding.unbind();
    }

    /**
     * Bind needed click listeners
     */
    private void bindClickListeners(){
        //start button
        mBinding.startButton.setOnClickListener(startGameClickListener);
    }

    /**
     * Unbind click listeners
     */
    private void unbindClickListeners() {
        //Choices
        mBinding.startButton.setOnClickListener(null);
    }

    /**
     * Click listener for start button
     */
    private View.OnClickListener startGameClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(getActivity() != null &&
                    (getActivity()) instanceof DashboardNavigation){
                ((DashboardNavigation)getActivity()).OnStartNewGameClicked();
            }
        }
    };

    public interface DashboardNavigation{
        void OnStartNewGameClicked();
    }

}
