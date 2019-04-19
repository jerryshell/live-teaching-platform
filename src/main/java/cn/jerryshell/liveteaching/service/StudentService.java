package cn.jerryshell.liveteaching.service;

import cn.jerryshell.liveteaching.dao.StudentDao;
import cn.jerryshell.liveteaching.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    public Student findById(String id) {
        return studentDao.findById(id).orElse(null);
    }

    public Student findByIdAndPassword(String id, String password) {
        return studentDao.findByIdAndPassword(id, password).orElse(null);
    }

    public void save(Student student) {
        studentDao.save(student);
    }
}
