package org.jono.medicalmodelsauthorizationserver.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jono.medicalmodelsauthorizationserver.model.LoginCompanies;
import org.jono.medicalmodelsauthorizationserver.model.MmUser;
import org.jono.medicalmodelsauthorizationserver.model.MmUserBuilder;
import org.jono.medicalmodelsauthorizationserver.utils.ResourceUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoRepository {

    private final Map<String, MmUser> userInfo = new HashMap<>();
    private final List<LoginCompanies> companyInfo = new ArrayList<>();
    private final Map<String, String> companyLogoMap = new HashMap<>();

    public MmUser findByUsername(final String username) {
        return this.userInfo.get(username);
    }

    public Collection<UserDetails> getUserDetails() {
        return userInfo.values().stream().map(MmUser::getUserDetails).toList();
    }

    public List<LoginCompanies> getLoginCompanies() {
        return companyInfo;
    }

    public String getBase64Logo(final String companyId) {
        return companyLogoMap.get(companyId);
    }

    public String getBase64Picture(final String username) {
        return userInfo.get(username).getBase64Picture();
    }

    public UserInfoRepository(final MmUserBuilder mmUserBuilder) throws IOException {
        final MmUser rtrenneman = mmUserBuilder.builder()
                .userId("1")
                .username("rtrenneman")
                .password("N78S9x9ft$HFGMrf")
                .companyId("1")
                .honorific("Mr.")
                .givenName("Roy")
                .familyName("Trenneman")
                .roles(List.of("SUPPORT"))
                .base64Picture(getAvatar("images/it-crowd-roy-1.png"))
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
                .base64Picture(getAvatar("images/it-crowd-moss-1.png"))
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
                .base64Picture(getAvatar("images/it-crowd-jen-1.png"))
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
                .base64Picture(getAvatar("images/cutty-profile-picture.png"))
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
                .base64Picture(getAvatar("images/house-md-image-2.png"))
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
                .base64Picture(getAvatar("images/house-wilson-image-1.png"))
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
                .base64Picture(getAvatar("images/mash-potter-1.png"))
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
                .base64Picture(getAvatar("images/mash-hawkeye-1.png"))
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
                .base64Picture(getAvatar("images/mash-radar-1.png"))
                .build();

        this.userInfo.put("rtrenneman", rtrenneman);
        this.userInfo.put("mmoss", mmoss);
        this.userInfo.put("jbarber", jbarber);
        this.userInfo.put("lcuddy", lcuddy);
        this.userInfo.put("ghouse", ghouse);
        this.userInfo.put("jwilson", jwilson);
        this.userInfo.put("spotter", spotter);
        this.userInfo.put("bpierce", bpierce);
        this.userInfo.put("woreilly", woreilly);

        final var mmSupport = new LoginCompanies("1", "Medical Models IT Support Crowd",
                                                 List.of(rtrenneman.getLoginUser(),
                                                              mmoss.getLoginUser(),
                                                              jbarber.getLoginUser())
        );
        final var house = new LoginCompanies("2", "House MD Centre for Superheroes",
                                             List.of(lcuddy.getLoginUser(),
                                                          ghouse.getLoginUser(),
                                                          jwilson.getLoginUser())
        );
        final var mash = new LoginCompanies("3", "Old Action Heroes M*A*S*H",
                                              List.of(spotter.getLoginUser(),
                                                           bpierce.getLoginUser(),
                                                           woreilly.getLoginUser())
        );
        this.companyInfo.add(mmSupport);
        this.companyInfo.add(house);
        this.companyInfo.add(mash);

        this.companyLogoMap.put("1", getAvatar("images/medical-models-company-logo-1.png"));
        this.companyLogoMap.put("2", getAvatar("images/house-md-logo-1.png"));
        this.companyLogoMap.put("3", getAvatar("images/mash-tv-logo-1.jpg"));
    }

    private String getAvatar(final String path) throws IOException {
        return ResourceUtils.loadBase64ResourceFile(path);
    }
}
