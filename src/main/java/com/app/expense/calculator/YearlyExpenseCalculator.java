package com.app.expense.calculator;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.app.expense.dto.MonthlyExpenseDTO;
import com.app.expense.dto.YearlyExpenseDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class YearlyExpenseCalculator {
    public void calculateYearlyExpenses (YearlyExpenseDTO yedto, List <MonthlyExpenseDTO> me) {
        //Intialize all variables to 0.0
        BigDecimal income = BigDecimal.ZERO;
        BigDecimal cpf = BigDecimal.ZERO;
        BigDecimal cdac = BigDecimal.ZERO;
        BigDecimal emergencyFund = BigDecimal.ZERO;
        BigDecimal srs = BigDecimal.ZERO;
        BigDecimal ssb = BigDecimal.ZERO;
        BigDecimal insurances = BigDecimal.ZERO;
        BigDecimal billsAndUtilities = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal transport = BigDecimal.ZERO;
        BigDecimal food = BigDecimal.ZERO;
        BigDecimal groceries = BigDecimal.ZERO;
        BigDecimal wants = BigDecimal.ZERO;
        BigDecimal mortgage = BigDecimal.ZERO;
        BigDecimal debt = BigDecimal.ZERO;
        BigDecimal parentsAllowance = BigDecimal.ZERO;
        BigDecimal haircut = BigDecimal.ZERO;
        BigDecimal medical = BigDecimal.ZERO;
        BigDecimal tithes = BigDecimal.ZERO;
        BigDecimal overspent = BigDecimal.ZERO;
        BigDecimal savings = BigDecimal.ZERO;

        //Sum up daily expenses for current year
        for (MonthlyExpenseDTO medto : me) {
            income = income
            .add(medto.getIncome());
            emergencyFund = emergencyFund
            .add(medto.getEmergencyFund());
            srs = srs
            .add(medto.getSrs());
            ssb = ssb
            .add(medto.getSsb());
            savings = savings
            .add(medto.getSavings());
            insurances = insurances
            .add(medto.getInsurances());
            billsAndUtilities = billsAndUtilities
            .add(medto.getBillsAndUtilities());
            tax = tax
            .add(medto.getTax());
            transport = transport
            .add(medto.getTransport());
            food = food
            .add(medto.getFood());
            groceries = groceries
            .add(medto.getGroceries());
            wants = wants
            .add(medto.getWants());
            mortgage = mortgage
            .add(medto.getMortgage());
            debt = debt
            .add(medto.getDebt());
            parentsAllowance = parentsAllowance
            .add(medto.getParentsAllowance());
            haircut = haircut
            .add(medto.getHaircut());
            medical = medical
            .add(medto.getMedical());
            tithes = tithes
            .add(medto.getTithes());
            overspent = overspent
            .add(medto.getOverspent());
        }

        //Store calculated values into yearly expenses
        yedto.setIncome(income);
        yedto.setCpf(cpf);
        yedto.setCdac(cdac);
        yedto.setEmergencyFund(emergencyFund);
        yedto.setSrs(srs);
        yedto.setSsb(ssb);
        yedto.setInsurances(insurances);
        yedto.setBillsAndUtilities(billsAndUtilities);
        yedto.setTax(tax);
        yedto.setTransport(transport);
        yedto.setFood(food);
        yedto.setGroceries(groceries);
        yedto.setWants(wants);
        yedto.setMortgage(mortgage);
        yedto.setDebt(debt);
        yedto.setParentsAllowance(parentsAllowance);
        yedto.setHaircut(haircut);
        yedto.setMedical(medical);
        yedto.setTithes(tithes);
        yedto.setOverspent(overspent);
        yedto.setSavings(savings);
    }
}