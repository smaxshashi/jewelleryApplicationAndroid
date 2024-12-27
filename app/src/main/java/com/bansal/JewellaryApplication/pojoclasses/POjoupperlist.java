package com.bansal.JewellaryApplication.pojoclasses;

public class POjoupperlist {
    String productId;
    String productName;
    String weight;
    String karat;
    String imageUrl,description;

    public POjoupperlist(String productId, String productName, String weight, String karat, String imageUrl, String description) {
        this.productId = productId;
        this.productName = productName;
        this.weight = weight;
        this.karat = karat;
        this.imageUrl = imageUrl;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
