package com.lazzy.console.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created: chheng
 * Date: 01-Apr-2023 Sat
 * Time: 17:08
 */
public class JNTMetadata {

    public static final String BASE_URL = "jnt.base.url";
    public static final String DIGEST = "jnt.digest";
    public static final String COMPANY_ID = "jnt.company";
    public static final String JSON = "jnt.json";
    public static final String MSG_TYPE = "jnt.msg.type";

    private String url;
    private String digest;
    private String companyId;
    private String json;
    private String msgType;

    public JNTMetadata() {
    }

    public JNTMetadata(String url, String digest, String companyId, String json, String msgType) {
        this.url = url;
        this.digest = digest;
        this.companyId = companyId;
        this.json = json;
        this.msgType = msgType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Collection<?> buildProperties() {
        List<String> list = new ArrayList<>();
        list.add(String.format("%s=%s", BASE_URL, url));
        list.add(String.format("%s=%s", DIGEST, digest));
        list.add(String.format("%s=%s", COMPANY_ID, companyId));
        list.add(String.format("%s=%s", JSON, json));
        list.add(String.format("%s=%s", MSG_TYPE, msgType));
        return list;
    }

    public boolean isValid() {
        return StringUtils.isNotEmpty(url)
                && StringUtils.isNotEmpty(digest)
                && StringUtils.isNotEmpty(companyId)
                && StringUtils.isNotEmpty(json)
                && StringUtils.isNotEmpty(msgType);
    }
}
