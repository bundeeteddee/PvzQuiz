package com.tinytinybites.android.pvzquiz.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import java.util.List;
import java.util.concurrent.Callable;
import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.application.EApplication;
import com.tinytinybites.android.pvzquiz.event.QuizesLoadedEvent;
import com.tinytinybites.android.pvzquiz.model.Quiz;
import com.tinytinybites.android.pvzquiz.session.GameSession;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by bundee on 9/14/16.
 */
public class SessionViewModel extends BaseObservable implements ViewModel, Parcelable{
    //Tag
    private static final String TAG = SessionViewModel.class.getName();

    //Variables
    private GameSession mGameSession;
    private ObservableBoolean mLoadingQuizes = new ObservableBoolean();
    private Subscription mSubscription;

    public SessionViewModel(){
        this.mGameSession = GameSession.getInstance();
        mLoadingQuizes.set(false);
    }

    protected SessionViewModel(Parcel in) {
        mLoadingQuizes = in.readParcelable(ObservableBoolean.class.getClassLoader());
    }

    public static final Creator<SessionViewModel> CREATOR = new Creator<SessionViewModel>() {
        @Override
        public SessionViewModel createFromParcel(Parcel in) {
            return new SessionViewModel(in);
        }

        @Override
        public SessionViewModel[] newArray(int size) {
            return new SessionViewModel[size];
        }
    };

    public String getSummary(){
        return String.format(EApplication.getInstance().getString(R.string.quiz_summary), (mGameSession.getCurrentQuizIndex()+1), mGameSession.getTotalQuizQuestions());
    }

    public String getNextButtonText(){
        if(!mGameSession.hasMoreQuizes()){
            return EApplication.getInstance().getString(R.string.quiz_see_results);
        }
        return EApplication.getInstance().getString(R.string.quiz_next);
    }

    public int getProgressContainerVisibility(){
        return mLoadingQuizes.get() ? View.VISIBLE : View.GONE;
    }

    public void loadQuizes(){
        mLoadingQuizes.set(true);
        notifyChange();

        mSubscription = Observable.fromCallable(new Callable<List<Quiz>>() {
                    @Override
                    public List<Quiz> call() throws Exception {
                        //Simulate longer work, as loading json from apk is too fast
                        Thread.sleep(3000);

                        return EApplication.getInstance().getDataManager().getQuizes();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Quiz>>() {
                    @Override
                    public void call(List<Quiz> quizs) {
                        GameSession.getInstance().setQuizes(quizs);
                        mLoadingQuizes.set(false);
                        notifyChange();

                        EventBus.getDefault().post(new QuizesLoadedEvent());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //Errors
                        mLoadingQuizes.set(false);
                        notifyChange();
                    }
                });
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();

        mSubscription = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mLoadingQuizes, flags);
    }

}
