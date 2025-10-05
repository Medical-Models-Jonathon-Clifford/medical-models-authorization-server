package org.jono.medicalmodelsauthorizationserver.model;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

public record MmUser(LoginUser loginUser,
                     UserDetails userDetails,
                     OidcUserInfo oidcUserInfo
) {}
