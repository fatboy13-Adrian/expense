package com.app.expense.exception.date;
import java.time.LocalDate;

public class DateAlreadyExistsException extends RuntimeException {
    public DateAlreadyExistsException(LocalDate date) {
        super("Expense already exists for date: " + date);
    }
}