package co.ke.ikocare.apollomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MutationsByType {

    @SerializedName("mutationType")
    @Expose
    private String mutationType;
    @SerializedName("mutations")
    @Expose
    private List<Mutation> mutations = null;

    public String getMutationType() {
        return mutationType;
    }

    public void setMutationType(String mutationType) {
        this.mutationType = mutationType;
    }

    public List<Mutation> getMutations() {
        return mutations;
    }

    public void setMutations(List<Mutation> mutations) {
        this.mutations = mutations;
    }

}
