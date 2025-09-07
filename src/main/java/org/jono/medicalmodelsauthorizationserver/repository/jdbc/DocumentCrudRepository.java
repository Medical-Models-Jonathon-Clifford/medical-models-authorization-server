package org.jono.medicalmodelsauthorizationserver.repository.jdbc;

import org.jono.medicalmodelsauthorizationserver.model.Document;
import org.springframework.data.repository.CrudRepository;

public interface DocumentCrudRepository extends CrudRepository<Document, String> {
}
