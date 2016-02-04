package com.example.mipo.model;

import android.graphics.Bitmap;

/**
 * Created by Sprintzin on 03/02/2016.
 */
public class ProfileBean {

    private String name;
    private String objectId;
    private String on_off;
    private String distance;
    private String seen;
    private String age;
    private String status;
    private String height;
    private String weight;
    private String nation;
    private String body_type;
    private String relationship_status;
    private String looking_for;
    private String about;
    private String imageId;
    private Bitmap pic;
    private String message_roomId;
    private String distanceType;
    private boolean isFavorite;
    private boolean isFilteredeOk;

    public ProfileBean(String name, String objectId, String on_off, String distance, String seen, String age, String status, String height, String weight, String nation, String body_type, String relationship_status, String looking_for, String about, String imageId, Bitmap pic, String message_roomId, String distanceType, boolean isFavorite, boolean isFilteredeOk) {

        this.name = name;
        this.objectId = objectId;
        this.on_off = on_off;
        this.distance = distance;
        this.seen = seen;
        this.age = age;
        this.status = status;
        this.height = height;
        this.weight = weight;
        this.nation = nation;
        this.body_type = body_type;
        this.relationship_status = relationship_status;
        this.looking_for = looking_for;
        this.about = about;
        this.imageId = imageId;
        this.pic = pic;
        this.message_roomId = message_roomId;
        this.distanceType = distanceType;
        this.isFavorite = isFavorite;
        this.isFilteredeOk = isFilteredeOk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getOn_off() {
        return on_off;
    }

    public void setOn_off(String on_off) {
        this.on_off = on_off;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public String getMessage_roomId() {
        return message_roomId;
    }

    public void setMessage_roomId(String message_roomId) {
        this.message_roomId = message_roomId;
    }

    public String getDistanceType() {
        return distanceType;
    }

    public void setDistanceType(String distanceType) {
        this.distanceType = distanceType;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isFilteredeOk() {
        return isFilteredeOk;
    }

    public void setIsFilteredeOk(boolean isFilteredeOk) {
        this.isFilteredeOk = isFilteredeOk;
    }
}
