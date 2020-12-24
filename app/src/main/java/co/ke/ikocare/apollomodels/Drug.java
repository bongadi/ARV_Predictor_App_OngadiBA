package co.ke.ikocare.apollomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Drug {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("displayAbbr")
    @Expose
    private String displayAbbr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayAbbr() {
        return displayAbbr;
    }

    public void setDisplayAbbr(String displayAbbr) {
        this.displayAbbr = displayAbbr;
    }

}
