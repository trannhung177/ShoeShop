package com.example.shoeshop.Models;

import android.content.Intent;

import java.io.Serializable;

public class CartModel implements Serializable {
    private String Id, UserId, ProductId, ProductName, ProductImage;
    private Double Price;
    private Integer Quantity;
    private Integer Size;
    private boolean IsCheckCart;

    public CartModel() {
    }

    public Integer getSize() {
        return Size;
    }

    public void setSize(Integer size) {
        Size = size;
    }

    public CartModel(String id, String userId, String productId, String productName,
                     String productImage, Double price, Integer productQuantity, Boolean isCheckCart, Integer size) {
        Id = id;
        UserId = userId;
        ProductId = productId;
        Price = price;
        Quantity = productQuantity;
        ProductName = productName;
        ProductImage = productImage;
        IsCheckCart = isCheckCart;
        Size = size;
    }

    public boolean isCheckCart() {
        return IsCheckCart;
    }

    public void setCheckCart(boolean checkCart) {
        IsCheckCart = checkCart;
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

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }
}
