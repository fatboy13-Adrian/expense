package com.app.expense.exception.date;
import java.time.LocalDate;

public class DateNotFoundException extends RuntimeException {
    public DateNotFoundException(LocalDate date) {
        super("Expense not found for date: " + date);
    }
}