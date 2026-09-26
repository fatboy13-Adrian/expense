package com.app.expense.service.daily_expense;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.expense.dto.DailyExpenseDTO;
import com.app.expense.entity.DailyExpense;
import com.app.expense.exception.date.DateAlreadyExistsException;
import com.app.expense.exception.date.DateNotFoundException;
import com.app.expense.mapper.DailyExpenseMapper;
import com.app.expense.repository.DailyExpenseRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyExpenseServiceImpl implements DailyExpenseService {
    private final DailyExpenseRepository repository;
    private final DailyExpenseMapper mapper;

    @Override
    @Transactional
    public DailyExpenseDTO create (DailyExpenseDTO dto) {
        repository.findById(dto.getDate()).ifPresent(existing -> {
            throw new DateAlreadyExistsException(dto.getDate());
        });

        DailyExpense de = mapper.toEntity(dto);
        DailyExpense saved = repository.save(de);
        return mapper.toDto(saved);
    }

    @Override
    public DailyExpenseDTO retrieveByDate (LocalDate date) {
        DailyExpense e = repository.findById(date)
        .orElseThrow(() -> new DateNotFoundException(date));
        return mapper.toDto(e);
    }

    @Override 
    public Page <DailyExpenseDTO> retrieveAllDates (int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional
    public DailyExpenseDTO update (LocalDate date, DailyExpenseDTO dto) {
        DailyExpense de = repository.findById(date)
        .orElseThrow(() -> new DateNotFoundException(date));

        updateDailyExpense(de, dto);
        DailyExpense updated = repository.save(de);
        return mapper.toDto(updated);
    }

    private void updateDailyExpense (DailyExpense de, DailyExpenseDTO dto) {
        de.setIncome(dto.getIncome());
        de.setEmergencyFund(dto.getEmergencyFund());
        de.setSsb(dto.getSsb());
        de.setSrs(dto.getSrs());
        de.setAiaPrimeLife(dto.getAiaPrimeLife());
        de.setHsbcCriticare(dto.getHsbcCriticare());
        de.setHsbcTermProtector(dto.getHsbcTermProtector());
        de.setMobilePhone(dto.getMobilePhone());
        de.setElectricity(dto.getElectricity());
        de.setInternet(dto.getInternet());
        de.setIras(dto.getIras());
        de.setPropertyTax(dto.getPropertyTax());
        de.setPublicTransport(dto.getPublicTransport());
        de.setPrivateTransport(dto.getPrivateTransport());
        de.setBreakfast(dto.getBreakfast());
        de.setLunch(dto.getLunch());
        de.setDinner(dto.getDinner());
        de.setGroceries(dto.getGroceries());
        de.setEatingOut(dto.getEatingOut());
        de.setRecreational(dto.getRecreational());
        de.setHoliday(dto.getHoliday());
        de.setShopping(dto.getShopping());
        de.setSports(dto.getSports());
        de.setTech(dto.getTech());
        de.setMortgage(dto.getMortgage());
        de.setDebt(dto.getDebt());
        de.setParentsAllowance(dto.getParentsAllowance());
        de.setHaircut(dto.getHaircut());
        de.setMedical(dto.getMedical());
        de.setTithes(dto.getTithes());
    }

    @Override
    @Transactional
    public void delete (LocalDate date) {
        repository.findById(date)
        .orElseThrow(() -> new DateNotFoundException(date));
        repository.deleteById(date);
    }
}