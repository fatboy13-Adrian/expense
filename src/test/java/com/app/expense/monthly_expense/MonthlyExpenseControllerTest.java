package com.app.expense.monthly_expense;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.app.expense.controller.monthly_expense.MonthlyExpenseController;
import com.app.expense.dto.MonthlyExpenseDTO;
import com.app.expense.service.monthly_expense.MonthlyExpenseService;

@ExtendWith(MockitoExtension.class)
public class MonthlyExpenseControllerTest {
    //Mock service layer
    @Mock 
    private MonthlyExpenseService svc;

    //Controller under test with mocks injected
    @InjectMocks 
    private MonthlyExpenseController controller;

    //Reusable test values for consistency
    private static final BigDecimal FOOD = BigDecimal.valueOf(515.14);
    private static final BigDecimal GROCERIES = BigDecimal.valueOf(99.44);
    private static final BigDecimal MEDICAL = BigDecimal.valueOf(45.67);

    private MonthlyExpenseDTO createMonthlyExpenseDTO () {
        MonthlyExpenseDTO medto = new MonthlyExpenseDTO();
        medto.setMonth(YearMonth.of(2024, 12));
        medto.setFood(FOOD);
        medto.setGroceries(GROCERIES);
        medto.setMedical(MEDICAL);
        return medto;
    }

    @Test 
    void testRetrieveByMonth () {
        YearMonth month = YearMonth.of(2024, 12);
        MonthlyExpenseDTO dto = createMonthlyExpenseDTO();
        when(svc.retrieveByMonth(month))
        .thenReturn(dto);
        ResponseEntity <MonthlyExpenseDTO> response = 
        controller.retrieveByMonth(month);
        assertEquals(HttpStatus.OK,
        response.getStatusCode());
        MonthlyExpenseDTO body =
        response.getBody();
        assertNotNull(body);
        assertEquals(month, body.getMonth());
        assertEquals(FOOD, body.getFood());
        assertEquals(GROCERIES, body.getGroceries());
        assertEquals(MEDICAL, body.getMedical());
        verify(svc).retrieveByMonth(month);
    }

    @Test 
    void testRetrieveAllMonths () {
        //Test pagination values
        int page = 1;
        int size = 10;

        //Create test DTO
        MonthlyExpenseDTO dto =
        createMonthlyExpenseDTO();

        //Create a Page containing the DTO
        Page<MonthlyExpenseDTO> serviceResult =
        new PageImpl<>(List.of(dto), 
        PageRequest.of(page - 1, size),1);

        //Mock service response
        when(svc.retrieveAllMonths(page, size))
        .thenReturn(serviceResult);

        //Call controller
        ResponseEntity <Page <MonthlyExpenseDTO>> response =
        controller.retrieveAllMonths(page, size);

        //Check HTTP status
        assertEquals(HttpStatus.OK,
        response.getStatusCode());

        //Check response body
        Page<MonthlyExpenseDTO> body =
        response.getBody();

        assertNotNull(body);

        //Check number of records
        assertEquals(1,
        body.getContent().size());

        //Get first result
        MonthlyExpenseDTO result =
        body.getContent().get(0);

        //Check monthly data
        assertEquals(
        YearMonth.of(2026, 12),
        result.getMonth());

        assertEquals(FOOD,result.getFood());
        assertEquals(GROCERIES, result.getGroceries());
        assertEquals(MEDICAL, result.getMedical());

        //Check pagination
        assertEquals(1,
        body.getTotalElements());

        //Verify service was called with correct parameters
        verify(svc).retrieveAllMonths(page, size);
    }
}