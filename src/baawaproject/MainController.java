/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baawaproject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author rohanayan
 */
public class MainController implements Initializable {
    private IncomeTableQuery databaseQuery = new IncomeTableQuery();
    private FXMLLoader incomeLoader;
    private FXMLLoader expenseLoader;
    private FXMLLoader incomeExpenseLoader;
    private Stage incomeStage;
    private Stage expenseStage;
    private Stage incomeExpenseStage;
    private Stage stage;
    
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnViewLeaseTable;
    @FXML
    private Button btnViewExpenseTable;
    @FXML
    private Button btnViewIncomeSummary;
    @FXML
    private Button btnExportData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(databaseQuery.isConnected()){
            lblStatus.setText("Connected");
        }else{
            lblStatus.setText("Not Connected");
        }
        incomeLoader = initLoader("IncomeView.fxml");
        incomeStage = initStage(incomeLoader, false);
        incomeStage.setTitle("Income Table");
        
        expenseLoader = initLoader("ExpenseView.fxml");
        expenseStage = initStage(expenseLoader, false);
        expenseStage.setTitle("Expense Table");
        
        incomeExpenseLoader = initLoader("IncomeExpenseView.fxml");
        incomeExpenseStage = initStage(incomeExpenseLoader, false);
        incomeExpenseStage.setTitle("Income Expense Summary");
    }    

    @FXML
    private void onBtnViewLeaseTablePressed(ActionEvent event) {
        incomeStage.show();
    }
    
    @FXML
    private void onBtnViewExpenseTablePressed(ActionEvent event) {
        expenseStage.show();
    }
    
    @FXML
    void onBtnViewIncomeSummaryPressed(ActionEvent event) {
        incomeExpenseStage.show();
    }
    
    @FXML
    private void onBtnExportDataPressed(ActionEvent event) {
        ExpenseController expenseController = expenseLoader.getController();
        IncomeController incomeController = incomeLoader.getController();
        ObservableList<ExpenseTable> listOfExpenseRecords = expenseController.getExpenseList();
        ObservableList<IncomeTable> listOfIncomeRecords = incomeController.getIncomeList();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csv file", "(*.csv)"));
        fileChooser.setInitialFileName("vsgData.csv");

        File fileToSave = fileChooser.showSaveDialog(incomeStage);
        if(fileToSave != null){
            try {
                PrintWriter writer;
                writer = new PrintWriter(fileToSave);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("*****EXPENSE TABLE*****\n");
                stringBuilder.append("Account,");
                stringBuilder.append("Category,");
                stringBuilder.append("Description,");
                stringBuilder.append("Amount,");
                stringBuilder.append("Date,");
                stringBuilder.append("Paid By,");
                stringBuilder.append("Paid To,");
                stringBuilder.append("GST");
                stringBuilder.append("\n");
                for(ExpenseTable expenseRecord: listOfExpenseRecords){
                    stringBuilder.append(expenseRecord.getAccount()); stringBuilder.append(",");
                    stringBuilder.append(expenseRecord.getCategory()); stringBuilder.append(",");
                    stringBuilder.append(expenseRecord.getDescription()); stringBuilder.append(",");
                    stringBuilder.append(expenseRecord.getAmount()); stringBuilder.append(",");
                    stringBuilder.append(expenseRecord.getDate()); stringBuilder.append(",");
                    stringBuilder.append(expenseRecord.getPaidby()); stringBuilder.append(",");
                    stringBuilder.append(expenseRecord.getPaidto()); stringBuilder.append(",");
                    stringBuilder.append(expenseRecord.getGst());
                    stringBuilder.append("\n");
                }
                stringBuilder.append("*****INCOME TABLE*****\n");
                stringBuilder.append("Date,");
                stringBuilder.append("Account,");
                stringBuilder.append("Client,");
                stringBuilder.append("From,");
                stringBuilder.append("To,");
                stringBuilder.append("Received,");
                stringBuilder.append("Actual,");
                stringBuilder.append("Comment,");
                stringBuilder.append("GST");
                stringBuilder.append("\n");
                for(IncomeTable incomeRecord: listOfIncomeRecords){
                    stringBuilder.append(incomeRecord.getDate()); stringBuilder.append(",");
                    stringBuilder.append(incomeRecord.getAccount()); stringBuilder.append(",");
                    stringBuilder.append(incomeRecord.getClient()); stringBuilder.append(",");
                    stringBuilder.append(incomeRecord.getFrom()); stringBuilder.append(",");
                    stringBuilder.append(incomeRecord.getTo()); stringBuilder.append(",");
                    stringBuilder.append(incomeRecord.getReceived()); stringBuilder.append(",");
                    stringBuilder.append(incomeRecord.getActual()); stringBuilder.append(",");
                    stringBuilder.append(incomeRecord.getComment()); stringBuilder.append(",");
                    stringBuilder.append(incomeRecord.getGst());
                    stringBuilder.append("\n");
                }
                stringBuilder.append("*****DATA END*****\n");
                writer.println(stringBuilder);
                writer.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    private Stage initStage(FXMLLoader loader, boolean undecorate){
        Stage stage = new Stage();
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            if(undecorate){
                stage.initStyle(StageStyle.UNDECORATED);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return stage;
    }
    
    private FXMLLoader initLoader(String fxmlFilename){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFilename));
        return loader;
    }
    
}
