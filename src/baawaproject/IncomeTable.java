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
public class IncomeTable {
    private int id;
    private String account;
    private String client;
    private String from;
    private String to;
    private String received;
    private String actual;
    private String date;
    private String comment;
    private String gst;
    
    public IncomeTable(int id, String account, String client, String from, String to, String received, String actual, String date, String comment, String gst) {
        this.id = id;
        this.account = account;
        this.client = client;
        this.from = from;
        this.to = to;
        this.received = received;
        this.actual = actual;
        this.date = date;
        this.comment = comment;
        this.gst = gst;
    }

    public int getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getClient() {
        return client;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
    
    public String getReceived() {
        return received;
    }

    public String getActual() {
        return actual;
    }

    public String getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public String getGst() {
        return gst;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
    
    
}
