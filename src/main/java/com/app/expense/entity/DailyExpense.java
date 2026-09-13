package com.app.expense.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyExpense {
    //Unique identifier for each record
    @Id
    @Column(nullable = false, unique = true)
    private LocalDate date;

    //Income
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal income = BigDecimal.ZERO;

    //Savings
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal emergencyFund = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal srs = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal ssb = BigDecimal.ZERO;

    //Insurance
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal aiaPrimeLife = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal hsbcCriticare = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal hsbcTermProtector = BigDecimal.ZERO;

    //Bills & utilities
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal mobilePhone = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal internet = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal electricity = BigDecimal.ZERO;

    //Tax
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal iras = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal propertyTax = BigDecimal.ZERO;

    //Transport
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal publicTransport = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal privateTransport = BigDecimal.ZERO;

    //Food
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal breakfast = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal lunch = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal dinner = BigDecimal.ZERO;

    //Groceries
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal groceries = BigDecimal.ZERO;

    //Wants
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal eatingOut = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal recreational = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal holiday = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal shopping = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal sports = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal tech = BigDecimal.ZERO;

    //Other fields
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal mortgage = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal debt = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal parentsAllowance = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal haircut = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal medical = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal tithes = BigDecimal.ZERO;
}