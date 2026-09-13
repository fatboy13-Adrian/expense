package com.app.expense.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.expense.entity.DailyExpense;

@Repository
public interface DailyExpenseRepository extends JpaRepository <DailyExpense, LocalDate> {
    List <DailyExpense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    Page <DailyExpense> findByDateBetween(LocalDate startdate, LocalDate endDate, Pageable pageable);
}