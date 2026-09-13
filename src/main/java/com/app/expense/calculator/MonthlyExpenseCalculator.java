package com.app.expense.calculator;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Component;

import com.app.expense.dto.MonthlyExpenseDTO;
import com.app.expense.entity.DailyExpense;
import com.app.expense.repository.DailyExpenseRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyExpenseCalculator {
    private final DailyExpenseRepository repository;

    public void calculateMonthlyExpenses
    (MonthlyExpenseDTO medto) {
        YearMonth month = medto.getMonth();

        //Fetch all daily transactions for the specified month target
        List<DailyExpense> deList = repository
        .findByDateBetween(month
        .atDay(1), 
        month
        .atEndOfMonth()
        );

        //Pull unique core allocations directly without multiplying inside daily iterations
        BigDecimal income = deList
        .stream()
        .map(DailyExpense::getIncome)
        .filter(v -> v != null && v.compareTo(BigDecimal.ZERO) > 0)
        .findFirst()
        .orElse(BigDecimal.ZERO);

        BigDecimal emergencyFund = deList
        .stream()
        .map(DailyExpense::getEmergencyFund)
        .filter(v -> v != null && v.compareTo(BigDecimal.ZERO) > 0)
        .findFirst()
        .orElse(BigDecimal.ZERO);

        BigDecimal srs = deList
        .stream()
        .map(DailyExpense::getSrs)
        .filter(v -> v != null && v.compareTo(BigDecimal.ZERO) > 0)
        .findFirst()
        .orElse(BigDecimal.ZERO);

        BigDecimal ssb = deList
        .stream()
        .map(DailyExpense::getSsb)
        .filter(v -> v != null && v.compareTo(BigDecimal.ZERO) > 0)
        .findFirst()
        .orElse(BigDecimal.ZERO);

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

        //Loop exclusively through standard daily operational cash outflows
        for (DailyExpense de : deList) {
            insurances = insurances
            .add(de.getAiaPrimeLife())
            .add(de.getHsbcCriticare())
            .add(de.getHsbcTermProtector());

            billsAndUtilities = billsAndUtilities
            .add(de.getMobilePhone())
            .add(de.getElectricity())
            .add(de.getInternet());

            tax = tax
            .add(de.getPropertyTax())
            .add(de.getIras());

            transport = transport
            .add(de.getPublicTransport())
            .add(de.getPrivateTransport());

            food = food
            .add(de.getBreakfast())
            .add(de.getLunch())
            .add(de.getDinner());

            groceries = groceries
            .add(de.getGroceries());
            
            wants = wants
            .add(de.getEatingOut())
            .add(de.getRecreational())
            .add(de.getSports())
            .add(de.getTech())
            .add(de.getHoliday())
            .add(de.getShopping());

            mortgage = mortgage
            .add(de.getMortgage());

            debt = debt
            .add(de.getDebt());

            parentsAllowance = parentsAllowance
            .add(de.getParentsAllowance());

            haircut = haircut
            .add(de.getHaircut());

            medical = medical
            .add(de.getMedical());

            tithes = tithes
            .add(de.getTithes());
        }

        //Populate your metrics cleanly
        medto.setIncome(income);
        medto.setEmergencyFund(emergencyFund);
        medto.setSsb(ssb);
        medto.setSrs(srs);
        medto.setInsurances(insurances);
        medto.setBillsAndUtilities(billsAndUtilities);
        medto.setTax(tax);
        medto.setTransport(transport);
        medto.setFood(food);
        medto.setGroceries(groceries);
        medto.setWants(wants);
        medto.setMortgage(mortgage);
        medto.setDebt(debt);
        medto.setParentsAllowance(parentsAllowance);
        medto.setHaircut(haircut);
        medto.setMedical(medical);
        medto.setTithes(tithes);

        // Run updated calculations
        medto.setSavings(calculateSavings(medto));
        medto.setOverspent(calculateOverspent(medto));
    }

    public BigDecimal calculateSavings(MonthlyExpenseDTO medto) {
        //Group all outflows accurately matching your old file's layout structures
        BigDecimal totalExpenses = medto
        .getEmergencyFund()
        .add(medto.getSsb())
        .add(medto.getSrs())
        .add(medto.getInsurances())
        .add(medto.getBillsAndUtilities())
        .add(medto.getTax())
        .add(medto.getTransport())
        .add(medto.getFood())
        .add(medto.getGroceries())
        .add(medto.getWants())
        .add(medto.getMortgage())
        .add(medto.getDebt())
        .add(medto.getParentsAllowance())
        .add(medto.getHaircut())
        .add(medto.getMedical())
        .add(medto.getTithes());

        return medto
        .getIncome()
        .subtract(totalExpenses);
    }

    public BigDecimal calculateOverspent(MonthlyExpenseDTO medto) {
        BigDecimal overspent = BigDecimal.ZERO;
        
        if (medto.getTransport()
        .compareTo(BigDecimal.valueOf(122.0)) > 0) {
            overspent = overspent
            .add(medto.getTransport()
            .subtract(BigDecimal.valueOf(122.0)));
        }

        if (medto.getFood()
        .compareTo(BigDecimal.valueOf(500.0)) > 0) {
            overspent = overspent
            .add(medto.getFood()
            .subtract(BigDecimal.valueOf(500.0)));
        }

        if (medto.getGroceries()
        .compareTo(BigDecimal.valueOf(100.0)) > 0) {
            overspent = overspent
            .add(medto.getGroceries()
            .subtract(BigDecimal.valueOf(100.0)));
        }

        if (medto.getHaircut()
        .compareTo(BigDecimal.valueOf(15.0)) > 0) {
            overspent = overspent
            .add(medto.getHaircut()
            .subtract(BigDecimal.valueOf(15.0)));
        }

        if (medto.getMedical()
        .compareTo(BigDecimal.valueOf(50.0)) > 0) {
            overspent = overspent
            .add(medto.getMedical()
            .subtract(BigDecimal.valueOf(50.0)));
        }
        
        if (medto.getWants()
        .compareTo(BigDecimal.valueOf(600.0)) > 0) {
            overspent = overspent
            .add(medto.getWants()
            .subtract(BigDecimal.valueOf(600.0)));
        }

        return overspent;
    }
}