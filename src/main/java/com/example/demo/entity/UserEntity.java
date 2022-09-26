package com.example.demo.entity;

import javax.persistence.*;

/**
 * user数据表格 实体对象类
 * 实体类需要使用 @Entity 注解标注。并且实体类的属性也要进行标注，使用 @Id 标注主键，使用 @Column 标注非主键。
 * */
@Entity
@Table(name="user_info") //指定表名
public class UserEntity {
    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_password")
    private String userPassword;

    @Column(name="user_mailbox")
    private String userMailbox;

    @Column(name="user_phone")
    private String userPhone;

    @Column(name="user_token")
    private String userToken;

    @Column(name="user_repository_time")
    private String userRepositoryTime;

    @Column(name="user_login_last_time")
    private String userLoginLastTime;

    @Column(name = "user_image1")
    private String userImage1;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "user_Sex")
    private String userSex;

    @Column(name = "user_Birthday")
    private String userBirthday;


    public UserEntity() {}

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserMailbox() {
        return userMailbox;
    }
    public void setUserMailbox(String userMailbox) {
        this.userMailbox = userMailbox;
    }

    public String getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserToken() {
        return userToken;
    }
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserRepositoryTime() {
        return userRepositoryTime;
    }
    public void setUserRepositoryTime(String userRepositoryTime) {
        this.userRepositoryTime = userRepositoryTime;
    }

    public String getUserLoginLastTime() {
        return userLoginLastTime;
    }
    public void setUserLoginLastTime(String userLoginLastTime) {
        this.userLoginLastTime = userLoginLastTime;
    }

    public String getUserImage1() {
        return userImage1;
    }
    public void setUserImage1(String image) {
        this.userImage1 = image;
    }

    public Integer getUserAge() {
        return userAge;
    }
    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserSex() {
        return userSex;
    }
    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserBirthday() {
        return userBirthday;
    }
    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

}
