package com.app.expense.controller.monthly_expense;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.app.expense.dto.MonthlyExpenseDTO;
import com.app.expense.service.monthly_expense.MonthlyExpenseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Expenses", description = "Endpoints for managing daily, monthly, and yearly expenses")
public class MonthlyExpenseController {
    @Autowired	
    private MonthlyExpenseService svc;

    @GetMapping("/month/{month}")
    @Operation(summary = "Retrieve expense record by month")
    public ResponseEntity<MonthlyExpenseDTO> retrieveByMonth 
    (@PathVariable @DateTimeFormat
    (pattern = "yyyy-MM") YearMonth month) {
        return ResponseEntity
        .ok(svc.retrieveByMonth(month));
    }

    @GetMapping("/months")
    @Operation(summary = "Retrieve a paginated list of monthly expense summaries")
    public ResponseEntity<Page<MonthlyExpenseDTO>> retrieveAllMonths
    (@RequestParam(defaultValue = "1") int page, 
    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
        .ok(svc
        .retrieveAllMonths(page, size));
    }
}