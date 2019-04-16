package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDao extends JpaRepository<Teacher, String> {
    Teacher findByIdAndPassword(String id, String password);
}
