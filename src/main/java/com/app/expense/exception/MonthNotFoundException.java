package com.app.expense.exception;
import java.time.YearMonth;

public class MonthNotFoundException extends RuntimeException {
    public MonthNotFoundException(YearMonth month) {
        super("Budget not found: " + month);
    }
}