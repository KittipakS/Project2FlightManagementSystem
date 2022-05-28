/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuration;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author kitti
 */
public class DatabaseConfiguration {public static Connection connect() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection connection = DriverManager.getConnection("jdbc:derby:flightdb");
            connection.setSchema("APP");
            System.out.println("Connection to Derby has been established.");
            return connection;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
