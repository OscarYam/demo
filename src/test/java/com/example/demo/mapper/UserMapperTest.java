package com.example.demo.mapper;

import com.example.demo.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    public UserMapper mapper;

    @Test
    public void findList() {
        System.err.println(mapper);

        List<UserEntity> list = mapper.findByUserName("tom");
        System.err.println("count=" + list.size());
        for (UserEntity item : list) {
            System.out.println("\n" + item.getUserName() + "\n");
        }
    }

    @Test
    public void addusr() {
        UserEntity usr = new UserEntity();
        usr.setUserName("TestName");
        usr.setUserPassword("TestName123456");
        usr.setUserPhone("132456789");
        mapper.save(usr);
    }

    @Test
    public void deleteusr() {
        List<UserEntity> t = mapper.findByUserName("tom2");
        for (UserEntity u : t) {
            System.err.println(u.getUserId());
            mapper.deleteById(u.getUserId());
        }
    }


}
