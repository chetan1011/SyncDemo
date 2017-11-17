package com.indianic.model;

/*
 * Model class, Contains the home screen banner data.
 */
public class BannerModel {

    private String imageUrl = "";
    private String title = "";
    private String webUrl = "";

    public BannerModel(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
