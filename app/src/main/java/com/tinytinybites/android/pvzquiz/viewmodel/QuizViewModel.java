package com.tinytinybites.android.pvzquiz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.fragment.QuizFragment;
import com.tinytinybites.android.pvzquiz.model.Choice;
import com.tinytinybites.android.pvzquiz.model.Quiz;

/**
 * Created by bundee on 9/14/16.
 */
public class QuizViewModel extends BaseObservable implements ViewModel{
    //Tag
    private static final String TAG = QuizViewModel.class.getName();

    //Variables
    private Quiz mQuiz;
    private Context mContext;
    private boolean mChoicesDisabled;
    private QuizFragment.QuizNavigation mListener;


    public QuizViewModel(Context context,
                         Quiz quiz,
                         QuizFragment.QuizNavigation listener){
        this.mContext = context;
        this.mQuiz = quiz;
        this.mListener = listener;
        this.mChoicesDisabled = false;
    }

    public Quiz getQuiz(){
        return mQuiz;
    }

    public boolean getChoicesDisabled(){    return mChoicesDisabled;}

    public Choice getFirstChoice(){
        return mQuiz.getChoices().get(0);
    }

    public Choice getSecondChoice(){
        return mQuiz.getChoices().get(1);
    }

    public Choice getThirdChoice(){
        return mQuiz.getChoices().get(2);
    }

    public Choice getFourthChoice(){
        return mQuiz.getChoices().get(3);
    }

    public Choice getFifthChoice(){
        return mQuiz.getChoices().get(4);
    }

    public View.OnClickListener onChoiceSelected() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChoicesDisabled = true;
                if(v.getTag() != null){
                    mQuiz.setChosen((Choice) v.getTag());
                    notifyChange();
                }
            }
        };
    }

    public View.OnClickListener onNextSelected() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.OnNextQuiz();
                }
            }
        };
    }

    public View.OnClickListener onCloseSelected() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.OnCloseQuiz();
                }
            }
        };
    }

    public int getBackgroundColor(Choice currentChoice){
        if(mQuiz.getChosen() != null){
            if(mQuiz.getChosen().equals(currentChoice)){
                if(currentChoice.getIsTheCorrectAnswer()) {
                    return ContextCompat.getColor(mContext, R.color.button_quiz_choice_correct);
                }else{
                    return  ContextCompat.getColor(mContext, R.color.button_quiz_choice_wrong);
                }
            }

            if(currentChoice.getIsTheCorrectAnswer()) {
                return ContextCompat.getColor(mContext, R.color.button_quiz_choice_correct);
            }
        }
        return ContextCompat.getColor(mContext, R.color.button_quiz_choice_default);
    }

    public int getNextButtonVisibility(){
        return mQuiz.getChosen() != null ? View.VISIBLE : View.GONE;
    }

    public boolean getChoiceMarginSelected(Choice currentChoice){
        if(mQuiz.getChosen() != null && mQuiz.getChosen().equals(currentChoice)){
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {
        //TODO: clean up any subscribers

        mListener = null;
        mContext = null;
    }
}
