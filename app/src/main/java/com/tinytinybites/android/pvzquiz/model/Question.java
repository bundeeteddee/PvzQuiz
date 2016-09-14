package com.tinytinybites.android.pvzquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by bundee on 9/13/16.
 * Describes a quiz question
 */
public class Question implements Parcelable{
    //Tag
    private static final String TAG = Question.class.getName();

    //Variables
    private int mId;
    private Date mCreatedDate;
    private String mContent;
    private String mCategory;

    /**
     * Constructor
     * @param mCategory
     * @param mContent
     * @param mCreatedDate
     * @param mId
     */
    public Question(String mCategory, String mContent, Date mCreatedDate, int mId) {
        this.mCategory = mCategory;
        this.mContent = mContent;
        this.mCreatedDate = mCreatedDate;
        this.mId = mId;
    }

    protected Question(Parcel in) {
        mId = in.readInt();
        mCreatedDate = new Date(in.readLong());
        mContent = in.readString();
        mCategory = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getCategory() {
        return mCategory;
    }

    public String getContent() {
        return mContent;
    }

    public Date getCreatedDate() {
        return mCreatedDate;
    }

    public int getId() {
        return mId;
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
        dest.writeString(mCategory);
    }
}
