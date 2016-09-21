package com.tinytinybites.android.pvzquiz.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.tinytinybites.android.pvzquiz.model.Quiz;
import com.tinytinybites.android.pvzquiz.util.ResourceUtil;

/**
 * Created by bundee on 9/18/16.
 */
public class DataManager {
    //Tag
    private static final String TAG = DataManager.class.getName();

    //Variables

    /**
     * Empty constructor
     */
    public DataManager() {

    }

    /**
     * Get a list of quizes from packaged apk under res/raw/
     * TODO: Add more quiz sets
     * @return
     */
    public List<Quiz> getQuizes(){
        try {
            String quizJson = ResourceUtil.GetQuizJsonResource();

            JSONObject quizObject = new JSONObject(quizJson);

            //Quizes
            HashMap<String, List<Quiz>> result = new HashMap<>();
            String category = quizObject.getString("category");
            ArrayList<Quiz> quizes = new ArrayList<>();
            JSONArray quizesJsonArray = quizObject.getJSONArray("quizes");
            for (int i = 0; i < quizesJsonArray.length(); i++) {
                quizes.add(new Quiz(quizesJsonArray.getJSONObject(i), category));
            }
            return quizes;
        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
