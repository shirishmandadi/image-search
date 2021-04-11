package com.shirish.sofi.image.search.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @Expose
    @SerializedName("errorStatus")
    private String errorStatus;

    @Expose
    @SerializedName("description")
    private String description;

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setError_code(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
