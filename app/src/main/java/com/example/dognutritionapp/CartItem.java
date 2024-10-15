package com.example.dognutritionapp;

public class CartItem {
    private int cartId;
    private String name;
    private String imageUrl;
    private double price;
    private int quantity;

    public CartItem(int cartId, String name, String imageUrl, double price, int quantity) {
        this.cartId = cartId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}