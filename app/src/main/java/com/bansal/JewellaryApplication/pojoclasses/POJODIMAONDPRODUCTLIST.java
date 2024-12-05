package com.bansal.JewellaryApplication.pojoclasses;

public class POJODIMAONDPRODUCTLIST {
    String name,code,image;

    public POJODIMAONDPRODUCTLIST(String name, String code,String image) {
        this.name = name;
        this.code = code;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
