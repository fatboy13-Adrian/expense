package com.app.expense.exception;
import java.time.LocalDate;

public class DateAlreadyExistsException extends RuntimeException {
    public DateAlreadyExistsException(LocalDate date) {
        super("Expense already exists for date: " + date);
    }
}