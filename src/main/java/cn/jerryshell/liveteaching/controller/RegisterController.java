package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.dao.StudentDao;
import cn.jerryshell.liveteaching.dao.TeacherDao;
import cn.jerryshell.liveteaching.model.Student;
import cn.jerryshell.liveteaching.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private Logger logger = LoggerFactory.getLogger(getClass());
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

    @GetMapping("/register/{kind}")
    public String toRegisterPage(@PathVariable String kind) {
        return "register-" + kind;
    }

    @PostMapping("/register/student")
    public String doRegisterStudent(Student student) {
        logger.info(student.toString());
        Student studentFromDB = studentDao.findById(student.getId()).orElse(null);
        if (studentFromDB != null) {
            return "redirect:/register/student";
        }
        studentDao.save(student);
        return "redirect:/login";
    }

    @PostMapping("/register/teacher")
    public String doRegisterTeacher(Teacher teacher) {
        logger.info(teacher.toString());
        Teacher teacherFromDB = teacherDao.findById(teacher.getId()).orElse(null);
        if (teacherFromDB != null) {
            return "redirect:/register/teacher";
        }
        teacherDao.save(teacher);
        return "redirect:/login";
    }
}
