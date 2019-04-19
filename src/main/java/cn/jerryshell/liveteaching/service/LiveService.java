package cn.jerryshell.liveteaching.service;

import cn.jerryshell.liveteaching.dao.LiveDao;
import cn.jerryshell.liveteaching.model.Live;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveService {
    @Autowired
    private LiveDao liveDao;

    public List<Live> findAll() {
        return liveDao.findAll();
    }

    public void save(Live live) {
        liveDao.save(live);
    }

    public void deleteById(String id) {
        liveDao.deleteById(id);
    }
}
