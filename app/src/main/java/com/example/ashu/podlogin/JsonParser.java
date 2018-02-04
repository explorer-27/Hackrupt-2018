package com.example.ashu.podlogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ashu on 4/2/18.
 */

public class JsonParser {
    public static String getJsonData(String method,String JsonFile){

        String Access="";
        try {
            if(JsonFile!=null&&method.equals("login")) {

                JSONObject root = new JSONObject(JsonFile);
                JSONArray resultsArray = root.optJSONArray("server_response");
                if(resultsArray.length()>0){
                    Access= "Allowed";
                }
                else
                    Access="Not Allowed";

            }

        } catch (JSONException e) {
            Access="Not Allowed";
            e.printStackTrace();
        }


        return Access;
    }
}
