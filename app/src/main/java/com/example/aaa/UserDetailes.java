package com.example.aaa;

/**
 * Created by מנהל on 15/12/2015.
 */
public class UserDetailes {


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
    int image_source;
    int message_roomId;

    public UserDetailes() {

    }



    public UserDetailes(String name, String id, String on_off,String distance ,String seen,String age , String status,
                        String height, String weight, String nation, String body_type, String relationship_status,
                        String looking_for, String about, int image_source,int message_roomId) {
        this.id = id;
        this.name = name;
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
        this.image_source = image_source;
        this.message_roomId = message_roomId;

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

    public void setOn_off(String on_off){
        this.on_off = on_off;
    }

    public String getOn_off() {
        return on_off;
    }

    public int getImage_source(){
        return image_source;
    }

    public void setImage_source(int image_source){
        this.image_source = image_source;
    }


    public int getMessage_roomId() {
        return message_roomId;
    }

    public void setMessage_roomId(int message_roomId) {
        this.message_roomId = message_roomId;
    }
}
