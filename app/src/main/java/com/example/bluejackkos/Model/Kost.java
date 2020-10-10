package com.example.bluejackkos.Model;

public class Kost {
    private Integer kostId;
    private String kostname;
    private String kostPrice;
    private String kostFacilities;
    private String kostImageSrc;
    private String kostAddress;
    private Double lat;
    private Double Lng;


    public Kost(Integer kostId, String kostname, String kostPrice, String kostFacilities, String kostImageSrc, String kostAddress, Double lat, Double lng) {
        this.kostId = kostId;
        this.kostname = kostname;
        this.kostPrice = kostPrice;
        this.kostFacilities = kostFacilities;
        this.kostImageSrc = kostImageSrc;
        this.kostAddress = kostAddress;
        this.lat = lat;
        this.Lng = lng;
    }

    public Integer getKostId() {
        return kostId;
    }

    public void setKostId(Integer kostId) {
        this.kostId = kostId;
    }

    public String getKostname() {
        return kostname;
    }

    public void setKostname(String kostname) {
        this.kostname = kostname;
    }

    public String getKostPrice() {
        return kostPrice;
    }

    public void setKostPrice(String kostPrice) {
        this.kostPrice = kostPrice;
    }

    public String getKostFacilities() {
        return kostFacilities;
    }

    public void setKostFacilities(String kostFacilities) {
        this.kostFacilities = kostFacilities;
    }

    public String getKostImageSrc() {
        return kostImageSrc;
    }

    public void setKostImageSrc(String kostImageSrc) {
        this.kostImageSrc = kostImageSrc;
    }

    public String getKostAddress() {
        return kostAddress;
    }

    public void setKostAddress(String kostAddress) {
        this.kostAddress = kostAddress;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        Lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return Lng;
    }
}
