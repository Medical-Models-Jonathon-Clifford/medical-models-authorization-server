package org.jono.medicalmodelsauthorizationserver.model;

public record LoginUser(String displayName, String role, String username, String password) {
}
