package pl.wizard.software.email;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class EmailWithAttachedExcelSender extends AbstractEmailWithAttachedFileSender {

    public static final String FILE_NAME_PREFIX = "Shopping list from ";

    @Override
    public File saveToFile(ShoppingListFileData fileData) throws FileNotFoundException {
        LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(fileData.getShoppingListDate()));
        String fileName = FILE_NAME_PREFIX + localDate.toString() + ".pdf";

        

        return null;
    }
}
