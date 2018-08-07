package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private  static String TAG = "Message";
    public static Sandwich parseSandwichJson(String json) throws Exception {
        Sandwich sandwich;
        if(json != null) {
            try {
                JSONObject _jsonObject = new JSONObject(json);
                JSONObject jsonObject = _jsonObject.getJSONObject("name");
                String mainName = jsonObject.getString("mainName");
                List<String> aliases = parseJsonArrayToList(jsonObject, "alsoKnownAs");
                List<String> ingredients = parseJsonArrayToList(_jsonObject, "ingredients");

                sandwich = new Sandwich(mainName,aliases
                        ,_jsonObject.getString("placeOfOrigin"),_jsonObject.getString("description"),_jsonObject.getString("image")
                        ,ingredients);
                return sandwich;
            }
            catch (JSONException e) {
                Log.e(TAG, "parseJsonArrayToList:" + e.getMessage());
                return null;
            }
        }
        return  null;
    }
    public static List<String> parseJsonArrayToList(JSONObject jsonObject, String jsonProperty) throws Exception{
        try {
            JSONArray array = jsonObject.getJSONArray(jsonProperty);
            Log.d(TAG,"totalCountOfItemsInList:"+ array.length());
            List<String> list = new ArrayList<>();
            if(array != null && array.length() > 0){
                for(int i = 0; i < array.length(); i++){
                    list.add(array.getString(i));
                }
            }
            return list;
        }
        catch (JSONException e){
            Log.e(TAG, "parseJsonArrayToList:"+e.getMessage());
            throw  new Exception(e.getMessage());
        }
        catch (Exception ex){
            Log.e(TAG, "errorOccurred:"+ex.getMessage());
            throw  new Exception(ex.getMessage());
        }
    }

}
