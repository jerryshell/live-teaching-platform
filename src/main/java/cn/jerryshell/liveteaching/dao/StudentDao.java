package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository<Student, String> {
}
