package com.example.rezan.data.db;

public class News {

    News() {

    }

    public News(String head, String desc, String photo_1) {
        this.photo_1 = photo_1;
        this.head = head;
        this.desc = desc;
    }

    private String photo_1;
    private String head;
    private String desc;

    public String getPhoto_1() {
        return photo_1;
    }


    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public void setPhoto_1(String photo_1) {
        this.photo_1 = photo_1;
    }


    public void setHead(String head) {
        this.head = head;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
