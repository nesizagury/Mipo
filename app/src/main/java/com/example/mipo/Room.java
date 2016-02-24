package com.example.mipo;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Room")
public class Room extends ParseObject {

    public String getLastMessage() {
        return getString ("lastMessage");
    }

    public void setLastMessage(String lastMessage) {
        put ("lastMessage", lastMessage);
    }

    public void setUserNum1(String userNum1) {
        put ("userNum1", userNum1);
    }

    public String getUserNum1() {
        return getString ("userNum1");
    }

    public void setUserNum2(String userNum2) {
        put ("userNum2", userNum2);
    }

    public String getUserNum2() {
        return getString ("userNum2");
    }
}
