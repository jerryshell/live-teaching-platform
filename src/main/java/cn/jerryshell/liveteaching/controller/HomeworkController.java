package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.config.HomeworkConfig;
import cn.jerryshell.liveteaching.model.Homework;
import cn.jerryshell.liveteaching.model.Student;
import cn.jerryshell.liveteaching.service.HomeworkService;
import cn.jerryshell.liveteaching.service.StudentService;
import cn.jerryshell.liveteaching.service.VideoService;
import cn.jerryshell.liveteaching.util.Util;
import cn.jerryshell.liveteaching.vm.HomeworkViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeworkController {
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private HomeworkConfig homeworkConfig;
    @Autowired
    private StudentService studentService;
    @Autowired
    private VideoService videoService;

    @PostMapping("/homework")
    public String uploadHomework(
            @RequestParam("homeworkFile") MultipartFile homeworkFile,
            @RequestParam("videoId") String videoId,
            HttpSession session
    ) {
        String filename = homeworkFile.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            return "redirect:/video/" + videoId;
        }

        String loginUserId = session.getAttribute("loginUserId").toString();
        Student student = studentService.findById(loginUserId);
        if (student == null) {
            return "redirect:/video/" + videoId;
        }

        // 如果重复提交作业，则删除旧的作业
        Homework homeworkFromDB = homeworkService.findByStudentIdAndVideoId(loginUserId, videoId);
        if (homeworkFromDB != null) {
            homeworkService.deleteById(homeworkFromDB.getId());
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
        return "redirect:/video/" + videoId;
    }

    @GetMapping("/homework/download/{id}")
    public ResponseEntity<Resource> downloadHomeworkById(@PathVariable String id) throws MalformedURLException {
        Homework homework = homeworkService.findById(id);
        if (homework == null) {
            return ResponseEntity.notFound().build();
        }
        String filename = homework.getId() + "." + homework.getFileType();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(new UrlResource("file://" + homeworkConfig.getFilepath() + "/" + filename));
    }

    @DeleteMapping("/homework/{id}")
    public String deleteById(@PathVariable String id) {
        homeworkService.deleteById(id);
        return "redirect:/user/homework";
    }

    @GetMapping("/homework/listByVideoId/{videoId}")
    public String toHomeworkListByVideoIdPage(@PathVariable String videoId, Model model) {
        List<Homework> homeworkList = homeworkService.listByVideoId(videoId);
        List<HomeworkViewModel> homeworkVMList = HomeworkViewModel.loadFromHomeworkList(
                homeworkList,
                studentService,
                videoService
        );
        model.addAttribute("homeworkVMList", homeworkVMList);
        return "homework-list";
    }

    @PostMapping("/homework/score/{id}")
    public String updateHomeworkScore(
            @PathVariable String id,
            @RequestParam("score") Double score
    ) {
        Homework homework = homeworkService.findById(id);
        if (homework == null) {
            return null;
        }
        homework.setScore(score);
        homeworkService.update(homework);
        return "redirect:/homework/listByVideoId/" + homework.getVideoId();
    }

    @PostMapping("/homework/comment/{id}")
    public String updateHomeworkComment(
            @PathVariable String id,
            @RequestParam("comment") String comment
    ) {
        Homework homework = homeworkService.findById(id);
        if (homework == null) {
            return null;
        }
        homework.setComment(comment);
        homeworkService.update(homework);
        return "redirect:/homework/listByVideoId/" + homework.getVideoId();
    }
}
