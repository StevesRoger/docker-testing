package com.lazzy.console.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazzy.console.model.JNTAreaMetadata;
import com.lazzy.console.model.JNTAreaMetadata.TranslateHolder;
import com.lazzy.console.model.JNTMetadata;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.*;

import static com.lazzy.console.Main.APP_PROP;

/**
 * Created: KimChheng
 * Date: 03-Apr-2023 Mon
 * Time: 4:42 PM
 */
public class JNTService {

    public void run(Properties properties) throws Exception {
        DBService dbService = new DBService();
        Connection con = null;
        try {
            // con = dbService.connectDB(properties);
            List<JNTAreaMetadata> jntAreaRespons = requestJNTProvince(properties);
            Map<String, JNTAreaMetadata> jntAreaResponseMap = new HashMap<>();
            Set<TranslateHolder> provinces = new HashSet<>();
            Set<TranslateHolder> disticts = new HashSet<>();
            Set<TranslateHolder> communes = new HashSet<>();
            for (JNTAreaMetadata metadata : jntAreaRespons) {
                jntAreaResponseMap.putIfAbsent(metadata.getDestinationCode(), metadata);
                provinces.add(new TranslateHolder(metadata.getProvince(), metadata.getDestinationCode()));
                disticts.add(new TranslateHolder(metadata.getCity(), metadata.getDestinationCode()));
                communes.add(new TranslateHolder(metadata.getCountyarea(), metadata.getDestinationCode()));
            }
            for (TranslateHolder holder : provinces) {

            }
            System.out.println("There are " + jntAreaRespons.size() + " area");
            System.out.println(jntAreaRespons);
        } finally {
            if (con != null) {
                con.close();
                System.out.println("Connection closed....");
            }
        }
    }

    private List<JNTAreaMetadata> requestJNTProvince(Properties properties) throws Exception {
        List<JNTAreaMetadata> list = new ArrayList<>();
        HttpURLConnection con = null;
        try {
            JNTMetadata metadata = new JNTMetadata();
            metadata.setUrl(properties.getProperty(JNTMetadata.BASE_URL));
            metadata.setDigest(properties.getProperty(JNTMetadata.DIGEST));
            metadata.setCompanyId(properties.getProperty(JNTMetadata.COMPANY_ID));
            metadata.setJson(properties.getProperty(JNTMetadata.JSON));
            metadata.setMsgType(properties.getProperty(JNTMetadata.MSG_TYPE));
            if (!metadata.isValid()) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please provide JNT metadata request");
                System.out.println("Please enter base url example: http://47.57.86.134");
                metadata.setUrl(sc.nextLine());
                System.out.println("Please enter data digest example: 23943d3830a92c0ffe43e213306b26ca");
                metadata.setDigest(sc.nextLine());
                System.out.println("Please enter company id example: Wing Bank");
                metadata.setCompanyId(sc.nextLine());
                System.out.println("Please enter json data example: {\"customerid\": \"10002\"}");
                metadata.setJson(sc.nextLine());
                System.out.println("Please enter msg type example: OBTAINPROVCITYAREA");
                metadata.setMsgType(sc.nextLine());
                System.out.println("saving JNT metadata to " + APP_PROP);
                FileUtils.writeLines(new File(APP_PROP), metadata.buildProperties(), true);
                System.out.println("successfully");
            }
            String url = metadata.getUrl() + "/jandt-khm-api/api/baseData/findAllProvincesAndCities.do";
            System.out.println("Requesting to JNT " + url);
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("POST");
            Map<String, String> parameters = new HashMap<>();
            parameters.put("data_digest", metadata.getDigest());
            parameters.put("eccompanyid", metadata.getCompanyId());
            parameters.put("logistics_interface", metadata.getJson());
            parameters.put("msg_type", metadata.getMsgType());
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
            JSONObject json = new JSONObject(content.toString());
            System.out.println("Response status " + status);
            System.out.println(json.toString());
            System.out.println("\n");
            if (200 == status && json.has("responseitems")) {
                ObjectMapper objectMapper = new ObjectMapper();
                JSONArray responseitems = json.getJSONArray("responseitems");
                JSONArray baseList = responseitems.getJSONObject(0).getJSONArray("baseList");
                for (int i = 0; i < baseList.length(); i++) {
                    list.add(objectMapper.readValue(baseList.getJSONObject(i).toString(), JNTAreaMetadata.class));
                }
            }
        } finally {
            if (con != null)
                con.disconnect();
        }
        return list;
    }

    private void requestTranslate(Properties properties, Set<TranslateHolder> holders) throws Exception {
        HttpURLConnection con = null;
        try {
            properties.getProperty("base.api.translate");
            if (!metadata.isValid()) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please provide JNT metadata request");
                System.out.println("Please enter base url example: http://47.57.86.134");
                metadata.setUrl(sc.nextLine());
                System.out.println("Please enter data digest example: 23943d3830a92c0ffe43e213306b26ca");
                metadata.setDigest(sc.nextLine());
                System.out.println("Please enter company id example: Wing Bank");
                metadata.setCompanyId(sc.nextLine());
                System.out.println("Please enter json data example: {\"customerid\": \"10002\"}");
                metadata.setJson(sc.nextLine());
                System.out.println("Please enter msg type example: OBTAINPROVCITYAREA");
                metadata.setMsgType(sc.nextLine());
                System.out.println("saving JNT metadata to " + APP_PROP);
                FileUtils.writeLines(new File(APP_PROP), metadata.buildProperties(), true);
                System.out.println("successfully");
            }
            String url = metadata.getUrl() + "/jandt-khm-api/api/baseData/findAllProvincesAndCities.do";
            System.out.println("Requesting to JNT " + url);
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("POST");
            Map<String, String> parameters = new HashMap<>();
            parameters.put("data_digest", metadata.getDigest());
            parameters.put("eccompanyid", metadata.getCompanyId());
            parameters.put("logistics_interface", metadata.getJson());
            parameters.put("msg_type", metadata.getMsgType());
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
            JSONObject json = new JSONObject(content.toString());
            System.out.println("Response status " + status);
            System.out.println(json.toString());
            System.out.println("\n");
            if (200 == status && json.has("responseitems")) {
                ObjectMapper objectMapper = new ObjectMapper();
                JSONArray responseitems = json.getJSONArray("responseitems");
                JSONArray baseList = responseitems.getJSONObject(0).getJSONArray("baseList");
                for (int i = 0; i < baseList.length(); i++) {
                    list.add(objectMapper.readValue(baseList.getJSONObject(i).toString(), JNTAreaMetadata.class));
                }
            }
        } finally {
            if (con != null)
                con.disconnect();
        }
    }

    private static String buildParamsString(Map<String, String> params) throws UnsupportedEncodingException {
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
