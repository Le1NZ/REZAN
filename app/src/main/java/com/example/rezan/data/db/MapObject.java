package com.example.rezan.data.db;

public class MapObject {

    private String head;
    private String desc;
    private Double latitude;
    private Double longitude;
    private String photo;

    public MapObject() {
    }

    public MapObject(String head, String desc, Double latitude, Double longitude, String photo) {
        this.head = head;
        this.desc = desc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photo = photo;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getPhoto() {
        return photo;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
