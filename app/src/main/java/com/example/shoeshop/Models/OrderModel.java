package com.example.shoeshop.Models;

import com.google.type.DateTime;

import java.time.LocalDateTime;

public class OrderModel {
    String OrderId, UserId, UserName, Phone, Address;
    String dtOrder, dtReceived;
    Double TotalPrice;
    String OrderStatus;

    public OrderModel(){

    }

    public OrderModel(String orderId, String userId,String userName, String phone, String address,
                      String dtOrder, String dtReceived, Double totalPrice, String orderStatus) {
        OrderId = orderId;
        UserId = userId;
        UserName = userName;
        Phone = phone;
        Address = address;
        this.dtOrder = dtOrder;
        this.dtReceived = dtReceived;
        TotalPrice = totalPrice;
        OrderStatus = orderStatus;
    }

    public String getOrderId() {
        return OrderId;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDtOrder() {
        return dtOrder;
    }

    public void setDtOrder(String dtOrder) {
        this.dtOrder = dtOrder;
    }

    public String getDtReceived() {
        return dtReceived;
    }

    public void setDtReceived(String dtReceived) {
        this.dtReceived = dtReceived;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }
}
