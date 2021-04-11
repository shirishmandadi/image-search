package com.shirish.sofi.image.search.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {
    @Expose
    @SerializedName("gif_id")
    private String gifId;

    public String getGifId() {
        return gifId;
    }

    public void setGifId(String gifId) {
        this.gifId = gifId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Expose
    @SerializedName("url")
    private String url;
}
