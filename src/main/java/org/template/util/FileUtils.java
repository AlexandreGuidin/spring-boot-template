package org.template.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
    }

    public static Optional<File> convertMultipartFileToFile(MultipartFile image) {
        return convertMultipartFileToFile("temp", image);
    }

    public static Optional<File> convertMultipartFileToFile(String fileName, MultipartFile image) {
        try {
            final byte[] bytes = image.getBytes();
            final File tempImage = File.createTempFile(fileName, ".tmp");
            Files.write(tempImage.toPath(), bytes);
            return Optional.of(tempImage);
        } catch (Exception e) {
            logger.error("FileUtils.convertMultipartFileToFile error", e);
            return Optional.empty();
        }
    }

    public static String extension(MultipartFile image) {
        return Optional.ofNullable(image.getOriginalFilename())
                .map(n -> n.split("\\.")[1])
                .orElse(null);
    }

    public static String classPathFileToBase64(String fileName) {
        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            return fileToBase64(file);
        } catch (Exception e) {
            logger.error("FileUtils.classPathFileToBase64 error", e);
            return null;
        }
    }

    public static String fileToBase64(File file) {
        try {
            InputStream input = new FileInputStream(file);
            byte[] imageBytes = new byte[(int) file.length()];
            input.read(imageBytes, 0, imageBytes.length);
            input.close();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            logger.error("FileUtils.fileToBase64 error", e);
            return null;
        }
    }

    public static File base64ToFile(String base64) {
        return base64ToFile(base64, UUID.randomUUID().toString());
    }

    public static File base64ToFile(String base64, String fileName) {
        try {
            final File tempImage = File.createTempFile(fileName, ".tmp");
            Files.write(tempImage.toPath(), Base64.getDecoder().decode(base64));
            return tempImage;
        } catch (Exception e) {
            logger.error("FileUtils.base64ToFile error", e);
            return null;
        }
    }
}
