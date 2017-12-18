package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("photoId")
    @Expose
    private String photoId;
    @SerializedName("createdAt")
    @Expose
    private Integer createdAt;
    @SerializedName("url")
    @Expose
    private String url;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
