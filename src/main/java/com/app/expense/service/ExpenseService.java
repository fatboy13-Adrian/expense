package com.app.expense.service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import com.app.expense.dto.ExpenseInputDTO;
import com.app.expense.dto.ExpenseOutputDTO;

public interface ExpenseService {
    ExpenseOutputDTO create (ExpenseInputDTO dto);
    ExpenseOutputDTO retrieveByDate (LocalDate date);
    List<ExpenseOutputDTO> retrieveByMonth(YearMonth month);
    List <ExpenseOutputDTO> retrieveAll ();
    ExpenseOutputDTO update(LocalDate date, ExpenseInputDTO dto);
}