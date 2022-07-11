package com.labs.lab4;


import java.io.FilterOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Service {
    private Connection conn;

    Service(Connection conn) {
        this.conn = conn;
        System.out.println("---Created service and connected to db---");
    }


    public void createProduct(String title, int amount, double price, String group) throws SQLException {
        PreparedStatement query = conn.prepareStatement("INSERT INTO product(title,amount,price,category) VALUES (?,?,?,?)");
        query.setString(1, title);
        query.setInt(2, amount);
        query.setDouble(3, price);
        query.setString(4, group);
        query.execute();
        System.out.println("Inserted product");
    }


    public List<Product> readProducts() throws SQLException {
        PreparedStatement query = conn.prepareStatement("SELECT * FROM product;");
        ResultSet resultSet = query.executeQuery();
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Product product = new Product(  resultSet.getInt("id"),
                                            resultSet.getString("title"),
                                            resultSet.getInt("amount"),
                                            resultSet.getDouble("price"),
                                            resultSet.getString("category"));
            products.add(product);
        }
        return products;
    }


    public void updateProduct(Product product) throws SQLException {
        PreparedStatement query = conn.prepareStatement("UPDATE product SET title=?,amount=?,price=?,category=? WHERE id=?;");
        query.setString(1, product.getTitle());
        query.setInt(2, product.getAmount());
        query.setDouble(3, product.getPrice());
        query.setString(4, product.getCategory());
        query.setInt(5, product.getId());
        query.executeUpdate();
        System.out.println("Updated product");
    }


    public void deleteProduct(int id) throws SQLException {
        PreparedStatement query = conn.prepareStatement("DELETE FROM product WHERE id=?;");
        query.setInt(1, id);
        query.executeUpdate();
        System.out.println("Deleted product");
    }



    public List<Product> listByCriteria(Map<String,Object> filters) throws SQLException {
        String queryString = "SELECT * FROM product WHERE";
        Object[] keys = filters.keySet().toArray();
        for (int i=0; i < keys.length; i++) {
            queryString = queryString + " " + keys[i] + " = ";
            switch (filters.get(keys[i]).getClass().getSimpleName()) {
                case "Integer":
                    queryString = queryString + filters.get(keys[i]) + ",";
                    break;
                case "String":
                    queryString = queryString + "'" + filters.get(keys[i]) + "',";
                    break;
                case "Double":
                    queryString = queryString + "'" + filters.get(keys[i]) + "',";
                    break;
            }
        }
        queryString = queryString.substring(0, queryString.length() - 1);
        queryString = queryString + ";";
        System.out.println("Query: " + queryString);
        PreparedStatement query = conn.prepareStatement(queryString);
        ResultSet resultSet = query.executeQuery();
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Product product = new Product(  resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getInt("amount"),
                    resultSet.getDouble("price"),
                    resultSet.getString("category"));
            products.add(product);
        }
        return products;
    }

    public void clearTable() throws SQLException {
        conn.prepareStatement("DELETE FROM product").execute();
        conn.prepareStatement("ALTER TABLE product AUTO_INCREMENT = 1").execute();
    }

    public void createTable() throws SQLException {
        PreparedStatement query = conn.prepareStatement("CREATE TABLE IF NOT EXISTS product (id INT AUTO_INCREMENT PRIMARY KEY, title text, amount int, price double, category text);");
        query.execute();
    }

}
