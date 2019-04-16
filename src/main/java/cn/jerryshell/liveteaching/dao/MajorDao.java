package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorDao extends JpaRepository<Major, String> {
}
