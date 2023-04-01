package com.lazzy.console;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created: KimChheng
 * Date: 01-Apr-2023 Sat
 * Time: 8:55 AM
 */
public class Main {

    public static void main(String[] args) {
        osInfo();
        //requestJNTProvince();
        try {
            connectDB();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void osInfo() {
        System.out.println("\n    The information about OS");
        System.out.println("\nName of the OS: " + System.getProperty("os.name"));
        System.out.println("Version of the OS: " + System.getProperty("os.version"));
        System.out.println("Architecture of THe OS: " + System.getProperty("os.arch"));
        System.out.println("\n");
    }

    private static void connectDB() throws SQLException {
        Connection con = null;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter database host or ip example: 192.168.45.1");
            String host = sc.nextLine();
            System.out.println("Please enter database port example: 5432");
            int port = sc.nextInt();
            sc.nextLine(); // This line you have to add (It consumes the \n character)
            System.out.println("Please enter database username example: admin");
            String username = sc.nextLine();
            System.out.println("Please enter database password example: 3es43s");
            String password = sc.nextLine();
            System.out.println("Please enter database name example: testing");
            String dbName = sc.nextLine();
            System.out.println("Connecting database....");
            String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established successfully");
            con.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
                System.out.println("Connection Closed....");
            }
        }
    }

    private static void requestJNTProvince() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a String");
        String str = sc.nextLine();
        System.out.println("The String is: " + str);
        System.out.println("Enter an Integer");
        int i = sc.nextInt();
        System.out.println("The Integer is: " + i);
        System.out.println("Enter a Float value");
        float f = sc.nextFloat();
        System.out.println("The Float value is: " + f);
        HttpURLConnection con = null;
        try {
            URL url = new URL("http://47.57.86.134/jandt-khm-api/api/baseData/findAllProvincesAndCities.do");
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("POST");
            Map<String, String> parameters = new HashMap<>();
            parameters.put("data_digest", "23943d3830a92c0ffe43e213306b26ca");
            parameters.put("eccompanyid", "Wing Bank");
            parameters.put("logistics_interface", "{\"customerid\": \"10002\" }");
            parameters.put("msg_type", "OBTAINPROVCITYAREA");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(buildParamsString(parameters));
            out.flush();
            out.close();
            int status = con.getResponseCode();
            Reader streamReader;
            if (200 == status) {
                streamReader = new InputStreamReader(con.getInputStream());
            } else {
                streamReader = new InputStreamReader(con.getErrorStream());
            }
            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(new JSONObject(content.toString()).toString(2));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null)
                con.disconnect();
        }
    }

    public static String buildParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }
        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
