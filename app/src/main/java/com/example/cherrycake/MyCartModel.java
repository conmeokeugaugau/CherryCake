package com.example.cherrycake;

public class MyCartModel {

    String anh;
    String name;
    int price;
    String totalQuantity;


    public MyCartModel() {
    }

    public MyCartModel(String anh, String name, int price, String totalQuantity) {
        this.anh = anh;
        this.name = name;
        this.price = price;
        this.totalQuantity = totalQuantity;
    }


    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
