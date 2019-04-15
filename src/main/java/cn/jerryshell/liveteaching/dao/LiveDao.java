package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Live;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveDao extends JpaRepository<Live, String> {
}
