package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.config.LiveConfig;
import cn.jerryshell.liveteaching.model.Live;
import cn.jerryshell.liveteaching.model.LiveMaterial;
import cn.jerryshell.liveteaching.service.*;
import cn.jerryshell.liveteaching.vm.LiveViewModel;
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
import java.util.Objects;
import java.util.UUID;

@Controller
public class LiveController {
    @Autowired
    private LiveService liveService;
    @Autowired
    private LiveConfig liveConfig;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private LiveMaterialService liveMaterialService;

    @GetMapping("/live")
    public String toLiveListPage(Model model) {
        List<Live> liveList = liveService.findAll();
        List<LiveViewModel> liveVMList = LiveViewModel.loadFromLiveList(liveConfig.getIp(), liveList, teacherService, courseService, majorService, liveMaterialService);
        model.addAttribute("liveVMList", liveVMList);
        return "live-list";
    }

    @GetMapping("/live/{teacherId}/{liveId}")
    public String toLiveWatchingPage(
            @PathVariable String teacherId,
            @PathVariable String liveId,
            Model model
    ) {
        Live live = liveService.findById(liveId);
        if (live == null) {
            return "redirect:/live";
        }

        String ip = liveConfig.getIp();
        String port = liveConfig.getPort();
        String liveSource = "http://" + ip + ":" + port + "/live/" + teacherId + "/" + liveId + ".m3u8";

        LiveMaterial liveMaterial = liveMaterialService.findByLiveId(liveId);
        if (liveMaterial != null) {
            model.addAttribute("liveMaterial", liveMaterial);
        }

        model.addAttribute("liveId", liveId);
        model.addAttribute("liveSource", liveSource);
        return "live-watching";
    }

    @PostMapping("/live")
    public String createLive(
            Live live,
            @RequestParam("liveMaterial") MultipartFile file,
            HttpSession session
    ) {
        live.setId(UUID.randomUUID().toString());
        live.setTeacherId(session.getAttribute("loginUserId").toString());
        liveService.save(live);

        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            return "redirect:/user";
        }

        LiveMaterial liveMaterial = new LiveMaterial();
        liveMaterial.setId(UUID.randomUUID().toString());
        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String fileType = split[split.length - 1];
        liveMaterial.setFileType(fileType);
        liveMaterial.setLiveId(live.getId());

        liveMaterialService.save(liveMaterial);
        liveMaterialService.uploadFile(file, liveMaterial.getId() + "." + fileType);
        return "redirect:/user";
    }

    @DeleteMapping("/live/{id}")
    public String deleteLiveById(@PathVariable String id) {
        liveService.deleteById(id);
        LiveMaterial liveMaterial = liveMaterialService.findByLiveId(id);
        if (liveMaterial != null) {
            liveMaterialService.deleteById(liveMaterial.getId());
        }
        return "redirect:/user";
    }

    @GetMapping("/live/material/{materialId}")
    public ResponseEntity<Resource> downloadLiveMaterial(@PathVariable("materialId") String materialId) throws MalformedURLException {
        LiveMaterial liveMaterial = liveMaterialService.findById(materialId);
        if (liveMaterial == null) {
            return ResponseEntity.notFound().build();
        }
        String filename = liveMaterial.getId() + "." + liveMaterial.getFileType();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(new UrlResource("file://" + liveConfig.getMaterialFilePath() + "/" + filename));
    }
}
