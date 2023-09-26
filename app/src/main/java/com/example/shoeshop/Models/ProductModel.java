package com.example.shoeshop.Models;

public class ProductModel {
    public String productName, productBrand, productDescription, productImage;
    public Integer productQuantity;
    public Float productPrice;

    public ProductModel() {
    }
    public ProductModel(String productName, String productBrand, String productDescription, Integer productQuantity, Float productPrice, String productImage) {
        this.productName = productName;
        this.productBrand = productBrand;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }
}
