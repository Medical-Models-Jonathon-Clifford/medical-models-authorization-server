package org.jono.medicalmodelsauthorizationserver.repository.jdbc;

import org.jono.medicalmodelsauthorizationserver.model.DocumentRelationship;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRelationshipCrudRepository extends CrudRepository<DocumentRelationship, String> {
}
