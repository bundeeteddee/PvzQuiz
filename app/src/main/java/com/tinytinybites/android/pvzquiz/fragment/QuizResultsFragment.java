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

        bindClickListeners();

        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbindClickListeners();

        mQuizResultsViewModel.destroy();
        mBinding.unbind();
    }

    /**
     * Bind needed click listeners
     */
    private void bindClickListeners(){
        //Done button
        mBinding.done.setOnClickListener(doneClickListener);

        //Play again button
        mBinding.playAgain.setOnClickListener(playAgainClickListener);
    }

    /**
     * Unbind click listeners
     */
    private void unbindClickListeners(){
        //Done button
        mBinding.done.setOnClickListener(null);

        //Play again button
        mBinding.playAgain.setOnClickListener(null);
    }

    /**
     * Click listener for done button
     */
    private View.OnClickListener doneClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(getActivity() != null &&
                    getActivity() instanceof QuizResultsNavigation){
                ((QuizResultsNavigation)getActivity()).OnDoneQuiz();
            }
        }
    };

    /**
     * Click listener for play again button
     */
    private View.OnClickListener playAgainClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(getActivity() != null &&
                    getActivity() instanceof QuizResultsNavigation){
                ((QuizResultsNavigation)getActivity()).OnPlayNewQuiz();
            }
        }
    };

    public interface QuizResultsNavigation{
        void OnDoneQuiz();
        void OnPlayNewQuiz();
    }
}
