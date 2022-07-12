package com.labs.lab4;

public class Product {

    private int id;
    private String title;
    private int amount;
    private double price;
    private String category;


    Product(){}

    Product(int id, String name, int amount, double price, String group) {
        this.id = id;
        this.title = name;
        this.amount = amount;
        this.price = price;
        this.category = group;
    }

    Product(String name, int amount, double price, String group) {
        this.title = name;
        this.amount = amount;
        this.price = price;
        this.category = group;
    }



    public void setId(int id) { this.id = id; }
    public int getId() { return this.id; }

    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return this.title; }

    public void setCategory(String category) { this.category = category; }
    public String getCategory() { return category; }

    public void setPrice(double price)  { this.price = price; }
    public double getPrice() { return price; }

    public void setAmount(int amount) { this.amount = amount; }
    public int getAmount() { return amount; }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", group='" + category +  '}';
    }


    public Boolean validate() {
        if (title.length() == 0) { return false; }
        if (amount < 0) { return false; }
        if (price < 0) { return false; }
        return true;
    }
}
