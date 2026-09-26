package com.app.expense.yearly_expense;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.expense.dto.YearlyExpenseDTO;
import com.app.expense.exception.ExportExcelFailedException;
import com.app.expense.report.YearlyExpenseReportSvc;
import com.app.expense.service.yearly_expense.YearlyExpenseService;

@ExtendWith(MockitoExtension.class)
class YearlyExpenseReportServiceTest {
        @InjectMocks
        private YearlyExpenseReportSvc reportSvc;

        @Mock
        private YearlyExpenseService svc;

        @Test
        void testExportYearlyExpenseRecords () throws Exception {
                //Create yearly expense DTO
                YearlyExpenseDTO dto = createYearlyExpenseDTO();

                //Mock service
                when(svc.retrieveAllYears())
                .thenReturn(List.of(dto));

                //Call the method
                ByteArrayInputStream result =
                reportSvc.exportYearlyExpenseRecords();

                //Verify result exists
                assertNotNull(result);

                //Verify service was called once
                verify(svc, times(1))
                .retrieveAllYears();

                //Open the generated Excel workbook
                try (Workbook workbook = new XSSFWorkbook(result)) {
                        //Verify sheet exists
                        assertEquals("Yearly Expense Records",
                        workbook.getSheetAt(0).getSheetName());

                        Sheet sheet = workbook.getSheetAt(0);

                        //Header + 1 data row
                        assertEquals(2, sheet.getPhysicalNumberOfRows());

                        //Verify headers & dta
                        Row row = sheet.getRow(1);
                        assertEquals(2026,
                        row.getCell(0).getNumericCellValue());
                        assertEquals(5000.00,
                        row.getCell(1).getNumericCellValue());
                        assertEquals(500.00,
                        row.getCell(2).getNumericCellValue());
                        assertEquals(100.00,
                        row.getCell(3).getNumericCellValue());
                        assertEquals(300.00,
                        row.getCell(4).getNumericCellValue());
                        assertEquals(200.00,
                        row.getCell(5).getNumericCellValue());
                        assertEquals(1000.00,
                        row.getCell(6).getNumericCellValue());
                        assertEquals(1900.00,
                        row.getCell(21).getNumericCellValue());
                }
        }

        @Test
        void testExportYearlyExpenseRecordsEmpty () throws Exception {
                //Mock service to return no records
                when(svc.retrieveAllYears())
                .thenReturn(List.of());

                //Call method
                ByteArrayInputStream result =
                reportSvc.exportYearlyExpenseRecords();

                //Verify result exists
                assertNotNull(result);

                //Verify service called once
                verify(svc, times(1))
                .retrieveAllYears();

                //Open workbook
                try (Workbook workbook = new XSSFWorkbook(result)) {
                        Sheet sheet = workbook.getSheetAt(0);

                        //Header row should still exist
                        assertEquals(1, sheet.getPhysicalNumberOfRows());

                        //Verify sheet name & heders
                        assertEquals("Yearly Expense Records",
                        sheet.getSheetName());
                }
        }

        @Test
        void testExportYearlyExpenseRecordsMultipleYears () throws Exception {

                //Create first year
                YearlyExpenseDTO dto2025 =
                createYearlyExpenseDTO();
                dto2025.setYear(2025);

                //Create second year
                YearlyExpenseDTO dto2026 =
                createYearlyExpenseDTO();
                dto2026.setYear(2026);

                //Mock service
                when(svc.retrieveAllYears())
                .thenReturn(List.of(dto2025,dto2026));

                //Call method
                ByteArrayInputStream result =
                reportSvc.exportYearlyExpenseRecords();

                //Open workbook
                try (Workbook workbook = new XSSFWorkbook(result)) {
                        Sheet sheet = workbook.getSheetAt(0);

                        //Header + 2 years
                        assertEquals(3,sheet.getPhysicalNumberOfRows());

                        //First year
                        assertEquals(2025, sheet.getRow(1)
                        .getCell(0).getNumericCellValue());

                        //Second year
                        assertEquals(2026, sheet.getRow(2)
                        .getCell(0).getNumericCellValue());
                }

                //Verify service called once
                verify(svc, times(1))
                .retrieveAllYears();
        }

        @Test
        void testExportYearlyExpenseRecordsNullValues () throws Exception {
                YearlyExpenseDTO dto = new YearlyExpenseDTO();
                dto.setYear(2026);
                when(svc.retrieveAllYears()).thenReturn(List.of(dto));

                //Call method
                ByteArrayInputStream result = 
                reportSvc.exportYearlyExpenseRecords();

                assertNotNull(result);

                //Open workbook
                try (Workbook workbook = new XSSFWorkbook(result)) {
                        Sheet sheet = workbook.getSheetAt(0);
                        Row row = sheet.getRow(1);

                        //Year should still be present
                        assertEquals(2026,
                        row.getCell(0).getNumericCellValue());

                        //All financial values should become 0
                        //because service converts null to BigDecimal.ZERO
                        for (int i = 1; i <= 21; i++) {
                                assertEquals(0.00,
                                row.getCell(i).getNumericCellValue(),
                                0.001);
                        }
                }
        }

        @Test
        void testExportYearlyExpenseRecordsFailure () {
                when(svc.retrieveAllYears())
                .thenReturn(null);
                ExportExcelFailedException exception = assertThrows(
                ExportExcelFailedException.class,
                () -> reportSvc.exportYearlyExpenseRecords());
                assertNotNull(exception);
        }

        private YearlyExpenseDTO createYearlyExpenseDTO () {
                YearlyExpenseDTO dto =
                new YearlyExpenseDTO();
                dto.setYear(2026);
                dto.setIncome(BigDecimal.valueOf(5000.00));
                dto.setCpf(BigDecimal.valueOf(500.00));
                dto.setCdac(BigDecimal.valueOf(100.00));
                dto.setEmergencyFund(BigDecimal.valueOf(300.00));
                dto.setSrs(BigDecimal.valueOf(200.00));
                dto.setSsb(BigDecimal.valueOf(1000.00));
                dto.setBillsAndUtilities(BigDecimal.valueOf(200.00));
                dto.setDebt(BigDecimal.valueOf(300.00));
                dto.setFood(BigDecimal.valueOf(400.00));
                dto.setGroceries(BigDecimal.valueOf(100.00));
                dto.setHaircut(BigDecimal.valueOf(50.00));
                dto.setInsurances(BigDecimal.valueOf(150.00));
                dto.setMedical(BigDecimal.valueOf(100.00));
                dto.setMortgage(BigDecimal.valueOf(500.00));
                dto.setParentsAllowance(BigDecimal.valueOf(200.00));
                dto.setTax(BigDecimal.valueOf(100.00));
                dto.setTithes(BigDecimal.valueOf(50.00));
                dto.setTransport(BigDecimal.valueOf(100.00));
                dto.setWants(BigDecimal.valueOf(200.00));
                dto.setOverspent(BigDecimal.valueOf(50.00));
                dto.setSavings(BigDecimal.valueOf(1900.00));
                return dto;
        }
}