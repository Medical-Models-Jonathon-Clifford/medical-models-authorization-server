package org.jono.medicalmodelsauthorizationserver.service;

import java.util.List;
import java.util.Optional;
import org.jono.medicalmodelsauthorizationserver.model.LoginCompanies;
import org.jono.medicalmodelsauthorizationserver.model.MmUser;
import org.jono.medicalmodelsauthorizationserver.repository.MmUserInfoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

@Service
public final class MmUserInfoService {

    private final MmUserInfoRepository mmUserInfoRepository;

    public MmUserInfoService(final MmUserInfoRepository mmUserInfoRepository) {
        this.mmUserInfoRepository = mmUserInfoRepository;
    }

    public Optional<OidcUserInfo> loadUser(final String username) {
        final Optional<MmUser> mmUser = this.mmUserInfoRepository.findByUsername(username);
        return mmUser.map(u -> new OidcUserInfo(u.oidcUserInfo().getClaims()));
    }

    public List<UserDetails> getUserDetails() {
        return this.mmUserInfoRepository.getUserDetails();
    }

    public List<LoginCompanies> getLoginCompanies() {
        return this.mmUserInfoRepository.getLoginCompanies();
    }
}
