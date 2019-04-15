package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoDao extends JpaRepository<Video, String> {
}
