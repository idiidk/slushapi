package com.kipsensatie.slushapi;

import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by Kipsensatie on 5-7-2016.
 */
public class SlushUser {
    private String ApiKey;
    private String ApiUrl;
    private SlushApi API;

    public String getUsername() {
        return username;
    }

    public String getWallet() {
        return wallet;
    }

    public String getHashrate() {
        return hashrate;
    }

    public String getSendThreshold() {
        return send_threshold;
    }

    public String getConfirmedReward() {
        return confirmed_reward;
    }

    public String getUnconfirmedReward() {
        return unconfirmed_reward;
    }

    public String getEstimatedReward() {
        return estimated_reward;
    }

    public String getTotalReward() {
        return total_reward;
    }

    private String username = "";
    private String wallet = "";
    private String hashrate = "";
    private String send_threshold = "";
    private String confirmed_reward = "";
    private String unconfirmed_reward = "";
    private String estimated_reward = "";
    private String total_reward = "";
    
    public SlushUser(SlushApi slushApi) {
        API = slushApi;
    }



    public boolean getUserData() {
        JsonObject slushObj;
        if (API == null) {
            return false;
        } else {
            try {
                slushObj = API.getJsonObject();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            }
            username = slushObj.get("username").getAsString();
            wallet = slushObj.get("wallet").getAsString();
            hashrate = slushObj.get("hashrate").getAsString();
            send_threshold = slushObj.get("send_threshold").getAsString();
            unconfirmed_reward = slushObj.get("unconfirmed_reward").getAsString();
            confirmed_reward = slushObj.get("confirmed_reward").getAsString();
            estimated_reward = slushObj.get("estimated_reward").getAsString();
            total_reward = Double.toString(Double.parseDouble(unconfirmed_reward) + Double.parseDouble(confirmed_reward));
        }
        return true;
    }
}
