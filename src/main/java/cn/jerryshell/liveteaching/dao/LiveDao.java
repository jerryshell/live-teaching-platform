package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Live;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LiveDao extends JpaRepository<Live, String> {
    List<Live> findByTeacherId(String teacherId);

    Integer countByTeacherId(String teacherId);

    Integer countByDateAfter(Date date);

    Integer countByDateAfterAndMajorIdAndGrade(Date date, String majorId, String grade);

    List<Live> findByDateAfterAndMajorIdAndGrade(Date date, String majorId, String grade);
}
