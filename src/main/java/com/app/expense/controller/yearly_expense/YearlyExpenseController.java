package com.app.expense.controller.yearly_expense;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.expense.dto.YearlyExpenseDTO;
import com.app.expense.service.yearly_expense.YearlyExpenseService;

import io.swagger.v3.oas.annotations.Operation;

public class YearlyExpenseController {
    @Autowired
    private YearlyExpenseService svc;

    @GetMapping("/year/{year}")
    @Operation(summary = "Retrieve expense record by year")
    public ResponseEntity <YearlyExpenseDTO> retrieveByYear (@PathVariable int year) {
        return ResponseEntity
        .ok(svc
        .retrieveByYear(year));
    }

    @GetMapping("/years")
    @Operation(summary = "Retrieve all historical yearly expense summaries")
    public ResponseEntity <List <YearlyExpenseDTO>> retrieveAllYears () {
        return ResponseEntity
        .ok(svc
        .retrieveAllYears());
    }
}