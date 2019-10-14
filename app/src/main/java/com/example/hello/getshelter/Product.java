package com.example.hello.getshelter;

import android.widget.ImageView;

public class Product {
    private String pplace;
    private String pcity;
    private String pcost;
    private String plandmark;
    private String pcapacity;
    private String ppincode;
    private ImageView pimages;
    private String pnumber;

    public Product(String pplace, String pcost, String pcapacity, String pnumber
    ) {
        this.pplace = pplace;
        this.pcost = pcost;
        this.pcapacity = pcapacity;
        this.pnumber=pnumber;
    }

    public String getPplace() {
        return pplace;
    }

    public String getPcity() {
        return pcity;
    }

    public String getPcost() {
        return pcost;
    }

    public String getPlandmark() {
        return plandmark;
    }

    public String getPcapacity() {
        return pcapacity;
    }

    public String getPpincode() {
        return ppincode;
    }

    public ImageView getPimages() {
        return pimages;
    }
    public String getPnumber() {
        return pnumber;
    }
}
