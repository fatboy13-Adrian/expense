package com.app.expense.service.yearly_expense;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.expense.calculator.YearlyExpenseCalculator;
import com.app.expense.dto.MonthlyExpenseDTO;
import com.app.expense.dto.YearlyExpenseDTO;
import com.app.expense.entity.DailyExpense;
import com.app.expense.repository.DailyExpenseRepository;
import com.app.expense.service.monthly_expense.MonthlyExpenseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class YearlyExpenseServiceImpl implements YearlyExpenseService {
    private final DailyExpenseRepository repository;
    private final MonthlyExpenseService meExpSvc;
    private final YearlyExpenseCalculator yeCalculator;

    @Override
    public YearlyExpenseDTO retrieveByYear (int year) {
        YearlyExpenseDTO yedto = new YearlyExpenseDTO();
        yedto.setYear(year);

        //Define the start and end of the entire year
        LocalDate startOfYear = LocalDate
        .of(year, 1, 1);
        LocalDate endOfYear = LocalDate
        .of(year, 12, 31);

        //Query DB ONCE for the whole year instead of 12 times
        List <DailyExpense> yearlyData = repository
        .findByDateBetween(startOfYear, endOfYear);

        //Build the MonthlyExpenseDTOs only for months that actually have data
        List <MonthlyExpenseDTO> medtoList = yearlyData
        .stream().map(de -> YearMonth.from(de.getDate()))
        .distinct().map(meExpSvc::retrieveByMonth).toList();

        //Run clean calculator
        yeCalculator.calculateYearlyExpenses(yedto, medtoList);
        return yedto;
    }

    @Override
    public List <YearlyExpenseDTO> retrieveAllYears () {
        //Retrieve the existing record entries
        List <DailyExpense> deList = repository.findAll();

        //Extract unique, sorted years directly from the list
        return deList.stream().map(de -> de.getDate()
        .getYear()).distinct().sorted()
        .map(this::retrieveByYear)
        .toList();
    }
}