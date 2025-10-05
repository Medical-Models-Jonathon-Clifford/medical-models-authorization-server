package org.jono.medicalmodelsauthorizationserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jono.medicalmodelsauthorizationserver.model.LoginCompanies;
import org.jono.medicalmodelsauthorizationserver.model.MmUserBuilder;
import org.jono.medicalmodelsauthorizationserver.repository.MmUserInfoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

class MmUserInfoServiceTest {

    private static final String TEST_BASE_URL = "http://localhost:8080";

    private static MmUserInfoService mmUserInfoService;

    @BeforeAll
    static void setupAll() {
        final var passwordEncoder = Mockito.mock(PasswordEncoder.class);
        when(passwordEncoder.encode(any())).thenReturn("verysecurepassword2");
        final var mmUserBuilder = new MmUserBuilder(TEST_BASE_URL, passwordEncoder);
        final var userInfoRepository = new MmUserInfoRepository(mmUserBuilder);
        mmUserInfoService = new MmUserInfoService(userInfoRepository);
    }

    @Nested
    @DisplayName("loadUser")
    class LoadUser {
        @Test
        void shouldReturnClaimsInfoForDemoUser() {
            final Optional<OidcUserInfo> oidcUserInfo = mmUserInfoService.loadUser("rtrenneman");

            assertThat(oidcUserInfo).isPresent();
            final OidcUserInfo loadedUser = oidcUserInfo.get();

            assertThat(loadedUser.getClaims()).containsAllEntriesOf(
                    Map.ofEntries(
                            Map.entry("sub", "rtrenneman"),
                            Map.entry("name", "Mr. Roy Trenneman"),
                            Map.entry("given_name", "Roy"),
                            Map.entry("family_name", "Trenneman"),
                            Map.entry("profile", "http://localhost:8080/rtrenneman"),
                            Map.entry("picture", "http://localhost:8080/users/picture/rtrenneman.webp"),
                            Map.entry("email", "rtrenneman@example.com"),
                            Map.entry("email_verified", true),
                            Map.entry("gender", "female"),
                            Map.entry("birthdate", "1970-01-01"),
                            Map.entry("roles", List.of("ROLE_SUPPORT")),
                            Map.entry("honorific", "Mr."),
                            Map.entry("companyId", "1"),
                            Map.entry("userId", "1"),
                            Map.entry("updated_at", "1970-01-01T00:00:00Z")
                    )
            );
        }

        @Test
        void shouldReturnEmptyForNonExistentUser() {
            final Optional<OidcUserInfo> oidcUserInfo = mmUserInfoService.loadUser("doesnotexist");

            assertThat(oidcUserInfo).isEmpty();
        }
    }

    @Nested
    @DisplayName("getUserDetails")
    class GetUserDetails {

        @Test
        void shouldReturnAllUserDetails() {
            final List<UserDetails> allUserDetails = mmUserInfoService.getUserDetails();

            assertThat(allUserDetails).hasSize(9);

            assertThat(allUserDetails).anyMatch(u -> u.getUsername().equals("rtrenneman"));
            final UserDetails loadedUser = extractUserDetails(allUserDetails, "rtrenneman");
            assertThat(loadedUser.getAuthorities()).anyMatch(a -> a.getAuthority().equals("ROLE_SUPPORT"));
        }

    }

    @Nested
    @DisplayName("getLoginCompanies")
    class GetLoginCompanies {

        @Test
        void shouldReturnAllUserDetails() {
            final List<LoginCompanies> allLoginCompanies = mmUserInfoService.getLoginCompanies();

            assertThat(allLoginCompanies).hasSize(3);

            final LoginCompanies first = allLoginCompanies.getFirst();
            assertThat(first.companyId()).isEqualTo("1");
            assertThat(first.name()).isEqualTo("Medical Models IT Support Crowd");
            assertThat(first.users()).hasSize(3);
        }

    }

    private static UserDetails extractUserDetails(final List<UserDetails> allUserDetails, final String username) {
        final Optional<UserDetails> userDetails = allUserDetails.stream().filter(
                u -> u.getUsername().equals(username)).findFirst();
        assertThat(userDetails).isPresent();
        return userDetails.get();
    }
}
