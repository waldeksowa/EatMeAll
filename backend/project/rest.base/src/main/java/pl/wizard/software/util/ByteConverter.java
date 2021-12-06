package pl.wizard.software.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

@Slf4j
public class ByteConverter<T> {
    public static <T> byte[] convertToBytes(List<T> list) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(list);
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("Error when serialize list ", e);
        }
        return null;
    }

    public static <T> List<T> convertFromBytes(byte[] trainings) {
        ByteArrayInputStream bis = new ByteArrayInputStream(trainings);
        try {
            ObjectInputStream in = new ObjectInputStream(bis);
            return (List<T>)in.readObject();
        } catch (Exception e) {
            log.error("Error when deserialize list", e);
        }
        return null;
    }
}
