package com.example.shoeshop;

public class productModel  {
    String img, title,brand, discription;
    double cost, quantity;

    public productModel(String img, String title, double cost) {
        this.img = img;
        this.title = title;
        this.cost = cost;
    }

    public productModel(String img, String title, String brand, String discription, double cost, double quantity) {
        this.img = img;
        this.title = title;
        this.brand = brand;
        this.discription = discription;
        this.cost = cost;
        this.quantity = quantity;
    }

    public productModel() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    /*public static String getTitle() {
        return title;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
