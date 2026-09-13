package com.app.expense.service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Page;

import com.app.expense.dto.DailyExpenseDTO;
import com.app.expense.dto.MonthlyExpenseDTO;
import com.app.expense.dto.YearlyExpenseDTO;

public interface ExpenseService {
    DailyExpenseDTO create (DailyExpenseDTO dto);
    DailyExpenseDTO retrieveByDate (LocalDate date);
    MonthlyExpenseDTO retrieveByMonth (YearMonth month);
    YearlyExpenseDTO retrieveByYear (int year);
    Page <DailyExpenseDTO> retrieveAllDates (int page, int size);
    Page <MonthlyExpenseDTO> retrieveAllMonths(int page, int size);
    List <YearlyExpenseDTO> retrieveAllYears ();
    DailyExpenseDTO update (LocalDate date, DailyExpenseDTO dto);
    void delete (LocalDate date);
}