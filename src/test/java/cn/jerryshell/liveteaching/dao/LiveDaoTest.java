package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Live;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveDaoTest {
    @Autowired
    private LiveDao liveDao;

    @Test
    public void contextLoads() {
        // 测试保存
        Live live = new Live();
        live.setId(UUID.randomUUID().toString());
        live.setTeacherId("1");
        live.setCourseId("1");
        live.setDate(new Date());
        live.setGrade("2016");
        live.setLength("1H");
        live.setMajorId("1");
        live.setName("test_live_name");
        live.setStartTime("12:00");
        liveDao.save(live);
        liveDao.findById(live.getId()).orElseThrow(() -> new RuntimeException("数据库插入直播信息失败"));

        // 测试修改
        live.setName("update_live_name");
        liveDao.save(live);
        Live liveTestUpdate = liveDao.findById(live.getId()).orElseThrow(() -> new RuntimeException("数据库插入直播信息失败"));
        assert liveTestUpdate.getName().equals(live.getName());

        // 测试删除
        liveDao.deleteById(live.getId());
        Live liveFromDB = liveDao.findById(live.getId()).orElse(null);
        assert liveFromDB == null;
    }
}
