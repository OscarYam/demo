package com.example.demo.service.impl;

import com.example.demo.controller.ResponResult;
import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import com.example.demo.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 业务层实现类
 * */
@Service
public class UserServiceImpl implements IUserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper usrMapper) {
        this.userMapper = usrMapper;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        if (userMapper.findAll().size() > 0) {
            return userMapper.findAll();
        } else {
            return null;
        }
    }

    @Override
    public List<UserEntity> getUserByName(String name) {
        if (userMapper.findByUserName(name).size() > 0) {
            return userMapper.findByUserName(name);
        } else {
            return null;
        }
    }

    @Override
    public ResponResult getUserByToken(UserEntity userEntity, String userTokenNew) {
        ResponResult res = new ResponResult();

        String token = userEntity.getUserToken();
        String id = userEntity.getUserId();

        if (token != null && id != null) {
            UserEntity usr = userMapper.getByUserToken(token);

            if (usr != null) {
                userMapper.updateUserToken(id, userTokenNew);

                // 返回验证信息
                usr.setUserToken(userTokenNew);

                // 不返回敏感信息
                usr.setUserPassword(null);

                res.setData(usr);
                res.setMessage("OK");
            } else {
                res.setMessage("用户Token错误");
            }
        } else {
            res.setMessage("用户Token不能为空");
        }

        return res;
    }

//    @Override
//    public Object getUserById(Integer Id) {
//        if (Id != null) {
//            if (userMapper.existsById(Id)) {
//                return userMapper.findById(Id);
//            } else {
//                return "用户ID不存在";
//            }
//
//        } else {
//            return "用户ID或Psw不能为空";
//        }
//    }
@Override
public Object getUserById(String Id) {
    if (Id != null) {
        if (userMapper.existsById(Id)) {
            return userMapper.findById(Id);
        } else {
            return "用户ID不存在";
        }

    } else {
        return "用户ID或Psw不能为空";
    }
}

    @Override
    public UserEntity addUser(UserEntity usr) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd z E HH:mm:ss");
        usr.setUserRepositoryTime(formatter.format(calendar.getTime()));

        return userMapper.save(usr);
    }

    @Override
    public String delUser(String usrId) {
        if (usrId != null) {
            if (userMapper.existsById(usrId)) {
                userMapper.deleteById(usrId);
                return "OK";
            } else {
                return "用户ID不存在";
            }
        } else {
            return "用户ID或Psw不能为空";
        }
    }

    @Override
    public Object updateUser(UserEntity usr, String userTokenNew) {
        ResponResult res = new ResponResult();

//        String userToken = usr.getUserToken();
//        if (userToken != null) {
//            if (userMapper.findByUserToken(userToken) != null) {
//                System.err.println("userToken : " + userMapper.findByUserToken(userToken).getUserToken());
//            } else {
//                System.err.println(" userToken not exist! ");
//            }
//        }


        String id = usr.getUserId();
        if (id != null && userTokenNew != null) {
            if (userMapper.existsById(id)) {
                UserEntity usr_old = userMapper.findById(id).get();
                if (usr.getUserToken().equals(usr_old.getUserToken())) {
                    // 用户 Token 验证成功
                    if (usr.getUserName() != null) {
                        userMapper.updateUserName(id, usr.getUserName());
                    }
                    if (usr.getUserPassword() != null) {
                        userMapper.updateUserPassword(id, usr.getUserPassword());
                    }
                    if (usr.getUserMailbox() != null) {
                        userMapper.updateUserMailbox(id, usr.getUserMailbox());
                    }
                    if (usr.getUserPhone() != null) {
                        userMapper.updateUserPhone(id, usr.getUserPhone());
                    }

                    if (usr.getUserImage1() != null) {
                        userMapper.updateUserImage1(id, usr.getUserImage1());
//                        System.err.println("usr.getUserImage1().getBytes().length = " + usr.getUserImage1().getBytes().length);
                    }

                    if (usr.getUserAge() != null) {
                        userMapper.updateUserAge(id, usr.getUserAge());
                    }

                    if (usr.getUserSex() != null) {
                        userMapper.updateUserSex(id, usr.getUserSex());
                    }

                    if (usr.getUserBirthday() != null) {
                        userMapper.updateUserBirthday(id, usr.getUserBirthday());
                    }

//                    String token = java.util.UUID.randomUUID().toString().replaceAll("-", "");
//                    userMapper.updateUserToken(id, token);
                    userMapper.updateUserToken(id, userTokenNew);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd z E HH:mm:ss");
                    String time_now = formatter.format(calendar.getTime());
                    userMapper.updateUserRepositoryTime(id, time_now);

                    // 返回验证信息
//                    usr_old.setUserToken(token);
                    usr_old.setUserToken(userTokenNew);
                    // 不返回敏感信息
                    usr_old.setUserPassword(null);
                    // 返回存档时刻
                    usr_old.setUserRepositoryTime(time_now);

                    if (usr.getUserName() != null) {
                        usr_old.setUserName(usr.getUserName());
                    }
                    if (usr.getUserMailbox() != null) {
                        usr_old.setUserMailbox(usr.getUserMailbox());
                    }
                    if (usr.getUserPhone() != null) {
                        usr_old.setUserPhone(usr.getUserPhone());
                    }

//                    if (usr.getUserImage1() != null) {
//                        usr_old.setUserImage1(usr.getUserImage1());
//                    }
                    // 不返回更新后的图片信息
                    usr_old.setUserImage1(null);

                    if (usr.getUserAge() != null) {
                        usr_old.setUserAge(usr.getUserAge());
                    }

                    if (usr.getUserSex() != null) {
                        usr_old.setUserSex(usr.getUserSex());
                    }

                    if (usr.getUserBirthday() != null) {
                        usr_old.setUserBirthday(usr.getUserBirthday());
                    }

                    res.setData(usr_old);
                    res.setMessage("OK");
                } else {
                    res.setMessage("用户Token错误");
                }
            } else {
                res.setMessage("用户ID不存在");
            }
        } else {
            res.setMessage("用户ID|Psw|Token不能为空");
        }

        return res;
    }

    @Override
    public Object userLogin(String usrID, String userPsw, String userTokenNew) {
        ResponResult res = new ResponResult();

        if (usrID != null && userPsw != null && userTokenNew != null) {
            if (userMapper.existsById(usrID)) {
                UserEntity usr = userMapper.findById(usrID).get();
                if (userPsw.equals(usr.getUserPassword())) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd z E HH:mm:ss");
                    String time_now = formatter.format(calendar.getTime());
                    userMapper.updateLoginLastTime(usrID, time_now);


//                    String token = java.util.UUID.randomUUID().toString().replaceAll("-", "");
//                    userMapper.updateUserToken(usrID, token);
//
//                    // 返回验证信息
//                    usr.setUserToken(token);

                    userMapper.updateUserToken(usrID, userTokenNew);

                    // 返回验证信息
                    usr.setUserToken(userTokenNew);

                    // 不返回敏感信息
                    usr.setUserPassword(null);

                    res.setData(usr);
                    res.setMessage("OK");
                } else {
                    res.setMessage("密码错误");
                }
            } else {
                res.setMessage("用户ID不存在");
            }
        } else {
            res.setMessage("用户ID|Psw|Token不能为空");
        }

        return res;
    }

    @Override
    public String getUserRam(int num) {
        return Utility.RamNum(num);
    }

}
