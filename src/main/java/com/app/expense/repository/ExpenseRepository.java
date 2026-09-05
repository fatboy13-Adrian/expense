package com.app.expense.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.expense.entity.Expense;

public interface ExpenseRepository extends JpaRepository <Expense, LocalDate> {
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
}