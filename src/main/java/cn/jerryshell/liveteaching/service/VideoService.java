package cn.jerryshell.liveteaching.service;

import cn.jerryshell.liveteaching.config.UploadVideoConfig;
import cn.jerryshell.liveteaching.dao.VideoDao;
import cn.jerryshell.liveteaching.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class VideoService {
    private UploadVideoConfig uploadVideoConfig;
    private VideoDao videoDao;

    @Autowired
    public void setVideoDao(VideoDao videoDao) {
        this.videoDao = videoDao;
    }

    @Autowired
    public void setUploadVideoConfig(UploadVideoConfig uploadVideoConfig) {
        this.uploadVideoConfig = uploadVideoConfig;
    }

    public void uploadVideo(MultipartFile uploadFile, String filename) {
        File file = new File("/home/jerry/Videos/" + filename);
        try {
            uploadFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteVideoById(String videoId) {
        Video video = videoDao.findById(videoId).orElse(null);
        if (video == null) {
            return;
        }
        String filepath = uploadVideoConfig.getFilepath() + "/" + video.getId() + "." + video.getFileType();
        File file = new File(filepath);
        file.delete();
        videoDao.deleteById(video.getId());
    }
}
