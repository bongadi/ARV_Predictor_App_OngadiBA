package co.ke.ikocare.apollomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentVersion {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("publishDate")
    @Expose
    private String publishDate;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

}