package com.tinytinybites.android.pvzquiz.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.databinding.FragmentQuizCompleteBinding;
import com.tinytinybites.android.pvzquiz.intent.IntentUtil;
import com.tinytinybites.android.pvzquiz.viewmodel.QuizResultsViewModel;

public class QuizResultsFragment extends Fragment {
    //Tag
    private static final String TAG = QuizResultsFragment.class.getName();

    //Variables
    private QuizResultsViewModel mQuizResultsViewModel;
    private FragmentQuizCompleteBinding mBinding;

    /**
     * Static constructor
     * @return
     */
    public static QuizResultsFragment newInstance(){
        QuizResultsFragment fragment = new QuizResultsFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if(savedInstanceState != null &&
                savedInstanceState.containsKey(IntentUtil.QUIZ_RESULT_VM)){
            mQuizResultsViewModel = savedInstanceState.getParcelable(IntentUtil.QUIZ_RESULT_VM);
        }else{
            mQuizResultsViewModel = new QuizResultsViewModel();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(IntentUtil.QUIZ_RESULT_VM, mQuizResultsViewModel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Data binding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_complete, container, false);
        mBinding.setQuizResultsViewModel(mQuizResultsViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mQuizResultsViewModel.destroy();
        mBinding.unbind();
    }
}
