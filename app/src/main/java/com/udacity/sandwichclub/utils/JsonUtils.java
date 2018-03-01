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

        try {

            JSONObject sandwichJson = new JSONObject(json);

            JSONObject nameJson = sandwichJson.getJSONObject("name");
            mainName = nameJson.getString("mainName");

            JSONArray nameJsonArray = nameJson.getJSONArray("alsoKnownAs");
            alsoKnownAs = convertJsonArrayToList(nameJsonArray);

            placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            description = sandwichJson.getString("description");
            image = sandwichJson.getString("image");

            JSONArray ingredientsJsonArray = sandwichJson.getJSONArray("ingredients");
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
