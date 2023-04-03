package com.lazzy.console.model;

import java.util.Objects;

/**
 * Created: chheng
 * Date: 01-Apr-2023 Sat
 * Time: 17:03
 */
public class JNTAreaMetadata {

    private String destinationCode;
    private String province;
    private String city;
    private String countyarea;
    private String cityCode;
    private String sixWordCode;
    private String threeSegmentCode;
    private String provinceId;
    private String distictId;
    private String communeId;

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

    public static class TranslateHolder {

        private String id;
        private String kh;
        private String en;
        private String code;

        public TranslateHolder() {
        }

        public TranslateHolder(String kh, String code) {
            this.kh = kh;
            this.code = code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKh() {
            return kh;
        }

        public void setKh(String kh) {
            this.kh = kh;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public int hashCode() {
            return Objects.hash(kh, code);
        }

        @Override
        public boolean equals(Object that) {
            return that instanceof TranslateHolder
                    && this.kh != null
                    && this.kh.equals(((TranslateHolder) that).getKh())
                    && this.code != null
                    && this.code.equals(((TranslateHolder) that).getCode());
        }
    }
}
