package com.android.greenfoodchallenge.carboncalculator;

public class UploadImage {
    private String mName;
    private String mUrl;

    public UploadImage() {

    }

    public UploadImage(String name, String url) {
        if(name.equals("")) {
            name = "No Name";
        }

        mName = name;
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
