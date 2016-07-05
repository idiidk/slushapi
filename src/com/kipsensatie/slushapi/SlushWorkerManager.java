package com.kipsensatie.slushapirb;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Kipsensatie on 5-7-2016.
 */
public class SlushWorkerManager {
    SlushApi API;

    public SlushWorkerManager(SlushApi slushAPI) {
        API = slushAPI;
    }

    public ArrayList<SlushWorker> getWorkers() {
        ArrayList<SlushWorker> workerList = new ArrayList<SlushWorker>();
        workerList.clear();
        JsonObject worker;
        try {
            if(API.getJsonObject() != null) {
                worker = API.getJsonObject().get("workers").getAsJsonObject();
            } else {
                return null;
            }

        } catch (Exception e) {
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
}
