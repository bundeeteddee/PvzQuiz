package com.tinytinybites.android.pvzquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.fragment.DashboardFragment;
import com.tinytinybites.android.pvzquiz.session.GameSession;

public class DashboardActivity extends AppCompatActivity implements DashboardFragment.DashboardNavigation{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    @Override
    public void OnStartNewGameClicked() {
        GameSession.getInstance().resetSession();
        startActivity(new Intent(this, QuizActivity.class));
    }
}
