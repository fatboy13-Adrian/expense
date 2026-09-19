package com.app.expense.service.daily_expense;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import com.app.expense.dto.DailyExpenseDTO;

public interface DailyExpenseService {
    DailyExpenseDTO create (DailyExpenseDTO dto);
    DailyExpenseDTO retrieveByDate (LocalDate date);
    Page <DailyExpenseDTO> retrieveAllDates (int page, int size);
    DailyExpenseDTO update (LocalDate date, DailyExpenseDTO dto);
    void delete (LocalDate date);
}