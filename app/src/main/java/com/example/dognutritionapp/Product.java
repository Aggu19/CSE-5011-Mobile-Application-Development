package com.example.dognutritionapp;

public class Product {
    private int id;
    private String name;
    private String brand;
    private String type;
    private String age;
    private String imageUrl;
    private double price;
    private String description;
    private String reviews;

    public Product(int id, String name, String brand, String type, String age, String imageUrl, double price, String description, String reviews) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.age = age;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public String getAge() {
        return age;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getReviews() {
        return reviews;
    }
}