package cn.jerryshell.liveteaching.dao;

import cn.jerryshell.liveteaching.model.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherDaoTest {
    @Autowired
    private TeacherDao teacherDao;

    @Test
    public void contextLoads() {
        // 测试保存
        Teacher teacher = new Teacher();
        teacher.setId("test_tea_id");
        teacher.setEmail("test_tea@email.com");
        teacher.setNickname("test_tea_nick");
        teacher.setPassword("test_tea_password");
        teacherDao.save(teacher);
        teacherDao.findById(teacher.getId()).orElseThrow(() -> new RuntimeException("数据库插入老师信息失败"));

        // 测试修改
        teacher.setNickname("update_nick");
        teacherDao.save(teacher);
        Teacher teacherTestUpdate = teacherDao.findById(teacher.getId()).orElseThrow(() -> new RuntimeException("数据库更新老师信息失败"));
        assert teacher.getNickname().equals(teacherTestUpdate.getNickname());

        // 测试删除
        teacherDao.deleteById(teacher.getId());
        Teacher teacherFromDB = teacherDao.findById(teacher.getId()).orElse(null);
        assert teacherFromDB == null;
    }
}
