package com.studentcloud.dbaccess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginError(
            @RequestParam(name = "error", required = false) Object error,
            @RequestParam(name = "registered", required = false) Object registered,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("loginError",
                    "Неправильные имя пользователя или пароль");
        }

        if (registered != null) {
            model.addAttribute("activateYourAccount",
                    "На почту выслано письмо со ссылкой для подтверждения регистрации. Проверьте почту");
        }

        return "login";
    }
}
