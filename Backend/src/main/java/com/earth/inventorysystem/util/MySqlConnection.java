package com.earth.inventorysystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
    private static final String db_url = "jdbc:mysql://csproject.sit.kmutt.ac.th:3306/db63130500205";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || !connection.isValid(200)) {
            return reConnect();
        }
        return connection;
    }

    public static Connection reConnect() throws SQLException {
        connection = DriverManager.getConnection(db_url, "63130500205", "abcd1234");
        return connection;
    }
}