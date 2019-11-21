/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baawaproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rohanayan
 */
public class DatabaseConnector {
    public static Connection Connector(){
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            con = DriverManager.getConnection("jdbc:sqlite:./resources/aliuber");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return con;
    }
}
