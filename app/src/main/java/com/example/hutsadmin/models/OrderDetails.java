package com.example.hutsadmin.models;

public class OrderDetails {


    public OrderDetails() {
    }

    private String name;
    private int newPrice;
    private int quantity ;

    public OrderDetails(String name, int newPrice, int quantity) {
        this.name = name;
        this.newPrice = newPrice;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(int newPrice) {
        this.newPrice = newPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
