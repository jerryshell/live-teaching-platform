package cn.jerryshell.liveteaching.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security")
public class SecurityConfig {
    private String md5salt;

    public String getMd5salt() {
        return md5salt;
    }

    public void setMd5salt(String md5salt) {
        this.md5salt = md5salt;
    }
}
