package pl.wizard.software.email;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class EmailWithAttachedPdfSender extends AbstractEmailWithAttachedFileSender {

    public EmailWithAttachedPdfSender() {
        super();
    }

    @Override
    public File saveToFile(ShoppingListFileData fileData) throws FileNotFoundException {
        File file = new File("TestFile.pdf");
        FileOutputStream outputStream = new FileOutputStream(file);
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Hello World", font);
            document.add(chunk);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return file;
    }
}
