package com.tinytinybites.android.pvzquiz.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.fragment.QuizFragment;
import com.tinytinybites.android.pvzquiz.fragment.QuizResultsFragment;
import com.tinytinybites.android.pvzquiz.session.GameSession;

public class QuizActivity extends AppCompatActivity implements QuizFragment.QuizNavigation, QuizResultsFragment.QuizResultsNavigation{
    //Tag
    private static final String TAG = QuizActivity.class.getName();

    //Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState == null) {
            //Initial insert
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragment_container, QuizFragment.newInstance(GameSession.getInstance().getCurrentQuiz()), QuizFragment.class.getName())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void OnNextQuiz() {
        if(GameSession.getInstance().nextQuiz()){
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragment_container, QuizFragment.newInstance(GameSession.getInstance().getCurrentQuiz()), QuizFragment.class.getName())
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragment_container, QuizResultsFragment.newInstance(), QuizResultsFragment.class.getName())
                    .commit();
        }
    }

    @Override
    public void OnCloseQuiz() {
        finish();
    }

    @Override
    public void OnDoneQuiz() {
        finish();
    }

    @Override
    public void OnPlayNewQuiz() {
        //Reset game session
        GameSession.getInstance().resetSession();

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.fragment_container, QuizFragment.newInstance(GameSession.getInstance().getCurrentQuiz()), QuizFragment.class.getName())
                .commit();
    }
}
