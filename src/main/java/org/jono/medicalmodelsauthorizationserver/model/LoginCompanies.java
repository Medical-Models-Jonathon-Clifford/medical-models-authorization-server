package org.jono.medicalmodelsauthorizationserver.model;

import java.util.List;

public record LoginCompanies(String companyId, String name, List<LoginUser> users) {
}
