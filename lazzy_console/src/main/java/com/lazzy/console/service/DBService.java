package com.lazzy.console.service;

import com.lazzy.console.model.DBMetadata;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.lazzy.console.Main.APP_PROP;

/**
 * Created: KimChheng
 * Date: 03-Apr-2023 Mon
 * Time: 4:44 PM
 */
public class DBService {

    public static Connection connectDB(Properties properties) throws SQLException, ClassNotFoundException, IOException {
        DBMetadata metadata = new DBMetadata();
        metadata.setHost(properties.getProperty(DBMetadata.DB_HOST));
        metadata.setPort(Integer.parseInt(properties.getProperty(DBMetadata.DB_PORT, "0")));
        metadata.setUsername(properties.getProperty(DBMetadata.DB_USERNAME));
        metadata.setPassword(properties.getProperty(DBMetadata.DB_PASSWORD));
        metadata.setDatabaseName(properties.getProperty(DBMetadata.DB_NAME));
        if (!metadata.isValid()) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter database host or ip example: 192.168.45.1");
            metadata.setHost(sc.nextLine());
            System.out.println("Please enter database port example: 5432");
            metadata.setPort(sc.nextInt());
            sc.nextLine(); // This line you have to add (It consumes the \n character)
            System.out.println("Please enter database username example: admin");
            metadata.setUsername(sc.nextLine());
            System.out.println("Please enter database password example: 3es43s");
            metadata.setPassword(sc.nextLine());
            System.out.println("Please enter database name example: testing");
            metadata.setDatabaseName(sc.nextLine());
            System.out.println("saving database metadata to " + APP_PROP);
            FileUtils.writeLines(new File(APP_PROP), metadata.buildProperties(), true);
            System.out.println("successfully");
        }
        System.out.println("Connecting database....");
        String url = String.format("jdbc:postgresql://%s:%s/%s", metadata.getHost(), metadata.getPort(), metadata.getDatabaseName());
        System.out.println(url);
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, metadata.getUsername(), metadata.getPassword());
        System.out.println("Connection established successfully");
        return connection;
    }
}
