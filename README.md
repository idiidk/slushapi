# SLUSHAPI
A java API for the slushpool. 

### Initialize API with: 

`SlushApi.initializeApi("API-KEY", Slush JSON URL default is: "https://slushpool.com/accounts/profile/json/");`

Use `SlushApi.getUserData();` to get the data of the user, can be accesed by using the SlushUser class. For example: `SlushUser.username();`

--
### Workers:

Use: `SlushApi.getWorkers();` to get an ArrayList of the workers. Simple Example: 
```
ArrayList<SlushWorker> workers = SlushApi.getWorkers();
if(workers != null) {
  String s = workers.get(0).getName();
}

```
--

### Dependicies:
This project depends on the GSON library. 

--
###Credits:
[Famicoman](https://github.com/Famicoman)


