package com.app.expense.yearly_expense;
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

import com.app.expense.calculator.YearlyExpenseCalculator;
import com.app.expense.dto.MonthlyExpenseDTO;
import com.app.expense.dto.YearlyExpenseDTO;
import com.app.expense.entity.DailyExpense;
import com.app.expense.repository.DailyExpenseRepository;
import com.app.expense.service.monthly_expense.MonthlyExpenseService;
import com.app.expense.service.yearly_expense.YearlyExpenseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class YearlyExpenseServiceTest {
    @InjectMocks
    private YearlyExpenseServiceImpl svc;
    
    @Mock
    private DailyExpenseRepository repository;

    @Mock
    private MonthlyExpenseService meExpSvc;

    @Mock
    private YearlyExpenseCalculator yeCalculator;

    private DailyExpense de;
    private int year;
    private MonthlyExpenseDTO medto;

    @BeforeEach
    public void setUp () {
        year = 2024;
        de = new DailyExpense();
        de.setDate(LocalDate.of(2024, 12, 31));
        medto = new MonthlyExpenseDTO();
        medto.setFood(BigDecimal.valueOf(150.00));
        medto.setGroceries(BigDecimal.valueOf(200.50));
        medto.setBillsAndUtilities(BigDecimal.valueOf(85.25));
    }

    @Test
    void testRetrieveByYear () {
        //Arrange
        YearMonth targetMonth = YearMonth.from(de.getDate());
        when(repository.findByDateBetween(
        LocalDate.of(2024, 1, 1),
        LocalDate.of(2024, 12, 31)))
        .thenReturn(List.of(de));

        when(meExpSvc.retrieveByMonth(targetMonth))
        .thenReturn(medto);

        //Act
        YearlyExpenseDTO result = 
        svc.retrieveByYear(year);

        //Assert
        assertNotNull(result);
        assertEquals(year, result.getYear());
        
        //Verify downstream calculator logic execution
        verify(yeCalculator, times(1))
        .calculateYearlyExpenses(result, List.of(medto));
    }

    @Test
    void testRetrieveByYearNotFound () {
        //Define target year
        int targetYear = 2024; 

        //Configure mock matching target year
        when(repository.findByDateBetween(
        LocalDate.of(2024, 1, 1),
        LocalDate.of(2024, 12, 31)
        )).thenReturn(List.of());

        //Pass matching targetYear variable
        YearlyExpenseDTO result = 
        svc.retrieveByYear(targetYear); 

        //Assertions
        assertNotNull(result);
        assertEquals(targetYear, result.getYear());
        verify(yeCalculator, times(1))
        .calculateYearlyExpenses(result, List.of());
    }

    @Test
    void testRetrieveAllYears () {
        //Arrange
        DailyExpense de2023 = new DailyExpense();
        de2023.setDate(LocalDate
        .of(2023, 5, 10));

        //Mock findAll to return an unsorted list with duplicate years to test stream logic
        when(repository.findAll())
        .thenReturn(List.of(de, de2023, de)); 

        //Mock inside calls triggered by this::retrieveByYear structure
        when(repository.findByDateBetween(LocalDate
        .of(2023, 1, 1), 
        LocalDate.of(2023, 12, 31)))
        .thenReturn(List.of(de2023));
        when(repository.findByDateBetween(LocalDate
        .of(2024, 1, 1), 
        LocalDate.of(2024, 12, 31)))
        .thenReturn(List.of(de));
        
        when(meExpSvc.retrieveByMonth(YearMonth.of(2023, 5)))
        .thenReturn(medto);
        when(meExpSvc.retrieveByMonth(YearMonth.of(2024, 12)))
        .thenReturn(medto);

        //Act
        List<YearlyExpenseDTO> resultList = 
        svc.retrieveAllYears();

        //Assert
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(2023, resultList.get(0).getYear());
        assertEquals(2024, resultList.get(1).getYear());
    }

    @Test
    void testRetrieveAllYearsNotFound () {
        /**Arrange: Mock the repository 
        to return an empty db list**/
        when(repository.findAll())
        .thenReturn(List.of());

        //Act: Invoke service method
        List<YearlyExpenseDTO> resultList = 
        svc.retrieveAllYears();

        /**Assert: Verify that a valid, 
        empty collection container is returned**/
        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());
        assertEquals(0, resultList.size());

        /**Verify: Ensure repository was queried 
        once & downstream methods were skipped**/
        verify(repository, times(1))
        .findAll();
        
        verify(yeCalculator, times(0))
        .calculateYearlyExpenses(any(), any());
    }
}