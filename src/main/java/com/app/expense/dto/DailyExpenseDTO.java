package com.app.expense.dto;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyExpenseDTO {
    //Unique identifier for each record
    private LocalDate date;

    //Income
    private BigDecimal income = BigDecimal.ZERO;

    //Savings
    private BigDecimal emergencyFund = BigDecimal.ZERO;
    private BigDecimal srs = BigDecimal.ZERO;
    private BigDecimal ssb = BigDecimal.ZERO;

    //Insurance
    private BigDecimal aiaPrimeLife = BigDecimal.ZERO;
    private BigDecimal hsbcCriticare = BigDecimal.ZERO;
    private BigDecimal hsbcTermProtector = BigDecimal.ZERO;

    //Bills & utilities
    private BigDecimal mobilePhone = BigDecimal.ZERO;
    private BigDecimal internet = BigDecimal.ZERO;
    private BigDecimal electricity = BigDecimal.ZERO;

    //Tax
    private BigDecimal iras = BigDecimal.ZERO;
    private BigDecimal propertyTax = BigDecimal.ZERO;

    //Transport
    private BigDecimal publicTransport = BigDecimal.ZERO;
    private BigDecimal privateTransport = BigDecimal.ZERO;

    //Food
    private BigDecimal breakfast = BigDecimal.ZERO;
    private BigDecimal lunch = BigDecimal.ZERO;
    private BigDecimal dinner = BigDecimal.ZERO;

    //Groceries
    private BigDecimal groceries = BigDecimal.ZERO;

    //Wants
    private BigDecimal eatingOut = BigDecimal.ZERO;
    private BigDecimal recreational = BigDecimal.ZERO;
    private BigDecimal holiday = BigDecimal.ZERO;
    private BigDecimal shopping = BigDecimal.ZERO;
    private BigDecimal sports = BigDecimal.ZERO;
    private BigDecimal tech = BigDecimal.ZERO;

    //Others
    private BigDecimal mortgage = BigDecimal.ZERO;
    private BigDecimal debt = BigDecimal.ZERO;
    private BigDecimal parentsAllowance = BigDecimal.ZERO;
    private BigDecimal haircut = BigDecimal.ZERO;
    private BigDecimal medical = BigDecimal.ZERO;
    private BigDecimal tithes = BigDecimal.ZERO;
}