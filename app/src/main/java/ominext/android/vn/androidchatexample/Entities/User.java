package ominext.android.vn.androidchatexample.Entities;

import java.util.Map;

/**
 * Created by MyPC on 18/07/2017.
 */

public class User {
    String id;
    String name;
    boolean online;
    Map<String, Boolean> contacts;
    public final static boolean ONLINE = true;
    public final static boolean OFFLINE = false;

    public User() {
    }

    public User(String id, String name, boolean online, Map<String, Boolean> contacts) {
        this.id = id;
        this.name = name;
        this.online = online;
        this.contacts = contacts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Map<String, Boolean> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, Boolean> contacts) {
        this.contacts = contacts;
    }
}

