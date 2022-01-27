package pl.wizard.software.email;

import java.io.File;

public class EmailWithAttachedPdfSender extends AbstractEmailWithAttachedFileSender {

    public EmailWithAttachedPdfSender() {
        super();
    }

    @Override
    public File saveToFile(ShoppingListFileData fileData) {
        return null;
    }
}
