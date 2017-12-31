package com.example.mohamed.blindapp.oneSignal.model;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 11/12/2017.  time :16:40
 */

public class NotificationFilter {
    private String field;
    private String relation;
    private String key;
    private String value;


    public NotificationFilter() {
    }

    public NotificationFilter(String field, String key, String relation, String value) {
        this.field = field;
        this.relation = relation;
        this.key = key;
        this.value = value;
    }

    public NotificationFilter(String field, String relation, String value) {
        this.field = field;
        this.relation = relation;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
