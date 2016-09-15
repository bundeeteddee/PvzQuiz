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
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Data binding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_complete, container, false);
        mQuizResultsViewModel = new QuizResultsViewModel(getActivity(), (QuizResultsNavigation) getActivity());
        mBinding.setQuizResultsViewModel(mQuizResultsViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mQuizResultsViewModel.destroy();
        mBinding.unbind();
    }

    public interface QuizResultsNavigation{
        void OnDoneQuiz();
        void OnPlayNewQuiz();
    }
}
