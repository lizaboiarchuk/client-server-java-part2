package com.labs.lab4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            DBService service = new DBService("localhost", 3306, "shop", "user", "PASSWORD");

            // create products
            service.createProduct("Milk", 3, 25.99, "Dairy");
            service.createProduct("Tomato", 200, 34.99, "Grocery");
            service.createProduct("Cucumber", 3, 32.99, "Grocery");
            service.createProduct("Carrot", 50, 22.99, "Grocery");
            printProducts(service.readProducts());


            // update product
            Product newTomato = new Product(2, "Tomato", 99,34.99, "Grocery");
            service.updateProduct(newTomato);
            printProducts(service.readProducts());


            // delete product
            service.deleteProduct(4);
            printProducts(service.readProducts());


            // select with filters
            Map<String,Object> filters = new HashMap<String, Object>(){{ put("amount", 3); }};
            System.out.println("\nSelected products: ");
            for (var p: service.listByCriteria(filters)) {
                System.out.println(p);
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void printProducts(List<Product> products) {
        System.out.println("\nCurrent products: ");
        for (var p: products) {
            System.out.println(p);
        }
        System.out.println("-----");
    }

}