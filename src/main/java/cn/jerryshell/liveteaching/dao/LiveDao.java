package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Live;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveDao extends JpaRepository<String, Live> {
}
