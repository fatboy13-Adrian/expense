package com.app.expense.mapper;
import java.time.YearMonth;

import org.springframework.stereotype.Component;

import com.app.expense.calculator.Calculator;
import com.app.expense.dto.ExpenseInputDTO;
import com.app.expense.dto.ExpenseOutputDTO;
import com.app.expense.entity.Expense;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExpenseMapper {
    private final Calculator calculator;

    public Expense toEntity (ExpenseInputDTO dto) {
        return Expense.builder()
        .date(dto.getDate())
        .income(dto.getIncome())
        .emergencyFund(dto.getEmergencyFund())
        .monthlySavings(dto.getMonthlySavings())
        .srs(dto.getSrs())
        .ssb(dto.getSsb())
        .aiaPrimeLife(dto.getAiaPrimeLife())
        .hsbcCriticare(dto.getHsbcCriticare())
        .hsbcTermProtector(dto.getHsbcTermProtector())
        .mobilePhone(dto.getMobilePhone())
        .internet(dto.getInternet())
        .electricity(dto.getElectricity())
        .iras(dto.getIras())
        .propertyTax(dto.getPropertyTax())
        .publicTransport(dto.getPublicTransport())
        .privateTransport(dto.getPrivateTransport())
        .breakfast(dto.getBreakfast())
        .lunch(dto.getLunch())
        .dinner(dto.getDinner())
        .groceries(dto.getGroceries())
        .eatingOut(dto.getEatingOut())
        .recreational(dto.getRecreational())
        .holiday(dto.getHoliday())
        .shopping(dto.getShopping())
        .sports(dto.getSports())
        .tech(dto.getTech())
        .mortgage(dto.getMortgage())
        .debt(dto.getDebt())
        .parentsAllowance(dto.getParentsAllowance())
        .haircut(dto.getHaircut())
        .medical(dto.getMedical())
        .tithes(dto.getTithes())
        .build();
    }

    public ExpenseOutputDTO toDto (Expense e) {
        return ExpenseOutputDTO.builder()
        .month(YearMonth.from(e.getDate()))
        .income(calculator.calculateIncome(e))
        .savings(calculator.calculateSavings(e))
        .insurance(calculator.calculateInsurance(e))
        .billsAndUtilities(calculator.calculateBillsAndUtilities(e))
        .tax(calculator.calculateTax(e))
        .transport(calculator.calculateTransport(e))
        .food(calculator.calculateFood(e))
        .groceries(e.getGroceries())
        .wants(calculator.calculateWants(e))
        .mortgage(e.getMortgage())
        .debt(e.getDebt())
        .parentsAllowance(e.getParentsAllowance())
        .haircut(e.getHaircut())
        .medical(e.getMedical())
        .tithes(e.getTithes())
        .build();
    }
}