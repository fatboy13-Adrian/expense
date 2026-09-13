package com.app.expense.controller;

import java.io.ByteArrayInputStream;
import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.expense.report.MonthlyExpenseReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Expenses", description = "Expense Tracker")
public class MonthlyExpenseReportController {
    @Autowired
    private MonthlyExpenseReportService svc;

    @GetMapping("/export/{year}")
    @Operation(summary = "Export monthly expense records to Excel")
    public ResponseEntity<InputStreamResource>
    exportMonthlyExpenseReport(@PathVariable Year year) {
        /**Generate Excel file from report service
         *& wrap it as an input stream resource*/
        ByteArrayInputStream file = svc.exportMonthlyExpenseRecords(year);

        //Set download filename
        HttpHeaders headers = new HttpHeaders();

        headers.add(
        HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"Expense Records For Year "
        + year
        + ".xlsx\"");

        /**Return Excel file as downloadable response*/
        return ResponseEntity.ok()
        .headers(headers)
        .contentType(
        MediaType.parseMediaType(
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .body(new InputStreamResource(file));
    }
}