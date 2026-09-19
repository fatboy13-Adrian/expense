package com.app.expense.dto;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YearlyExpenseDTO {
    private int year;
    private BigDecimal income = BigDecimal.ZERO;
    private BigDecimal cpf = BigDecimal.ZERO;
    private BigDecimal cdac = BigDecimal.ZERO;
    private BigDecimal emergencyFund = BigDecimal.ZERO;
    private BigDecimal srs = BigDecimal.ZERO;
    private BigDecimal ssb = BigDecimal.ZERO;
    private BigDecimal insurances = BigDecimal.ZERO;
    private BigDecimal billsAndUtilities = BigDecimal.ZERO;
    private BigDecimal tax = BigDecimal.ZERO;
    private BigDecimal transport = BigDecimal.ZERO;
    private BigDecimal food = BigDecimal.ZERO;
    private BigDecimal groceries = BigDecimal.ZERO;
    private BigDecimal wants = BigDecimal.ZERO;
    private BigDecimal mortgage = BigDecimal.ZERO;
    private BigDecimal debt = BigDecimal.ZERO;
    private BigDecimal parentsAllowance = BigDecimal.ZERO;
    private BigDecimal haircut = BigDecimal.ZERO;
    private BigDecimal medical = BigDecimal.ZERO;
    private BigDecimal tithes = BigDecimal.ZERO;
    private BigDecimal overspent = BigDecimal.ZERO;
    private BigDecimal savings = BigDecimal.ZERO;
}