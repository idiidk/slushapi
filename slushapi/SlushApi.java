package com.slushapi;

import android.app.ProgressDialog;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Kipsensatie on 3-7-2016.
 */

public class SlushApi {
    //Private Global Vars
    static private String ApiKeyGlobal;
    static private String ApiUrlGlobal;
    static private boolean initializedApi = false;

    //Testing Vars
    private static String testJson = "http://jsonplaceholder.typicode.com/posts/1";

    //Public Vars
    public static final String BTC_SYMBOL = "\u0e3f";

    //Private Vars
    private static boolean passedJson = false;

    //Array of Workers
     static private ArrayList<SlushWorker> workerList = new ArrayList<SlushWorker>();

    //End of vars



    public static boolean initializeApi(String ApiKey, String ApiUrl) {

        SlushApi.ApiKeyGlobal = ApiKey;
        SlushApi.ApiUrlGlobal = ApiUrl;
        initializedApi = true;
        return true;
    }



    public static ArrayList<SlushWorker> getWorkers() {

        JsonObject worker = getJsonObject().get("workers").getAsJsonObject();

        for (Map.Entry<String,JsonElement> entry : worker.entrySet()) {
            String worker_name = entry.getKey().toString();
            int last_share = entry.getValue().getAsJsonObject().get("last_share").getAsInt();
            String score = entry.getValue().getAsJsonObject().get("score").getAsString();
            int shares = entry.getValue().getAsJsonObject().get("shares").getAsInt();
            int hashrate = entry.getValue().getAsJsonObject().get("hashrate").getAsInt();
            Boolean alive = entry.getValue().getAsJsonObject().get("alive").getAsBoolean();

            workerList.add(new SlushWorker(worker_name, last_share, score, shares, hashrate, alive));
        }
        return workerList;
    }



    public static boolean uninitializeApi() {
        initializedApi = false;
        return true;
    }

    public static String getApiKey() {
        if(isApiInitialized()) {
            return ApiKeyGlobal;
        } else {
            return null;
        }
    }

    public static String getApiUrl() {
        if(isApiInitialized()) {
            return ApiUrlGlobal;
        } else {
           return null;
        }
    }

    public static JsonObject getJsonObject() {
       JsonObject jsonFinal;
        if(isApiInitialized()) {
            Log.e("Out",getApiUrl() + getApiKey());

            JsonElement slushJson = new JsonParser().parse(getJsonString(getApiUrl() + getApiKey().trim()));
            JsonObject slushObj = slushJson.getAsJsonObject();
            jsonFinal = slushObj;
        } else {
            Log.e("Slush API Error: ", "API Not initialized!!!!");
            jsonFinal = null;
        }
        return jsonFinal;
    }

    public static boolean isApiInitialized() {

            return initializedApi;



    }

    public static void getUserData() {
        JsonObject slushObj = getJsonObject();
        SlushUser.username = slushObj.get("username").getAsString();
        SlushUser.wallet = slushObj.get("wallet").getAsString();
        SlushUser.hashrate = slushObj.get("hashrate").getAsString();
        SlushUser.send_threshold = slushObj.get("send_threshold").getAsString();
        SlushUser.unconfirmed_reward = slushObj.get("unconfirmed_reward").getAsString();
        SlushUser.confirmed_reward = slushObj.get("confirmed_reward").getAsString();
        SlushUser.estimated_reward = slushObj.get("estimated_reward").getAsString();
        SlushUser.total_reward =  Double.toString(Double.parseDouble(SlushUser.unconfirmed_reward) + Double.parseDouble(SlushUser.confirmed_reward));
    }

    public static ArrayList<SlushWorker> getWorkerList() {
        return workerList;
    }

    public static String testJson() {
        String JsonString = getJsonString(testJson);
        if(passedJson) {
            JsonParser jp = new JsonParser();


            JsonElement slushJson = new JsonParser().parse(JsonString);
            JsonObject testObj = slushJson.getAsJsonObject();
            resetJsonPassed();
            String s = testObj.get("userId").getAsString();
            if(s == null) {
                Log.e("Slush Api Error: ", "STRING RESULTED NULL, THIS SHOULDN`T HAPPEN!!!!");
                return null;
            } else {
                return s;
            }

        } else {
            resetJsonPassed();
            return null;
        }
    }

    public static void resetJsonPassed() {
        passedJson = false;
    }



    //Json Handler
    private static String getJsonString(String urlString){
        StringBuffer buff = new StringBuffer();
        URL url = null;
        InputStream input = null;

        //Construct a URL
        try {
            url = new URL(urlString);
            input = url.openStream();

            //Read in the json
            BufferedReader rd = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = rd.readLine()) != null) {
                buff.append(line);
            }
            passedJson = true;
            return buff.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            passedJson = false;
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            passedJson = false;
            return null;
        }
    }
}
