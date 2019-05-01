package cn.jerryshell.liveteaching;

import cn.jerryshell.liveteaching.config.HomeworkConfig;
import cn.jerryshell.liveteaching.config.LiveConfig;
import cn.jerryshell.liveteaching.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({
        LiveConfig.class,
        SecurityConfig.class,
        HomeworkConfig.class
})
@SpringBootApplication
public class LiveTeachingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveTeachingApplication.class, args);
    }

}
