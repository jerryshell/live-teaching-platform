package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.model.Homework;
import cn.jerryshell.liveteaching.model.Student;
import cn.jerryshell.liveteaching.service.HomeworkService;
import cn.jerryshell.liveteaching.service.StudentService;
import cn.jerryshell.liveteaching.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class HomeworkController {
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private StudentService studentService;

    @PostMapping("/homework")
    public String uploadHomework(
            @RequestParam("homeworkFile") MultipartFile homeworkFile,
            @RequestParam("videoId") String videoId,
            HttpSession session
    ) {
        String filename = homeworkFile.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            return "redirect:/";
        }

        String loginUserId = session.getAttribute("loginUserId").toString();
        Student student = studentService.findById(loginUserId);
        if (student == null) {
            return "redirect:/";
        }

        Homework homework = new Homework();
        homework.setId(UUID.randomUUID().toString());
        homework.setComment("正在等待老师批改...");
        homework.setScore(.0);
        homework.setStudentId(loginUserId);
        homework.setVideoId(videoId);
        homework.setFileType(Util.getFileTypeByFilename(filename));

        homeworkService.save(
                homework,
                homeworkFile,
                String.format("%s.%s", homework.getId(), homework.getFileType())
        );
        return "redirect:/";
    }
}
