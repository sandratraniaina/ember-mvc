package mg.emberframework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.http.Part;

public class FileUtils {
    public byte[] getPartByte(Part part) throws IOException {
        try (InputStream inputStream = part.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        }
    }

    public static String createFilePath(String dirPath, String fileName) {
        dirPath = dirPath.replaceAll("/+$", "");
        fileName = fileName.replaceAll("^/+", "");

        return dirPath + "/" + fileName;
    }

    public static String getSimpleFileName(String fileName, String extension) {
        return fileName.substring(0, (fileName.length() - extension.length()) - 1);
    }

    private FileUtils() {
    }
}
