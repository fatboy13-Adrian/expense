package com.app.expense.dto;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.YearMonth;
import lombok.Builder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseOutputDTO {
    //Unique identifier for each record
    private YearMonth month;

    @Builder.Default
    private BigDecimal income = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal savings = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal insurance = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal billsAndUtilities = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal tax = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal transport = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal food = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal groceries = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal wants = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal mortgage = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal debt = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal parentsAllowance = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal haircut = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal medical = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal tithes = BigDecimal.ZERO;
}