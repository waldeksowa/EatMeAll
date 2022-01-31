package pl.wizard.software.email;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static pl.wizard.software.email.ShoppingListDocumentSettings.*;

public class ShoppingListAsExcelSender extends AbstractShoppingListSender {

    @Override
    public File saveToFile(ShoppingListFileData fileData) throws FileNotFoundException {
        LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(fileData.getShoppingListDate()));
        String fileName = FILE_NAME_PREFIX + localDate.toString() + ".xlsx";
        File file = new File(fileName);
        FileOutputStream outputStream = new FileOutputStream(file);

        try {
            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("Shopping list");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);

            addTableHeader(workbook, sheet);
            addTableRows(fileData, workbook, sheet);
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    private void addTableHeader(Workbook workbook, Sheet sheet) {
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue(FIRST_COLUMN_HEADER);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue(SECOND_COLUMN_HEADER);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue(THIRD_COLUMN_HEADER);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue(FOURTH_COLUMN_HEADER);
        headerCell.setCellStyle(headerStyle);
    }

    private void addTableRows(ShoppingListFileData fileData, Workbook workbook, Sheet sheet) {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int i = 0; i < fileData.getRows().size(); i++) {
            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(fileData.getRows().get(i).getProductName());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(fileData.getRows().get(i).getAmount());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(fileData.getRows().get(i).getSpecialAmount());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(fileData.getRows().get(i).getBought());
            cell.setCellStyle(style);
        }
    }
}
