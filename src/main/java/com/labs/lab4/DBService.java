package com.labs.lab4;


import com.labs.lab5.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBService {
    private Connection conn;

    public DBService(String host, int port, String db, String user, String pass) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + db;
            Connection connection = DriverManager.getConnection(connectionString, user, pass);
            this.conn = connection;
            createTable();
            clearTable();
        } catch (Exception e) { e.printStackTrace(); }
    }


    public Integer createProduct(String title, int amount, double price, String group) {
        try {
            PreparedStatement query = conn.prepareStatement("INSERT INTO product(title,amount,price,category) VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            query.setString(1, title);
            query.setInt(2, amount);
            query.setDouble(3, price);
            query.setString(4, group);
            int id = query.executeUpdate();
            ResultSet rs = query.getGeneratedKeys();
            if (rs.next()) { id = rs.getInt(1); }
            return id;
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }


    public List<Product> readProducts() {
        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM product;");
            ResultSet resultSet = query.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("category"));
                products.add(product);
            }
            return products;
        } catch (Exception e) { e.printStackTrace(); }
        return new ArrayList<>();
    }


    public void updateProduct(Product product) {
        try {
            PreparedStatement query = conn.prepareStatement("UPDATE product SET title=?,amount=?,price=?,category=? WHERE id=?;");
            query.setString(1, product.getTitle());
            query.setInt(2, product.getAmount());
            query.setDouble(3, product.getPrice());
            query.setString(4, product.getCategory());
            query.setInt(5, product.getId());
            query.executeUpdate();
            System.out.println("Updated product");
        } catch (Exception e) { e.printStackTrace(); }
    }


    public void deleteProduct(int id) {
        try {
            PreparedStatement query = conn.prepareStatement("DELETE FROM product WHERE id=?;");
            query.setInt(1, id);
            query.executeUpdate();
            System.out.println("Deleted product");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Product getById(int id) {
        Map<String,Object> filters = new HashMap<String, Object>(){{ put("id", id); }};
        List<Product> found = listByCriteria(filters);
        if (found.size() > 0) { return found.get(0); }
        return null;
    }



    public List<Product> listByCriteria(Map<String,Object> filters) {

        try {
            String queryString = "SELECT * FROM product WHERE";
            Object[] keys = filters.keySet().toArray();
            for (int i = 0; i < keys.length; i++) {
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
                Product product = new Product(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("category"));
                products.add(product);
            }
            return products;
        } catch (Exception e) { e.printStackTrace(); }
        return new ArrayList<>();
    }

    public void clearTable() {
        try {
            conn.prepareStatement("DELETE FROM product").execute();
            conn.prepareStatement("ALTER TABLE product AUTO_INCREMENT = 1").execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void createTable() {
        try {
            PreparedStatement query = conn.prepareStatement("CREATE TABLE IF NOT EXISTS product (id INT AUTO_INCREMENT PRIMARY KEY, title text, amount int, price double, category text);");
            query.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }


    public void addUser(User user) {

        try {
            PreparedStatement query = conn.prepareStatement("INSERT INTO user(login, password) VALUES (?,?)");
            query.setString(1, user.getLogin());
            query.setString(2, user.getPassword());
            query.execute();
            System.out.println("Inserted user " + user.getLogin());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public User getUser(String login) {
        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM user WHERE login=?");
            query.setString(1, login);
            ResultSet resultSet = query.executeQuery();
            System.out.println(resultSet);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("login"),
                        resultSet.getString("password"));
                users.add(user);
            }

            System.out.println(users);
            if (users.size() == 0) {
                return null;
            } else {
                return users.get(0);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
