/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baawaproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author rohanayan
 */
public class IncomeTableQuery {
    private Connection connection;
    
    public IncomeTableQuery(){
        connection = DatabaseConnector.Connector();
    }
    
    public boolean isConnected(){
        boolean closed = false;
        try {
            closed = connection.isClosed();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return !closed;
    }
    
    public ObservableList<IncomeTable> selectIncomeTable(String query){
        ObservableList<IncomeTable> list = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                list.add(new IncomeTable(Integer.parseInt(resultSet.getString(1)), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }finally{
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return list;
    }
    
    public int updateIncomeTable(String query){
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }finally{
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return result;
    }
}
