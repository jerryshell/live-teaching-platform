package cn.jerryshell.liveteaching.service;

import cn.jerryshell.liveteaching.config.LiveConfig;
import cn.jerryshell.liveteaching.dao.LiveMaterialDao;
import cn.jerryshell.liveteaching.model.LiveMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class LiveMaterialService {
    @Autowired
    private LiveConfig liveConfig;
    @Autowired
    private LiveMaterialDao liveMaterialDao;

    public LiveMaterial findById(String id) {
        return liveMaterialDao.findById(id).orElse(null);
    }

    public LiveMaterial findByLiveId(String liveId) {
        return liveMaterialDao.findByLiveId(liveId).orElse(null);
    }

    public void save(LiveMaterial liveMaterial) {
        liveMaterialDao.save(liveMaterial);
    }

    public void uploadFile(MultipartFile uploadFile, String filename) {
        File file = new File(liveConfig.getMaterialFilePath() + "/" + filename);
        try {
            uploadFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(String id) {
        LiveMaterial liveMaterial = liveMaterialDao.findById(id).orElse(null);
        if (liveMaterial == null) {
            return;
        }
        liveMaterialDao.deleteById(id);
        String filepath = String.format("%s/%s.%s", liveConfig.getMaterialFilePath(), liveMaterial.getId(), liveMaterial.getFileType());
        File file = new File(filepath);
        file.delete();
    }
}
