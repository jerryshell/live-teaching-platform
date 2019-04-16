package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.dao.StudentDao;
import cn.jerryshell.liveteaching.dao.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private StudentDao studentDao;
    private TeacherDao teacherDao;

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Autowired
    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @GetMapping("/login")
    public String toLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("id") String id,
                        @RequestParam("password") String password,
                        @RequestParam("kind") String kind,
                        HttpSession session) {
        String loginResult = null;
        switch (kind) {
            case "student":
                loginResult = studentLogin(id, password);
                break;
            case "teacher":
                loginResult = teacherLogin(id, password);
        }
        if (loginResult == null) {
            return "/login";
        }
        session.setAttribute("loginUserId", id);
        session.setAttribute("loginUserKind", kind);
        return "redirect:/";
    }

    private String studentLogin(String id, String password) {
        return studentDao.findByIdAndPassword(id, password) == null ? null : "redirect:/";
    }

    private String teacherLogin(String id, String password) {
        return teacherDao.findByIdAndPassword(id, password) == null ? null : "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
