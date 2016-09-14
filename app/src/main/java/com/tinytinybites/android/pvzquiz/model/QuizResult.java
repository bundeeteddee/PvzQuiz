package com.tinytinybites.android.pvzquiz.model;

import java.util.Date;

/**
 * Created by bundee on 9/13/16.
 * Describes a quiz result
 */
public class QuizResult {
    //Tag
    private static final String TAG = QuizResult.class.getName();

    //Variables
    private int mId;
    private Date mCreatedDate;
    private int mTotalQuestions;
    private int mTotalCorrectAnswers;
    private String mCategory;

    /**
     * Constructor
     * @param mCategory
     * @param mCreatedDate
     * @param mId
     * @param mTotalCorrectAnswers
     * @param mTotalQuestions
     */
    public QuizResult(String mCategory, Date mCreatedDate, int mId, int mTotalCorrectAnswers, int mTotalQuestions) {
        this.mCategory = mCategory;
        this.mCreatedDate = mCreatedDate;
        this.mId = mId;
        this.mTotalCorrectAnswers = mTotalCorrectAnswers;
        this.mTotalQuestions = mTotalQuestions;
    }

    public String getCategory() {
        return mCategory;
    }

    public Date getCreatedDate() {
        return mCreatedDate;
    }

    public int getId() {
        return mId;
    }

    public int getTotalCorrectAnswers() {
        return mTotalCorrectAnswers;
    }

    public int getTotalQuestions() {
        return mTotalQuestions;
    }
}
