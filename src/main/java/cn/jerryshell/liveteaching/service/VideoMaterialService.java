package cn.jerryshell.liveteaching.service;

import cn.jerryshell.liveteaching.config.VideoConfig;
import cn.jerryshell.liveteaching.dao.VideoMaterialDao;
import cn.jerryshell.liveteaching.model.VideoMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class VideoMaterialService {
    @Autowired
    private VideoConfig videoConfig;
    @Autowired
    private VideoMaterialDao videoMaterialDao;

    public VideoMaterial findById(String id) {
        return videoMaterialDao.findById(id).orElse(null);
    }

    public VideoMaterial findByVideoId(String videoId) {
        return videoMaterialDao.findByVideoId(videoId).orElse(null);
    }

    public void save(VideoMaterial videoMaterial) {
        videoMaterialDao.save(videoMaterial);
    }

    public void upload(MultipartFile multipartFile, String filename) {
        File file = new File(videoConfig.getMaterialFilePath() + "/" + filename);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(String materialId) {
        VideoMaterial videoMaterial = videoMaterialDao.findById(materialId).orElse(null);
        if (videoMaterial == null) {
            return;
        }
        String filepath = videoConfig.getMaterialFilePath() + "/" + materialId + "." + videoMaterial.getFileType();
        File file = new File(filepath);
        file.delete();
        videoMaterialDao.deleteById(materialId);
    }
}
