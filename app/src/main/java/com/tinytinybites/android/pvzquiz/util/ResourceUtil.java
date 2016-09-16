package com.tinytinybites.android.pvzquiz.util;

import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import com.tinytinybites.android.pvzquiz.R;
import com.tinytinybites.android.pvzquiz.application.EApplication;

/**
 * Created by bundee on 9/14/16.
 */
public class ResourceUtil {

    /**
     * Get quiz (json file) from res/raw/quiz.json, and return a json string
     * @return
     */
    public static final String GetQuizJsonResource(){
        InputStream is = EApplication.getInstance().getResources().openRawResource(R.raw.quiz);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return writer.toString();
    }

    /**
     * Given dimension resource, get dp
     * @param dimenResId
     * @return
     */
    public static final int GetDp(@DimenRes int dimenResId){
        return (int) (EApplication.getInstance().getResources().getDimension(dimenResId) / EApplication.getInstance().getResources().getDisplayMetrics().density);
    }

    /**
     * Get the color given color res id
     * @param colorResId
     * @return
     */
    public static final int GetColor(@ColorRes int colorResId){
        return ContextCompat.getColor(EApplication.getInstance(), colorResId);
    }

}
