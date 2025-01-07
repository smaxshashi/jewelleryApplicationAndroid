package com.bansalandsons.JewellaryApplication.pojoclasses;

public class POJOSokumate {
    String name,code,image;

    public POJOSokumate(String name, String code, String image) {
        this.name = name;
        this.code = code;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getImage() {
        return image;
    }
}
