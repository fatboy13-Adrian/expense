package com.app.expense.exception.month;
import java.time.YearMonth;

public class MonthAlreadyExistsException extends RuntimeException {
    public MonthAlreadyExistsException(YearMonth month) {
        super ("Budget already exists for " + month);
    }
}