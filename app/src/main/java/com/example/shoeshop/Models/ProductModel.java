package com.example.shoeshop.Models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    public String id, productName, productBrand, productDescription, productImage;
    public Integer productQuantity;
    public Double productPrice;

    public ProductModel() {
    }
    public ProductModel(String productId, String productName, String productBrand, String productDescription, Integer productQuantity, Double productPrice, String productImage) {
        this.productName = productName;
        this.productBrand = productBrand;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.id = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
