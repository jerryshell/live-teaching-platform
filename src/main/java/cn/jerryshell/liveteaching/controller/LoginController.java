package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.model.Student;
import cn.jerryshell.liveteaching.model.Teacher;
import cn.jerryshell.liveteaching.service.StudentService;
import cn.jerryshell.liveteaching.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/login")
    public String toLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("id") String id,
            @RequestParam("password") String password,
            @RequestParam("kind") String kind,
            HttpSession session,
            Model model
    ) {
        boolean loginResult;
        switch (kind) {
            case "student":
                loginResult = studentLogin(id, password, session);
                break;
            case "teacher":
                loginResult = teacherLogin(id, password, session);
                break;
            default:
                return "redirect:/";
        }
        if (!loginResult) {
            model.addAttribute("message", "登录失败，用户名或密码错误");
            return "/login";
        }
        session.setAttribute("loginUserId", id);
        session.setAttribute("loginUserKind", kind);
        return "redirect:/";
    }

    private boolean studentLogin(String id, String password, HttpSession session) {
        Student student = studentService.findByIdAndPassword(id, password);
        if (student == null) {
            return false;
        }
        session.setAttribute("loginUserNickname", student.getNickname());
        return true;
    }

    private boolean teacherLogin(String id, String password, HttpSession session) {
        Teacher teacher = teacherService.findByIdAndPassword(id, password);
        if (teacher == null) {
            return false;
        }
        session.setAttribute("loginUserNickname", teacher.getNickname());
        return true;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
