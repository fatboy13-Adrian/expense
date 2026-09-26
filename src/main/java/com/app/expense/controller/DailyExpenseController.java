package com.app.expense.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.expense.dto.DailyExpenseDTO;
import com.app.expense.service.daily_expense.DailyExpenseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Expenses", description = "Endpoints for managing daily, monthly, and yearly expenses")
public class DailyExpenseController {
    @Autowired	
    private DailyExpenseService svc;

    @PostMapping("/create")
    @Operation(summary = "Add a new expense record")
    public ResponseEntity <DailyExpenseDTO> create (@RequestBody DailyExpenseDTO dto) {
        return ResponseEntity.ok(svc.create(dto));
    }

    @GetMapping("/date/{date}")
    @Operation(summary = "Retrieve expense record by date")
    public ResponseEntity <DailyExpenseDTO> retrieveByDate (@PathVariable @DateTimeFormat
    (iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(svc
        .retrieveByDate(date));
    }

    @GetMapping("/dates")
    @Operation(summary = "Retrieve a paginated list of daily expense records")
    public ResponseEntity <Page <DailyExpenseDTO>> retrieveAllDates 
    (@RequestParam(defaultValue = "1") int page, 
    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(svc
        .retrieveAllDates(page, size));
    }

    @PutMapping("/date/{date}")
    @Operation(summary = "Update an existing expense record by date")
    public ResponseEntity <DailyExpenseDTO> update (@PathVariable 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, 
    @RequestBody DailyExpenseDTO dto) {
        return ResponseEntity.ok(svc
        .update(date, dto));
    }

    @DeleteMapping("/date/{date}")
    @Operation(summary = "Delete expense record by date")
    public ResponseEntity <Void> delete (@PathVariable 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        svc.delete(date);
        return ResponseEntity.noContent().build();
    }
}