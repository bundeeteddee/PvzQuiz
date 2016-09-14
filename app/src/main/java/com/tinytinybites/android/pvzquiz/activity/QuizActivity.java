package com.tinytinybites.android.pvzquiz.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.fragment.QuizFragment;
import com.tinytinybites.android.pvzquiz.model.Choice;
import com.tinytinybites.android.pvzquiz.model.Question;
import com.tinytinybites.android.pvzquiz.model.Quiz;

public class QuizActivity extends AppCompatActivity {
    //Tag
    private static final String TAG = QuizActivity.class.getName();

    //Element
    @BindView(R.id.fragment_container) FrameLayout mFragmentContainer;

    //Variables
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Slice and dice it
        mUnbinder = ButterKnife.bind(this);

        //TODO:
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice("China", new Date(), 1, false));
        choices.add(new Choice("India", new Date(), 2, true));
        choices.add(new Choice("USA", new Date(), 3, false));
        choices.add(new Choice("Indonesia", new Date(), 4, false));
        choices.add(new Choice("Hong Kong", new Date(), 5, false));
        Question question = new Question("General Knowledge", "Which is the most populous country in the world?", new Date(), 10);
        Quiz quiz = new Quiz(choices, null, question);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, QuizFragment.newInstance(quiz))
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mUnbinder.unbind();
    }
}
