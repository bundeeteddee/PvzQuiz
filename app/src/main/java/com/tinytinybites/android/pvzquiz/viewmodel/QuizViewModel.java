package com.tinytinybites.android.pvzquiz.viewmodel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.model.Choice;
import com.tinytinybites.android.pvzquiz.model.Quiz;
import com.tinytinybites.android.pvzquiz.util.ResourceUtil;

/**
 * Created by bundee on 9/14/16.
 */
public class QuizViewModel extends BaseObservable implements ViewModel, Parcelable{
    //Tag
    private static final String TAG = QuizViewModel.class.getName();

    //Time out, in sec
    private static final int QUIZ_TIMEOUT = 10;

    //Variables
    private Quiz mQuiz;
    private boolean mChoicesDisabled;
    private CountDownTimer mTimer;
    private int mCountDownTime;

    /**
     * Default constructor
     * @param quiz
     */
    public QuizViewModel(Quiz quiz){
        this.mQuiz = quiz;
        this.mChoicesDisabled = false;

        if(mQuiz.getChosen() == null) {
            if(mTimer == null){
                this.mCountDownTime = QUIZ_TIMEOUT;
                mTimer = new CountDownTimer(QUIZ_TIMEOUT * 1000, 800) {
                    public void onTick(long millisUntilFinished) {
                        mCountDownTime = Math.round(millisUntilFinished / 1000f);
                        notifyChange();
                    }

                    public void onFinish() {
                        mChoicesDisabled = true;
                        mCountDownTime = 0;
                        mQuiz.setChosen(new Choice());
                        notifyChange();
                    }
                }.start();
            }
        }else{
            this.mCountDownTime = 0;
        }
    }

    protected QuizViewModel(Parcel in) {
        Log.e(TAG, "QuizViewModel >>> Parcel", null);
        mQuiz = in.readParcelable(Quiz.class.getClassLoader());
        mChoicesDisabled = in.readByte() != 0;
        mCountDownTime = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mQuiz, flags);
        dest.writeByte((byte) (mChoicesDisabled ? 1 : 0));
        dest.writeInt(mCountDownTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuizViewModel> CREATOR = new Creator<QuizViewModel>() {
        @Override
        public QuizViewModel createFromParcel(Parcel in) {
            return new QuizViewModel(in);
        }

        @Override
        public QuizViewModel[] newArray(int size) {
            return new QuizViewModel[size];
        }
    };

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

    public String getCountDownTime(){
        return mCountDownTime + "s";
    }

    public void onChoiceSelected(View v){
        mChoicesDisabled = true;
        mTimer.cancel();
        mCountDownTime = 0;

        if(v.getTag() != null){
            mQuiz.setChosen((Choice) v.getTag());
            notifyChange();
        }
    }

    public int getBackgroundColor(Choice currentChoice){
        if(mQuiz.getChosen() != null){
            if(mQuiz.getChosen().equals(currentChoice)){
                if(currentChoice.getIsTheCorrectAnswer()) {
                    return ResourceUtil.GetColor(R.color.button_quiz_choice_correct);
                }else{
                    return ResourceUtil.GetColor(R.color.button_quiz_choice_wrong);
                }
            }

            if(currentChoice.getIsTheCorrectAnswer()) {
                return ResourceUtil.GetColor(R.color.button_quiz_choice_correct);
            }
        }
        return ResourceUtil.GetColor(R.color.button_quiz_choice_default);
    }

    public int getNextButtonVisibility(){
        return mQuiz.getChosen() != null ? View.VISIBLE : View.GONE;
    }

    public int getCountDownVisibility(){
        return mCountDownTime == 0 ? View.GONE : View.VISIBLE;
    }

    public boolean getChoiceMarginSelected(Choice currentChoice){
        if(mQuiz.getChosen() != null && mQuiz.getChosen().equals(currentChoice)){
            return true;
        }
        return false;
    }

    public boolean getNextButtonVisible(){
        return getNextButtonVisibility() == View.VISIBLE;
    }

    @BindingAdapter("fadeVisible")
    public static void setFadeVisible(final View view, boolean visible) {
        if (view.getTag() == null) {
            view.setTag(true);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        } else {
            view.animate().cancel();

            if (visible) {
                view.setVisibility(View.VISIBLE);
                view.setAlpha(0);
                view.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setAlpha(1);
                    }
                });
            } else {
                view.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setAlpha(1);
                        view.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public int getResultIcon(){
        if(mQuiz.getChosen() != null && mQuiz.getChosen().getIsTheCorrectAnswer()){
            return R.drawable.ico_correct;
        }
        return R.drawable.ico_wrong;
    }

    @BindingAdapter("resultIcon")
    public static void setResultIcon(ImageView view, @DrawableRes int resId){
        view.setImageResource(resId);
    }

    @Override
    public void destroy() {
        //TODO: clean up any other subscribers

        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }
}
