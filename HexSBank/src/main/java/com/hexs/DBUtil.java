// File: DBUtil.java
package com.hexs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections to the HexS Bank MySQL database.
 */
public class DBUtil {

    // JDBC URL to connect to the local MySQL database named 'hexsbank'
    private static final String URL = "jdbc:mysql://localhost:3306/hexsbank";

    // Database username
    private static final String USER = "root";

    // Database password (⚠️ Avoid hardcoding in production systems)
    private static final String PASSWORD = "0813";

    // Static block to load the MySQL JDBC driver when the class is first accessed
    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // If driver is not found, print error details
            System.err.println("❌ MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    /**
     * Establishes and returns a connection to the database using the configured credentials.
     *
     * @return A live Connection object to the hexsbank database
     * @throws SQLException if a connection error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// T.W.M
