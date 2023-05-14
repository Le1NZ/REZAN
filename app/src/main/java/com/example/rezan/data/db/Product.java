package com.example.rezan.data.db;

public class Product {

    Product () { }

    private String name;
    private Integer cost;
    private String photo;

    public Product(String name, Integer cost, String photo) {
        this.name = name;
        this.cost = cost;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public Integer getCost() {
        return cost;
    }

    public String getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
