package com.app.expense.report;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.app.expense.dto.YearlyExpenseDTO;
import com.app.expense.exception.ExportExcelFailedException;
import com.app.expense.service.yearly_expense.YearlyExpenseService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class YearlyExpenseReportSvc {
    private final YearlyExpenseService svc;

    public ByteArrayInputStream exportYearlyExpenseRecords () {
        //Retrieve all yearly expense records
        List<YearlyExpenseDTO> yedtos = svc.retrieveAllYears();

        //Build & return Excel file
        return buildYearlyExpenseExcel(yedtos);
    }

    private ByteArrayInputStream buildYearlyExpenseExcel
    (List <YearlyExpenseDTO> yedtos) {
        try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                //Create worksheet
                Sheet sheet = workbook.createSheet("Yearly Expense Records");

                //Create header style
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);
                headerStyle.setAlignment(HorizontalAlignment.CENTER);

                //Create currency style
                CellStyle currencyStyle = workbook.createCellStyle();

                currencyStyle.setDataFormat(workbook
                .createDataFormat()
                .getFormat("[$$-en-SG]#,##0.00"));

                currencyStyle.setAlignment(HorizontalAlignment.CENTER);

                //Define Excel column headers
                String[] columns = {
                        "Year",
                        "Income",
                        "Cpf",
                        "Cdac", 
                        "Emergency Fund",
                        "SRS",
                        "SSB",
                        "Bills & Utilities",
                        "Debt",
                        "Food",
                        "Groceries",
                        "Haircut",
                        "Insurances",
                        "Medical",
                        "Mortgage",
                        "Parent's Allowance",
                        "Tax",
                        "Tithes",
                        "Transport",
                        "Wants",
                        "Overspent",
                        "Yearly Savings"
                };

                //Create header row
                Row header = sheet.createRow(0);
                for (int i = 0; i < columns.length; i++) {
                        Cell cell = header.createCell(i);
                        cell.setCellValue(columns[i]);
                        cell.setCellStyle(headerStyle);
                }

                //Create data rows
                for (int i = 0; i < yedtos.size(); i++) {
                        //Retrieve current yearly expense DTO
                        YearlyExpenseDTO yeDto = yedtos.get(i);

                        //Create row
                        Row row = sheet.createRow(i + 1);

                        //Set year
                        row.createCell(0)
                        .setCellValue(yeDto.getYear());

                        //Store financial values
                        BigDecimal[] values = {
                                yeDto.getIncome(),
                                yeDto.getCpf(),
                                yeDto.getCdac(),
                                yeDto.getEmergencyFund(),
                                yeDto.getSrs(),
                                yeDto.getSsb(),
                                yeDto.getBillsAndUtilities(),
                                yeDto.getDebt(),
                                yeDto.getFood(),
                                yeDto.getGroceries(),
                                yeDto.getHaircut(),
                                yeDto.getInsurances(),
                                yeDto.getMedical(),
                                yeDto.getMortgage(),
                                yeDto.getParentsAllowance(),
                                yeDto.getTax(),
                                yeDto.getTithes(),
                                yeDto.getTransport(),
                                yeDto.getWants(),
                                yeDto.getOverspent(),
                                yeDto.getSavings()
                        };

                        //Create financial cells
                        for (int j = 0; j < values.length; j++) {
                                Cell cell = row.createCell(j + 1);

                                //Prevent null values
                                BigDecimal value =
                                values[j] != null
                                ? values[j]
                                : BigDecimal.ZERO;

                                cell.setCellValue(value.doubleValue());
                                cell.setCellStyle(currencyStyle);
                        }
                }

                //Automatically resize columns
                for (int i = 0; i < columns.length; i++) {
                        sheet.autoSizeColumn(i);
                }

                //Write workbook into output stream
                workbook.write(output);

                //Return Excel file
                return new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
                //Handle Excel export failure
                throw new ExportExcelFailedException
                ("Failed to export yearly expense records", e);
        }
    }
}