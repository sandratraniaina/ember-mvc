package mg.emberframework.core.data;

import java.io.FileOutputStream;
import java.io.IOException;

import mg.emberframework.utils.io.FileUtils;

/**
 * Represents a file with a name and binary content.
 * <p>
 * This class provides methods to rename the file and write its content to a directory.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class File {
    /** The name of the file. */
    String fileName;
    /** The binary content of the file. */
    byte[] fileBytes;

    /**
     * Renames the file using a new name.
     * @param newFileName the new name for the file
     */
    public void rename(String newFileName) {
        String newName = FileUtils.changeFileName(getFileName(), newFileName);
        setFileName(newName);
    }

    /**
     * Writes the file's content to a specified directory.
     * @param dirPath the directory path to write to
     * @throws IOException if an I/O error occurs during writing
     */
    public void writeTo(String dirPath) throws IOException {
        String filePath = FileUtils.createFilePath(dirPath, getFileName());
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(fileBytes);
        }
    }

    /** Default constructor. */
    public File() {}

    /**
     * Constructs a file with a name and binary content.
     * @param fileName the file name
     * @param fileBytes the file content as bytes
     */
    public File(String fileName, byte[] fileBytes) {
        setFileName(fileName);
        setFileBytes(fileBytes);
    }

    /**
     * Gets the file name.
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     * @param fileName the name to set
     */
    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the file content as bytes.
     * @return the file bytes
     */
    public byte[] getFileBytes() {
        return fileBytes;
    }

    /**
     * Sets the file content as bytes.
     * @param fileBytes the bytes to set
     */
    private void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
}