package com.android.accenture.simplecalculator;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ykashiwagi on 6/26/17.
 */

public class HttpRequester {
    public String getRequest(String num1, String num2, String itemOp) {

                String result = null;

                // Create Request Object
                Request request = new Request.Builder()
                        .url("http://54.248.218.27/devel/fb_api/calculator.php?num1=" + num1 + "&num2=" + num2 + "&operator="+ itemOp)
                        .get()
                        .build();

                // Create client object
                OkHttpClient client = new OkHttpClient();

                // Request and receive results
                try {
                    Response response = client.newCall(request).execute();
                    result = response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return result;

    }
}
