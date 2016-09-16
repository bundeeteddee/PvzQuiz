package com.tinytinybites.android.pvzquiz.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

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
            showCurrentQuizFragment(null, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void OnNextQuiz(View closeView, View topPanelView) {
        if(GameSession.getInstance().nextQuiz()){
            showCurrentQuizFragment(closeView, topPanelView);
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

        showCurrentQuizFragment(null, null);
    }

    /**
     * Convenince function to show a new quiz fragment
     * @param closeView
     * @param topPanelView
     */
    private void showCurrentQuizFragment(View closeView, View topPanelView){
        //Quiz fragment instance
        QuizFragment fragment = QuizFragment.newInstance(GameSession.getInstance().getCurrentQuiz());

        //Basic fragment transaction with animation
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

        //If supported, apply shared element (close btuton, top panel)
        //Also, add some transition for all other elements in the fragment (the new fragment)
        //Only for lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Inflate transitions to apply
            Transition noTransform = TransitionInflater.from(this).inflateTransition(android.R.transition.no_transition);
            Transition slideRightTransform = TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right);

            //Setup enter transition on fragment
            fragment.setSharedElementEnterTransition(noTransform); //Transition for shared elements
            fragment.setEnterTransition(slideRightTransform); //Transition for fragment entering

            //Get close image and top panel view
            if(closeView != null && topPanelView != null) {
                ViewCompat.setTransitionName(closeView, getString(R.string.transition_close_button));
                ft.addSharedElement(closeView, getString(R.string.transition_close_button));
                ft.addSharedElement(topPanelView, getString(R.string.transition_top_panel));
            }
        }

        ft.replace(R.id.fragment_container, fragment, QuizFragment.class.getName());
        ft.commit();
    }
}
