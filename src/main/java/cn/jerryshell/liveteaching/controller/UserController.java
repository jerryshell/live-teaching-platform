package cn.jerryshell.liveteaching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @GetMapping("/user")
    public String toUserPage(HttpSession session) {
        String loginUserKind = (String) session.getAttribute("loginUserKind");
        return "user-" + loginUserKind;
    }

    @GetMapping("/user/create-live")
    public String toCreateLivePage() {
        return "user-teacher-create-live";
    }

    @GetMapping("/user/upload-video")
    public String toUploadVideo() {
        return "user-teacher-upload-video";
    }
}
