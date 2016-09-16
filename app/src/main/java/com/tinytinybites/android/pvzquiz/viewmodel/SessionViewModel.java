package com.tinytinybites.android.pvzquiz.viewmodel;

import android.databinding.BaseObservable;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.application.EApplication;
import com.tinytinybites.android.pvzquiz.session.GameSession;

/**
 * Created by bundee on 9/14/16.
 */
public class SessionViewModel extends BaseObservable implements ViewModel{
    //Tag
    private static final String TAG = SessionViewModel.class.getName();

    //Variables
    private GameSession mGameSession;

    public SessionViewModel(){
        this.mGameSession = GameSession.getInstance();
    }

    public String getSummary(){
        return String.format(EApplication.getInstance().getString(R.string.quiz_summary), (mGameSession.getCurrentQuizIndex()+1), mGameSession.getTotalQuizQuestions());
    }

    public String getNextButtonText(){
        if(!mGameSession.hasMoreQuizes()){
            return EApplication.getInstance().getString(R.string.quiz_see_results);
        }
        return EApplication.getInstance().getString(R.string.quiz_next);
    }

    @Override
    public void destroy() {

    }









}
