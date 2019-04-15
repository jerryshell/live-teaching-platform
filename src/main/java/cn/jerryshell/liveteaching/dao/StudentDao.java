package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<String, Student> {
}
