package org.jono.medicalmodelsauthorizationserver.repository.jdbc;

import org.jono.medicalmodelsauthorizationserver.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserCrudRepository extends CrudRepository<User, String> {
}
