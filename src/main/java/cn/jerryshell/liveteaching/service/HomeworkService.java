package cn.jerryshell.liveteaching.service;

import cn.jerryshell.liveteaching.config.HomeworkConfig;
import cn.jerryshell.liveteaching.dao.HomeworkDao;
import cn.jerryshell.liveteaching.model.Homework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkDao homeworkDao;
    @Autowired
    private HomeworkConfig homeworkConfig;

    public Homework findById(String id) {
        return homeworkDao.findById(id).orElse(null);
    }

    public Homework findByStudentIdAndVideoId(String studentId, String videoId) {
        return homeworkDao.findByStudentIdAndVideoId(studentId, videoId).orElse(null);
    }

    public List<Homework> listByStudentId(String studentId) {
        return homeworkDao.findByStudentId(studentId);
    }

    public List<Homework> listByVideoId(String videoId) {
        return homeworkDao.findByVideoId(videoId);
    }

    public void save(Homework homework, MultipartFile multipartFile, String filename) {
        String filepath = homeworkConfig.getFilepath() + "/" + filename;
        try {
            multipartFile.transferTo(new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        homeworkDao.save(homework);
    }

    public void update(Homework homework) {
        homeworkDao.save(homework);
    }

    public void deleteById(String id) {
        Homework homework = homeworkDao.findById(id).orElse(null);
        if (homework == null) {
            return;
        }
        homeworkDao.deleteById(id);
        String filepath = homeworkConfig.getFilepath() + "/" + id + "." + homework.getFileType();
        new File(filepath).delete();
    }
}
