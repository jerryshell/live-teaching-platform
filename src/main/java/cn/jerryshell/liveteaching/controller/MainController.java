package cn.jerryshell.liveteaching.controller;

import cn.jerryshell.liveteaching.dao.CarouselDao;
import cn.jerryshell.liveteaching.model.Carousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CarouselDao carouselDao;

    @GetMapping("/")
    public String main(Model model) {
        List<Carousel> carouselList = carouselDao.findAll();
        model.addAttribute("carouselList", carouselList);
        return "main";
    }

    @GetMapping("/teachers")
    public String toTeachersPage() {
        return "teachers";
    }
}
