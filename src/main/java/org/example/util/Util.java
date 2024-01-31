package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String url = "jdbc:postgresql://localhost:5432/jdbc";
    private final static String username = "postgres";
    private final static String password = "2020";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,username,password);
            System.out.println("Successfully sunny :)");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }return connection;
    }

}
