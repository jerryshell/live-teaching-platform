package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.config.UploadVideoConfig;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.util.UUID;

@Controller
@RequestMapping("/video")
public class VideoController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private VideoService videoService;
    private UploadVideoConfig uploadVideoConfig;

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }

    @Autowired
    public void setUploadVideoConfig(UploadVideoConfig uploadVideoConfig) {
        this.uploadVideoConfig = uploadVideoConfig;
    }

    @GetMapping
    public String index() {
        return "video-list";
    }

    @PostMapping("/upload")
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

    @GetMapping("/{videoName}")
    public ModelAndView video(@PathVariable String videoName) {
        ModelAndView modelAndView = new ModelAndView("video.html");
        modelAndView.addObject("videoName", videoName);
        return modelAndView;
    }

    @GetMapping("/delete/{videoName}")
    public String deleteVideo(@PathVariable String videoName) {
        videoService.deleteVideo(videoName);
        return "redirect:/user";
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(new UrlResource("file://" + uploadVideoConfig.getFilepath() + "/" + filename));
    }

}
