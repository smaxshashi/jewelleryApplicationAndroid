package com.bansalandsons.JewellaryApplication.pojoclasses;

public class POJOGifting {
    private String giftingName;
    private int giftingCode;
    private String exfield1;

    public POJOGifting(String giftingName, int giftingCode, String exfield1) {
        this.giftingName = giftingName;
        this.giftingCode = giftingCode;
        this.exfield1 = exfield1;
    }

    // Getters
    public String getGiftingName() {
        return giftingName;
    }

    public int getGiftingCode() {
        return giftingCode;
    }

    public String getExfield1() {
        return exfield1;
    }
}