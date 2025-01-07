package com.bansalandsons.JewellaryApplication.pojoclasses;

public class POJOGetallcategory {
    String categoryname,categorycode,categoryImage;

    public POJOGetallcategory(String categoryname, String categorycode,String categoryImage) {
        this.categoryname = categoryname;
        this.categorycode = categorycode;
        this.categoryImage = categoryImage;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCategorycode() {
        return categorycode;
    }

    public void setCategorycode(String categorycode) {
        this.categorycode = categorycode;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
