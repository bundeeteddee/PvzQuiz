package com.tinytinybites.android.pvzquiz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.tinytinybites.android.pvzquiz.fragment.DashboardFragment;
import com.tinytinybites.android.pvzquiz.session.GameSession;

/**
 * Created by bundee on 9/14/16.
 */
public class DashboardViewModel extends BaseObservable implements ViewModel{
    //Tag
    private static final String TAG = DashboardViewModel.class.getName();

    //Variables
    private Context mContext;
    private GameSession mGameSession;
    private DashboardFragment.DashboardNavigation mListener;

    /**
     * Constructor
     * @param context
     * @param listener
     */
    public DashboardViewModel(Context context, DashboardFragment.DashboardNavigation listener){
        this.mContext = context;
        this.mGameSession = GameSession.getInstance();
        this.mListener = listener;
    }

    public View.OnClickListener onStartNewGameClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.OnStartNewGameClicked();
                }
            }
        };
    }

    @Override
    public void destroy() {
        mContext = null;
        mListener = null;
    }

}
