package com.app.expense.report;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
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

import com.app.expense.dto.MonthlyExpenseDTO;
import com.app.expense.exception.ExportExcelFailedException;
import com.app.expense.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyExpenseReportService {
    private final ExpenseService svc;

    public List<MonthlyExpenseDTO> getMonthRange(Year year) {
        //Initialize list to store monthly expense records
        List<MonthlyExpenseDTO> medto =
        new ArrayList<>();

        //Calculate expenses for each month from January to December
        for (int month = 1; month <= 12; month++) {
            medto.add(
            svc.retrieveByMonth(
            YearMonth.of(
            year.getValue(),
            month)));
        }

        //Return monthly expense records
        return medto;
    }

    private ByteArrayInputStream buildMonthlyExpenseExcel (
    List<MonthlyExpenseDTO> medto, String sheetName) {
        try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream output =
        new ByteArrayOutputStream()) {

            //Create worksheet
            Sheet sheet = workbook.createSheet(sheetName);

            //Create header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            headerStyle.setAlignment(
            HorizontalAlignment.CENTER);

            //Create currency style
            CellStyle currencyStyle =
            workbook.createCellStyle();

            currencyStyle.setDataFormat(
            workbook
            .createDataFormat()
            .getFormat("[$$-en-SG]#,##0.00"));

            currencyStyle.setAlignment(
            HorizontalAlignment.CENTER);

            //Define column headers
            String[] columns = {
                "Month",
                "Income",
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
                "Monthly Savings"
            };

            //Create header row
            Row header = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);

                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            //Create monthly data rows
            for (int i = 0; i < medto.size(); i++) {
                //Retrieve current monthly expense DTO
                MonthlyExpenseDTO meDto = medto.get(i);

                //Create new row
                Row row = sheet.createRow(i + 1);

                //Set month
                row.createCell(0)
                .setCellValue(meDto.getMonth().toString());

                //Store financial values
                BigDecimal[] values = {
                    meDto.getIncome(),
                    meDto.getEmergencyFund(),
                    meDto.getSrs(),
                    meDto.getSsb(),
                    meDto.getBillsAndUtilities(),
                    meDto.getDebt(),
                    meDto.getFood(),
                    meDto.getGroceries(),
                    meDto.getHaircut(),
                    meDto.getInsurances(),
                    meDto.getMedical(),
                    meDto.getMortgage(),
                    meDto.getParentsAllowance(),
                    meDto.getTax(),
                    meDto.getTithes(),
                    meDto.getTransport(),
                    meDto.getWants(),
                    meDto.getOverspent(),
                    meDto.getSavings()
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
            return new ByteArrayInputStream(
            output.toByteArray());
        } catch (Exception e) {
            //Handle Excel export failure
            throw new ExportExcelFailedException(
            "Failed to export monthly expense records", e);
        }
    }

    public ByteArrayInputStream exportMonthlyExpenseRecords (Year year) {
        //Retrieve monthly expense records
        List<MonthlyExpenseDTO> medto = getMonthRange(year);

        //Build & return Excel file
        return buildMonthlyExpenseExcel(
        medto, "Expense Records for Year " + year);
    }
}