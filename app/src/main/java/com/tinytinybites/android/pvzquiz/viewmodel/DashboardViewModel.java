package com.tinytinybites.android.pvzquiz.viewmodel;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bundee on 9/14/16.
 */
public class DashboardViewModel extends BaseObservable implements ViewModel, Parcelable{
    //Tag
    private static final String TAG = DashboardViewModel.class.getName();

    //Variables

    /**
     * Constructor
     */
    public DashboardViewModel(){

    }

    protected DashboardViewModel(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DashboardViewModel> CREATOR = new Creator<DashboardViewModel>() {
        @Override
        public DashboardViewModel createFromParcel(Parcel in) {
            return new DashboardViewModel(in);
        }

        @Override
        public DashboardViewModel[] newArray(int size) {
            return new DashboardViewModel[size];
        }
    };

    @Override
    public void destroy() {

    }

}
