package com.labs.lab02.entities;

import java.lang.reflect.Array;

public class Shop {
    private Product[] products;
    private Group[] groups;

    Shop() {
        this.products = new Product[0];
        this.groups = new Group[0];
    }
}
