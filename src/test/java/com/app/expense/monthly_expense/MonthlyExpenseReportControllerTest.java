package com.app.expense.monthly_expense;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Year;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.app.expense.controller.monthly_expense.MonthlyExpenseReportController;
import com.app.expense.report.MonthlyExpenseReportSvc;

@ExtendWith(MockitoExtension.class)
public class MonthlyExpenseReportControllerTest {
    //Mock report service
    @Mock
    private MonthlyExpenseReportSvc svc;

    //Controller under test
    @InjectMocks
    private MonthlyExpenseReportController controller;

    @Test
    void testExportMonthlyExpenseReport () throws IOException {
        //Test year
        Year year = Year.of(2026);

        //Fake Excel file content
        byte[] fileContent = "Test Excel File".getBytes();

        ByteArrayInputStream file =
        new ByteArrayInputStream(fileContent);

        //Mock report service
        when(svc.exportMonthlyExpenseRecords(year))
        .thenReturn(file);

        //Call controller
        ResponseEntity<InputStreamResource> response =
        controller.exportMonthlyExpenseReport(year);

        assertEquals(200,
        response.getStatusCode().value());

        InputStreamResource resource =
        response.getBody();

        assertNotNull(resource);
        assertEquals(
        MediaType.parseMediaType(
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
        response.getHeaders().getContentType());

        String contentDisposition =
        response.getHeaders()
        .getFirst(HttpHeaders.CONTENT_DISPOSITION);

        assertNotNull(contentDisposition);

        assertEquals(
        "attachment; filename=\"Expense Records For Year 2026.xlsx\"",
        contentDisposition);

        byte[] returnedContent =
        resource.getInputStream().readAllBytes();
        assertArrayEquals(fileContent, returnedContent);
        verify(svc).exportMonthlyExpenseRecords(year);
    }

    @Test
    void testExportMonthlyExpenseReportEmptyFile () throws IOException {
        //Test year
        Year year = Year.of(2026);

        //Empty file
        byte[] fileContent = new byte[0];

        ByteArrayInputStream file =
        new ByteArrayInputStream(fileContent);

        //Mock service
        when(svc.exportMonthlyExpenseRecords(year))
        .thenReturn(file);

        //Call controller
        ResponseEntity<InputStreamResource> response =
        controller.exportMonthlyExpenseReport(year);

        //Check status
        assertEquals(200,
        response.getStatusCode().value());

        //Check body
        InputStreamResource resource =
        response.getBody();

        assertNotNull(resource);

        //Check content type
        assertEquals(
        MediaType.parseMediaType(
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
        response.getHeaders().getContentType());

        //Check filename
        assertEquals(
        "attachment; filename=\"Expense Records For Year 2026.xlsx\"",
        response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));

        //Check empty content
        assertEquals(0,
        resource.getInputStream().readAllBytes().length);

        //Verify service
        verify(svc).exportMonthlyExpenseRecords(year);
    }

    @Test
    void testExportMonthlyExpenseReportDifferentYear () throws IOException {
        Year year = Year.of(2024);

        ByteArrayInputStream file =
        new ByteArrayInputStream(
        "Excel content".getBytes());

        when(svc.exportMonthlyExpenseRecords(year))
        .thenReturn(file);

        ResponseEntity<InputStreamResource> response =
        controller.exportMonthlyExpenseReport(year);

        assertEquals(
        "attachment; filename=\"Expense Records For Year 2024.xlsx\"",
        response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        verify(svc).exportMonthlyExpenseRecords(year);
    }
}