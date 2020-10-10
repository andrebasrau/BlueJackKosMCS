package com.example.bluejackkos.Model;

public class Booking {
    String id;
    String name;
    String price;
    String facilities;
    String address;
    String image;
    String bookdate;
    Double lat;
    Double lng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBookdate() {
        return bookdate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Booking(String id, String name, String price, String facilities, String address, String image, String bookdate, Double lat, Double lng) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.facilities = facilities;
        this.address = address;
        this.image = image;
        this.bookdate = bookdate;
        this.lat = lat;
        this.lng = lng;
    }
}
