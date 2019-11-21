/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baawaproject;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author rohanayan
 */
public class IncomeExpenseController implements Initializable {
    

    private ExpenseTableQuery expenseTableQuery = new ExpenseTableQuery();
    private IncomeTableQuery incomeTableQuery = new IncomeTableQuery();
    private ObservableList<ExpenseTable> listOfExpenseRecords = FXCollections.observableArrayList();
    private ObservableList<IncomeTable> listOfIncomeRecords = FXCollections.observableArrayList();
    private ObservableList<IncomeExpenseTable> listOfIncomeExpenseRecords = FXCollections.observableArrayList();
    
//    private LocalDate testDate;
    private CustomDate customDate;

    @FXML
    private TextField txtFieldIncomeWeekly;
    @FXML
    private TextField txtFieldIncomeMonthly;
    @FXML
    private TextField txtFieldIncomeQuaterly;
    @FXML
    private TextField txtFieldIncomeAnnually;
    @FXML
    private TextField txtFieldExpenseWeekly;
    @FXML
    private TextField txtFieldExpenseMonthly;
    @FXML
    private TextField txtFieldExpenseQuaterly;
    @FXML
    private TextField txtFieldExpenseAnnually;
    @FXML
    private TextField txtFieldGstPaidWeekly;
    @FXML
    private TextField txtFieldGstPaidMonthly;
    @FXML
    private TextField txtFieldGstPaidQuaterly;
    @FXML
    private TextField txtFieldGstPaidAnnually;
    @FXML
    private TextField txtFieldGstCollectedWeekly;
    @FXML
    private TextField txtFieldGstCollectedMonthly;
    @FXML
    private TextField txtFieldGstCollectedQuaterly;
    @FXML
    private TextField txtFieldGstCollectedAnnually;
    @FXML
    private TextField txtFieldIncome;
    @FXML
    private TextField txtFieldExpense;
    @FXML
    private TextField txtFieldGstPaid;
    @FXML
    private TextField txtFieldGstCollected;
    @FXML
    private TextField txtFieldYear;
    @FXML
    private DatePicker dtPickerFrom;
    @FXML
    private DatePicker dtPickerTo;
    @FXML
    private TableView<IncomeExpenseTable> categoryTableView;
    @FXML
    private TableColumn<IncomeExpenseTable, Integer> srnumber;
    @FXML
    private TableColumn<IncomeExpenseTable, CheckBox> select;
    @FXML
    private TableColumn<IncomeExpenseTable, String> category;
    @FXML
    private CheckBox chkBoxAll;
    @FXML
    private Button btnGetSummary;

    private LocalDate yearStartDate;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtFieldYear.setText(Integer.toString(LocalDate.now().getYear()));
        txtFieldYear.setFont(new Font(16));
        yearStartDate = LocalDate.of(Integer.parseInt(txtFieldYear.getText()), Month.JULY, 1);
        customDate = new CustomDate(yearStartDate);
        dtPickerFrom.setValue(yearStartDate);
        dtPickerTo.setValue(customDate.getEndDateOfYear(yearStartDate));
        listOfExpenseRecords = expenseTableQuery.selectExpenseTable("select * from expense");
        listOfIncomeRecords = incomeTableQuery.selectIncomeTable("select * from income");
        
        for(int i=0; i<listOfExpenseRecords.size(); i++){
            int id = listOfExpenseRecords.get(i).getId();
            CheckBox chkBox = new CheckBox();
            String cat = listOfExpenseRecords.get(i).getCategory();
            IncomeExpenseTable obj = new IncomeExpenseTable(id, chkBox, cat);
            
            if(!listOfIncomeExpenseRecords.contains(obj)){
                listOfIncomeExpenseRecords.add(obj);
            }
        }
        for(int i=0; i<listOfIncomeRecords.size(); i++){
            int id = listOfIncomeRecords.get(i).getId();
            CheckBox chkBox = new CheckBox();
            String cat = listOfIncomeRecords.get(i).getClient();
            IncomeExpenseTable obj = new IncomeExpenseTable(id, chkBox, cat);
            
            if(!listOfIncomeExpenseRecords.contains(obj)){
                listOfIncomeExpenseRecords.add(obj);
            }
        }
        
        srnumber.setCellValueFactory(new PropertyValueFactory<>("srnumber"));
        select.setCellValueFactory(new PropertyValueFactory<>("select"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        
        categoryTableView.setItems(listOfIncomeExpenseRecords);
    }
    
    @FXML
    void onChkBoxAllSelected(ActionEvent event) {
        for(int i=0; i<listOfIncomeExpenseRecords.size(); i++){
            listOfIncomeExpenseRecords.get(i).getSelect().setSelected(true);
        }
    }
    
    @FXML
    private void onBtnGetSummaryPressed(ActionEvent event) {
        LocalDate todayDate = LocalDate.now();
        LocalDate weekStartDate = customDate.getStartDateOfWeek(todayDate);
        LocalDate weekEndDate = customDate.getEndDateOfWeek(todayDate);
         LocalDate monthStartDate = customDate.getStartDateOfMonth(todayDate);
        LocalDate monthEndDate = customDate.getEndDateOfMonth(todayDate);
        LocalDate qtrStartDate = customDate.getStartDateOfQuarter(todayDate);
        LocalDate qtrEndDate = customDate.getEndDateOfQuarter(todayDate);
        LocalDate yrStartDate = customDate.getStartDateOfYear(todayDate);
        LocalDate yrEndDate = customDate.getEndDateOfYear(todayDate);
        double weeklyExpense = 0.0;
        double monthlyExpense = 0.0;
        double quaterlyExpense = 0.0;
        double yearlyExpense = 0.0;
        
        double weeklyGstPaid = 0.0;
        double monthlyGstPaid = 0.0;
        double quaterlyGstPaid = 0.0;
        double yearlyGstPaid = 0.0;
        
        double weeklyGstCollected = 0.0;
        double monthlyGstCollected = 0.0;
        double quaterlyGstCollected = 0.0;
        double yearlyGstCollected = 0.0;
        
        double weeklyIncome = 0.0;
        double monthlyIncome = 0.0;
        double quaterlyIncome = 0.0;
        double yearlyIncome = 0.0;
        
        double selectedCategoryExpense = 0.0;
        double selectedCategoryIncome = 0.0;
        double selectedCategoryGstPaid = 0.0;
        double selectedCategoryGstCollected = 0.0;
        
        LocalDate fromDate = dtPickerFrom.getValue();
        LocalDate toDate = dtPickerTo.getValue();
        
        for(int i=0; i<listOfIncomeExpenseRecords.size(); i++){
            CheckBox checkBox = listOfIncomeExpenseRecords.get(i).getSelect();
            if(checkBox.isSelected()){
                String selectedCategory = listOfIncomeExpenseRecords.get(i).getCategory();
                
                //Calculate Expenses and GST
                for(int j=0; j<listOfExpenseRecords.size(); j++){
                    
                    //First check if the category is selected
                    if(listOfExpenseRecords.get(j).getCategory().equals(selectedCategory)){
                        LocalDate expenseDate = LocalDate.parse(listOfExpenseRecords.get(j).getDate());
                        //Weekly Expense and GST
                        if((expenseDate.isEqual(weekStartDate) || expenseDate.isAfter(weekStartDate)) 
                                && (expenseDate.isEqual(weekEndDate) || expenseDate.isBefore(weekEndDate))){
                            weeklyExpense += Double.parseDouble(listOfExpenseRecords.get(j).getAmount());
                            weeklyGstPaid += Double.parseDouble(listOfExpenseRecords.get(j).getGst());
                        }
                        //Monthly Expense and GST
                        if((expenseDate.isEqual(monthStartDate) || expenseDate.isAfter(monthStartDate)) 
                                && (expenseDate.isEqual(monthEndDate) || expenseDate.isBefore(monthEndDate))){
                            monthlyExpense += Double.parseDouble(listOfExpenseRecords.get(j).getAmount());
                            monthlyGstPaid += Double.parseDouble(listOfExpenseRecords.get(j).getGst());
                        }
                        //Quaterly Expense and GST
                        if((expenseDate.isEqual(qtrStartDate) || expenseDate.isAfter(qtrStartDate)) 
                                && (expenseDate.isEqual(qtrEndDate) || expenseDate.isBefore(qtrEndDate))){
                            quaterlyExpense += Double.parseDouble(listOfExpenseRecords.get(j).getAmount());
                            quaterlyGstPaid += Double.parseDouble(listOfExpenseRecords.get(j).getGst());
                        }
                        //Yearly Expense and GST
                        if((expenseDate.isEqual(yrStartDate) || expenseDate.isAfter(yrStartDate)) 
                                && (expenseDate.isEqual(yrEndDate) || expenseDate.isBefore(yrEndDate))){
                            yearlyExpense += Double.parseDouble(listOfExpenseRecords.get(j).getAmount());
                            yearlyGstPaid += Double.parseDouble(listOfExpenseRecords.get(j).getGst());
                        }
                        
                        //From-To Date Expenses and GST Paid
                        if((expenseDate.isEqual(fromDate) || expenseDate.isAfter(fromDate)) 
                                && (expenseDate.isEqual(toDate) || expenseDate.isBefore(toDate))){
                            selectedCategoryExpense += Double.parseDouble(listOfExpenseRecords.get(j).getAmount());
                            selectedCategoryGstPaid += Double.parseDouble(listOfExpenseRecords.get(j).getGst());
                        }

                    }
                }
                
                //Calculate Income
                for(int j=0; j<listOfIncomeRecords.size(); j++){
                    
                    //First check if the category is selected
                    if(listOfIncomeRecords.get(j).getClient().equals(selectedCategory)){
                        LocalDate incomeDate = LocalDate.parse(listOfIncomeRecords.get(j).getDate());
                        //Weekly Expense
                        if((incomeDate.isEqual(weekStartDate) || incomeDate.isAfter(weekStartDate)) 
                                && (incomeDate.isEqual(weekEndDate) || incomeDate.isBefore(weekEndDate))){
                            weeklyIncome += Double.parseDouble(listOfIncomeRecords.get(j).getReceived());
                            weeklyGstCollected += Double.parseDouble(listOfIncomeRecords.get(j).getGst());
                        }
                        //Monthly Expense
                        if((incomeDate.isEqual(monthStartDate) || incomeDate.isAfter(monthStartDate)) 
                                && (incomeDate.isEqual(monthEndDate) || incomeDate.isBefore(monthEndDate))){
                            monthlyIncome += Double.parseDouble(listOfIncomeRecords.get(j).getReceived());
                            monthlyGstCollected += Double.parseDouble(listOfIncomeRecords.get(j).getGst());
                        }
                        //Quaterly Expense
                        if((incomeDate.isEqual(qtrStartDate) || incomeDate.isAfter(qtrStartDate)) 
                                && (incomeDate.isEqual(qtrEndDate) || incomeDate.isBefore(qtrEndDate))){
                            quaterlyIncome += Double.parseDouble(listOfIncomeRecords.get(j).getReceived());
                            quaterlyGstCollected += Double.parseDouble(listOfIncomeRecords.get(j).getGst());
                        }
                        //Yearly Expense
                        if((incomeDate.isEqual(yrStartDate) || incomeDate.isAfter(yrStartDate)) 
                                && (incomeDate.isEqual(yrEndDate) || incomeDate.isBefore(yrEndDate))){
                            yearlyIncome += Double.parseDouble(listOfIncomeRecords.get(j).getReceived());
                            yearlyGstCollected += Double.parseDouble(listOfIncomeRecords.get(j).getGst());
                        }
                        
                        //From-To Date Income
                        if((incomeDate.isEqual(fromDate) || incomeDate.isAfter(fromDate)) 
                                && (incomeDate.isEqual(toDate) || incomeDate.isBefore(toDate))){
                            selectedCategoryIncome += Double.parseDouble(listOfIncomeRecords.get(j).getReceived());
                            selectedCategoryGstCollected += Double.parseDouble(listOfIncomeRecords.get(j).getGst());
                        }
                    }
                }
            }
        }
        
        txtFieldExpenseWeekly.setText(Double.toString(round(weeklyExpense)));
        txtFieldExpenseMonthly .setText(Double.toString(round(monthlyExpense)));
        txtFieldExpenseQuaterly.setText(Double.toString(round(quaterlyExpense)));
        txtFieldExpenseAnnually.setText(Double.toString(round(yearlyExpense)));
        
        txtFieldGstPaidWeekly.setText(Double.toString(round(weeklyGstPaid)));
        txtFieldGstPaidMonthly .setText(Double.toString(round(monthlyGstPaid)));
        txtFieldGstPaidQuaterly.setText(Double.toString(round(quaterlyGstPaid)));
        txtFieldGstPaidAnnually.setText(Double.toString(round(yearlyGstPaid)));
        
        txtFieldGstCollectedWeekly.setText(Double.toString(round(weeklyGstCollected)));
        txtFieldGstCollectedMonthly .setText(Double.toString(round(monthlyGstCollected)));
        txtFieldGstCollectedQuaterly.setText(Double.toString(round(quaterlyGstCollected)));
        txtFieldGstCollectedAnnually.setText(Double.toString(round(yearlyGstCollected)));
        
        txtFieldIncomeWeekly.setText(Double.toString(round(weeklyIncome)));
        txtFieldIncomeMonthly .setText(Double.toString(round(monthlyIncome)));
        txtFieldIncomeQuaterly.setText(Double.toString(round(quaterlyIncome)));
        txtFieldIncomeAnnually.setText(Double.toString(round(yearlyIncome)));
        
        txtFieldIncome.setText(Double.toString(round(selectedCategoryIncome)));
        txtFieldExpense .setText(Double.toString(round(selectedCategoryExpense)));
        txtFieldGstPaid.setText(Double.toString(round(selectedCategoryGstPaid)));
        txtFieldGstCollected.setText(Double.toString(round(selectedCategoryGstCollected)));
    }
    
    private double round(double number){
        return Math.round(number*100)/100.0;
    }
}
