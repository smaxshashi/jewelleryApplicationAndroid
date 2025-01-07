package com.bansalandsons.JewellaryApplication.pojoclasses;

public class POJOGEThimHerProduct {

    String productId;
    String productName;
    String weight;
    String karat;
    String imageUrl,soulmet,description;

    public POJOGEThimHerProduct(String productId, String productName, String weight, String karat, String imageUrl,String soulmet,String description) {
        this.productId = productId;
        this.productName = productName;
        this.weight = weight;
        this.karat = karat;
        this.imageUrl = imageUrl;
        this.soulmet=soulmet;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSoulmet() {
        return soulmet;
    }

    public void setSoulmet(String soulmet) {
        this.soulmet = soulmet;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getKarat() {
        return karat;
    }

    public void setKarat(String karat) {
        this.karat = karat;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
