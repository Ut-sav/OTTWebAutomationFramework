package com.binge.qa.configReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;

    public ConfigReader() {
        properties = new Properties();

        String configPath = System.getProperty("user.dir")
                + File.separator
                + "src"
                + File.separator
                + "main"
                + File.separator
                + "java"
                + File.separator
                + "com"
                + File.separator
                + "binge"
                + File.separator
                + "qa"
                + File.separator
                + "config"
                + File.separator
                + "config.properties";

        try (FileInputStream fis = new FileInputStream(configPath)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load config.properties from path: " + configPath, e
            );
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
