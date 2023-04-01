package com.lazzy.console.model;

/**
 * Created: chheng
 * Date: 01-Apr-2023 Sat
 * Time: 17:03
 */
public class JNTAreaResponse {

    private String destinationCode;
    private String province;
    private String city;
    private String countyarea;
    private String cityCode;
    private String sixWordCode;
    private String threeSegmentCode;

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountyarea() {
        return countyarea;
    }

    public void setCountyarea(String countyarea) {
        this.countyarea = countyarea;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSixWordCode() {
        return sixWordCode;
    }

    public void setSixWordCode(String sixWordCode) {
        this.sixWordCode = sixWordCode;
    }

    public String getThreeSegmentCode() {
        return threeSegmentCode;
    }

    public void setThreeSegmentCode(String threeSegmentCode) {
        this.threeSegmentCode = threeSegmentCode;
    }
}
