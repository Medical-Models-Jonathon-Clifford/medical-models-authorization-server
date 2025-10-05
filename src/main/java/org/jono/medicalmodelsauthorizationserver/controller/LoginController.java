package org.jono.medicalmodelsauthorizationserver.controller;

import org.jono.medicalmodelsauthorizationserver.service.MmUserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public final class LoginController {

    private final MmUserInfoService mmUserInfoService;

    LoginController(final MmUserInfoService mmUserInfoService) {
        this.mmUserInfoService = mmUserInfoService;
    }

    @GetMapping("/login")
    String login(final Model model) {
        model.addAttribute("loginCompanies", mmUserInfoService.getLoginCompanies());
        return "login.html";
    }
}
