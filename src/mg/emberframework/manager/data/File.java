package mg.emberframework.manager.data;

import java.io.FileOutputStream;
import java.io.IOException;

import mg.emberframework.utils.io.FileUtils;

public class File {
    String fileName;
    byte[] fileBytes;

    // Method
    public void rename(String newFileName) {
        String newName = FileUtils.changeFileName(getFileName(), newFileName);
        setFileName(newName);
    }

    public void writeTo(String dirPath) throws IOException {
        String filePath = FileUtils.createFilePath(dirPath, getFileName());

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(fileBytes);
        }
    }

    // Contructor
    public File() {}
    public File(String fileName, byte[] fileBytes) {
        setFileName(fileName);
        setFileBytes(fileBytes);
    }

    // Getters and setters
    public String getFileName() {
        return fileName;
    }
    private void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public byte[] getFileBytes() {
        return fileBytes;
    }
    private void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
}
