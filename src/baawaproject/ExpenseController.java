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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * FXML Controller class
 *
 * @author rohanayan
 */
public class ExpenseController implements Initializable {
    private ExpenseTableQuery expenseTableQuery = new ExpenseTableQuery();
    private ObservableList<ExpenseTable> listOfExpenseTableRecords = FXCollections.observableArrayList();
    
    private ObservableList<String> accountList = FXCollections.observableArrayList();
    private ObservableList<String> categoryList = FXCollections.observableArrayList();
    private ObservableList<String> paidByList = FXCollections.observableArrayList();
    private ObservableList<String> paidToList = FXCollections.observableArrayList();

    @FXML
    private TableView<ExpenseTable> expenseTableView;
    @FXML
    private TableColumn<ExpenseTable, Integer> id;
    @FXML
    private TableColumn<ExpenseTable, String> date;
    @FXML
    private TableColumn<ExpenseTable, String> account;
    @FXML
    private TableColumn<ExpenseTable, String> category;
    @FXML
    private TableColumn<ExpenseTable, String> description;
    @FXML
    private TableColumn<ExpenseTable, String> amount;
    @FXML
    private TableColumn<ExpenseTable, String> paidby;
    @FXML
    private TableColumn<ExpenseTable, String> paidto;
    @FXML
    private TableColumn<ExpenseTable, String> gst;
    @FXML
    private ComboBox<String> cmboAccount;
    @FXML
    private ComboBox<String> cmboCategory;
    @FXML
    private ComboBox<String> cmboPaidBy;
    @FXML
    private ComboBox<String> cmboPaidTo;
    @FXML
    private DatePicker dtPckerDate;
    @FXML
    private TextField txtFieldCategory;
    @FXML
    private TextField txtFieldAccount;
    @FXML
    private TextField txtFieldPaidBy;
    @FXML
    private Button btnAddRecord;
    @FXML
    private Button btnDeleteRecord;
    @FXML
    private TextField txtFieldPaidTo;
    @FXML
    private TextField txtFieldDescription;
    @FXML
    private TextField txtFieldAmount;
    @FXML
    private TextField txtFieldExpenseTotal;
    @FXML
    private TextField txtFieldGstTotal;
    @FXML
    private CheckBox chkBoxGst;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        expenseTableView.setEditable(true);
        date.setCellFactory(TextFieldTableCell.forTableColumn());
        account.setCellFactory(TextFieldTableCell.forTableColumn());
        category.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        amount.setCellFactory(TextFieldTableCell.forTableColumn());
        paidby.setCellFactory(TextFieldTableCell.forTableColumn());
        paidto.setCellFactory(TextFieldTableCell.forTableColumn());
        gst.setCellFactory(TextFieldTableCell.forTableColumn());
        
        accountList.add("All");
        paidByList.add("All");
        paidToList.add("All");
        setupComboLists();
        
        setupExpenseTable();
        
        dtPckerDate.setValue(LocalDate.now());
        txtFieldAmount.setText("0");

        txtFieldAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*.?\\d{0,2}")) {
                txtFieldAmount.setText(oldValue);
            }
        });
     
        cmboAccount.setValue("All");
        calcTotalExpenseAndGst();
    }
    
    private void setupExpenseTable(){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        account.setCellValueFactory(new PropertyValueFactory<>("account"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        paidby.setCellValueFactory(new PropertyValueFactory<>("paidby"));
        paidto.setCellValueFactory(new PropertyValueFactory<>("paidto"));
        gst.setCellValueFactory(new PropertyValueFactory<>("gst"));
        
        expenseTableView.setItems(listOfExpenseTableRecords);
    }
    
    private void setupComboLists(){
        listOfExpenseTableRecords = expenseTableQuery.selectExpenseTable("select * from expense");
        for(int i=0; i<listOfExpenseTableRecords.size(); i++){
            String accountString = listOfExpenseTableRecords.get(i).getAccount();
            if(!accountList.contains(accountString)){
                accountList.add(accountString);
            }
            String paidByString = listOfExpenseTableRecords.get(i).getPaidby();
            if(!paidByList.contains(paidByString)){
                paidByList.add(paidByString);
            }
            String paidToString = listOfExpenseTableRecords.get(i).getPaidto();
            if(!paidToList.contains(paidToString)){
                paidToList.add(paidToString);
            }
        }

        cmboAccount.setItems(accountList);
        cmboPaidBy.setItems(paidByList);
        cmboPaidTo.setItems(paidToList);
    }
    
    private void calcTotalExpenseAndGst(){
        double expenseTotal = 0.0;
        double gstTotal = 0.0;
        for(int i=0; i<listOfExpenseTableRecords.size(); i++){
            expenseTotal += Double.parseDouble(listOfExpenseTableRecords.get(i).getAmount());
            gstTotal += Double.parseDouble(listOfExpenseTableRecords.get(i).getGst());
        }
        txtFieldExpenseTotal.setText(Double.toString(round(expenseTotal)));
        txtFieldGstTotal.setText(Double.toString(round(gstTotal)));
    }

    @FXML
    public void onEditDateCell(TableColumn.CellEditEvent<ExpenseTable, String> event) {
        int id = expenseTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update expense set 'date' = '" + event.getNewValue().toUpperCase() +"' where id = " + id;
        expenseTableQuery.updateExpenseTable(query);
    }

    @FXML
    public void onEditAccountCell(TableColumn.CellEditEvent<ExpenseTable, String> event) {
        int id = expenseTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update expense set account = '" + event.getNewValue().toUpperCase() +"' where id = " + id;
        expenseTableQuery.updateExpenseTable(query);
    }
    
    @FXML
    public void onEditCategoryCell(TableColumn.CellEditEvent<ExpenseTable, String> event) {
        int id = expenseTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update expense set category = '" + event.getNewValue().toUpperCase() +"' where id = " + id;
        expenseTableQuery.updateExpenseTable(query);
    }

    @FXML
    public void onEditDescriptionCell(TableColumn.CellEditEvent<ExpenseTable, String> event) {
        int id = expenseTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update expense set description = '" + event.getNewValue().toUpperCase() +"' where id = " + id;
        expenseTableQuery.updateExpenseTable(query);
    }
    
    @FXML
    public void onEditAmountCell(TableColumn.CellEditEvent<ExpenseTable, String> event) {
        int id = expenseTableView.getSelectionModel().getSelectedItems().get(0).getId();
        double newValue = 0.0;
        try {
            newValue = Double.parseDouble(event.getNewValue());
        } catch (NumberFormatException e) {
            expenseTableView.getSelectionModel().getSelectedCells().get(0).getTableColumn().setCellFactory(TextFieldTableCell.forTableColumn());
            return;
        }
        String query = "update expense set amount = " + newValue +" where id = " + id;
        expenseTableQuery.updateExpenseTable(query);
    }
    
    @FXML
    public void onEditPaidByCell(TableColumn.CellEditEvent<ExpenseTable, String> event) {
        int id = expenseTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update expense set paidby = '" + event.getNewValue().toUpperCase() +"' where id = " + id;
        expenseTableQuery.updateExpenseTable(query);
    }

    @FXML
    public void onEditPaidToCell(TableColumn.CellEditEvent<ExpenseTable, String> event) {
        int id = expenseTableView.getSelectionModel().getSelectedItems().get(0).getId();
        String query = "update expense set paidto = '" + event.getNewValue().toUpperCase() +"' where id = " + id;
        expenseTableQuery.updateExpenseTable(query);
    }

    @FXML
    public void onEditGstCell(TableColumn.CellEditEvent<ExpenseTable, String> event) {
        int id = expenseTableView.getSelectionModel().getSelectedItems().get(0).getId();
        double newValue = 0.0;
        try {
            newValue = Double.parseDouble(event.getNewValue());
        } catch (NumberFormatException e) {
            expenseTableView.getSelectionModel().getSelectedCells().get(0).getTableColumn().setCellFactory(TextFieldTableCell.forTableColumn());
            return;
        }
        String query = "update expense set gst = " + newValue +" where id = " + id;
        expenseTableQuery.updateExpenseTable(query);
    }

    @FXML
    public void onCmboAccountPressed(ActionEvent event) {
        categoryList.clear();
        String selectedAccount = cmboAccount.getSelectionModel().getSelectedItem();
        String query = "select * from expense where account = '"+ selectedAccount +"'";
        if(selectedAccount.equals("All")){
            query = "select * from expense";
        }
        listOfExpenseTableRecords = expenseTableQuery.selectExpenseTable(query);
        for(int i=0; i<listOfExpenseTableRecords.size(); i++){
            String accountString = listOfExpenseTableRecords.get(i).getAccount();
            if(accountString.equals(selectedAccount)){
                String categoryString = listOfExpenseTableRecords.get(i).getCategory();
                if(!categoryList.contains(categoryString)){
                    categoryList.add(categoryString);
                }
            }
        }
        cmboCategory.setItems(categoryList);
        calcTotalExpenseAndGst();
        setupExpenseTable();
    }
    
    @FXML
    public void onCmboCategoryPressed(ActionEvent event) {
        String selectedAccount = cmboAccount.getSelectionModel().getSelectedItem();
        String selectedCategory = cmboCategory.getSelectionModel().getSelectedItem();
        String query = "select * from expense where category = '"+ selectedCategory +"'";
        listOfExpenseTableRecords = expenseTableQuery.selectExpenseTable(query);
        listOfExpenseTableRecords.removeIf(ac -> (!ac.getAccount().equals(selectedAccount)));
        calcTotalExpenseAndGst();
        setupExpenseTable();
    }

    @FXML
    public void onCmboPaidByPressed(ActionEvent event) {
        String selectedPaidBy = cmboPaidBy.getSelectionModel().getSelectedItem();
        String query = "select * from expense where paidby = '"+ selectedPaidBy +"'";
        if(selectedPaidBy.equals("All")){
            query = "select * from expense";
        }
        listOfExpenseTableRecords = expenseTableQuery.selectExpenseTable(query);
        calcTotalExpenseAndGst();
        setupExpenseTable();
    }
    
    @FXML
    public void onCmboPaidToPressed(ActionEvent event) {
        String selectedPaidTo = cmboPaidTo.getSelectionModel().getSelectedItem();
        String query = "select * from expense where paidto = '"+ selectedPaidTo +"'";
        if(selectedPaidTo.equals("All")){
            query = "select * from expense";
        }
        listOfExpenseTableRecords = expenseTableQuery.selectExpenseTable(query);
        calcTotalExpenseAndGst();
        setupExpenseTable();
    }

    @FXML
    public void onBtnAddRecordPressed(ActionEvent event) {
        String accountString = txtFieldAccount.getText().toUpperCase();
        String categoryString = txtFieldCategory.getText().toUpperCase();
        String categoryDescriptin = txtFieldDescription.getText().toUpperCase();
        String expenseDate = dtPckerDate.getValue().toString();
        double expenseAmount = 0.0;
        double expenseGst = 0.0;
        String paidByString = txtFieldPaidBy.getText().toUpperCase();
        String paidToString = txtFieldPaidTo.getText().toUpperCase();
        
        try {
            expenseAmount = Double.parseDouble(txtFieldAmount.getText());
        } catch (NumberFormatException e) {
            txtFieldAmount.setText("0");
            return;
        }
        //Add GST
        if(chkBoxGst.isSelected()){
            try {
            expenseGst = Double.parseDouble(txtFieldAmount.getText()) / 11.0;
            expenseAmount = expenseAmount - expenseGst;
            } catch (NumberFormatException e) {
                return;
            }
        }
        
        expenseAmount = round(expenseAmount);
        expenseGst = round(expenseGst);

        String query = "insert into expense (date, account, category, description, amount, paidby, paidto, gst) values ('"+expenseDate+"','"+accountString+"','"+categoryString+"','"+categoryDescriptin+"',"+expenseAmount+",'"+paidByString+"','"+paidToString+"',"+expenseGst+")";
        if(!accountString.equals("") && !categoryString.equals("") && !categoryDescriptin.equals("") && !expenseDate.equals("") && expenseAmount >= 0 && !paidByString.equals("")){
            expenseTableQuery.updateExpenseTable(query);
            cmboPaidBy.setValue("");
            cmboPaidTo.setValue("");
            cmboAccount.setValue("");
            cmboAccount.setValue("All");
        }
        setupComboLists();
    }

    @FXML
    public void onBtnDeleteRecordPressed(ActionEvent event) {
        ExpenseTable selectedRow = expenseTableView.getSelectionModel().getSelectedItems().get(0);
        if(selectedRow != null){
            int id = selectedRow.getId();
            String selectedCategory = selectedRow.getCategory();
            String selectedPaidBy = selectedRow.getPaidby();
            String selectedPaidTo = selectedRow.getPaidto();
            String query = "delete from expense where id = " + id;
            expenseTableQuery.updateExpenseTable(query);
            paidByList.remove(selectedPaidBy);
            paidToList.remove(selectedPaidTo);
            cmboPaidBy.setValue("");
            cmboPaidTo.setValue("");
            cmboAccount.setValue("");
            cmboAccount.setValue("All");
        }
    }
    
    public ObservableList<ExpenseTable> getExpenseList(){
        return listOfExpenseTableRecords;
    }
    
    private double round(double number){
        return Math.round(number*100)/100.0;
    }
}
