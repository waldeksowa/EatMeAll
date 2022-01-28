package pl.wizard.software.email;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.stream.Stream;

public class EmailWithAttachedPdfSender extends AbstractEmailWithAttachedFileSender {

    public static final String FILE_NAME_PREFIX = "Shopping list from ";
    public static final String FIRST_COLUMN_HEADER = "Product";
    public static final String SECOND_COLUMN_HEADER = "Amount";
    public static final String THIRD_COLUMN_HEADER = "Special Amount";
    public static final String FOURTH_COLUMN_HEADER = "Buyed";

    public EmailWithAttachedPdfSender() {
        super();
    }

    @Override
    public File saveToFile(ShoppingListFileData fileData) throws FileNotFoundException {
        LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(fileData.getShoppingListDate()));
        String fileName = FILE_NAME_PREFIX + localDate.toString() + ".pdf";
        File file = new File(fileName);
        FileOutputStream outputStream = new FileOutputStream(file);
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            PdfPTable table = new PdfPTable(4);
            addTableHeader(table);
            addTableRows(table, fileData);

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return file;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of(FIRST_COLUMN_HEADER, SECOND_COLUMN_HEADER, THIRD_COLUMN_HEADER, FOURTH_COLUMN_HEADER)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addTableRows(PdfPTable table, ShoppingListFileData fileData) {
        fileData.getRows()
                .forEach(row -> {
                    table.addCell(row.getProductName());
                    table.addCell(row.getAmount());
                    table.addCell(row.getSpecialAmount());
                    table.addCell(row.getBought());
                });
    }
}
