package com.example.shoeshop.Models;

import android.content.Intent;

import java.io.Serializable;

public class CartModel implements Serializable {
    public String Id, UserId, ProductId;
    Float Price;
    Integer Quantity;
    public CartModel(){

    }

    public CartModel(String id, String userId, String productId, Float price, Integer quantity) {
        Id = id;
        UserId = userId;
        ProductId = productId;
        Price = price;
        Quantity = quantity;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }
}
