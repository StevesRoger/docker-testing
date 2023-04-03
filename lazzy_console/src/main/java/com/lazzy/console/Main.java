package com.lazzy.console;

import com.lazzy.console.service.JNTService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created: KimChheng
 * Date: 01-Apr-2023 Sat
 * Time: 8:55 AM
 */
public class Main {

    public static final String APP_PROP = "application.properties";

    public static void main(String[] args) throws Exception {
        osInfo();
        new JNTService().run(loadAppProperties());
    }

    private static void osInfo() {
        System.out.println("\n    The information about OS");
        System.out.println("\nName of the OS: " + System.getProperty("os.name"));
        System.out.println("Version of the OS: " + System.getProperty("os.version"));
        System.out.println("Architecture of THe OS: " + System.getProperty("os.arch"));
        System.out.println("\n");
    }

    private static Properties loadAppProperties() throws IOException {
        File file = new File(APP_PROP);
        if (file.exists()) {
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            return properties;
        }
        return new Properties();
    }
}
