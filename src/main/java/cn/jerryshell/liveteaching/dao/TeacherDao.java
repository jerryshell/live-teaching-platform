package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherDao extends JpaRepository<String, Teacher> {
}
