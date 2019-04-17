package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.config.LiveServerConfig;
import cn.jerryshell.liveteaching.dao.*;
import cn.jerryshell.liveteaching.model.Course;
import cn.jerryshell.liveteaching.model.Live;
import cn.jerryshell.liveteaching.model.Major;
import cn.jerryshell.liveteaching.model.Student;
import cn.jerryshell.liveteaching.vm.LiveViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    private LiveDao liveDao;
    private CourseDao courseDao;
    private MajorDao majorDao;
    private TeacherDao teacherDao;
    private LiveServerConfig liveServerConfig;
    private StudentDao studentDao;

    @Autowired
    public void setLiveDao(LiveDao liveDao) {
        this.liveDao = liveDao;
    }

    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Autowired
    public void setMajorDao(MajorDao majorDao) {
        this.majorDao = majorDao;
    }

    @Autowired
    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Autowired
    public void setLiveServerConfig(LiveServerConfig liveServerConfig) {
        this.liveServerConfig = liveServerConfig;
    }

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

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
        Student student = studentDao.findById(studentId).orElse(null);
        if (student == null) {
            return "redirect:/";
        }
        // 过滤时间和专业和年级
        Date lastDayDate = new Date(System.currentTimeMillis() - 86400000);
        List<Live> liveList = liveDao.findByDateAfterAndMajorIdAndGrade(lastDayDate, student.getMajorId(), student.getGrade());
        List<LiveViewModel> liveVMList = LiveViewModel.loadFromLiveList(liveServerConfig.getIp(), liveList, teacherDao, courseDao, majorDao);
        model.addAttribute("liveViewModelList", liveVMList);
        return "user-student";
    }

    private String toTeacherUserPage(String teacherId, Model model) {
        List<Live> liveList = liveDao.findByTeacherId(teacherId);
        List<LiveViewModel> liveVMList = LiveViewModel.loadFromLiveList(liveServerConfig.getIp(), liveList, teacherDao, courseDao, majorDao);
        model.addAttribute("liveViewModelList", liveVMList);
        return "user-teacher";
    }

    @GetMapping("/user/create-live")
    public String toCreateLivePage(Model model) {
        List<Course> courseList = courseDao.findAll();
        List<Major> majorList = majorDao.findAll();
        model.addAttribute("courseList", courseList);
        model.addAttribute("majorList", majorList);
        return "user-teacher-create-live";
    }

    @GetMapping("/user/upload-video")
    public String toUploadVideo(Model model) {
        List<Course> courseList = courseDao.findAll();
        List<Major> majorList = majorDao.findAll();
        model.addAttribute("courseList", courseList);
        model.addAttribute("majorList", majorList);
        return "user-teacher-upload-video";
    }
}
