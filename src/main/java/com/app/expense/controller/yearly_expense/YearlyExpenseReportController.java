package com.app.expense.controller.yearly_expense;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.expense.report.YearlyExpenseReportSvc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Expenses", description = "Expense Tracker")
public class YearlyExpenseReportController {
        @Autowired
        private YearlyExpenseReportSvc svc;

        @GetMapping("/yearly/export")
        @Operation(summary = "Export yearly expense records to Excel")
        public ResponseEntity <InputStreamResource> exportYearlyExpenseRecords () {
                /**Generate Excel file from report service
                *& wrap it as an input stream resource*/
                ByteArrayInputStream file = svc.exportYearlyExpenseRecords();

                //Set download filename
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"Yearly Expense Records.xlsx\"");

                /**Return Excel file as downloadable response*/
                return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(file));
        }
}