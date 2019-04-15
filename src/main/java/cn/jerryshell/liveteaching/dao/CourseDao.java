package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends JpaRepository<Course, String> {
}
