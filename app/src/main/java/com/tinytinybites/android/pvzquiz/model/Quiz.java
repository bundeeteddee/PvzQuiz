package com.tinytinybites.android.pvzquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bundee on 9/13/16.
 * Describes a full quiz instance, with the question, list of choices and chosen answer
 */
public class Quiz implements Parcelable{
    //Tag
    private static final String TAG = Quiz.class.getName();

    //Variables
    private Question mQuestion;
    private List<Choice> mChoices;
    private Choice mChosen;

    /**
     * Constructor
     * @param mChoices
     * @param mChosen
     * @param mQuestion
     */
    public Quiz(List<Choice> mChoices, Choice mChosen, Question mQuestion) {
        this.mChoices = mChoices;
        this.mChosen = mChosen;
        this.mQuestion = mQuestion;
    }

    protected Quiz(Parcel in) {
        mQuestion = in.readParcelable(Question.class.getClassLoader());
        mChoices = in.createTypedArrayList(Choice.CREATOR);
        mChosen = in.readParcelable(Choice.class.getClassLoader());
    }

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    public List<Choice> getChoices() {
        return mChoices;
    }

    public Choice getChosen() {
        return mChosen;
    }

    public Question getQuestion() {
        return mQuestion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mQuestion, flags);
        dest.writeTypedList(mChoices);
        dest.writeParcelable(mChosen, flags);
    }
}
