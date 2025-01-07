package com.bansalandsons.JewellaryApplication.pojoclasses;

public class Banner {
    private int bannerId;
    private String bannerName;
    private String imageUrl;

    // Constructor
    public Banner(int bannerId, String bannerName, String imageUrl) {
        this.bannerId = bannerId;
        this.bannerName = bannerName;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getBannerId() {
        return bannerId;
    }

    public String getBannerName() {
        return bannerName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

