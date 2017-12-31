package com.example.mohamed.blindapp.oneSignal.model;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 12/12/2017.  time :10:16
 */

public class Data {
    private String key ;
    private String val;

    public Data(String key, String val) {
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }
}
