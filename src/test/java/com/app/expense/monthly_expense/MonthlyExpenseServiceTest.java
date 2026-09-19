package com.app.expense.monthly_expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import com.app.expense.calculator.MonthlyExpenseCalculator;
import com.app.expense.dto.DailyExpenseDTO;
import com.app.expense.dto.MonthlyExpenseDTO;
import com.app.expense.entity.DailyExpense;
import com.app.expense.repository.DailyExpenseRepository;
import com.app.expense.service.monthly_expense.MonthlyExpenseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MonthlyExpenseServiceTest {
    @InjectMocks
    private MonthlyExpenseServiceImpl svc;
    
    @Mock
    private DailyExpenseRepository repository;

    @Mock
    private MonthlyExpenseCalculator meCalculator;

    private DailyExpense de;

    @BeforeEach
    public void setUp () {
        //Declared missing date context variable
        LocalDate date = LocalDate
        .of(2024, 12, 31);
        
        DailyExpenseDTO dedto = new DailyExpenseDTO(); 
        dedto.setDate(date);
        dedto.setBreakfast(BigDecimal.valueOf(5.1));
        dedto.setLunch(BigDecimal.valueOf(6.4));
        dedto.setDinner(BigDecimal.valueOf(7.1));
        dedto.setGroceries(BigDecimal.valueOf(9.99));
        dedto.setMedical(BigDecimal.valueOf(22.34));

        //Entity representing db record
        de = new DailyExpense();
        de.setDate(date);
        de.setBreakfast(BigDecimal.valueOf(5.1));
        de.setLunch(BigDecimal.valueOf(6.4));
        de.setDinner(BigDecimal.valueOf(7.1));
        de.setGroceries(BigDecimal.valueOf(9.99));
        de.setMedical(BigDecimal.valueOf(22.34));
    }

    @Test
    void testRetrieveByMonth () {
        //Define month to retrieve
        YearMonth targetMonth = 
        YearMonth.of(2026, 1);

        //Call service method
        MonthlyExpenseDTO result = 
        svc.retrieveByMonth(targetMonth);

        //Assertions
        assertNotNull(result);
        assertEquals(targetMonth, result.getMonth());

        //Verify monthly calculator execution
        verify(meCalculator, times(1))
        .calculateMonthlyExpenses(result);
    }

    @Test 
    void testRetrieveByMonthNotFound () {
        //Define a target month
        YearMonth targetMonth = 
        YearMonth.of(2026, 1);

        //Call service method
        MonthlyExpenseDTO result = 
        svc.retrieveByMonth(targetMonth);
        
        /**Assert that a valid container
        DTO is still returned with month set**/
        assertNotNull(result);
        assertEquals(targetMonth, 
        result.getMonth());

        /**Verify that monthly calculator was 
        still invoked once to process empty DTO safely**/
        verify(meCalculator, times(1))
        .calculateMonthlyExpenses(result);
    }

    @Test
    void testRetrieveAllMonths () {
        //Define page
        int page = 1;
        int size = 2;

        /**Create unsorted records with duplicate 
        months to test streaming logic**/
        DailyExpense marchExpense = 
        new DailyExpense();
        marchExpense.setDate(LocalDate
        .of(2026, 3, 15));

        DailyExpense januaryExpense = 
        new DailyExpense();
        januaryExpense.setDate(LocalDate
        .of(2026, 1, 1));

        DailyExpense februaryExpense = 
        new DailyExpense();
        februaryExpense.setDate(LocalDate
        .of(2026, 2, 10));
        
        DailyExpense duplicateJanExpense = 
        new DailyExpense();
        duplicateJanExpense.setDate(LocalDate
        .of(2026, 1, 20));

        when(repository.findAll()).thenReturn(List.of(
        marchExpense, januaryExpense, 
        februaryExpense, 
        duplicateJanExpense));

        //Call the service with a page size of 2
        Page<MonthlyExpenseDTO> result = 
        svc.retrieveAllMonths(page, size);

        //Assertions
        assertNotNull(result);
        assertEquals(3, result.getTotalElements()); 
        assertEquals(2, result.getContent().size()); 

        //Verifies distinct sorting logic order
        assertEquals(YearMonth.of(2026, 1), 
        result.getContent().get(0).getMonth());
        assertEquals(YearMonth.of(2026, 2), 
        result.getContent().get(1).getMonth());

        verify(repository, times(1))
        .findAll();
        
        /**Calculator triggers only twice
        for sliced content context window**/
        verify(meCalculator, times(2))
        .calculateMonthlyExpenses(any(MonthlyExpenseDTO.class));
    }

    @Test
    void testRetrieveAllMonthsNotFound () {
        int page = 1;
        int size = 10;

        when(repository.findAll())
        .thenReturn(List.of());

        Page <MonthlyExpenseDTO> result = 
        svc.retrieveAllMonths(page, size);

        assertNotNull(result);
        assertEquals(0, 
        result.getContent().size());
        assertTrue(result.isEmpty());
        assertEquals(0, 
        result.getTotalElements());

        verify(repository, times(1))
        .findAll();
        verify(meCalculator, times(0))
        .calculateMonthlyExpenses(any(MonthlyExpenseDTO.class));
    }
}