package co.ke.ikocare.apollomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MutationsAnalysis {

    @SerializedName("validationResults")
    @Expose
    private List<Object> validationResults = null;
    @SerializedName("drugResistance")
    @Expose
    private List<DrugResistance> drugResistance = null;

    public List<Object> getValidationResults() {
        return validationResults;
    }

    public void setValidationResults(List<Object> validationResults) {
        this.validationResults = validationResults;
    }

    public List<DrugResistance> getDrugResistance() {
        return drugResistance;
    }

    public void setDrugResistance(List<DrugResistance> drugResistance) {
        this.drugResistance = drugResistance;
    }

}
