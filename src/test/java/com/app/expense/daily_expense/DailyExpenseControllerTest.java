package com.app.expense.daily_expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.app.expense.controller.DailyExpenseController;
import com.app.expense.dto.DailyExpenseDTO;
import com.app.expense.service.daily_expense.DailyExpenseService;

@ExtendWith(MockitoExtension.class)
public class DailyExpenseControllerTest {
    //Mock service layer
    @Mock 
    private DailyExpenseService svc;

    //Controller under test with mocks injected
    @InjectMocks 
    private DailyExpenseController controller;

    //Reusable test values for consistency
    private static final BigDecimal BREAKFAST = BigDecimal.valueOf(5.15);
    private static final BigDecimal LUNCH = BigDecimal.valueOf(7.15);
    private static final BigDecimal DINNER = BigDecimal.valueOf(6.15);
    private static final BigDecimal GROCERIES = BigDecimal.valueOf(9.99);
    private static final BigDecimal MEDICAL = BigDecimal.valueOf(15.15);

    private DailyExpenseDTO createExpenseDTO () {
        DailyExpenseDTO dedto = new DailyExpenseDTO();
        dedto.setDate(LocalDate
        .of(2026, 1, 1));
        dedto.setBreakfast(BREAKFAST);
        dedto.setLunch(LUNCH);
        dedto.setDinner(DINNER);
        dedto.setGroceries(GROCERIES);
        dedto.setMedical(MEDICAL);
        return dedto;
    }

    @Test
    void testCreate () {
        DailyExpenseDTO dto = createExpenseDTO();

        //Mock service response
        when(svc.create(dto)).thenReturn(dto);
        
        //Call controller
        ResponseEntity <DailyExpenseDTO> response = controller.create(dto);

        //Check HTTP status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        DailyExpenseDTO body = response.getBody();

        //Validate returned values
        assertEquals(BREAKFAST, body.getBreakfast());
        assertEquals(LUNCH, body.getLunch());
        assertEquals(DINNER, body.getDinner());
        assertEquals(GROCERIES, body.getGroceries());
        assertEquals(MEDICAL, body.getMedical());
    }

    //Generate testRetrieveByDate

    @Test
    void testRetrieveAllDates () {
        DailyExpenseDTO dto = createExpenseDTO();
        int page = 1;
        int size = 10;

        Pageable pageable = 
        PageRequest.of(page - 1, size);

        Page<DailyExpenseDTO> serviceResult =
        new PageImpl<>(List.of(dto), pageable, 1);

        //Mock service response
        when(svc.retrieveAllDates(page, size))
        .thenReturn(serviceResult);

        //Call controller
        ResponseEntity<Page<DailyExpenseDTO>> response =
        controller.retrieveAllDates(page, size);

        //Check HTTP status
        assertEquals(HttpStatus.OK, 
        response.getStatusCode());

        //Check response body
        Page<DailyExpenseDTO> body = 
        response.getBody();

        assertNotNull(body);
        assertEquals(1, 
        body.getContent().size());

        DailyExpenseDTO result = 
        body.getContent().get(0);

        //Validate returned values
        assertEquals(BREAKFAST, result.getBreakfast());
        assertEquals(LUNCH, result.getLunch());
        assertEquals(DINNER, result.getDinner());
        assertEquals(GROCERIES, result.getGroceries());
        assertEquals(MEDICAL, result.getMedical());
    }

    @Test
    public void testUpdate () {
        //Create original DTO & test date
        DailyExpenseDTO dto = createExpenseDTO();
        LocalDate testDate = dto.getDate();

        //Create updated DTO with modified values
        DailyExpenseDTO updatedDto = createExpenseDTO();
        updatedDto.setBreakfast(BigDecimal.valueOf(5.25));
        updatedDto.setMedical(BigDecimal.valueOf(15.25));

        //Mock service update behaviour
        when(svc.update(testDate, updatedDto))
        .thenReturn(updatedDto);

        //Call controller update method
        ResponseEntity<DailyExpenseDTO> response = 
        controller.update(testDate, updatedDto);

        //Assert: verify HTTP status
        assertEquals(HttpStatus.OK, 
        response.getStatusCode());

        //Extract response body for validation
        DailyExpenseDTO body = 
        response.getBody();

        //Assert: verify updated values
        assertEquals(BigDecimal.valueOf(5.25), 
        body.getBreakfast());
        assertEquals(BigDecimal.valueOf(15.25), 
        body.getMedical());
    }

    @Test
    void testDelete () {
        LocalDate date = 
        LocalDate.of(2026, 1, 1);

        //Mock void service method
        doNothing().when(svc).delete(date);

        //Call controller
        ResponseEntity<Void> response =
        controller.delete(date);

        //Check HTTP status
        assertEquals( HttpStatus.NO_CONTENT,
        response.getStatusCode());

        //Check response body is empty
        assertNull(response.getBody());

        //Verify service was called once
        verify(svc, times(1))
        .delete(date);
    }
}