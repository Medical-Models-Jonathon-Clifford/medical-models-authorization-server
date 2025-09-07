package org.jono.medicalmodelsauthorizationserver.service.comment;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jono.medicalmodelsauthorizationserver.model.Comment;
import org.jono.medicalmodelsauthorizationserver.model.CommentRelationship;
import org.jono.medicalmodelsauthorizationserver.service.ForestBuilder;

@Slf4j
public class CommentForestBuilder {

    private CommentForestBuilder() {
        // Utility class
    }

    public static List<CommentTree> buildForest(final List<Comment> commentList,
            final List<CommentRelationship> commentRelationshipList) {
        return ForestBuilder.buildForest(commentList, commentRelationshipList, CommentTree::new,
                                         CommentTree::getChildren);
    }
}
