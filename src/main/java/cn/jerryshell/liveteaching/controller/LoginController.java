package cn.jerryshell.liveteaching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String toLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("id") String id,
                        @RequestParam("password") String password,
                        @RequestParam("kind") String kind,
                        HttpSession session) {
        System.out.println(id);
        System.out.println(password);
        System.out.println(kind);
        if (StringUtils.isEmpty(id) || !"123".equals(password)) {
            return "redirect:/login";
        }
        session.setAttribute("loginUserId", id);
        session.setAttribute("loginUserKind", kind);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
