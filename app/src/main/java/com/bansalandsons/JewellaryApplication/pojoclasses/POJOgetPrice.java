package com.bansalandsons.JewellaryApplication.pojoclasses;

public class POJOgetPrice {
    private String price24K;
    private String price22K;
    private String price18K;
    private String price14K;
    private String metalName;

    // Constructor
    public POJOgetPrice(String price24K, String price22K, String price18K, String price14K, String metalName) {
        this.price24K = price24K;
        this.price22K = price22K;
        this.price18K = price18K;
        this.price14K = price14K;
        this.metalName = metalName;
    }

    // Getter methods
    public String getPrice24K() { return price24K; }
    public String getPrice22K() { return price22K; }
    public String getPrice18K() { return price18K; }
    public String getPrice14K() { return price14K; }
    public String getMetalName() { return metalName; }
}
