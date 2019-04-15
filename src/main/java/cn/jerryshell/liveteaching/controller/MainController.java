package cn.jerryshell.liveteaching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(HttpSession session) {
        Object loginUserId = session.getAttribute("loginUserId");
        if (loginUserId == null) {
            return "redirect:/login";
        }
        return "main";
    }
}
