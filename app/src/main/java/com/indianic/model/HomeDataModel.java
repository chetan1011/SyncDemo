package com.indianic.model;


import java.util.ArrayList;

/*
 * Model class, Contains the home screen data.
 */
public class HomeDataModel {

    private ArrayList<BannerModel> bannerList;


    public ArrayList<BannerModel> getBannerList() {
        return bannerList;
    }

    public void setBannerList(ArrayList<BannerModel> bannerList) {
        this.bannerList = bannerList;
    }

}
