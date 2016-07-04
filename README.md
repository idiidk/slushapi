# SLUSHAPI
A java API for the slushpool. 

### Initialize API with: 

`SlushApi.initializeApi("API-KEY", Slush JSON URL default is: "https://slushpool.com/accounts/profile/json/");`

Use `SlushApi.getUserData();` to get the data of the user, can be accesed by using the SlushUser class. For example: `SlushUser.username();`

NOT TESTED YET: Use `SlushApi.getWorkers();` to get an ArrayList of the workers. Simple Example: 
```
ArrayList<SlushWorker> workers = SlushApi.getWorkers();

workers.get(0).getName();
```

### DEPENDICIES:
This project depends on the GSON library. 



