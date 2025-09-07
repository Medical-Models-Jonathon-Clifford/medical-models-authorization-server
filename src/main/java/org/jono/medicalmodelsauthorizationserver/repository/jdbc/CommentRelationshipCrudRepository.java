package org.jono.medicalmodelsauthorizationserver.repository.jdbc;

import java.util.List;
import org.jono.medicalmodelsauthorizationserver.model.CommentRelationship;
import org.springframework.data.repository.CrudRepository;

public interface CommentRelationshipCrudRepository extends CrudRepository<CommentRelationship, String> {
    List<CommentRelationship> findAllByDocumentId(String documentId);

    List<CommentRelationship> findAllByParentCommentId(String parentCommentId);

    List<CommentRelationship> findAllByChildCommentId(String childCommentId);

    CommentRelationship findFirstByChildCommentId(String childCommentId);
}
