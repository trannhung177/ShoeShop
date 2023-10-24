package com.example.shoeshop.Models;

import android.content.Intent;

import java.io.Serializable;

public class CartModel implements Serializable {
    private String Id, UserId, ProductId, ProductName, ProductImage;
    private Float Price;
    private Integer Quantity;

    public CartModel() {
    }

    public CartModel(String id, String userId, String productId, String productName, String productImage, Float price, Integer quantity) {
        Id = id;
        UserId = userId;
        ProductId = productId;
        Price = price;
        Quantity = quantity;
        ProductName = productName;
        ProductImage = productImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
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
