package org.jono.medicalmodelsauthorizationserver.repository.jdbc;

import java.util.List;
import org.jono.medicalmodelsauthorizationserver.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentCrudRepository extends CrudRepository<Comment, String> {
    List<Comment> findAllByDocumentId(String documentId);
}
