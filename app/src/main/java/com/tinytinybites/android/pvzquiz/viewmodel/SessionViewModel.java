package com.tinytinybites.android.pvzquiz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.session.GameSession;

/**
 * Created by bundee on 9/14/16.
 */
public class SessionViewModel extends BaseObservable implements ViewModel{
    //Tag
    private static final String TAG = SessionViewModel.class.getName();

    //Variables
    private Context mContext;
    private GameSession mGameSession;

    public SessionViewModel(Context context){
        this.mContext = context;
        this.mGameSession = GameSession.getInstance();
    }

    public String getSummary(){
        StringBuilder builder = new StringBuilder();
        builder.append("Question ");
        builder.append(mGameSession.getCurrentQuizIndex()+1);
        builder.append(" of ");
        builder.append(mGameSession.getTotalQuizQuestions());

        return builder.toString();
    }

    public String getNextButtonText(){
        if(!mGameSession.hasMoreQuizes()){
            return mContext.getString(R.string.quiz_see_results);
        }
        return mContext.getString(R.string.quiz_next);
    }

    @Override
    public void destroy() {
        mContext = null;
    }









}
