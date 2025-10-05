package org.jono.medicalmodelsauthorizationserver.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jono.medicalmodelsauthorizationserver.model.LoginCompanies;
import org.jono.medicalmodelsauthorizationserver.model.MmUser;
import org.jono.medicalmodelsauthorizationserver.model.MmUserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public final class MmUserInfoRepository {

    private final Map<String, MmUser> userInfo;
    private final List<LoginCompanies> companyInfo;

    public MmUserInfoRepository(final MmUserBuilder mmUserBuilder) {
        final MmUser rtrenneman = mmUserBuilder.builder()
                .userId("1")
                .username("rtrenneman")
                .password("N78S9x9ft$HFGMrf")
                .companyId("1")
                .honorific("Mr.")
                .givenName("Roy")
                .familyName("Trenneman")
                .roles(List.of("SUPPORT"))
                .build();
        final MmUser mmoss = mmUserBuilder.builder()
                .userId("2")
                .username("mmoss")
                .password("y?jaHKGTaji6xAd9")
                .companyId("1")
                .honorific("Mr.")
                .givenName("Maurice")
                .familyName("Moss")
                .roles(List.of("SUPPORT"))
                .build();
        final MmUser jbarber = mmUserBuilder.builder()
                .userId("3")
                .username("jbarber")
                .password("GM!mQn!8K8Db9p#p")
                .companyId("1")
                .honorific("Ms.")
                .givenName("Jen")
                .familyName("Barber")
                .roles(List.of("SUPPORT"))
                .build();
        final MmUser lcuddy = mmUserBuilder.builder()
                .userId("4")
                .username("lcuddy")
                .password("YjzJdH6!G??tntQ#")
                .companyId("2")
                .honorific("Dr.")
                .givenName("Lisa")
                .familyName("Cuddy")
                .roles(List.of("ADMIN"))
                .build();
        final MmUser ghouse = mmUserBuilder.builder()
                .userId("5")
                .username("ghouse")
                .password("S!p5fs!MFx&&GTPs")
                .companyId("2")
                .honorific("Dr.")
                .givenName("Gregory")
                .familyName("House")
                .roles(List.of("USER"))
                .build();
        final MmUser jwilson = mmUserBuilder.builder()
                .userId("6")
                .username("jwilson")
                .password("s9dQd$grL!!Y5?$h")
                .companyId("2")
                .honorific("Dr.")
                .givenName("James")
                .familyName("Wilson")
                .roles(List.of("USER"))
                .build();
        final MmUser spotter = mmUserBuilder.builder()
                .userId("7")
                .username("spotter")
                .password("N78S9x9ft$HFGMrf")
                .companyId("3")
                .honorific("Col.")
                .givenName("Sherman T.")
                .familyName("Potter")
                .roles(List.of("ADMIN"))
                .build();
        final MmUser bpierce = mmUserBuilder.builder()
                .userId("8")
                .username("bpierce")
                .password("C$At$BBGL5yLP&AM")
                .companyId("3")
                .honorific("Cap.")
                .givenName("\"Hawkeye\"")
                .familyName("Pierce")
                .roles(List.of("USER"))
                .build();
        final MmUser woreilly = mmUserBuilder.builder()
                .userId("9")
                .username("woreilly")
                .password("so#KKNYiqe!F5!Ph")
                .companyId("3")
                .honorific("Priv.")
                .givenName("Walter \"Radar\"")
                .familyName("O'Reilly")
                .roles(List.of("USER"))
                .build();

        this.userInfo = Map.of(
                "rtrenneman", rtrenneman,
                "mmoss", mmoss,
                "jbarber", jbarber,
                "lcuddy", lcuddy,
                "ghouse", ghouse,
                "jwilson", jwilson,
                "spotter", spotter,
                "bpierce", bpierce,
                "woreilly", woreilly
        );

        final var mmSupport = new LoginCompanies("1", "Medical Models IT Support Crowd",
                                                 List.of(rtrenneman.loginUser(),
                                                         mmoss.loginUser(),
                                                         jbarber.loginUser())
        );
        final var house = new LoginCompanies("2", "House MD Centre for Superheroes",
                                             List.of(lcuddy.loginUser(),
                                                     ghouse.loginUser(),
                                                     jwilson.loginUser())
        );
        final var mash = new LoginCompanies("3", "Old Action Heroes M*A*S*H",
                                            List.of(spotter.loginUser(),
                                                    bpierce.loginUser(),
                                                    woreilly.loginUser())
        );

        this.companyInfo = List.of(mmSupport, house, mash);
    }

    public Optional<MmUser> findByUsername(final String username) {
        return Optional.ofNullable(this.userInfo.get(username));
    }

    public List<UserDetails> getUserDetails() {
        return userInfo.values().stream().map(MmUser::userDetails).toList();
    }

    public List<LoginCompanies> getLoginCompanies() {
        return companyInfo;
    }
}
