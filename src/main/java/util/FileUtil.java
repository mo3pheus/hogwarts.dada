package util;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    public static String readContent(String fileName) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Files.readAllLines(Paths.get(fileName)));
        return stringBuilder.toString();
    }
}
