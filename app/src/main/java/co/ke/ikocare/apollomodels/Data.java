package co.ke.ikocare.apollomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("currentVersion")
    @Expose
    private CurrentVersion currentVersion;
    @SerializedName("mutationsAnalysis")
    @Expose
    private MutationsAnalysis mutationsAnalysis;

    public CurrentVersion getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(CurrentVersion currentVersion) {
        this.currentVersion = currentVersion;
    }

    public MutationsAnalysis getMutationsAnalysis() {
        return mutationsAnalysis;
    }

    public void setMutationsAnalysis(MutationsAnalysis mutationsAnalysis) {
        this.mutationsAnalysis = mutationsAnalysis;
    }

}
