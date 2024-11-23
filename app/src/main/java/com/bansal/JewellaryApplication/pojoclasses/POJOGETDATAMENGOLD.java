package com.bansal.JewellaryApplication.pojoclasses;

public class POJOGETDATAMENGOLD {
    String name,code;

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public POJOGETDATAMENGOLD(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
