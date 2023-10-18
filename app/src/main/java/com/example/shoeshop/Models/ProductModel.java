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

    public ProductModel(String productName, String productImage, Float productPrice) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }
}
