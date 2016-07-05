# SLUSHAPI
A java API for the slushpool. 



## Initialize API: 

Use: `SlushApi api = new SlushApi("API-URL (Default is: https://slushpool.com/accounts/profile/json/)","API-KEY");` to initialize the api.

## User:

Use `SlushUser user = new SlushUser(api);` to initialize a user. To get the data of the user call: `user.getUserData()` To access this data use the following functions:
```
user.getHashrate();
user.getConfirmedReward();
user.getEstimatedReward();
user.getSendThreshold();
user.getTotalReward();
user.getUnconfirmedReward();
user.getUsername();
user.getWallet()
```

## Workers:

Use: `SlushWorkerManager swm = new SlushWorkerManager(api);` to initialize the Worker Manager class. You can use this class to get the data of a worker, for example:
```
SlushWorkerManager swm = new SlushWorkerManager(api);
ArrayList<String> data = new ArrayList<String>();
    for (SlushWorker worker : swm.getWorkers()) {
    data.add(worker.getName());
    data.add(worker.getAlive().toString());
    data.add(String.valueOf(worker.getHashrate()));
    data.add(worker.getLastShare());
    data.add(worker.getScore());
    data.add(String.valueOf(worker.getShares()));
}
```
To get all the data of the user in an ArrayList.

## Dependicies:

This project depends on the GSON library. 

## Credits:

[Famicoman](https://github.com/Famicoman)


