package com.app.expense.service.yearly_expense;
import com.app.expense.dto.YearlyExpenseDTO;
import java.util.List;

public interface YearlyExpenseService {
    YearlyExpenseDTO retrieveByYear (int year);
    List <YearlyExpenseDTO> retrieveAllYears ();
}