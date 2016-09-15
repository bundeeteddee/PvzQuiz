package com.tinytinybites.android.pvzquiz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.fragment.QuizResultsFragment;
import com.tinytinybites.android.pvzquiz.session.GameSession;

/**
 * Created by bundee on 9/15/16.
 */
public class QuizResultsViewModel extends BaseObservable implements ViewModel{
    //Tag
    private static final String TAG = QuizResultsViewModel.class.getName();

    //Variables
    private Context mContext;
    private GameSession mGameSession;
    private QuizResultsFragment.QuizResultsNavigation mListener;

    public QuizResultsViewModel(Context context, QuizResultsFragment.QuizResultsNavigation listener){
        this.mContext = context;
        this.mGameSession = GameSession.getInstance();
        this.mListener = listener;
    }

    @Override
    public void destroy() {
        mContext = null;
    }

    public View.OnClickListener onDoneSelected(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.OnDoneQuiz();
                }
            }
        };
    }

    public String getResultHeader(){
        int totalCorrect = mGameSession.getTotalCorrect();

        if(totalCorrect < 3){
            return mContext.getString(R.string.quiz_results_header_good_try);
        }else if(totalCorrect < 6){
            return mContext.getString(R.string.quiz_results_header_good_job);
        }else if(totalCorrect < 10){
            return mContext.getString(R.string.quiz_results_header_well_done);
        }else{
            return mContext.getString(R.string.quiz_results_header_excellent);
        }
    }

    public String getSummary(){
        return String.format(mContext.getString(R.string.quiz_results_summary),
                mGameSession.getCategory(),
                mGameSession.getTotalQuizQuestions(),
                mGameSession.getTotalCorrect());
    }

    public String getScore(){
        return (((float)mGameSession.getTotalCorrect()/mGameSession.getTotalQuizQuestions()) * 100) + "%";
    }

    public View.OnClickListener onPlayAgainSelected(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.OnPlayNewQuiz();
                }
            }
        };
    }
}
