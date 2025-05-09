package org.example.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static Connection con;

    public static Connection getCon() {
        try {
          //  Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee", "postgres", "1234");

        } catch (SQLException  | RuntimeException e) {
            e.printStackTrace();
        }

        return con;
    }

}
