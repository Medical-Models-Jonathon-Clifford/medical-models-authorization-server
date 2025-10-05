package org.jono.medicalmodelsauthorizationserver.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldDisplayPageTitle() throws Exception {
        final String html = this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        final Document document = Jsoup.parse(html);
        final Elements title = document.select("title");

        assertThat(title.text()).isEqualTo("Medical Models - Login");
    }

    @Test
    void shouldDisplayThreeCompaniesWithThreeUsersEach() throws Exception {
        final String html = this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        final Document document = Jsoup.parse(html);
        final Elements companyDivs = document.select("div.company");

        assertThat(companyDivs.size()).isEqualTo(3);

        final var expectedCompanyNames = List.of(
                "Medical Models IT Support Crowd",
                "House MD Centre for Superheroes",
                "Old Action Heroes M*A*S*H"
        );
        for (int i = 0; i < companyDivs.size(); i++) {
            final Element companyDiv = companyDivs.get(i);
            final String companyName = companyDiv.select("div.company-name p").text();
            assertThat(companyName).isEqualTo(expectedCompanyNames.get(i));

            final Elements userDivs = companyDiv.select("div.user-box");
            assertThat(userDivs.size()).isEqualTo(3);
        }
    }

    @Test
    public void formLoginWithGoodCreds() throws Exception {
        this.mockMvc.perform(formLogin().user("rtrenneman").password("N78S9x9ft$HFGMrf"))
                .andExpect(status().is(302))
                .andExpect(header().string("Location", "/"));
    }

    @Test
    public void formLoginWithBadCreds() throws Exception {
        this.mockMvc.perform(formLogin().user("rtrenneman").password("12345"))
                .andExpect(status().is(302))
                .andExpect(header().string("Location", "/login?error"));
    }
}
