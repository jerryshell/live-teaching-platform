package cn.jerryshell.liveteaching.service;

import cn.jerryshell.liveteaching.dao.LiveDao;
import cn.jerryshell.liveteaching.model.Live;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LiveService {
    @Autowired
    private LiveDao liveDao;

    public List<Live> findAll() {
        return liveDao.findAll();
    }

    public Live findById(String id) {
        return liveDao.findById(id).orElse(null);
    }

    public void save(Live live) {
        liveDao.save(live);
    }

    public void deleteById(String id) {
        liveDao.deleteById(id);
    }

    public List<Live> findByDateAfterAndMajorIdAndGrade(Date lastDayDate, String majorId, String grade) {
        return liveDao.findByDateAfterAndMajorIdAndGrade(lastDayDate, majorId, grade);
    }

    public List<Live> findByTeacherId(String teacherId) {
        return liveDao.findByTeacherId(teacherId);
    }
}
