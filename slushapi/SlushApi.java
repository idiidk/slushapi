package com.kipsensatie.slushapi;



import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


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


    //Sets Global API vars
    public static boolean initializeApi(String ApiKey, String ApiUrl) {
    	SlushApi.ApiKeyGlobal = ApiKey;
        SlushApi.ApiUrlGlobal = ApiUrl;
        initializedApi = true;
        return true;
    }


    //Initializes the workerList and returns the ArrayList
    public static ArrayList<SlushWorker> getWorkers() {
    	JsonObject worker;
		try {
			worker = getJsonObject().get("workers").getAsJsonObject();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}

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



    //Uninitialize the main variables
    public static boolean uninitializeApi() {
        initializedApi = false;
        return true;
    }
    
    //Return initialized API key.
    public static String getApiKey() {
        if(isApiInitialized()) {
            return ApiKeyGlobal;
        } else {
            return null;
        }
    }

    //Return initialized API url
    public static String getApiUrl() {
        if(isApiInitialized()) {
            return ApiUrlGlobal;
        } else {
           return null;
        }
    }
    
    //Getting the main Slush JSON object.
    public static JsonObject getJsonObject() throws MalformedURLException {
       JsonObject jsonFinal;
        if(isApiInitialized()) {
            JsonElement slushJson = new JsonParser().parse(getJsonString(getApiUrl() + getApiKey().trim()));
            JsonObject slushObj = slushJson.getAsJsonObject();
            jsonFinal = slushObj;
        	} else {
            System.out.println("Slush API Error: API Not initialized!");
            jsonFinal = null;
        }
        return jsonFinal;
    }

    //Retruns if the API is initialized
    public static boolean isApiInitialized() {
    	return initializedApi;
    }
    
    //Getting JSON data from User to SlushUser class. Returns true if succesfull.
    public static boolean getUserData() {
        JsonObject slushObj;
		try {
			slushObj = getJsonObject();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		}
        SlushUser.username = slushObj.get("username").getAsString();
        SlushUser.wallet = slushObj.get("wallet").getAsString();
        SlushUser.hashrate = slushObj.get("hashrate").getAsString();
        SlushUser.send_threshold = slushObj.get("send_threshold").getAsString();
        SlushUser.unconfirmed_reward = slushObj.get("unconfirmed_reward").getAsString();
        SlushUser.confirmed_reward = slushObj.get("confirmed_reward").getAsString();
        SlushUser.estimated_reward = slushObj.get("estimated_reward").getAsString();
        SlushUser.total_reward =  Double.toString(Double.parseDouble(SlushUser.unconfirmed_reward) + Double.parseDouble(SlushUser.confirmed_reward));
        return true;
    }

    //Getting an ArrayList containing the workers, needs to be initialized by getWorkers();
    public static ArrayList<SlushWorker> getWorkerList() {
        if(workerList != null) {
        	return workerList;
        } else {
        	System.out.println("Slush API Error: Worker list has not been initialized!");
        	return null;
        	
        }
    }
    
    //Function for testing JSON capabilities
    public static String testJson() {
        String JsonString = getJsonString(testJson);
        if(passedJson) {
            JsonElement slushJson = new JsonParser().parse(JsonString);
            JsonObject testObj = slushJson.getAsJsonObject();
            resetJsonPassed();
            String s = testObj.get("userId").getAsString();
            if(s == null) {
                System.out.println("Slush API Error: String resulted in NULL, this shouldn`t happen!");
                return null;
            } else {
                return s;
            }

        } else {
            resetJsonPassed();
            return null;
        }
    }

    //Reset JSON passed
    private static void resetJsonPassed() {
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
