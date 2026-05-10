package util;

import io.qameta.allure.Allure;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

import static java.nio.file.Files.newInputStream;

public class AllureUtils {

    public  static void attachScreenshot(String name, String path) {
        try {
            Allure.addAttachment(name, newInputStream(Path.of(path)));
        } catch (Exception e) {
            System.err.println("Failed to attach screenshot: " + e.getMessage());

        }
    }
}
