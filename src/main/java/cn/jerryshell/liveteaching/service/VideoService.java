package cn.jerryshell.liveteaching.service;

import cn.jerryshell.liveteaching.config.UploadVideoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class VideoService {
    private List<String> videoNameList = new LinkedList<>();
    private UploadVideoConfig uploadVideoConfig;

    @Autowired
    public void setUploadVideoConfig(UploadVideoConfig uploadVideoConfig) {
        this.uploadVideoConfig = uploadVideoConfig;
    }

    public boolean uploadVideo(MultipartFile uploadFile, String filename) {
        File file = new File("/home/jerry/Videos/" + filename);
        try {
            uploadFile.transferTo(file);
            videoNameList.add(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteVideo(String videoName) {
        videoNameList.remove(videoName);
        File file = new File(uploadVideoConfig.getFilepath() + videoName);
        return file.delete();
    }
}
