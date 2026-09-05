package com.app.expense.calculator;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import com.app.expense.entity.Expense;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Calculator {
    public BigDecimal calculateIncome (Expense e) {
        return e.getIncome();
    }

    public BigDecimal calculateSavings (Expense e) {
        return e.getEmergencyFund()
        .add(e.getMonthlySavings())
        .add(e.getSrs())
        .add(e.getSsb());
    }

    public BigDecimal calculateInsurance (Expense e) {
        return e.getAiaPrimeLife()
        .add(e.getHsbcCriticare())
        .add(e.getHsbcTermProtector());
    }

    public BigDecimal calculateBillsAndUtilities (Expense e) {
        return e.getMobilePhone()
        .add(e.getElectricity())
        .add(e.getInternet());
    }

    public BigDecimal calculateTax (Expense e) { 
        return e.getIras()
        .add(e.getPropertyTax());
    }

    public BigDecimal calculateTransport (Expense e) {
        return e.getPublicTransport()
        .add(e.getPrivateTransport());
    }

    public BigDecimal calculateFood (Expense e) {
        return e.getBreakfast()
        .add(e.getLunch())
        .add(e.getDinner());
    }

    public BigDecimal calculateWants (Expense e) {
        return e.getEatingOut()
        .add(e.getRecreational())
        .add(e.getHoliday())
        .add(e.getShopping())
        .add(e.getSports())
        .add(e.getTech());
    }
}