package com.tinytinybites.android.pvzquiz.session;

import java.util.ArrayList;
import java.util.List;
import com.tinytinybites.android.pvzquiz.model.Quiz;

/**
 * Created by bundee on 9/14/16.
 */
public class GameSession {
    //Tag
    private static final String TAG = GameSession.class.getName();

    //Variables
    private static GameSession _instance;
    private List<Quiz> mQuizes;
    private String mCategory;
    private int mCurrentQuizIndex;
    private Object mMutex = new Object();

    /**
     * Protected constructor
     */
    protected GameSession(){
        initSession();
    }

    /**
     * Get singleton instance of game session
     * @return
     */
    public static GameSession getInstance(){
        if(_instance == null){
            _instance = new GameSession();
        }
        return _instance;
    }

    /**
     * Init session
     */
    private void initSession(){
        synchronized (mMutex) {
            mQuizes = new ArrayList<>();
            mCategory = null;
            mCurrentQuizIndex = 0;
        }
    }

    /**
     * Reset game session
     */
    public void resetSession(){
        synchronized (mMutex) {
            if (mQuizes != null) {
                mQuizes.clear();
            } else {
                mQuizes = new ArrayList<>();
            }
            mCurrentQuizIndex = 0;
        }
    }

    public void setQuizes(List<Quiz> newQuizes){
        synchronized (mMutex) {
            if(newQuizes != null &&
                    !newQuizes.isEmpty()){
                mCategory = newQuizes.get(0).getQuestion().getCategory();
            }

            mQuizes = new ArrayList<>(newQuizes);
        }
    }

    public Quiz getCurrentQuiz() {
        synchronized (mMutex) {
            if(mQuizes != null &&
                    !mQuizes.isEmpty()){
                return mQuizes.get(mCurrentQuizIndex);
            }
            return null;
        }
    }

    public int getCurrentQuizIndex(){
        return mCurrentQuizIndex;
    }

    public int getTotalQuizQuestions(){
        return mQuizes.size();
    }

    public int getTotalCorrect(){
        int acc = 0;
        synchronized (mMutex) {
            for (Quiz q : mQuizes) {
                if(q.getChosen().getIsTheCorrectAnswer()){
                    acc++;
                }
            }
        }
        return acc;
    }

    public String getCategory(){    return mCategory;}

    public boolean nextQuiz(){
        synchronized (mMutex) {
            if(hasMoreQuizes()){
                mCurrentQuizIndex++;
                return true;
            }
            return false;
        }
    }

    public boolean hasMoreQuizes(){
        synchronized (mMutex) {
            return mQuizes != null &&
                    !mQuizes.isEmpty() &&
                    mCurrentQuizIndex < mQuizes.size() - 1;
        }
    }

    public boolean needToLoadQuizes(){
        synchronized (mMutex){
            return mQuizes == null || mQuizes.isEmpty();
        }
    }


}
