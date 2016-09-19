package com.javalab.marketing.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {

    /*
    This method create connection between web app and database
    After create it return connection in dao or another for further work with data base
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfordb", "root", "882501");
        return connection;
    }

}
