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
import com.tinytinybites.android.pvzquiz.session.GameSession;
import com.tinytinybites.android.pvzquiz.viewmodel.QuizViewModel;
import com.tinytinybites.android.pvzquiz.viewmodel.SessionViewModel;

public class QuizFragment extends Fragment {
    //Tag
    private static final String TAG = QuizFragment.class.getName();

    //Variables
    private Quiz mQuiz;
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

        Bundle arguments = getArguments();
        if (arguments != null) {
            mQuiz = arguments.getParcelable(IntentUtil.QUIZ);
        }else if(savedInstanceState == null ||
                !savedInstanceState.containsKey(IntentUtil.QUIZ)) {
            //nothing from saved instance, attempt to get it from intent bundle
            Bundle extras = getActivity().getIntent().getExtras();
            if(extras != null){
                if(extras.containsKey(IntentUtil.QUIZ)){
                    mQuiz = extras.getParcelable(IntentUtil.QUIZ);
                }
            }
        }else {
            mQuiz = savedInstanceState.getParcelable(IntentUtil.QUIZ);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(IntentUtil.QUIZ, mQuiz);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Data binding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false);
        mQuizViewModel = new QuizViewModel(getActivity(), GameSession.getInstance().getCurrentQuiz(), (QuizNavigation) getActivity());
        mSessionViewModel = new SessionViewModel(getActivity());
        mBinding.setQuizViewModel(mQuizViewModel);
        mBinding.setSessionViewModel(mSessionViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mQuizViewModel.destroy();
        mSessionViewModel.destroy();
        mBinding.unbind();
    }

    public interface QuizNavigation{
        void OnNextQuiz();
        void OnCloseQuiz();
    }
}
