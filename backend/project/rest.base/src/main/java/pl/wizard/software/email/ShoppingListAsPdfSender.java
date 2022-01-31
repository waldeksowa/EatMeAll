package pl.wizard.software.email;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.stream.Stream;

import static pl.wizard.software.email.ShoppingListDocumentSettings.*;

@Component
public class ShoppingListAsPdfSender extends AbstractShoppingListSender {

    @Autowired
    public ShoppingListAsPdfSender(EmailSenderService emailSenderService) {
        super(emailSenderService);
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

            PdfPTable table = new PdfPTable(NUMBER_OF_COLUMNS);
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
