package com.tinytinybites.android.pvzquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by bundee on 9/13/16.
 * A choice for a question
 */
public class Choice implements Parcelable{
    //Tag
    private static final String TAG = Choice.class.getName();

    //Variables
    private int mId;
    private Date mCreatedDate;
    private String mContent;
    private boolean mIsTheAnswer;

    /**
     * Constructor
     * @param mContent
     * @param mCreatedDate
     * @param mId
     */
    public Choice(String mContent, Date mCreatedDate, int mId, boolean mIsTheAnswer) {
        this.mContent = mContent;
        this.mCreatedDate = mCreatedDate;
        this.mId = mId;
        this.mIsTheAnswer = mIsTheAnswer;
    }

    protected Choice(Parcel in) {
        mId = in.readInt();
        mCreatedDate = new Date(in.readLong());
        mContent = in.readString();
        mIsTheAnswer = in.readByte() != 0;
    }

    public Choice(JSONObject jsonObj){
        Iterator<String> iterator = jsonObj.keys();
        if(iterator.hasNext()) {
            mContent = iterator.next();
            mIsTheAnswer = jsonObj.optBoolean(mContent);
            mCreatedDate = new Date();
            mId = -1;
        }
    }

    public static final Creator<Choice> CREATOR = new Creator<Choice>() {
        @Override
        public Choice createFromParcel(Parcel in) {
            return new Choice(in);
        }

        @Override
        public Choice[] newArray(int size) {
            return new Choice[size];
        }
    };

    public String getContent() {
        return mContent;
    }

    public Date getCreatedDate() {
        return mCreatedDate;
    }

    public int getId() {
        return mId;
    }

    public boolean getIsTheCorrectAnswer(){
        return mIsTheAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeLong(mCreatedDate.getTime());
        dest.writeString(mContent);
        dest.writeByte((byte) (mIsTheAnswer ? 1 : 0));
    }
}
