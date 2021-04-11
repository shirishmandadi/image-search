package com.shirish.sofi.image.search.domain;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ImageSearchResponseDEF {
    @Expose
    @SerializedName("item_count")
    private Integer itemCount = null;

    @Expose
    @SerializedName("data")
    private Data data = null;

    @Expose
    @SerializedName("Errors")
    private List<ErrorResponse> errors = null;

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<ErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorResponse> errors) {
        this.errors = errors;
    }

}
