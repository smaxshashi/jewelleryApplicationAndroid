package com.bansalandsons.JewellaryApplication.pojoclasses;

public class POJOMenWomenProductlist {
    String productname,image;

    public POJOMenWomenProductlist(String productname, String image) {
        this.productname = productname;
        this.image = image;
    }

    public String getProductname() {
        return productname;
    }

    public String getImage() {
        return image;
    }
}
