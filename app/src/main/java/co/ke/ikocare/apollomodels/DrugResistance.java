package co.ke.ikocare.apollomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugResistance {

    @SerializedName("gene")
    @Expose
    private Gene gene;
    @SerializedName("drugScores")
    @Expose
    private List<DrugScore> drugScores = null;
    @SerializedName("mutationsByTypes")
    @Expose
    private List<MutationsByType> mutationsByTypes = null;
    @SerializedName("commentsByTypes")
    @Expose
    private List<CommentsByType> commentsByTypes = null;

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public List<DrugScore> getDrugScores() {
        return drugScores;
    }

    public void setDrugScores(List<DrugScore> drugScores) {
        this.drugScores = drugScores;
    }

    public List<MutationsByType> getMutationsByTypes() {
        return mutationsByTypes;
    }

    public void setMutationsByTypes(List<MutationsByType> mutationsByTypes) {
        this.mutationsByTypes = mutationsByTypes;
    }

    public List<CommentsByType> getCommentsByTypes() {
        return commentsByTypes;
    }

    public void setCommentsByTypes(List<CommentsByType> commentsByTypes) {
        this.commentsByTypes = commentsByTypes;
    }

}
