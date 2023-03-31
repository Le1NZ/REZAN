package com.example.rezan;

public class News {

    News() {

    }

    private String photo_1;
    private String photo_2;
    private String head;
    private String desc;

    public String getPhoto_1() {
        return photo_1;
    }

    public String getPhoto_2() {
        return photo_2;
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

    public void setPhoto_2(String photo_2) {
        this.photo_2 = photo_2;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public News(String photo_1, String photo_2, String head, String desc) {
        this.photo_1 = photo_1;
        this.photo_2 = photo_2;
        this.head = head;
        this.desc = desc;
    }
}
