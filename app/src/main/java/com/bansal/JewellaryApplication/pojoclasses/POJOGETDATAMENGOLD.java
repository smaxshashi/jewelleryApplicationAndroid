package com.bansal.JewellaryApplication.pojoclasses;

public class POJOGETDATAMENGOLD {
    String name,code,image;



    public POJOGETDATAMENGOLD(String name, String code,String image) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
