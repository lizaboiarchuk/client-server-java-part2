package com.labs.lab5;

import com.labs.lab4.DBService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        DBService dc = new DBService("localhost", 3306, "shop", "user", "PASSWORD");
        Server s = new Server(8893, dc);
    }
}