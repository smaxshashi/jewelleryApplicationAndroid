package com.bansal.JewellaryApplication.pojoclasses;

public class POJOOccusion {
    String name,image;
    int code;

    public POJOOccusion(String name, String image, int code) {
        this.name = name;
        this.image = image;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getCode() {
        return code;
    }
}

