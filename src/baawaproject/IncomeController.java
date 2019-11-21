/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baawaproject;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author rohanayan
 */
public class IncomeController implements Initializable {
    private IncomeTableQuery incomeTableQuery = new IncomeTableQuery();
    private ObservableList<IncomeTable> listOfIncomeTableRecords = FXCollections.observableArrayList();
    private ObservableList<String> accountList = FXCollections.observableArrayList();
    private ObservableList<String> clientList = FXCollections.observableArrayList();
    
    
    @FXML
    private TableView<IncomeTable> incomeTableView;
    @FXML
    private TableColumn<IncomeTable, Integer> id;
    @FXML
    private TableColumn<IncomeTable, String> account;
    @FXML
    private TableColumn<IncomeTable, String> client;
    @FXML
    private TableColumn<IncomeTable, String> from;
    @FXML
    private TableColumn<IncomeTable, String> to;
    @FXML
    private TableColumn<IncomeTable, String> received;
    @FXML
    private TableColumn<IncomeTable, String> actual;
    @FXML
    private TableColumn<IncomeTable, String> date;
    @FXML
    private TableColumn<IncomeTable, String> comment;
    @FXML
    private TableColumn<IncomeTable, String> gst;
    @FXML
    private ComboBox<String> cmboAccount;
    @FXML
    private ComboBox<String> cmboClient;
    @FXML
    private TextField txtFieldAccount;
    @FXML
    private TextField txtFieldClient;
    @FXML
    private DatePicker dtPckerFrom;
    @FXML
    private DatePicker dtPickerTo;
    @FXML
    private TextField txtFieldReceived;
    @FXML
    private TextField txtFieldActual;
    @FXML
    private TextField txtFieldReceivedTotal;
    @FXML
    private TextField txtFieldActualTotal;
    @FXML
    private TextField txtFieldGstTotal;
    @FXML
    private DatePicker dtPickerDate;
    @FXML
    private TextArea txtAreaComments;
    @FXML
    private Button btnAddRecord;
    @FXML
    private Button btnDeleteRecord;
    @FXML
    private CheckBox chkBoxGst;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        incomeTableView.setEditable(true);
        account.setCellFactory(TextFieldTableCell.forTableColumn());
        client.setCellFactory(TextFieldTableCell.forTableColumn());
        from.setCellFactory(TextFieldTableCell.forTableColumn());
        to.setCellFactory(TextFieldTableCell.forTableColumn());
        received.setCellFactory(TextFieldTableCell.forTableColumn());
        actual.setCellFactory(TextFieldTableCell.forTableColumn());
        date.setCellFactory(TextFieldTableCell.forTableColumn());
        comment.setCellFactory(TextFieldTableCell.forTableColumn());
        gst.setCellFactory(TextFieldTableCell.forTableColumn());

        accountList.add("All");
        setupComboLists();
        
        cmboAccount.setItems(accountList);
        cmboClient.setItems(clientList);
        
        setupLeaseTable();
        
        dtPckerFrom.setValue(LocalDate.now());
        dtPickerTo.setValue(LocalDate.now());
        dtPickerDate.setValue(LocalDate.now());
        txtFieldActual.setText("0");
        txtFieldReceived.setText("0");
        
        txtFieldReceived.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*.?\\d{0,2}")) {
                txtFieldReceived.setText(oldValue);
            }
        });
        
        txtFieldActual.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*.?\\d{0,2}")) {
                txtFieldActual.setText(oldValue);
            }
        });

        cmboAccount.setValue("All");
        calcTotalReceivedAndActual();
    }
    
    private void setupLeaseTable(){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        account.setCellValueFactory(new PropertyValueFactory<>("account"));
        client.setCellValueFactory(new PropertyValueFactory<>("client"));
        from.setCellValueFactory(new PropertyValueFactory<>("from"));
        to.setCellValueFactory(new PropertyValueFactory<>("to"));
        received.setCellValueFactory(new PropertyValueFactory<>("received"));
        actual.setCellValueFactory(new PropertyValueFactory<>("actual"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        gst.setCellValueFactory(new PropertyValueFactory<>("gst"));
        incomeTableView.setItems(listOfIncomeTableRecords);
    }
    
    private void setupComboLists(){
        listOfIncomeTableRecords = incomeTableQuery.selectIncomeTable("select * from income");
        for(int i=0; i<listOfIncomeTableRecords.size(); i++){
            String account = listOfIncomeTableRecords.get(i).getAccount();
            if(!accountList.contains(account)){
                accountList.add(account);
            }
        }
        cmboAccount.setItems(accountList);
    }
    
    private void calcTotalReceivedAndActual(){
        double totalReceived = 0.0;
        double totalActual = 0.0;
        double totalGst = 0.0;
        for(int i=0; i<listOfIncomeTableRecords.size(); i++){
            totalReceived += Double.parseDouble(listOfIncomeTableRecords.get(i).getReceived());
            totalActual += Double.parseDouble(listOfIncomeTableRecords.get(i).getActual());
            totalGst += Double.parseDouble(listOfIncomeTableRecords.get(i).getGst());
        }
        txtFieldReceivedTotal.setText(Double.toString(round(totalReceived)));
        txtFieldActualTotal.setText(Double.toString(round(totalActual)));
        txtFieldGstTotal.setText(Double.toString(round(totalGst)));
    }

    @FXML
    public void onCmboAccountPressed(ActionEvent event) {
        clientList.clear();
        String selectedAccount = cmboAccount.getSelectionModel().getSelectedItem();
        String query = "select * from income where account = '"+ selectedAccount +"'";
        if(selectedAccount.equals("All")){
            query = "select * from income";
        }
        listOfIncomeTableRecords = incomeTableQuery.selectIncomeTable(query);
        for(int i=0; i<listOfIncomeTableRecords.size(); i++){
            String accountString = listOfIncomeTableRecords.get(i).getAccount();
            if(accountString.equals(selectedAccount)){
                String clientString = listOfIncomeTableRecords.get(i).getClient();
                if(!clientList.contains(clientString)){
                    clientList.add(clientString);
                }
            }
        }
        calcTotalReceivedAndActual();
        setupLeaseTable();
    }

    @FXML
    public void onCmboClientPressed(ActionEvent event) {
        String selectedAccount = cmboAccount.getSelectionModel().getSelectedItem();
        String selectedClient = cmboClient.getSelectionModel().getSelectedItem();
        String query = "select * from income where client = '"+ selectedClient +"'";

        listOfIncomeTableRecords = incomeTableQuery.selectIncomeTable(query);
        listOfIncomeTableRecords.removeIf(ac -> (!ac.getAccount().equals(selectedAccount)));
        calcTotalReceivedAndActual();
        setupLeaseTable();
    }

    public void onEditAccountCell(TableColumn.CellEditEvent<IncomeTable, String> event) {
        int id = incomeTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update income set account = '" + event.getNewValue().toUpperCase() +"' where id = " + id;
        incomeTableQuery.updateIncomeTable(query);
    }
    public void onEditClientCell(TableColumn.CellEditEvent<IncomeTable, String> event) {
        int id = incomeTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update income set client = '" + event.getNewValue().toUpperCase() +"' where id = " + id;
        incomeTableQuery.updateIncomeTable(query);
    }
    public void onEditFromCell(TableColumn.CellEditEvent<IncomeTable, String> event) {
        int id = incomeTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update income set 'from' = '" + event.getNewValue() +"' where id = " + id;
        incomeTableQuery.updateIncomeTable(query);
    }
    public void onEditToCell(TableColumn.CellEditEvent<IncomeTable, String> event) {
        int id = incomeTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update income set 'to' = '" + event.getNewValue() +"' where id = " + id;
        incomeTableQuery.updateIncomeTable(query);
    }
    public void onEditReceivedCell(TableColumn.CellEditEvent<IncomeTable, String> event) {
        int id = incomeTableView.getSelectionModel().getSelectedItems().get(0).getId();
        double newValue = 0.0;
        try {
            newValue = Double.parseDouble(event.getNewValue());
        } catch (NumberFormatException e) {
            incomeTableView.getSelectionModel().getSelectedCells().get(0).getTableColumn().setCellFactory(TextFieldTableCell.forTableColumn());
            return;
        }
        String query = "update income set received = " + newValue +" where id = " + id;
        incomeTableQuery.updateIncomeTable(query);
        listOfIncomeTableRecords = incomeTableQuery.selectIncomeTable("select * from income");
        calcTotalReceivedAndActual();
    }
    public void onEditActualCell(TableColumn.CellEditEvent<IncomeTable, String> event) {
        int id = incomeTableView.getSelectionModel().getSelectedItems().get(0).getId();
        double newValue = 0.0;
        try {
            newValue = Double.parseDouble(event.getNewValue());
        } catch (NumberFormatException e) {
            incomeTableView.getSelectionModel().getSelectedCells().get(0).getTableColumn().setCellFactory(TextFieldTableCell.forTableColumn());
            return;
        }
        String query = "update income set actual = " + newValue +" where id = " + id;
        incomeTableQuery.updateIncomeTable(query);
        listOfIncomeTableRecords = incomeTableQuery.selectIncomeTable("select * from income");
        calcTotalReceivedAndActual();
    }
    public void onEditDateCell(TableColumn.CellEditEvent<IncomeTable, String> event) {
        int id = incomeTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update income set 'date' = '" + event.getNewValue() +"' where id = " + id;
        incomeTableQuery.updateIncomeTable(query);
    }
    public void onEditCommentCell(TableColumn.CellEditEvent<IncomeTable, String> event) {
        int id = incomeTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update income set comment = '" + event.getNewValue().toUpperCase() +"' where id = " + id;
        incomeTableQuery.updateIncomeTable(query);
    }

    @FXML
    public void onBtnAddRecordPressed(ActionEvent event) {
        String accountToAdd = txtFieldAccount.getText().toUpperCase();
        String clientToAdd = txtFieldClient.getText().toUpperCase();
        String dtFromToAdd = dtPckerFrom.getValue().toString();
        String dtToToAdd = dtPickerTo.getValue().toString();
        double receivedToAdd = 0.0;
        double actualToAdd = 0.0;
        double includeGst = 0.0;
        String dtDateToAdd = dtPickerDate.getValue().toString();
        String commentToAdd = txtAreaComments.getText().toUpperCase();

        try {
            receivedToAdd = Double.parseDouble(txtFieldReceived.getText());
        } catch (NumberFormatException e) {
            txtFieldReceived.setText("0");
            return;
        }
        
        try {
            actualToAdd = Double.parseDouble(txtFieldActual.getText());
        } catch (NumberFormatException e) {
            txtFieldActual.setText("0");
            return;
        }
        
        if(chkBoxGst.isSelected()){
            includeGst = receivedToAdd/11.0;
            receivedToAdd = receivedToAdd-includeGst;
        }
        
        receivedToAdd = round(receivedToAdd);
        actualToAdd = round(actualToAdd);
        includeGst = round(includeGst);
        
        String query = "insert into income (account, client, 'from', 'to', received, actual, date, comment,gst) values ('"+accountToAdd+"','"+clientToAdd+"','"+dtFromToAdd+"','"+dtToToAdd+"',"+receivedToAdd+","+actualToAdd+",'"+dtDateToAdd+"','"+commentToAdd+"',"+includeGst+")";
        if(!accountToAdd.equals("") && !clientToAdd.equals("") && !dtFromToAdd.equals("") && !dtToToAdd.equals("") && receivedToAdd >= 0 && actualToAdd >= 0 && !dtDateToAdd.equals("")){
            incomeTableQuery.updateIncomeTable(query);
            cmboAccount.setValue("");
            cmboAccount.setValue("All");
        }
        setupComboLists();
    }
    
    @FXML
    public void onBtnDeleteRecordPressed(ActionEvent event) {
        IncomeTable selectedRow = incomeTableView.getSelectionModel().getSelectedItems().get(0);
        if(selectedRow != null){
            int id = selectedRow.getId();
            String query = "delete from income where id = " + id;
            incomeTableQuery.updateIncomeTable(query);
            String selectedAccount = selectedRow.getAccount();
            String selectedClient = selectedRow.getClient();
            accountList.remove(selectedAccount);
            cmboAccount.setValue("");
            cmboAccount.setValue("All");
        }
    }
    
    public ObservableList<IncomeTable> getIncomeList(){
        return listOfIncomeTableRecords;
    }
    
    private double round(double number){
        return Math.round(number*100)/100.0;
    }
}
