package com.example.mipo;

import android.graphics.Bitmap;

public class UserDetails {


    String id;
    String name;
    String age;
    String on_off;
    String distance;
    String seen;
    String status;
    String height;
    String Weight;
    String nation;
    String body_type;
    String relationship_status;
    String looking_for;
    String about;

    public Bitmap getImageId() {
        return imageId;
    }

    public void setImageId(Bitmap imageId) {
        this.imageId = imageId;
    }

    Bitmap imageId;
    int message_roomId;
    int distanceType;
    private boolean favorite = false;
    boolean isFilteredOK = true;


    public UserDetails(String name, String id, String on_off, String distance, String seen, String age, String status,
                       String height, String weight, String nation, String body_type, String relationship_status,
                       String looking_for, String about, Bitmap imageId, int message_roomId, int distanceType) {
        this.name = name;
        this.id = id;
        this.on_off = on_off;
        this.age = age;
        this.seen = seen;
        this.distance = distance;
        this.status = status;
        this.height = height;
        this.Weight = weight;
        this.nation = nation;
        this.body_type = body_type;
        this.relationship_status = relationship_status;
        this.looking_for = looking_for;
        this.about = about;
        this.imageId = imageId;
        this.message_roomId = message_roomId;
        this.distanceType = distanceType;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBody_type() {
        return body_type;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }

    public String getRelationship_status() {
        return relationship_status;
    }

    public void setRelationship_status(String relationship_status) {
        this.relationship_status = relationship_status;
    }

    public String getLooking_for() {
        return looking_for;
    }

    public void setLooking_for(String looking_for) {
        this.looking_for = looking_for;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setOn_off(String on_off) {
        this.on_off = on_off;
    }

    public String getOn_off() {
        return on_off;
    }

    public int getMessage_roomId() {
        return message_roomId;
    }

    public void setMessage_roomId(int message_roomId) {
        this.message_roomId = message_roomId;
    }

    public int getDistanceType() {
        return distanceType;
    }

    public void setDistanceType(int distanceType) {
        this.distanceType = distanceType;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFilteredOK() {
        return isFilteredOK;
    }

    public void setIsFilteredOK(boolean isFilteredOk) {
        this.isFilteredOK = isFilteredOk;
    }
}
