package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.config.LiveServerConfig;
import cn.jerryshell.liveteaching.model.Live;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping("/live")
public class LiveController {

    private LiveServerConfig liveServerConfig;

    @Autowired
    public void setLiveServerConfig(LiveServerConfig liveServerConfig) {
        this.liveServerConfig = liveServerConfig;
    }

    @GetMapping
    public String liveList() {
        return "live-list";
    }

    @GetMapping("/{roomName}")
    public ModelAndView live(@PathVariable String roomName) {
        ModelAndView modelAndView = new ModelAndView("live.html");
        modelAndView.addObject("ip", liveServerConfig.getIp());
        modelAndView.addObject("port", liveServerConfig.getPort());
        modelAndView.addObject("roomName", roomName);
        return modelAndView;
    }

    @PostMapping
    public String createLive(Live live, HttpSession session) {
        live.setId(UUID.randomUUID().toString());
        live.setTeacherId(session.getAttribute("loginUserId").toString());
        System.out.println(live);
        return "redirect:/user";
    }
}
