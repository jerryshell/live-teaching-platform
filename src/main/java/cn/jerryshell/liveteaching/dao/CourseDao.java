package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDao extends JpaRepository<String, Course> {
}
