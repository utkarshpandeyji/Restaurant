package com.example.adilkhan.restraunt.Model;

import java.util.List;

public class RequestModel {

    private String phone;
    private String name;
    private String address;
    private String total;
    private String status;
    private String comment;
    private List<OrderModel> foods;

    public RequestModel() {
    }

    public RequestModel(String phone, String name, String address, String total, String status, String comment, List<OrderModel> foods) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.status = status;
        this.comment = comment;
        this.foods = foods;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<OrderModel> getFoods() {
        return foods;
    }

    public void setFoods(List<OrderModel> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", total='" + total + '\'' +
                ", foods=" + foods +
                '}';
    }
}
