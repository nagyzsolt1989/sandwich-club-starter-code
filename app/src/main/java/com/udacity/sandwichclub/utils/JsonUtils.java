package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        String mainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients;

        final String KEY_NAME = "name";
        final String KEY_MAIN_NAME = "mainName";
        final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
        final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String KEY_DESCRIPTION = "description";
        final String KEY_IMAGE = "image";
        final String KEY_INGREDIENTS = "ingredients";

        try {

            JSONObject sandwichJson = new JSONObject(json);

            JSONObject nameJson = sandwichJson.getJSONObject(KEY_NAME);
            mainName = nameJson.getString(KEY_MAIN_NAME);

            JSONArray nameJsonArray = nameJson.getJSONArray(KEY_ALSO_KNOW_AS);
            alsoKnownAs = convertJsonArrayToList(nameJsonArray);

            placeOfOrigin = sandwichJson.optString(KEY_PLACE_OF_ORIGIN);
            description = sandwichJson.optString(KEY_DESCRIPTION);
            image = sandwichJson.optString(KEY_IMAGE);

            JSONArray ingredientsJsonArray = sandwichJson.getJSONArray(KEY_INGREDIENTS);
            ingredients = convertJsonArrayToList(ingredientsJsonArray);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<String> convertJsonArrayToList(JSONArray jsonArray) {
        ArrayList<String> list = new ArrayList<>();
        if (jsonArray == null) {
            return null;
        }
        try {
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                list.add(jsonArray.get(i).toString());
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
