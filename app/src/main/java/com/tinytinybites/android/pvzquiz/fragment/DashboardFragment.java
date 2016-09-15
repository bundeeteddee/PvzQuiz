package com.tinytinybites.android.pvzquiz.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.databinding.FragmentDashboardBinding;
import com.tinytinybites.android.pvzquiz.viewmodel.DashboardViewModel;


public class DashboardFragment extends Fragment {
    //Tag
    private static final String TAG = DashboardFragment.class.getName();

    //Variables
    private FragmentDashboardBinding mBinding;
    private DashboardViewModel mDashboardViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Data binding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        mDashboardViewModel = new DashboardViewModel(getActivity(), (DashboardNavigation) getActivity());
        mBinding.setDashboardViewModel(mDashboardViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface DashboardNavigation{
        void OnStartNewGameClicked();
    }

}
