package com.example.mohamed.blindapp.oneSignal.model;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 12/12/2017.  time :10:58
 */

public class ButtonData {
    private String id;
    private String text;

    public ButtonData(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
