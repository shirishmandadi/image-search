package com.shirish.sofi.image.search.domain;

import com.google.gson.annotations.Expose;

public class ErrorResponse {

    @Expose(deserialize = false, serialize = true)
    private String error_code;

    @Expose(deserialize = false, serialize = true)
    private String description;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
