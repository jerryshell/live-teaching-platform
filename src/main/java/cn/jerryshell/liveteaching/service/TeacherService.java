package cn.jerryshell.liveteaching.service;

import cn.jerryshell.liveteaching.dao.TeacherDao;
import cn.jerryshell.liveteaching.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    public Teacher findById(String id) {
        return teacherDao.findById(id).orElse(null);
    }

    public Teacher findByIdAndPassword(String id, String password) {
        return teacherDao.findByIdAndPassword(id, password).orElse(null);
    }

    public void save(Teacher teacher) {
        teacherDao.save(teacher);
    }
}
