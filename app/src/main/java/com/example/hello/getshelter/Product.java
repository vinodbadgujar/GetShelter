package com.example.hello.getshelter;

import android.widget.ImageView;

public class Product {
    private String Place;
    private String City;
    private String Cost;
    private String Landmark;
    private String maximum_peoples;
    private String Pincode;
    private String Mobile ;

    public Product() {

    }

    public Product(String city, String cost, String maximum_peoples, String mobile) {
        City=city;
        Cost = cost;
        this.maximum_peoples = maximum_peoples;
        Mobile = mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getPlace() {
        return Place;
    }

    public String getCity() {
        return City;
    }

    public String getCost() {
        return Cost;
    }

    public String getLandmark() {
        return Landmark;
    }

    public String getMaximum_peoples() {
        return maximum_peoples;
    }

    public String getPincode() {
        return Pincode;
    }


    public void setPlace(String place) {
        Place = place;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public void setMaximum_peoples(String maximum_peoples) {
        this.maximum_peoples = maximum_peoples;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

}
