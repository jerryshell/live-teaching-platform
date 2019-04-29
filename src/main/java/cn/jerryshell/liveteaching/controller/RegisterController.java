package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.model.Major;
import cn.jerryshell.liveteaching.model.Student;
import cn.jerryshell.liveteaching.model.Teacher;
import cn.jerryshell.liveteaching.service.MajorService;
import cn.jerryshell.liveteaching.service.StudentService;
import cn.jerryshell.liveteaching.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MajorService majorService;

    @GetMapping("/register/{kind}")
    public String toRegisterPage(@PathVariable String kind, Model model) {
        switch (kind) {
            case "teacher":
                return "register-teacher";
            case "student":
                List<Major> majorList = majorService.findAll();
                model.addAttribute("majorList", majorList);
                return "register-student";
            default:
                return "redirect:/";
        }
    }

    @PostMapping("/register/student")
    public String registerStudent(Student student, Model model) {
        Student studentFromDB = studentService.findById(student.getId());
        if (studentFromDB != null) {
            model.addAttribute("message", "该学号已经被注册");
            return "/register-student";
        }
        studentService.save(student);
        return "redirect:/login";
    }

    @PostMapping("/register/teacher")
    public String registerTeacher(Teacher teacher, Model model) {
        Teacher teacherFromDB = teacherService.findById(teacher.getId());
        if (teacherFromDB != null) {
            model.addAttribute("message", "该工号已经被注册");
            return "/register-teacher";
        }
        teacherService.save(teacher);
        return "redirect:/login";
    }
}
