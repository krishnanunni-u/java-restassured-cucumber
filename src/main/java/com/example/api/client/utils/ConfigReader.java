package com.example.api.client.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final String CONFIG_FILE_PATH = "src/main/resources/config/application.properties";
    private static Properties properties = new Properties();

    // Load properties file during class initialization
    static {
        try (FileInputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load configuration file: " + e.getMessage());
        }
    }

    // Get a property value by key
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("Property '" + key + "' not found in application.properties");
        }
        return value;
    }
}
