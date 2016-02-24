package com.example.mipo;

public class MessageRoomBean {

    String name;
    String body;
    String id;
    String picUrl;
    int indexInList;

    public MessageRoomBean(String name, String body, String id, String picUrl, int indexInList) {
        this.name = name;
        this.body = body;
        this.id = id;
        this.picUrl = picUrl;
        this.indexInList = indexInList;
    }

    public int getIndexInList() {
        return indexInList;
    }

    public void setIndexInList(int indexInList) {
        this.indexInList = indexInList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
