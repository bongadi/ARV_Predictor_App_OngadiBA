package co.ke.ikocare.apollomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugScore {

    @SerializedName("drugClass")
    @Expose
    private DrugClass drugClass;
    @SerializedName("drug")
    @Expose
    private Drug drug;
    @SerializedName("SIR")
    @Expose
    private String sIR;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("partialScores")
    @Expose
    private List<PartialScore> partialScores = null;

    public DrugClass getDrugClass() {
        return drugClass;
    }

    public void setDrugClass(DrugClass drugClass) {
        this.drugClass = drugClass;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public String getSIR() {
        return sIR;
    }

    public void setSIR(String sIR) {
        this.sIR = sIR;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<PartialScore> getPartialScores() {
        return partialScores;
    }

    public void setPartialScores(List<PartialScore> partialScores) {
        this.partialScores = partialScores;
    }

}
