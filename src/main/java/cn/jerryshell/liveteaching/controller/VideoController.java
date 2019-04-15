package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.net.MalformedURLException;

@Controller
@RequestMapping("/video")
public class VideoController {

    private VideoService videoService;

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public String index() {
        return "video-list";
    }

    @PostMapping("/upload")
    public String upload(MultipartFile uploadFile) {
        String filename = uploadFile.getOriginalFilename();
        if (filename == null) {
            return "redirect:/video";
        }
        videoService.uploadVideo(uploadFile, filename);
        return "redirect:/video";
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
        return "redirect:/video";
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(new UrlResource("file:///home/jerry/Videos/" + filename));
    }

}
