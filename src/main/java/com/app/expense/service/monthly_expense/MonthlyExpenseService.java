package com.app.expense.service.monthly_expense;
import java.time.YearMonth;
import org.springframework.data.domain.Page;
import com.app.expense.dto.MonthlyExpenseDTO;

public interface MonthlyExpenseService {
    MonthlyExpenseDTO retrieveByMonth (YearMonth month);
    Page <MonthlyExpenseDTO> retrieveAllMonths(int page, int size);
}