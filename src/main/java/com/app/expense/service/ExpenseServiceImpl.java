package com.app.expense.service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.expense.dto.ExpenseInputDTO;
import com.app.expense.dto.ExpenseOutputDTO;
import com.app.expense.entity.Expense;
import com.app.expense.exception.DateAlreadyExistsException;
import com.app.expense.exception.DateNotFoundException;
import com.app.expense.mapper.ExpenseMapper;
import com.app.expense.repository.ExpenseRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
     //Data access layer for expense
    private final ExpenseRepository repository;

    //Mapper class for conversion between entity to dto
    private final ExpenseMapper mapper;

    @Override
    @Transactional
    public ExpenseOutputDTO create (ExpenseInputDTO dto) {
        //Checks if date already exists
        repository.findById(dto.getDate()).ifPresent(existing -> {
            throw new DateAlreadyExistsException(dto.getDate());
        });

        //Convert DTO to expense
        Expense e = mapper.toEntity(dto);

        //Save new record to DB
        Expense saved = repository.save(e);

        //Convert expense to DTO
        return mapper.toDto(saved);
    }

    @Override
    public ExpenseOutputDTO retrieveByDate (LocalDate date) {
        //Fetch expense by date or throw exception
        Expense e = repository.findById(date)
        .orElseThrow(() -> new DateNotFoundException(date));

        //Convert expense to DTO & return
        return mapper.toDto(e);
    }

    @Override
    public List <ExpenseOutputDTO> retrieveByMonth (YearMonth month) {
        //Get month from 1st to last day of the current month
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();
        List<Expense> e = repository.findByDateBetween(startDate, endDate);

        return e.stream()
        .map(mapper::toDto)
        .toList();
    }

    @Override
    public List <ExpenseOutputDTO> retrieveAll () {
        return repository
        .findAll()
        .stream()
        .map(mapper::toDto)
        .toList();
    }

    @Override
    public ExpenseOutputDTO update (LocalDate date, ExpenseInputDTO dto) {
        Expense e = repository.findById(date)
        .orElseThrow(() -> new DateNotFoundException(date));
        updateExpenseRecord (e, dto);
        Expense updated = repository.save(e);
        return mapper.toDto(updated);
    }

    private void updateExpenseRecord (Expense e, ExpenseInputDTO dto) {
        //Update all fields from DTO into expense
        e.setIncome(dto.getIncome());
        e.setEmergencyFund(dto.getEmergencyFund());
        e.setMonthlySavings(dto.getMonthlySavings());
        e.setSrs(dto.getSrs());
        e.setSsb(dto.getSsb());
        e.setAiaPrimeLife(dto.getAiaPrimeLife());
        e.setHsbcCriticare(dto.getHsbcCriticare());
        e.setHsbcTermProtector(dto.getHsbcTermProtector());
        e.setMobilePhone(dto.getMobilePhone());
        e.setElectricity(dto.getElectricity());
        e.setInternet(dto.getInternet());
        e.setIras(dto.getIras());
        e.setPropertyTax(dto.getPropertyTax());
        e.setPublicTransport(dto.getPublicTransport());
        e.setPrivateTransport(dto.getPrivateTransport());
        e.setBreakfast(dto.getBreakfast());
        e.setLunch(dto.getLunch());
        e.setDinner(dto.getDinner());
        e.setGroceries(dto.getGroceries());
        e.setEatingOut(dto.getEatingOut());
        e.setRecreational(dto.getRecreational());
        e.setHoliday(dto.getHoliday());
        e.setShopping(dto.getShopping());
        e.setSports(dto.getSports());
        e.setTech(dto.getTech());
        e.setMortgage(dto.getMortgage());
        e.setDebt(dto.getDebt());
        e.setParentsAllowance(dto.getParentsAllowance());
        e.setHaircut(dto.getHaircut());
        e.setMedical(dto.getMedical());
        e.setTithes(dto.getTithes());
    }
}