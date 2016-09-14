package com.tinytinybites.android.pvzquiz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.intent.IntentUtil;
import com.tinytinybites.android.pvzquiz.model.Quiz;

public class QuizFragment extends Fragment {
    //Tag
    private static final String TAG = QuizFragment.class.getName();

    //Elements
    @BindView(R.id.close) ImageView mClose;
    @BindView(R.id.quiz_summary) TextView mQuizSummary;
    @BindView(R.id.next) TextView mNext;
    @BindView(R.id.quiz_category) TextView mQuizCategory;
    @BindView(R.id.question_content) TextView mQuestion;
    @BindView(R.id.choice1) CardView mFirstChoice;
    @BindView(R.id.choice2) CardView mSecondChoice;
    @BindView(R.id.choice3) CardView mThirdChoice;
    @BindView(R.id.choice4) CardView mFourthChoice;
    @BindView(R.id.choice5) CardView mFifthChoice;

    //Variables
    private Quiz mQuiz;
    private Unbinder mUnbinder;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        //Butterknife
        mUnbinder = ButterKnife.bind(this, view);

        //Bind
        mQuestion.setText(mQuiz.getQuestion().getContent());
        mQuizCategory.setText(mQuiz.getQuestion().getCategory());
        TextView choice1 = ButterKnife.findById(mFirstChoice, R.id.choice);
        TextView choice2 = ButterKnife.findById(mSecondChoice, R.id.choice);
        TextView choice3 = ButterKnife.findById(mThirdChoice, R.id.choice);
        TextView choice4 = ButterKnife.findById(mFourthChoice, R.id.choice);
        TextView choice5 = ButterKnife.findById(mFifthChoice, R.id.choice);

        choice1.setText(mQuiz.getChoices().get(0).getContent());
        choice2.setText(mQuiz.getChoices().get(1).getContent());
        choice3.setText(mQuiz.getChoices().get(2).getContent());
        choice4.setText(mQuiz.getChoices().get(3).getContent());
        choice5.setText(mQuiz.getChoices().get(4).getContent());


        return view;
    }

    @OnClick(R.id.close)
    public void closeClicked(){
        //TODO: Show Confirmation
        getActivity().finish();
    }
}
