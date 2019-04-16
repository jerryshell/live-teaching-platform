package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.config.LiveServerConfig;
import cn.jerryshell.liveteaching.dao.LiveDao;
import cn.jerryshell.liveteaching.model.Live;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
public class LiveController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private LiveServerConfig liveServerConfig;
    private LiveDao liveDao;

    @Autowired
    public void setLiveServerConfig(LiveServerConfig liveServerConfig) {
        this.liveServerConfig = liveServerConfig;
    }

    @Autowired
    public void setLiveDao(LiveDao liveDao) {
        this.liveDao = liveDao;
    }

    @GetMapping("/live")
    public String liveList(Model model) {
        List<Live> liveList = liveDao.findAll();
        model.addAttribute("liveList", liveList);
        return "live-list";
    }

    @GetMapping("/live/{roomName}")
    public ModelAndView live(@PathVariable String roomName) {
        ModelAndView modelAndView = new ModelAndView("live.html");
        modelAndView.addObject("ip", liveServerConfig.getIp());
        modelAndView.addObject("port", liveServerConfig.getPort());
        modelAndView.addObject("roomName", roomName);
        return modelAndView;
    }

    @PostMapping("/live")
    public String createLive(Live live, HttpSession session) {
        live.setId(UUID.randomUUID().toString());
        live.setTeacherId(session.getAttribute("loginUserId").toString());
        logger.info(live.toString());
        liveDao.save(live);
        return "redirect:/user";
    }
}
