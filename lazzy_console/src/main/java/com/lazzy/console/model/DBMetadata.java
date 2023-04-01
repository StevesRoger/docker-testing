package com.lazzy.console.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created: chheng
 * Date: 01-Apr-2023 Sat
 * Time: 16:25
 */
public class DBMetadata {

    public static final String DB_HOST = "db.host";
    public static final String DB_PORT = "db.port";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_NAME = "db.name";

    private String host;
    private int port;
    private String username;
    private String password;
    private String databaseName;

    public DBMetadata() {
    }

    public DBMetadata(String host, int port, String username, String password, String databaseName) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Collection<?> buildProperties() {
        List<String> list = new ArrayList<>();
        list.add(String.format("%s=%s", DB_HOST, host));
        list.add(String.format("%s=%s", DB_PORT, port));
        list.add(String.format("%s=%s", DB_USERNAME, username));
        list.add(String.format("%s=%s", DB_PASSWORD, password));
        list.add(String.format("%s=%s", DB_NAME, databaseName));
        return list;
    }

    public boolean isValid() {
        return StringUtils.isNotEmpty(host)
                && port > 0
                && StringUtils.isNotEmpty(username)
                && StringUtils.isNotEmpty(password)
                && StringUtils.isNotEmpty(databaseName);
    }
}
