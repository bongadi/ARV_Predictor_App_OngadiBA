package co.ke.ikocare.apollomodels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentsByType {

    @SerializedName("commentType")
    @Expose
    private String commentType;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}