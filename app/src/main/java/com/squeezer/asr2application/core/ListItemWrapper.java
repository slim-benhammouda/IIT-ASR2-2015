package com.squeezer.asr2application.core;


import java.io.Serializable;

public class ListItemWrapper implements Serializable {

    private int mImageRes;
    private String mTitle;
    private String mDescription;

    public ListItemWrapper(){

    }

    public ListItemWrapper(int imageRes, String title, String description) {
        mImageRes = imageRes;
        mTitle = title;
        mDescription = description;
    }


    public int getImageRes() {
        return mImageRes;
    }

    public void setImageRes(int mImageRes) {
        this.mImageRes = mImageRes;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
