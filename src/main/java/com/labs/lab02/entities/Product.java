package com.labs.lab02.entities;

public class Product {
    private int amount;
    private int price;
    private Group group;


    Product(int amount, int price, Group group) {
        this.amount = amount;
        this.price = price;
        this.group = group;
    }

    public void setGroup(Group group) { this.group = group; }
    public Group getGroup() { return group; }

    public void setPrice(int price)  { this.price = price; }
    public int getPrice() { return price; }

    public void setAmount(int amount) { this.amount = amount; }
    public int getAmount() { return amount; }
}
