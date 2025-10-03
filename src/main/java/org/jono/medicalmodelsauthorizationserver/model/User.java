package org.jono.medicalmodelsauthorizationserver.model;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

@Data
public class User {
    private LoginUser loginUser;
    private UserDetails userDetails;
    private OidcUserInfo oidcUserInfo;
    private String base64Picture;

    public User(final LoginUser loginUser, final UserDetails userDetails, final OidcUserInfo oidcUserInfo,
            final String base64Picture) {
        this.loginUser = loginUser;
        this.userDetails = userDetails;
        this.oidcUserInfo = oidcUserInfo;
        this.base64Picture = base64Picture;
    }
}
