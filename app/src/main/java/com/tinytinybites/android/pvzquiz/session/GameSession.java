package com.tinytinybites.android.pvzquiz.session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import com.tinytinybites.android.pvzquiz.model.Quiz;
import com.tinytinybites.android.pvzquiz.util.ResourceUtil;

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
        resetSession();
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

        generateQuizes();
    }

    /**
     * Reset session and generate quiz questions
     */
    private void generateQuizes(){
        synchronized (mMutex) {
            try {
                String quizJson = ResourceUtil.GetQuizJsonResource();

                JSONObject quizObject = new JSONObject(quizJson);

                //Quizes
                mCategory = quizObject.getString("category");
                JSONArray quizesJsonArray = quizObject.getJSONArray("quizes");
                for (int i = 0; i < quizesJsonArray.length(); i++) {
                    mQuizes.add(new Quiz(quizesJsonArray.getJSONObject(i), mCategory));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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


}
