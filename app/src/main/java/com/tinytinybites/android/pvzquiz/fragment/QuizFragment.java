package com.tinytinybites.android.pvzquiz.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.databinding.FragmentQuizBinding;
import com.tinytinybites.android.pvzquiz.intent.IntentUtil;
import com.tinytinybites.android.pvzquiz.model.Quiz;
import com.tinytinybites.android.pvzquiz.viewmodel.QuizViewModel;
import com.tinytinybites.android.pvzquiz.viewmodel.SessionViewModel;

public class QuizFragment extends Fragment {
    //Tag
    private static final String TAG = QuizFragment.class.getName();

    //Variables
    private FragmentQuizBinding mBinding;
    private QuizViewModel mQuizViewModel;
    private SessionViewModel mSessionViewModel;

    /**
     * Static constructor
     * @param quiz
     * @return
     */
    public static QuizFragment newInstance(Quiz quiz){
        QuizFragment fragment = new QuizFragment();

        //Supply arguments
        Bundle args = new Bundle();
        args.putParcelable(IntentUtil.QUIZ, quiz);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        Bundle arguments = getArguments();
        if(savedInstanceState != null &&
                savedInstanceState.containsKey(IntentUtil.QUIZ_VM)){
            mQuizViewModel = savedInstanceState.getParcelable(IntentUtil.QUIZ_VM);
        }else if(arguments != null) {
            //Check for parcelable from bundle
            Quiz quiz = arguments.getParcelable(IntentUtil.QUIZ);
            mQuizViewModel = new QuizViewModel(quiz);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(IntentUtil.QUIZ_VM, mQuizViewModel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Data binding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false);
        mSessionViewModel = new SessionViewModel();
        mBinding.setQuizViewModel(mQuizViewModel);
        mBinding.setSessionViewModel(mSessionViewModel); //Session VM is special as it binds only with singleton instance.

        //Bind click listeners
        bindClickListeners();

        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbindClickListeners();

        mQuizViewModel.destroy();
        mSessionViewModel.destroy();
        mBinding.unbind();
    }

    /**
     * Bind needed click listeners
     */
    private void bindClickListeners(){
        //Choices
        mBinding.choice1.choice.setOnClickListener(choiceClickListener);
        mBinding.choice2.choice.setOnClickListener(choiceClickListener);
        mBinding.choice3.choice.setOnClickListener(choiceClickListener);
        mBinding.choice4.choice.setOnClickListener(choiceClickListener);
        mBinding.choice5.choice.setOnClickListener(choiceClickListener);

        //Next button
        mBinding.next.setOnClickListener(nextClickListener);

        //Close button
        mBinding.close.setOnClickListener(closeClickListener);
    }

    /**
     * Unbind click listeners
     */
    private void unbindClickListeners(){
        //Choices
        mBinding.choice1.choice.setOnClickListener(null);
        mBinding.choice2.choice.setOnClickListener(null);
        mBinding.choice3.choice.setOnClickListener(null);
        mBinding.choice4.choice.setOnClickListener(null);
        mBinding.choice5.choice.setOnClickListener(null);

        //Next button
        mBinding.next.setOnClickListener(null);

        //Close button
        mBinding.close.setOnClickListener(null);
    }

    /**
     * Click listener for choices
     */
    private View.OnClickListener choiceClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            mBinding.getQuizViewModel().onChoiceSelected(v);
        }
    };

    /**
     * Click listener for next button
     */
    private View.OnClickListener nextClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(getActivity() != null &&
                    getActivity() instanceof QuizNavigation){
                ((QuizNavigation)getActivity()).OnNextQuiz();
            }
        }
    };

    /**
     * Click listener for close button
     */
    private View.OnClickListener closeClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(getActivity() != null &&
                    getActivity() instanceof QuizNavigation){
                ((QuizNavigation)getActivity()).OnCloseQuiz();
            }
        }
    };

    public interface QuizNavigation{
        void OnNextQuiz();
        void OnCloseQuiz();
    }
}
