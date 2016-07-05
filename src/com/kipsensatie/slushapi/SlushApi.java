package com.kipsensatie.slushapi;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Kipsensatie on 5-7-2016.
 */
public class SlushApi {
    private String ApiKey;
    private String ApiUrl;
    private boolean isInitialized = false;

    public SlushApi(String URL, String apiKey) {
        ApiKey = apiKey;
        ApiUrl = URL;
        isInitialized = true;
    }

    public void clearApi() {
        isInitialized = false;
        ApiKey = null;
        ApiUrl = null;
    }

    public String getKey() {
        return ApiKey;
    }

    public String getUrl() {
        return ApiUrl;
    }

    //JSON Object
    public JsonObject getJsonObject() throws MalformedURLException {
        try {
            JsonObject jsonFinal;
            if (isInitialized == true) {
                JsonElement slushJson = new JsonParser().parse(getJsonString(getUrl() + getKey()));
                JsonObject slushObj = slushJson.getAsJsonObject();
                jsonFinal = slushObj;
            } else {
                System.out.print("Slush API Error: API Not initialized!");
                jsonFinal = null;
            }
            return jsonFinal;
        } catch(Exception e) {
            return null;
        }
    }

    //JSON reader
    private String getJsonString(String urlString){
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
            return buff.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
