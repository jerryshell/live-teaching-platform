package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.dao.LiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private LiveDao liveDao;

    @Autowired
    public void setLiveDao(LiveDao liveDao) {
        this.liveDao = liveDao;
    }

    @GetMapping("/user")
    public String toUserPage(HttpSession session, Model model) {
        String loginUserKind = session.getAttribute("loginUserKind").toString();
        String url = "redirect:/";
        switch (loginUserKind) {
            case "student":
                url = toStudentUserPage();
                break;
            case "teacher":
                String teacherId = session.getAttribute("loginUserId").toString();
                url = toTeacherUserPage(teacherId, model);
                break;
        }
        return url;
    }

    private String toStudentUserPage() {
        return "user-student";
    }

    private String toTeacherUserPage(String teacherId, Model model) {
        model.addAttribute("liveList", liveDao.findByTeacherId(teacherId));
        return "user-teacher";
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
