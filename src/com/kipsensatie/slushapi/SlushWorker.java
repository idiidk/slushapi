package com.kipsensatie.slushapi;

/**
 * Created by Kipsensatie on 5-7-2016.
 */
public class SlushWorker {
    private String name;
    private int last_share;
    private String score;
    private int shares;
    private int hashrate;
    private Boolean alive;

    public SlushWorker(String n,int ls,String s,int sh,int hr,boolean a) {
        name = n;
        last_share = ls;
        score = s;
        shares = sh;
        hashrate = hr;
        alive = a;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public int getShares() {
        return shares;
    }

    public int getHashrate() {
        return hashrate;
    }

    public Boolean getAlive() {
        return alive;
    }

    public String getLastShare(){
        int SECOND = 1;
        int MINUTE = 60 * SECOND;
        int HOUR = 60 * MINUTE;
        int DAY = 24 * HOUR;

        //Take the current epoch time and subtract the time of the last share epoch time
        long difference = (System.currentTimeMillis()/1000)-last_share;
        StringBuffer text = new StringBuffer("");

        //Now lets divide to figure out days/hours/minutes/seconds
        if (difference > DAY) {
            text.append(difference / DAY).append(" days ");
            difference %= DAY;
        }
        if (difference > HOUR) {
            text.append(difference / HOUR).append(" hours ");
            difference %= HOUR;
        }
        if (difference > MINUTE) {
            text.append(difference / MINUTE).append(" minutes ");
            difference %= MINUTE;
        }
        if (difference > SECOND) {
            text.append(difference / SECOND).append(" seconds ");
            difference %= SECOND;
        }
        else{
            text.append("0 seconds ");
        }

        return text.toString() + "ago";
    }
}
