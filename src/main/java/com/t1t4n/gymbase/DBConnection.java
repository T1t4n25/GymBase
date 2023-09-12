package com.t1t4n.gymbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    static Connection connection = null;
    static Statement statement = null;
    static Statement statement2 = null;



    public static void getConnect () {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gymdata",
                    "zeyad",
                    "test");
            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement2 = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
