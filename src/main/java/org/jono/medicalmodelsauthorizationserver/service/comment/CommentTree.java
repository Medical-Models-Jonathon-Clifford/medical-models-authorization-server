package org.jono.medicalmodelsauthorizationserver.service.comment;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.jono.medicalmodelsauthorizationserver.model.Comment;

@Data
public class CommentTree {
    private Comment comment;
    private List<CommentTree> children;

    public CommentTree(final Comment comment) {
        this.comment = comment;
        this.children = new ArrayList<>();
    }
}
