package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.config.LiveConfig;
import cn.jerryshell.liveteaching.model.*;
import cn.jerryshell.liveteaching.service.*;
import cn.jerryshell.liveteaching.vm.HomeworkViewModel;
import cn.jerryshell.liveteaching.vm.LiveViewModel;
import cn.jerryshell.liveteaching.vm.VideoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private LiveService liveService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private MajorService majorDao;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LiveConfig liveConfig;
    @Autowired
    private StudentService studentService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private LiveMaterialService liveMaterialService;
    @Autowired
    private VideoMaterialService videoMaterialService;
    @Autowired
    private HomeworkService homeworkService;

    @GetMapping("/user")
    public String toUserPage(HttpSession session, Model model) {
        String loginUserKind = session.getAttribute("loginUserKind").toString();
        String url = "redirect:/";
        switch (loginUserKind) {
            case "student":
                String studentId = session.getAttribute("loginUserId").toString();
                url = toStudentUserPage(studentId, model);
                break;
            case "teacher":
                String teacherId = session.getAttribute("loginUserId").toString();
                url = toTeacherUserPage(teacherId, model);
                break;
        }
        return url;
    }

    private String toStudentUserPage(String studentId, Model model) {
        Student student = studentService.findById(studentId);
        if (student == null) {
            return "redirect:/";
        }
        // 过滤时间和专业和年级
        Date yesterday = new Date(System.currentTimeMillis() - 86400000);
        List<Live> liveList = liveService.findByDateAfterAndMajorIdAndGrade(yesterday, student.getMajorId(), student.getGrade());
        List<LiveViewModel> liveVMList = LiveViewModel.loadFromLiveList(liveConfig.getIp(), liveList, teacherService, courseService, majorDao, liveMaterialService);
        model.addAttribute("liveVMList", liveVMList);
        model.addAttribute("active", "live-list");
        return "user-student";
    }

    private String toTeacherUserPage(String teacherId, Model model) {
        List<Live> liveList = liveService.findByTeacherId(teacherId);
        List<LiveViewModel> liveVMList = LiveViewModel.loadFromLiveList(liveConfig.getIp(), liveList, teacherService, courseService, majorDao, liveMaterialService);
        model.addAttribute("liveVMList", liveVMList);
        model.addAttribute("active", "live-list");
        return "user-teacher";
    }

    @GetMapping("/user/create-live")
    public String toCreateLivePage(Model model) {
        List<Course> courseList = courseService.findAll();
        List<Major> majorList = majorDao.findAll();
        model.addAttribute("courseList", courseList);
        model.addAttribute("majorList", majorList);
        model.addAttribute("active", "create-live");
        return "user-teacher-create-live";
    }

    @GetMapping("/user/upload-video")
    public String toUploadVideoPage(Model model) {
        List<Course> courseList = courseService.findAll();
        List<Major> majorList = majorDao.findAll();
        model.addAttribute("courseList", courseList);
        model.addAttribute("majorList", majorList);
        model.addAttribute("active", "upload-video");
        return "user-teacher-upload-video";
    }

    @GetMapping("/user/video-list")
    public String toVideoListPage(HttpSession session, Model model) {
        String loginUserId = session.getAttribute("loginUserId").toString();
        List<Video> videoList = videoService.findByTeacherId(loginUserId);
        List<VideoViewModel> videoVMlList = VideoViewModel.loadFromVideoList(
                videoList,
                teacherService,
                courseService,
                videoMaterialService
        );
        model.addAttribute("videoVMList", videoVMlList);
        model.addAttribute("active", "video-list");
        return "user-teacher-video-list";
    }

    @GetMapping("/user/homework")
    public String toStudentHomeworkListPage(HttpSession session, Model model) {
        String loginUserKind = session.getAttribute("loginUserKind").toString();
        if (!"student".equals(loginUserKind)) {
            return "redirect:/user";
        }
        String studentId = session.getAttribute("loginUserId").toString();
        List<Homework> homeworkList = homeworkService.listByStudentId(studentId);
        List<HomeworkViewModel> homeworkVMList = HomeworkViewModel.loadFromHomeworkList(
                homeworkList,
                studentService,
                videoService
        );
        model.addAttribute("homeworkVMList", homeworkVMList);
        model.addAttribute("active", "homework");
        return "user-student-homework-list";
    }
}
