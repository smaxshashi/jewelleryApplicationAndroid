package com.bansal.JewellaryApplication.pojoclasses;
public class POJOgetPrice {
    String k24, k22, k18, k14;
    String metal;

    public POJOgetPrice(String k24, String k22, String k18, String k14, String metal) {
        this.k24 = k24;
        this.k22 = k22;
        this.k18 = k18;
        this.k14 = k14;
        this.metal = metal;
    }

    public String getK24() {
        return k24;
    }

    public void setK24(String k24) {
        this.k24 = k24;
    }

    public String getK22() {
        return k22;
    }

    public void setK22(String k22) {
        this.k22 = k22;
    }

    public String getK18() {
        return k18;
    }

    public void setK18(String k18) {
        this.k18 = k18;
    }

    public String getK14() {
        return k14;
    }

    public void setK14(String k14) {
        this.k14 = k14;
    }

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }
}
