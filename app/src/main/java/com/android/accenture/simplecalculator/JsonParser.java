package com.android.accenture.simplecalculator;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by ykashiwagi on 6/26/17.
 */

public class JsonParser {

    // Read JSON file
    public JSONObject getParsedJSONObject(String result){

        JSONObject response = null;

        try {
            JSONObject rootObject = new JSONObject(result);
            response = rootObject.getJSONObject("response");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;

    }

    // Convert JSONObject to String
    public String getConvertedResponse(JSONObject response) {

        String status = null;
        String setResult = null;

        if (response != null) {

                try {
                    status = response.getString("status");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (status.equals("OK")) {
                        setResult = (response.getString("result"));

                    } else {
                        setResult = ("Please enter a correct value");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
         }

        return setResult;

    }
}
