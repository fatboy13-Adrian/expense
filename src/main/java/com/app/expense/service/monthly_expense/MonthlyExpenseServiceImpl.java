package com.app.expense.service.monthly_expense;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.expense.calculator.MonthlyExpenseCalculator;
import com.app.expense.dto.MonthlyExpenseDTO;
import com.app.expense.entity.DailyExpense;
import com.app.expense.repository.DailyExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonthlyExpenseServiceImpl implements MonthlyExpenseService {
    private final DailyExpenseRepository repository;
    private final MonthlyExpenseCalculator meCalculator;

    @Override
    public MonthlyExpenseDTO retrieveByMonth (YearMonth month) {
        MonthlyExpenseDTO medto = new MonthlyExpenseDTO();
        medto.setMonth(month);
        meCalculator.calculateMonthlyExpenses(medto);
        return medto;
    }

    @Override
    public Page <MonthlyExpenseDTO> retrieveAllMonths (int page, int size) {
        //Maintain the 0-based conversion for internal processing bounds
        Pageable pageable = PageRequest.of(page - 1, size);

        //Fetch records and extract unique months in memory
        List <DailyExpense> deList = repository.findAll();
        List <YearMonth> uniqueMonths = deList
        .stream().map(de -> YearMonth
        .from(de.getDate())).distinct()
        .sorted().toList();

        //Apply safe pagination window slicing filters
        int start = Math.min((int) 
        pageable.getOffset(), uniqueMonths.size());
        int end = Math.min(start + size, uniqueMonths.size());
        
        //Map ONLY the sliced segment into calculation models
        List<MonthlyExpenseDTO> content = uniqueMonths
        .subList(start, end).stream()
        .map(this::retrieveByMonth)
        .toList();

        //Return standard PageImpl container mapping framework targets
        return new PageImpl <> (content, pageable, uniqueMonths.size());
    }
}