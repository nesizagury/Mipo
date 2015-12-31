package com.example.aaa;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by מנהל on 21/12/2015.
 */@ParseClassName("Room")
public class Room extends ParseObject{

    public String getUserId() {
        return getString("userId");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setDes(int des) {

        put("des", des);
    }

    public int getDes(){
        return getInt("des");
    }

    public void setDes2(String des2) {
        put("des2", des2);
    }

    public String getDes2(){
        return getString("des2");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getName(){
        return getString("name");
    }


    public void setConversationId(int ConversationId){

        put("ConversationId",ConversationId);
    }


    public int getConversationId(){

        return getInt("ConversationId");

    }

}
