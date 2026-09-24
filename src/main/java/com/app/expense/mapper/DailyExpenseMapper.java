package com.app.expense.mapper;
import org.springframework.stereotype.Component;

import com.app.expense.dto.DailyExpenseDTO;
import com.app.expense.entity.DailyExpense;

@Component
public class DailyExpenseMapper {
    public DailyExpense toEntity (DailyExpenseDTO dto) {
        return DailyExpense.builder()
        .date(dto.getDate())
        .income(dto.getIncome())
        .cpf(dto.getCpf())
        .cdac(dto.getCdac())
        .emergencyFund(dto.getEmergencyFund())
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

    public DailyExpenseDTO toDto (DailyExpense e) {
        return new DailyExpenseDTO(
        e.getDate(),
        e.getIncome(),
        e.getCpf(),
        e.getCdac(),
        e.getEmergencyFund(),
        e.getSrs(),
        e.getSsb(),
        e.getAiaPrimeLife(),
        e.getHsbcCriticare(),
        e.getHsbcTermProtector(),
        e.getMobilePhone(),
        e.getInternet(),
        e.getElectricity(),
        e.getIras(),
        e.getPropertyTax(),
        e.getPublicTransport(),
        e.getPrivateTransport(),
        e.getBreakfast(),
        e.getLunch(),
        e.getDinner(),
        e.getGroceries(),
        e.getEatingOut(),
        e.getRecreational(),
        e.getHoliday(),
        e.getShopping(),
        e.getSports(),
        e.getTech(),
        e.getMortgage(),
        e.getDebt(),
        e.getParentsAllowance(),
        e.getHaircut(),
        e.getMedical(),
        e.getTithes()
        );
    }
}