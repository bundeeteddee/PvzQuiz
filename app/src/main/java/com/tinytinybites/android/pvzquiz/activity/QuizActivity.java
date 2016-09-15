package com.tinytinybites.android.pvzquiz.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.fragment.QuizFragment;
import com.tinytinybites.android.pvzquiz.session.GameSession;

public class QuizActivity extends AppCompatActivity implements QuizFragment.QuizNavigation{
    //Tag
    private static final String TAG = QuizActivity.class.getName();

    //Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //TODO:
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, QuizFragment.newInstance(GameSession.getInstance().getCurrentQuiz()))
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void OnNextQuiz() {
        if(GameSession.getInstance().nextQuiz()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, QuizFragment.newInstance(GameSession.getInstance().getCurrentQuiz()))
                    .commit();
        }else{
            //TODO: We are done show result

        }


    }

    @Override
    public void OnCloseQuiz() {
        finish();
    }
}
