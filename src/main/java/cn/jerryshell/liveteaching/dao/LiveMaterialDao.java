package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.LiveMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiveMaterialDao extends JpaRepository<LiveMaterial, String> {
    Optional<LiveMaterial> findByLiveId(String liveId);
}
