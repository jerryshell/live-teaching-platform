package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.model.Student;
import cn.jerryshell.liveteaching.model.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @GetMapping("/register/{kind}")
    public String toRegisterPage(@PathVariable String kind) {
        return "register-" + kind;
    }

    @PostMapping("/register/student")
    public String doRegisterStudent(Student student) {
        System.out.println(student);
        return "redirect:/login";
    }

    @PostMapping("/register/teacher")
    public String doRegisterTeacher(Teacher teacher) {
        System.out.println(teacher);
        return "redirect:/login";
    }
}
