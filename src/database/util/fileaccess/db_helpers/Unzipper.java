package database.util.fileaccess.db_helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static constants.Constants.ANSI.*;

public class Unzipper {
    private String targetDirectory;
    private String zipFilePath;

    // Constructor to initialize the target directory and ZIP file path
    public Unzipper(String targetDirectory, String zipFilePath) {
        this.targetDirectory = targetDirectory;
        this.zipFilePath = zipFilePath;

        System.out.println(GREEN + "[UNZIP: " + zipFilePath + " TO " + targetDirectory + "]" + RESET);

    }

    // Method to unzip the file
    public void unzip() throws IOException {
        // Ensure the target directory exists or create it
        Files.createDirectories(Paths.get(targetDirectory));

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipInputStream.getNextEntry();

            // Process each entry in the zip file
            while (entry != null) {
                String filePath = Paths.get(targetDirectory, entry.getName()).toString();
                Path path = Paths.get(filePath);

                if (!entry.isDirectory()) {
                    // If the entry is a file and does not exist, extract it
                    if (!Files.exists(path)) {
                        extractFile(zipInputStream, filePath);
                    } else {
                        System.out.println("File already exists, skipping: " + filePath);
                    }
                } else {
                    // If the entry is a directory, make the directory if it doesn't already exist
                    if (!Files.exists(path)) {
                        Files.createDirectories(path);
                    }
                }
                
                zipInputStream.closeEntry();
                entry = zipInputStream.getNextEntry();
            }
        }
    }

    // Helper method to extract a file from the zip input stream
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = zipIn.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }
    }
}
