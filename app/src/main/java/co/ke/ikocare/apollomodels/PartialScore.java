package co.ke.ikocare.apollomodels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartialScore {

    @SerializedName("mutations")
    @Expose
    private List<Mutation> mutations = null;
    @SerializedName("score")
    @Expose
    private Integer score;

    public List<Mutation> getMutations() {
        return mutations;
    }

    public void setMutations(List<Mutation> mutations) {
        this.mutations = mutations;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
