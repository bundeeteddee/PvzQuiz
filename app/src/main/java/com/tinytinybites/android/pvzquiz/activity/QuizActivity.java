package com.tinytinybites.android.pvzquiz.activity;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.databinding.ActivityQuizBinding;
import com.tinytinybites.android.pvzquiz.event.CloseQuizEvent;
import com.tinytinybites.android.pvzquiz.event.DoneQuizesEvent;
import com.tinytinybites.android.pvzquiz.event.PlayQuizAgainEvent;
import com.tinytinybites.android.pvzquiz.event.QuizesLoadedEvent;
import com.tinytinybites.android.pvzquiz.fragment.QuizFragment;
import com.tinytinybites.android.pvzquiz.fragment.QuizResultsFragment;
import com.tinytinybites.android.pvzquiz.intent.IntentUtil;
import com.tinytinybites.android.pvzquiz.session.GameSession;
import com.tinytinybites.android.pvzquiz.viewmodel.SessionViewModel;

public class QuizActivity extends AppCompatActivity implements QuizFragment.QuizNavigation{
    //Tag
    private static final String TAG = QuizActivity.class.getName();

    //Variables
    private SessionViewModel mSessionViewModel;
    private ActivityQuizBinding mBinding;
    private EventBus mEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        mEventBus = EventBus.getDefault();
        mEventBus.register(this);

        if(savedInstanceState != null){
            mSessionViewModel = savedInstanceState.getParcelable(IntentUtil.SESSION_VM);
        }else{
            mSessionViewModel = new SessionViewModel();
        }

        //Data binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);
        mBinding.setSessionViewModel(mSessionViewModel); //Session VM is special as it binds only with singleton instance.

        if(savedInstanceState == null){
            setupQuizProcess();
        }
    }

    private void setupQuizProcess() {
        //Check if we need to load quizes
        if(GameSession.getInstance().needToLoadQuizes()){
            //Ensure no fragments in manager
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if(fragment != null){
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            mSessionViewModel.loadQuizes();
        }else{
            //Initial insert
            showCurrentQuizFragment(null, null);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(IntentUtil.SESSION_VM, mSessionViewModel);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mEventBus.unregister(this);
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

    @Subscribe
    public void onQuizesLoaded(QuizesLoadedEvent event){
        setupQuizProcess();
    }

    @Subscribe
    public void OnCloseQuiz(CloseQuizEvent event) {
        finish();
    }

    @Subscribe
    public void OnDoneQuiz(DoneQuizesEvent event) {
        finish();
    }

    @Subscribe
    public void OnPlayNewQuiz(PlayQuizAgainEvent event) {
        //Reset game session
        GameSession.getInstance().resetSession();

        setupQuizProcess();
    }
}
