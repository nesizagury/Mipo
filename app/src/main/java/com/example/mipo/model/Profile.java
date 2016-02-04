package com.example.mipo.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by Sprintzin on 01/02/2016.
 */
@ParseClassName("Profile")
public class Profile extends ParseObject {



//    public String getId(){
//        return getString("id");
//    }
//    public void setId(String id){
//        put("id", id);
//    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getAge(){
        return getString("age");
    }
    public void setAge(String age){
        put("age", age);
    }

    public String getOn_off(){
        return getString("on_off");
    }
    public void setOn_off(String on_off){
        put("on_off", on_off);
    }

    public String getDistance(){
        return getString("distance");
    }
    public void setDistance(String distance){
        put("distance", distance);
    }
    public String getSeen(){
        return getString("seen");
    }
    public void setSeen(String seen){
        put("seen", seen);
    }
    public String getStatus(){
        return getString("status");
    }
    public void setStatus(String status){
        put("status", status);
    }

    public String getHeight(){
        return getString("height");
    }
    public void setHeight(String height){
        put("height", height);
    }
    public String getWeight(){
        return getString("weight");
    }
    public void setWeight(String weight){
        put("weight", weight);
    }
    public String getLooking_for(){
        return getString("looking_for");
    }
    public void setLooking_for(String looking_for){
        put("looking_for", looking_for);
    }
    public String getBody_type(){
        return getString("body_type");
    }
    public void setBody_type(String body_type){
        put("body_type", body_type);
    }
    public String getRelationship_status(){
        return getString("relationship_status");
    }
    public void setRelationship_status(String relationship_status){
        put("relationship_status", relationship_status);
    }

    public String getNation(){
        return getString("nation");
    }
    public void setNation(String nation){
        put("nation", nation);
    }
    public String getAbout(){
        return getString("about");
    }
    public void setAbout(String about){
        put("about", about);
    }
    public String getMessage_roomId(){
        return getString("message_roomId");
    }
    public void setMessage_roomId(String message_roomId){
        put("message_roomId", message_roomId);
    }

    public String getDistanceType(){
        return getString("distanceType");
    }
    public void setDistanceType(String distanceType){
        put("distanceType", distanceType);
    }

    public String getImageId() {
        return getString("imageId");
    }

    public void setImageId(String imageId) {
        put("imageId", imageId);
    }

    public ParseFile getPic(){
        return getParseFile("pic");
    }
    public void setPic(ParseFile file) {
        put("pic",file);
    }


    public boolean isFavorite(){
        return getBoolean("isFavorite");
    }
    public void setFavorite(boolean isFavorite){
        put("isFavorite", isFavorite);
    }

    public boolean isFilteredOK(){
        return getBoolean("isFilteredOK");
    }
    public void setFilteredOK(boolean isFilteredOK){
        put("isFilteredOK", isFilteredOK);
    }


}
