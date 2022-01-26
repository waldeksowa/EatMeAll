package pl.wizard.software.email;

import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.mapper.ShoppingListFileDataMapper;

import java.io.File;

public abstract class EmailWithAttachedFileSender {

    public void send(ShoppingListDto shoppingList) {
        ShoppingListFileData fileData = parse(shoppingList);
        File file = saveToFile(fileData);
        sendEmailWithAttachedFile(file);
    }

    ShoppingListFileData parse(ShoppingListDto shoppingList) {
        return ShoppingListFileDataMapper.mapToFileData(shoppingList);
    }

    public abstract File saveToFile(ShoppingListFileData fileData);

    void sendEmailWithAttachedFile(File file) {
        
    }
}
