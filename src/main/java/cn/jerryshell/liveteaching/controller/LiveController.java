package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.config.LiveServerConfig;
import cn.jerryshell.liveteaching.dao.CourseDao;
import cn.jerryshell.liveteaching.dao.LiveDao;
import cn.jerryshell.liveteaching.dao.MajorDao;
import cn.jerryshell.liveteaching.dao.TeacherDao;
import cn.jerryshell.liveteaching.model.Live;
import cn.jerryshell.liveteaching.vm.LiveViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class LiveController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private LiveServerConfig liveServerConfig;
    private LiveDao liveDao;
    private TeacherDao teacherDao;
    private CourseDao courseDao;
    private MajorDao majorDao;

    @Autowired
    public void setLiveServerConfig(LiveServerConfig liveServerConfig) {
        this.liveServerConfig = liveServerConfig;
    }

    @Autowired
    public void setLiveDao(LiveDao liveDao) {
        this.liveDao = liveDao;
    }

    @Autowired
    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Autowired
    public void setMajorDao(MajorDao majorDao) {
        this.majorDao = majorDao;
    }

    @GetMapping("/live")
    public String liveList(Model model) {
        List<Live> liveList = liveDao.findAll();
        List<LiveViewModel> liveViewModelList = new ArrayList<>(liveList.size());
        for (Live live : liveList) {
            LiveViewModel liveViewModel = LiveViewModel.loadFromModel(live, teacherDao, courseDao, majorDao);
            liveViewModelList.add(liveViewModel);
        }
        logger.debug(liveViewModelList.toString());
        model.addAttribute("liveViewModel", liveViewModelList);
        model.addAttribute("liveList", liveList);
        return "live-list";
    }

    @GetMapping("/live/{teacherId}/{roomName}")
    public String live(@PathVariable String teacherId,
                       @PathVariable String roomName,
                       Model model) {
        model.addAttribute("ip", liveServerConfig.getIp());
        model.addAttribute("port", liveServerConfig.getPort());
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("roomName", roomName);
        return "live-watching";
    }

    @PostMapping("/live")
    public String createLive(Live live, HttpSession session) {
        live.setId(UUID.randomUUID().toString());
        live.setTeacherId(session.getAttribute("loginUserId").toString());
        logger.info(live.toString());
        liveDao.save(live);
        return "redirect:/user";
    }

    @DeleteMapping("/live/{id}")
    public String deleteLiveById(@PathVariable String id) {
        liveDao.deleteById(id);
        return "redirect:/user";
    }
}
