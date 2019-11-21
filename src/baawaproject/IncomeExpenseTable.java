/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baawaproject;

import java.util.Objects;
import javafx.scene.control.CheckBox;

/**
 *
 * @author rohanayan
 */
public class IncomeExpenseTable {
    private int srnumber;
    private CheckBox select;
    private String category;

    public IncomeExpenseTable(int srnumber, CheckBox select, String category) {
        this.srnumber = srnumber;
        this.select = select;
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.category);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IncomeExpenseTable other = (IncomeExpenseTable) obj;
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        return true;
    }

    
    public int getSrnumber() {
        return srnumber;
    }

    public CheckBox getSelect() {
        return select;
    }

    public String getCategory() {
        return category;
    }

    public void setSrnumber(int srnumber) {
        this.srnumber = srnumber;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
