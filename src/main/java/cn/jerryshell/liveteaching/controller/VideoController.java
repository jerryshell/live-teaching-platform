package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.config.UploadVideoConfig;
import cn.jerryshell.liveteaching.dao.CourseDao;
import cn.jerryshell.liveteaching.dao.VideoDao;
import cn.jerryshell.liveteaching.model.Course;
import cn.jerryshell.liveteaching.model.Video;
import cn.jerryshell.liveteaching.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@Controller
public class VideoController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private VideoService videoService;
    private UploadVideoConfig uploadVideoConfig;
    private VideoDao videoDao;
    private CourseDao courseDao;

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }

    @Autowired
    public void setUploadVideoConfig(UploadVideoConfig uploadVideoConfig) {
        this.uploadVideoConfig = uploadVideoConfig;
    }

    @Autowired
    public void setVideoDao(VideoDao videoDao) {
        this.videoDao = videoDao;
    }

    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @GetMapping("/video")
    public String list(Model model) {
        List<Course> courseList = courseDao.findAll();
        model.addAttribute("courseList", courseList);
        List<Video> videoList = videoDao.findAll();
        model.addAttribute("videoList", videoList);
        return "video-list";
    }

    @GetMapping("/video/course/{courseId}")
    public String listByCourseId(@PathVariable String courseId, Model model) {
        List<Course> courseList = courseDao.findAll();
        model.addAttribute("courseList", courseList);
        List<Video> videoList = videoDao.findByCourseId(courseId);
        model.addAttribute("videoList", videoList);
        return "video-list";
    }

    @PostMapping("/video/upload")
    public String upload(Video video,
                         @RequestParam("uploadFile") MultipartFile uploadFile,
                         HttpSession session) {
        String filename = uploadFile.getOriginalFilename();
        if (filename != null) {
            videoService.uploadVideo(uploadFile, filename);
        }
        video.setId(UUID.randomUUID().toString());
        video.setTeacherId(session.getAttribute("loginUserId").toString());
        logger.info(video.toString());
        return "redirect:/user";
    }

    @GetMapping("/video/{videoName}")
    public ModelAndView video(@PathVariable String videoName) {
        ModelAndView modelAndView = new ModelAndView("video.html");
        modelAndView.addObject("videoName", videoName);
        return modelAndView;
    }

    @GetMapping("/video/delete/{videoName}")
    public String deleteVideo(@PathVariable String videoName) {
        videoService.deleteVideo(videoName);
        return "redirect:/user";
    }

    @GetMapping("/video/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(new UrlResource("file://" + uploadVideoConfig.getFilepath() + "/" + filename));
    }

}
