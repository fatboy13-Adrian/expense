package com.app.expense.yearly_expense;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.app.expense.controller.yearly_expense.YearlyExpenseReportController;
import com.app.expense.report.YearlyExpenseReportSvc;

@ExtendWith(MockitoExtension.class)
public class YearlyExpenseReportControllerTest {
    //Mock report service
    @Mock
    private YearlyExpenseReportSvc svc;
    
    //Controller under test
    @InjectMocks
    private YearlyExpenseReportController controller;

    @Test 
    void testExportYearlyExpenseReport () throws Exception {
        //Fake excel content
        byte[] fileContent = 
        "Test excel file".getBytes();
        ByteArrayInputStream file =
        new ByteArrayInputStream(fileContent);

        //Mock report service
        when(svc.exportYearlyExpenseRecords())
        .thenReturn(file);

        //Call controller
        ResponseEntity <InputStreamResource> response =
        controller.exportYearlyExpenseRecords();

        //Check HTTP status
        assertEquals(200, 
        response.getStatusCode().value());

        //Check response body
        InputStreamResource resource =
        response.getBody();
        assertNotNull(resource);

        //Check content type
        assertEquals(MediaType.
        parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), 
        response.getHeaders().getContentType());

        //Check content disposition
        String contentDisposition =
        response.getHeaders()
        .getFirst(HttpHeaders.CONTENT_DISPOSITION);
        assertNotNull(contentDisposition);
        assertEquals("attachment; filename=\"Yearly Expense Records.xlsx\"",
        contentDisposition);

        //Check file content
        byte[] returnedContent =
        resource.getInputStream()
        .readAllBytes();
        assertArrayEquals(fileContent,
        returnedContent);

        //Verify service call
        verify(svc).exportYearlyExpenseRecords();
    }

    @Test
    void testExportYearlyExpenseRecordsEmptyFile () throws IOException {
        //Empty file
        ByteArrayInputStream file =
        new ByteArrayInputStream(new byte[0]);

        //Mock report service
        when(svc.exportYearlyExpenseRecords())
        .thenReturn(file);

        //Call controller
        ResponseEntity<InputStreamResource> response =
        controller.exportYearlyExpenseRecords();

        //Check HTTP status
        assertEquals(200,
        response.getStatusCode().value());

        //Check response body
        InputStreamResource resource =
        response.getBody();
        assertNotNull(resource);

        //Check content type
        assertEquals(MediaType.parseMediaType(
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
        response.getHeaders().getContentType());

        //Check filename
        assertEquals("attachment; filename=\"Yearly Expense Records.xlsx\"",
        response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));

        //Check file is empty
        assertEquals(0, resource.getInputStream()
        .readAllBytes().length);

        //Verify service call
        verify(svc).exportYearlyExpenseRecords();
    }
}