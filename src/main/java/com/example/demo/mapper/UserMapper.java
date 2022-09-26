package com.example.demo.mapper;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository为数据库访问层，它在内部封装了数据查询和存储的逻辑，也可以在这里自定义UserEntity的实现方法。
 */
@Repository
public interface UserMapper extends JpaRepository<UserEntity, String> {
    List<UserEntity> findByUserName(String userName);
    
    UserEntity getByUserToken(String userToken);

    @Query(value = "update user_info p set p.user_name=?2 where p.user_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    void updateUserName(String userID, String user_name);

    @Query(value = "update user_info p set p.user_password=?2 where p.user_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    void updateUserPassword(String userID, String user_password);

    @Query(value = "update user_info p set p.user_mailbox=?2 where p.user_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    void updateUserMailbox(String userID, String user_mailbox);

    @Query(value = "update user_info p set p.user_phone=?2 where p.user_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    void updateUserPhone(String userID, String user_phone);

    @Query(value = "update user_info p set p.user_token=?2 where p.user_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    void updateUserToken(String userID, String user_token);

    @Query(value = "update user_info p set p.user_repository_time=?2 where p.user_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    void updateUserRepositoryTime(String userID, String user_repository_time);

    @Query(value = "update user_info p set p.user_login_last_time=?2 where p.user_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    void updateLoginLastTime(String userID, String user_login_last_time);

    @Query(value = "update user_info p set p.user_image1=?2 where p.user_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    void updateUserImage1(String userID, String image1);

    @Query(value = "update user_info p set p.user_age=?2 where p.user_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    void updateUserAge(String userID, Integer user_age);

    @Query(value = "update user_info p set p.user_Sex=?2 where p.user_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    void updateUserSex(String userID, String user_Sex);

    @Query(value = "update user_info p set p.user_Birthday=?2 where p.user_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    void updateUserBirthday(String userID, String user_Birthday);

}
