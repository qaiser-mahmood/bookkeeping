/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baawaproject;

/**
 *
 * @author rohanayan
 */
public class ExpenseTable {
    private int id;
    private String date;
    private String account;
    private String category;
    private String description;
    private String amount;
    private String paidby;
    private String paidto;
    private String gst;

    public ExpenseTable(int id, String date, String account, String category, String description, String amount, String paidby, String paidto, String gst) {
        this.id = id;
        this.date = date;
        this.account = account;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.paidby = paidby;
        this.paidto = paidto;
        this.gst = gst;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getAccount() {
        return account;
    }

    
    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    
    public String getPaidby() {
        return paidby;
    }

    public String getPaidto() {
        return paidto;
    }

    public String getGst() {
        return gst;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    
    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    
    public void setPaidby(String paidby) {
        this.paidby = paidby;
    }

    public void setPaidto(String paidto) {
        this.paidto = paidto;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
    
}
