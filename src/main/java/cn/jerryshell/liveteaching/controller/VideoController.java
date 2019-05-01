package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.config.VideoConfig;
import cn.jerryshell.liveteaching.model.Course;
import cn.jerryshell.liveteaching.model.Video;
import cn.jerryshell.liveteaching.service.CourseService;
import cn.jerryshell.liveteaching.service.TeacherService;
import cn.jerryshell.liveteaching.service.VideoService;
import cn.jerryshell.liveteaching.vm.VideoViewModel;
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
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoConfig videoConfig;
    @Autowired
    private CourseService courseDao;
    @Autowired
    private TeacherService teacherDao;

    @GetMapping("/video")
    public String toVideoListPage(Model model) {
        return toVideoListPage(model, videoService.findAll());
    }

    @GetMapping("/video/course/{courseId}")
    public String toVideoListByCourseIdPage(@PathVariable String courseId, Model model) {
        return toVideoListPage(model, videoService.findByCourseId(courseId));
    }

    private String toVideoListPage(Model model, List<Video> videoList) {
        List<Course> courseList = courseDao.findAll();
        model.addAttribute("courseList", courseList);
        List<VideoViewModel> videoVMList = VideoViewModel.loadFromVideoList(videoList, teacherDao, courseDao);
        model.addAttribute("videoVMList", videoVMList);
        return "video-list";
    }

    @PostMapping("/video/upload")
    public String upload(
            Video video,
            @RequestParam("uploadFile") MultipartFile uploadFile,
            HttpSession session
    ) {
        String filename = uploadFile.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            return "redirect:/user/upload-video";
        }
        // 文件后缀名
        String[] split = filename.split("\\.");
        String fileType = split[split.length - 1];
        // 只能解码 mp4 文件
        if (!"mp4".equals(fileType)) {
            return "redirect:/user/upload-video";
        }
        video.setId(UUID.randomUUID().toString());
        video.setTeacherId(session.getAttribute("loginUserId").toString());
        video.setFileType(fileType);
        videoService.uploadVideo(uploadFile, video.getId() + "." + video.getFileType());
        videoService.save(video);
        return "redirect:/user/video-list";
    }

    @GetMapping("/video/{videoId}")
    public String videoWatching(@PathVariable String videoId, Model model) {
        Video video = videoService.findById(videoId);
        if (video == null) {
            return "redirect:/video";
        }
        model.addAttribute("videoName", video.getId() + "." + video.getFileType());
        return "video-watching";
    }

    @DeleteMapping("/video/{videoId}")
    public String deleteVideo(@PathVariable String videoId) {
        videoService.deleteVideoById(videoId);
        return "redirect:/user/video-list";
    }

    @GetMapping("/video/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(new UrlResource("file://" + videoConfig.getFilepath() + "/" + filename));
    }

}
