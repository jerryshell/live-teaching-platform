package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoDao extends JpaRepository<Video, String> {
    List<Video> findByCourseId(String courseId);

    List<Video> findByTeacherId(String teacherId);
}
