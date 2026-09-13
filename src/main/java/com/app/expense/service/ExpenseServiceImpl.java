package com.app.expense.service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.expense.calculator.MonthlyExpenseCalculator;
import com.app.expense.calculator.YearlyExpenseCalculator;
import com.app.expense.dto.DailyExpenseDTO;
import com.app.expense.dto.MonthlyExpenseDTO;
import com.app.expense.dto.YearlyExpenseDTO;
import com.app.expense.entity.DailyExpense;
import com.app.expense.exception.date.DateAlreadyExistsException;
import com.app.expense.exception.date.DateNotFoundException;
import com.app.expense.mapper.DailyExpenseMapper;
import com.app.expense.repository.DailyExpenseRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final DailyExpenseRepository repository;
    private final MonthlyExpenseCalculator meCalculator;
    private final YearlyExpenseCalculator yeCalculator;
    private final DailyExpenseMapper mapper;

    @Override
    @Transactional
    public DailyExpenseDTO create (DailyExpenseDTO dto) {
        repository.findById(dto.getDate()).ifPresent(existing -> {
            throw new DateAlreadyExistsException(dto.getDate());
        });

        DailyExpense de = mapper
        .toEntity(dto);
        DailyExpense saved = repository
        .save(de);
        return mapper
        .toDto(saved);
    }

    @Override
    public DailyExpenseDTO retrieveByDate (LocalDate date) {
        DailyExpense e = repository
        .findById(date)
        .orElseThrow(() -> new DateNotFoundException(date));
        return mapper
        .toDto(e);
    }

    @Override
    public MonthlyExpenseDTO retrieveByMonth (YearMonth month) {
        MonthlyExpenseDTO medto = new MonthlyExpenseDTO();
        medto.setMonth(month);
        meCalculator
        .calculateMonthlyExpenses(medto);
        return medto;
    }

    @Override
    public YearlyExpenseDTO retrieveByYear (int year) {
        YearlyExpenseDTO yedto = new YearlyExpenseDTO();
        yedto.setYear(year);

        //Define the start and end of the entire year
        LocalDate startOfYear = LocalDate
        .of(year, 1, 1);
        LocalDate endOfYear = LocalDate
        .of(year, 12, 31);

        //Query DB ONCE for the whole year instead of 12 times
        List<DailyExpense> yearlyData = repository
        .findByDateBetween(startOfYear, endOfYear);

        //Build the MonthlyExpenseDTOs only for months that actually have data
        List<MonthlyExpenseDTO> medtoList = yearlyData
        .stream()
        .map(de -> YearMonth
        .from(de.getDate()))
        .distinct()
        .map(this::retrieveByMonth)
        .toList();

        //Run your clean calculator
        yeCalculator
        .calculateYearlyExpenses(yedto, medtoList);
        return yedto;
    }


    @Override 
    public Page<DailyExpenseDTO> retrieveAllDates (int page, int size) {
        Pageable pageable = PageRequest
        .of(page - 1, size);
        return repository
        .findAll(pageable)
        .map(mapper::toDto);
    }

    @Override
    public Page<MonthlyExpenseDTO> retrieveAllMonths (int page, int size) {
        //Maintain the 0-based conversion for internal processing bounds
        Pageable pageable = PageRequest.of(page - 1, size);

        //Fetch records and extract unique months in memory
        List<DailyExpense> deList = repository
        .findAll();
        List<YearMonth> uniqueMonths = deList
        .stream()
        .map(de -> YearMonth
        .from(de.getDate()))
        .distinct()
        .sorted()
        .toList();

        //Apply safe pagination window slicing filters
        int start = Math.min((int) 
        pageable.getOffset(), uniqueMonths.size());
        int end = Math.min(start + size, uniqueMonths.size());
        
        //Map ONLY the sliced segment into calculation models
        List<MonthlyExpenseDTO> content = uniqueMonths
        .subList(start, end).stream()
        .map(this::retrieveByMonth)
        .toList();

        //Return standard PageImpl container mapping framework targets
        return new PageImpl<>(content, pageable, uniqueMonths.size());
    }


    @Override
    public List<YearlyExpenseDTO> retrieveAllYears () {
        //Retrieve the existing record entries
        List<DailyExpense> deList = repository
        .findAll();

        //Extract unique, sorted years directly from the list
        return deList.stream()
        .map(de -> de.getDate()
        .getYear())
        .distinct()
        .sorted()
        .map(this::retrieveByYear)
        .toList();
    }

    @Override
    @Transactional
    public DailyExpenseDTO update (LocalDate date, 
    DailyExpenseDTO dto) {
        DailyExpense de = repository
        .findById(date)
        .orElseThrow(() -> new DateNotFoundException(date));

        updateDailyExpense(de, dto);
        DailyExpense updated = repository
        .save(de);
        return mapper
        .toDto(updated);
    }

    @Override
    @Transactional
    public void delete (LocalDate date) {
        repository.findById(date)
        .orElseThrow(() -> new DateNotFoundException(date));
        repository
        .deleteById(date);
    }

    private void updateDailyExpense(DailyExpense de, DailyExpenseDTO dto) {
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
}