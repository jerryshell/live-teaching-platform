package cn.jerryshell.liveteaching.config;

import cn.jerryshell.liveteaching.dao.LiveDao;
import cn.jerryshell.liveteaching.dao.StudentDao;
import cn.jerryshell.liveteaching.dao.TeacherDao;
import cn.jerryshell.liveteaching.interceptor.LiveCountInterceptor;
import cn.jerryshell.liveteaching.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig {
    private LiveDao liveDao;
    private StudentDao studentDao;
    private TeacherDao teacherDao;

    @Autowired
    public void setLiveDao(LiveDao liveDao) {
        this.liveDao = liveDao;
    }

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Autowired
    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns("/", "/login", "/register/**", "/css/**", "/js/**", "/img/**");
                registry.addInterceptor(new LiveCountInterceptor(liveDao, studentDao, teacherDao)).addPathPatterns("/**");
            }
        };
    }
}
