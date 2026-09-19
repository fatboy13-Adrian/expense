package com.app.expense.daily_expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.app.expense.dto.DailyExpenseDTO;
import com.app.expense.entity.DailyExpense;
import com.app.expense.exception.date.DateAlreadyExistsException;
import com.app.expense.exception.date.DateNotFoundException;
import com.app.expense.mapper.DailyExpenseMapper;
import com.app.expense.repository.DailyExpenseRepository;
import com.app.expense.service.daily_expense.DailyExpenseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DailyExpenseServiceTest {
    @InjectMocks
    private DailyExpenseServiceImpl svc;

    @Mock
    private DailyExpenseRepository repository;

    @Mock
    private DailyExpenseMapper mapper;

    private LocalDate date;
    private DailyExpense de;
    private DailyExpenseDTO dedto;

    @BeforeEach
    public void setUp () {
        date = LocalDate
        .of(2026, 1, 1);

        //DTO used as input for service methods
        dedto = new DailyExpenseDTO();
        dedto.setDate(date);
        dedto.setBreakfast(BigDecimal.valueOf(5.1));
        dedto.setLunch(BigDecimal.valueOf(6.4));
        dedto.setDinner(BigDecimal.valueOf(7.1));
        dedto.setGroceries(BigDecimal.valueOf(9.99));
        dedto.setMedical(BigDecimal.valueOf(22.34));

        //Entity representing the database record
        de = new DailyExpense();
        de.setDate(date);
        de.setBreakfast(BigDecimal.valueOf(5.1));
        de.setLunch(BigDecimal.valueOf(6.4));
        de.setDinner(BigDecimal.valueOf(7.1));
        de.setGroceries(BigDecimal.valueOf(9.99));
        de.setMedical(BigDecimal.valueOf(22.34));
    }

    @Test
    void testCreate () {
        //Mock: no existing expense exists for this date
        when(repository.findById(date))
        .thenReturn(Optional.empty());

        //Mock mapper: convert DTO to entity
        when(mapper.toEntity(any(DailyExpenseDTO.class)))
        .thenReturn(de);

        //Mock repository: save the entity
        when(repository.save(any(DailyExpense.class)))
        .thenReturn(de);

        //Mock mapper: convert saved entity back to DTO
        when(mapper.toDto(any(DailyExpense.class)))
        .thenReturn(dedto);

        //Call the service method
        DailyExpenseDTO result = svc.create(dedto);

        //Verify that a result was returned
        assertNotNull(result);

        //Verify that the returned DTO contains the expected values
        assertEquals(dedto.getDate(), result.getDate());
        assertEquals(dedto.getBreakfast(), result.getBreakfast());

        //Verify that repository.save() was called once
        verify(repository, times(1))
        .save(any(DailyExpense.class));

        //Verify that the mapper converted the DTO to an entity
        verify(mapper, times(1))
        .toEntity(dedto);

        //Verify that the mapper converted the entity back to a DTO
        verify(mapper, times(1))
        .toDto(de);
    }

    @Test
    void testCreateDateAlreadyExists () {
        //Mock: expense already exists for this date
        when(repository.findById(date)).thenReturn(Optional.of(de));

        //Expect exception when creating duplicate expense
        DateAlreadyExistsException exception =
        assertThrows(DateAlreadyExistsException.class,
        () -> svc.create(dedto));
        assertNotNull(exception);

        //Ensure save is never called
        verify(repository, never()).save(any(DailyExpense.class));
    }

    @Test
    void testRetrieveByDate () {
        //Mock: expense exists for given date
        when(repository.findById(date)).thenReturn(Optional.of(de));
        when(mapper.toDto(de)).thenReturn(dedto);
        DailyExpenseDTO result = svc.retrieveByDate(date);
        assertNotNull(result);
        assertEquals(dedto.getDate(), result.getDate());
        verify(repository, times(1)).findById(date);
    }

    @Test
    void testRetrieveByDateNotFound () {
        //Mock: no expense found
        when(repository.findById(date)).thenReturn(Optional.empty());

        //Expect exception when not found
        DateNotFoundException exception =
        assertThrows(DateNotFoundException.class,
        () -> svc.retrieveByDate(date));
        assertNotNull(exception);
        verify(repository, times(1)).findById(date);
    }

    @Test
    void testRetrieveAllDates () {
        //Define the page number & page size
        int page = 1;
        int size = 10;

        //Create a Pageable that repository is expected to receive
        Pageable pageable;
        pageable = PageRequest.of(page - 1, size);

        //Create a list of expense entities
        List <DailyExpense> expenseList = List.of(de);

        //Create a Page containing expense entities
        Page <DailyExpense> expensePage =
        new PageImpl <> (expenseList, 
        pageable, expenseList.size());

        //Mock repository response
        when(repository
        .findAll(pageable))
        .thenReturn(expensePage);

        //Mock entity-to-DTO mapping
        when(mapper.toDto(de))
        .thenReturn(dedto);

        //Call service method
        Page <DailyExpenseDTO> result =
        svc.retrieveAllDates(page, size);

        //Verify that a result was returned
        assertNotNull(result);

        //Verify page contains one DTO
        assertEquals(1, 
        result.getContent().size());

        //Verify that correct DTO was returned
        assertEquals(dedto, 
        result.getContent().get(0));

        //Verify page number
        assertEquals(0, 
        result.getNumber());

        //Verify page size
        assertEquals(size, 
        result.getSize());

        //Verify that repository was called once
        verify(repository, 
        times(1))
        .findAll(pageable);

        //Verify that the mapper was called once
        verify(mapper, 
        times(1))
        .toDto(de);
    }

    @Test
    void testRetrieveAllDatesNotFound () {
        //Define the page number and page size
        int page = 1;
        int size = 10;

        //Create the expected Pageable
        Pageable pageable = 
        PageRequest.of(page - 1, size);

        //Mock the repository to return an empty page
        Page <DailyExpense> empty = new PageImpl<>(List
        .of(), pageable, 0);

        when(repository.findAll(pageable))
        .thenReturn(empty);

        //Call the service method
        Page <DailyExpenseDTO> result = 
        svc.retrieveAllDates(page, size);

        //Verify that a result was returned
        assertNotNull(result);

        //Verify that the page contains no records
        assertEquals(0, 
        result.getContent().size());

        //Verify that page is empty
        assertTrue(result.isEmpty());

        //Verify that total number of records is zero
        assertEquals(0, 
        result.getTotalElements());

        //Verify that repository was called once
        verify(repository, 
        times(1))
        .findAll(pageable);

        //Verify that the mapper was never called
        verify(mapper, 
        times(0))
        .toDto(any(DailyExpense.class));
    }

    @Test
    void testDelete () {
        //Arrange
        date = LocalDate
        .of(2026, 1, 15);

        DailyExpense expense = new DailyExpense();

        when(repository.findById(date))
        .thenReturn(Optional.of(expense));

        //Act
        svc.delete(date);

        //Assert
        verify(repository, times(1))
        .findById(date);
        verify(repository, times(1))
        .deleteById(date);
    }

    @Test
    void testDeleteNotFound () {
        //Arrange
        date = LocalDate
        .of(2026, 01, 15);
        when(repository.findById(date))
        .thenReturn(Optional.empty());

        //Act & Assert
        DateNotFoundException exception = 
        assertThrows(DateNotFoundException.class,
        () -> svc.delete(date));

        //Verify exception was thrown
        assertNotNull(exception);

        //Verify that findById was called
        verify(repository, times(1))
        .findById(date);

        //deleteById must NOT be called because the date doesn't exist
        verify(repository, never())
        .deleteById(date);
    }
}