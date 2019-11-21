/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baawaproject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author rohanayan
 */
public class CustomDate {
    public static final DayOfWeek START_DAY_OF_WEEK = DayOfWeek.MONDAY;
    public static final DayOfWeek END_DAY_OF_WEEK = DayOfWeek.SUNDAY;
    public static final int DAYS_IN_Q1 = Month.JULY.maxLength() + Month.AUGUST.maxLength() + Month.SEPTEMBER.maxLength();
    public static final int DAYS_IN_Q2 = Month.OCTOBER.maxLength() + Month.NOVEMBER.maxLength() + Month.DECEMBER.maxLength();
    public static int DAYS_IN_Q3 = Month.JANUARY.maxLength() + 28 + Month.MARCH.maxLength();
    public static final int DAYS_IN_Q4 = Month.APRIL.maxLength() + Month.MAY.maxLength() + Month.JUNE.maxLength();
    public static int DAYS_IN_YEAR = DAYS_IN_Q1 + DAYS_IN_Q2 + DAYS_IN_Q3 + DAYS_IN_Q4;
    
    private LocalDate startDateOfYear;
    private boolean q1,q2,q3,q4;

    public CustomDate(LocalDate startDateOfYear) {
        this.startDateOfYear = startDateOfYear;
        q1=false;
        q2=false;
        q3=false;
        q4=false;
    }
    
    public LocalDate getStartDateOfWeek(LocalDate date){
        while(date.getDayOfWeek() != START_DAY_OF_WEEK){
            date = date.minusDays(1);
        }
        return date;
    }
    
    public LocalDate getEndDateOfWeek(LocalDate date){
        while(date.getDayOfWeek() != END_DAY_OF_WEEK){
            date = date.plusDays(1);
        }
        return date;
    }
    
    public LocalDate getStartDateOfMonth(LocalDate date){
        while(date.getDayOfMonth()!= 1){
            date = date.minusDays(1);
        }
        return date;
    }
    
    public LocalDate getEndDateOfMonth(LocalDate date){
        while(date.getDayOfMonth()!= date.lengthOfMonth()){
            date = date.plusDays(1);
        }
        return date;
    }
    
    public LocalDate getStartDateOfQuarter(LocalDate date){
        if(date.isLeapYear()){
            DAYS_IN_Q3 = Month.JANUARY.maxLength() + 29 + Month.MARCH.maxLength();
        }else{
            DAYS_IN_Q3 = Month.JANUARY.maxLength() + 28 + Month.MARCH.maxLength();
        }
        Month month = date.getMonth();
        if(null != month)switch (month) {
            case JULY:
            case AUGUST:
            case SEPTEMBER:
                q1 = true;
                break;
            case OCTOBER:
            case NOVEMBER:
            case DECEMBER:
                q2 = true;
                break;
            case JANUARY:
            case FEBRUARY:
            case MARCH:
                q3 = true;
                break;
            case APRIL:
            case MAY:
            case JUNE:
                q4 = true;
                break;
            default:
                break;
        }
        int dayOfYear = date.getDayOfYear();
        int daysBeforeQ1 = startDateOfYear.getDayOfYear()-1;
        int daysBeforeQ2 = startDateOfYear.getDayOfYear()-1+DAYS_IN_Q1;
        int daysBeforeQ3 = 0;
        int daysBeforeQ4 = DAYS_IN_Q3;
        if(q1){
            while(date.getDayOfYear()!= daysBeforeQ1+1){
                date = date.minusDays(1);
            }
        }else if(q2){
            while(date.getDayOfYear()!= daysBeforeQ2+1){
                date = date.minusDays(1);
            }
        }else if(q3){
            while(date.getDayOfYear()!= daysBeforeQ3+1){
                date = date.minusDays(1);
            }
        }else if(q4){
            while(date.getDayOfYear()!= daysBeforeQ4+1){
                date = date.minusDays(1);
            }
        }
        return date;
    }
    
    public LocalDate getEndDateOfQuarter(LocalDate date){
        if(date.isLeapYear()){
            DAYS_IN_Q3 = Month.JANUARY.maxLength() + 29 + Month.MARCH.maxLength();
        }else{
            DAYS_IN_Q3 = Month.JANUARY.maxLength() + 28 + Month.MARCH.maxLength();
        }
        Month month = date.getMonth();
        if(null != month)switch (month) {
            case JULY:
            case AUGUST:
            case SEPTEMBER:
                q1 = true;
                break;
            case OCTOBER:
            case NOVEMBER:
            case DECEMBER:
                q2 = true;
                break;
            case JANUARY:
            case FEBRUARY:
            case MARCH:
                q3 = true;
                break;
            case APRIL:
            case MAY:
            case JUNE:
                q4 = true;
                break;
            default:
                break;
        }
        int dayOfYear = date.getDayOfYear();
        int daysBeforeQ1 = startDateOfYear.getDayOfYear()-1;
        int daysBeforeQ2 = startDateOfYear.getDayOfYear()-1+DAYS_IN_Q1;
        int daysBeforeQ3 = 0;
        int daysBeforeQ4 = DAYS_IN_Q3;
        if(q1){
            while(date.getDayOfYear()!= daysBeforeQ1+DAYS_IN_Q1){
                date = date.plusDays(1);
            }
        }else if(q2){
            while(date.getDayOfYear()!= daysBeforeQ2+DAYS_IN_Q2){
                date = date.plusDays(1);
            }
        }else if(q3){
            while(date.getDayOfYear()!= daysBeforeQ3+DAYS_IN_Q3){
                date = date.plusDays(1);
            }
        }else if(q4){
            while(date.getDayOfYear()!= daysBeforeQ4+DAYS_IN_Q4){
                date = date.plusDays(1);
            }
        }
        return date;
    }
    
    public LocalDate getStartDateOfYear(LocalDate date){
        
        if(date.isLeapYear()){
            DAYS_IN_Q3 = Month.JANUARY.maxLength() + 29 + Month.MARCH.maxLength();
        }else{
            DAYS_IN_Q3 = Month.JANUARY.maxLength() + 28 + Month.MARCH.maxLength();
        }
        DAYS_IN_YEAR = DAYS_IN_Q1 + DAYS_IN_Q2 + DAYS_IN_Q3 + DAYS_IN_Q4;
        Month month = date.getMonth();
        if(null != month)switch (month) {
            case JULY:
            case AUGUST:
            case SEPTEMBER:
                q1 = true;
                break;
            case OCTOBER:
            case NOVEMBER:
            case DECEMBER:
                q2 = true;
                break;
            case JANUARY:
            case FEBRUARY:
            case MARCH:
                q3 = true;
                break;
            case APRIL:
            case MAY:
            case JUNE:
                q4 = true;
                break;
            default:
                break;
        }
        
        
        int dayOfYear = date.getDayOfYear();
        int daysBeforeQ1 = startDateOfYear.getDayOfYear()-1;
        int daysBeforeQ2 = startDateOfYear.getDayOfYear()-1+DAYS_IN_Q1;
        int daysBeforeQ3 = 0;
        int daysBeforeQ4 = DAYS_IN_Q3;
        
        if(q1 || q2){
            while(date.getDayOfYear()!= daysBeforeQ1+1){
                date = date.minusDays(1);
            }
        }else if(q3 || q4){
            while(date.getDayOfYear()!= 1){
                date = date.minusDays(1);
            }
            date = date.minusDays(1);
            
            while(date.getDayOfYear()!= daysBeforeQ1+1){
                date = date.minusDays(1);
            }
        }
        return date;
    }
    
    public LocalDate getEndDateOfYear(LocalDate date){
        if(date.isLeapYear()){
            DAYS_IN_Q3 = Month.JANUARY.maxLength() + 29 + Month.MARCH.maxLength();
        }else{
            DAYS_IN_Q3 = Month.JANUARY.maxLength() + 28 + Month.MARCH.maxLength();
        }
        DAYS_IN_YEAR = DAYS_IN_Q1 + DAYS_IN_Q2 + DAYS_IN_Q3 + DAYS_IN_Q4;
        Month month = date.getMonth();
        if(null != month)switch (month) {
            case JULY:
            case AUGUST:
            case SEPTEMBER:
                q1 = true;
                break;
            case OCTOBER:
            case NOVEMBER:
            case DECEMBER:
                q2 = true;
                break;
            case JANUARY:
            case FEBRUARY:
            case MARCH:
                q3 = true;
                break;
            case APRIL:
            case MAY:
            case JUNE:
                q4 = true;
                break;
            default:
                break;
        }
        
        
        int dayOfYear = date.getDayOfYear();
        int daysBeforeQ1 = startDateOfYear.getDayOfYear()-1;
        int daysBeforeQ2 = startDateOfYear.getDayOfYear()-1+DAYS_IN_Q1;
        int daysBeforeQ3 = 0;
        int daysBeforeQ4 = DAYS_IN_Q3;
        
        if(q1 || q2){
            while(date.getDayOfYear()!= DAYS_IN_YEAR){
                date = date.plusDays(1);
            }
            
            date = date.plusDays(1);
            if(date.isLeapYear()){
            DAYS_IN_Q3 = Month.JANUARY.maxLength() + 29 + Month.MARCH.maxLength();
            }else{
                DAYS_IN_Q3 = Month.JANUARY.maxLength() + 28 + Month.MARCH.maxLength();
            }
            DAYS_IN_YEAR = DAYS_IN_Q1 + DAYS_IN_Q2 + DAYS_IN_Q3 + DAYS_IN_Q4;
            
            while(date.getDayOfYear()!= DAYS_IN_Q3+DAYS_IN_Q4){
                date = date.plusDays(1);
            }
        }else if(q3 || q4){
            while(date.getDayOfYear()!= DAYS_IN_Q3+DAYS_IN_Q4){
                date = date.plusDays(1);
            }
        }
        return date;
    }
}
