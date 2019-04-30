package cn.jerryshell.liveteaching.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "live")
public class LiveServerConfig {
    private String ip;
    private String port;
    private String liveMaterialFilePath;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLiveMaterialFilePath() {
        return liveMaterialFilePath;
    }

    public void setLiveMaterialFilePath(String liveMaterialFilePath) {
        this.liveMaterialFilePath = liveMaterialFilePath;
    }
}
