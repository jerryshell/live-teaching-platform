package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselDao extends JpaRepository<Carousel, String> {
}
