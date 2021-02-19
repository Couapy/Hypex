package cloud.marchand.hypex.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

    private FileUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Read the file.
     * 
     * @param file file path
     * @return file content
     * @throws IOException the file doesn't exists
     */
    public static String readFile(String file) throws IOException {
        InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String content = "";
        String line;

        // Read the file
        while ((line = reader.readLine()) != null) {
            content = content.concat(line + "\n");
        }

        return content.substring(0, content.length() - 1);
    }
    
}