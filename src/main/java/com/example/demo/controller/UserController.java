package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.IUserService;
import com.example.demo.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;


@RestController
public class UserController {
    private final IUserService usrService;

    @Autowired
    public UserController(IUserService usrServ) {
        this.usrService = usrServ;
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public Object home(@RequestBody String reqData) throws Exception {
        String ret_d = Utility.Decrypt_AES(reqData);
        System.err.println("Decode: " + ret_d);

        ResponResult result = new ResponResult();
        result.setCode(300);
        result.setStatus("Success");

        int start_index = ret_d.indexOf("{");
        int end_index = ret_d.lastIndexOf("}") + 1;
        ret_d = ret_d.substring(start_index, end_index);
//        System.err.println("ret_d : " + ret_d);

        JSONObject jsonObject = JSONObject.parseObject(ret_d);
        JSONObject jsonUserEntity;
        UserEntity usrEntity = new UserEntity();
        String service = jsonObject.getString("service");
        if (service == null) {
            result.setCode(400);
            result.setStatus("Fail");
            result.setMessage("无效请求!");
            return result;
        }
        switch (service) {
            case "s.home" :
                result.setMessage(usrService.getUserRam(32));
                break;

            case "s.hello" :
                result.setMessage("hello");
                break;

            case "s.getAllUsers" :
                result.setData(usrService.getAllUsers());
                break;

            case "s.getUserById" :
                result.setData(usrService.getUserById(jsonObject.getString("userId")));
                break;

            case "s.getUserByName" :
                result.setData(usrService.getUserByName(jsonObject.getString("userName")));
                break;

            case "s.deleteUser" :
                result.setMessage(usrService.delUser(jsonObject.getString("userId")));
                break;

            case "s.addUser" :
                jsonUserEntity =  jsonObject.getJSONObject("userEntity");

                usrEntity.setUserMailbox(jsonUserEntity.getString("userMailbox"));
                usrEntity.setUserPassword(jsonUserEntity.getString("userPassword"));
                usrEntity.setUserPhone(jsonUserEntity.getString("userPhone"));
                usrEntity.setUserName(jsonUserEntity.getString("userName"));
                result.setData(usrService.addUser(usrEntity));
                break;

            case "s.userLogin" :
                ResponResult respondResult_userLogin = (ResponResult) usrService.userLogin(
                        jsonObject.getString("userId"),
                        jsonObject.getString("userPsw"),
                        jsonObject.getString("userToken"));
                result.setData(respondResult_userLogin.getData());
                result.setMessage(respondResult_userLogin.getMessage());
                break;

            case "s.updateUser" :
                jsonUserEntity =  jsonObject.getJSONObject("userEntity");
//                System.err.println("jsonUserEntity : " + jsonUserEntity);

                usrEntity.setUserMailbox(jsonUserEntity.getString("userMailbox"));
                usrEntity.setUserPassword(jsonUserEntity.getString("userPassword"));
                usrEntity.setUserPhone(jsonUserEntity.getString("userPhone"));
                usrEntity.setUserName(jsonUserEntity.getString("userName"));
                usrEntity.setUserId(jsonUserEntity.getString("userId"));
                usrEntity.setUserToken(jsonUserEntity.getString("userToken"));
                usrEntity.setUserImage1(jsonUserEntity.getString("userImage1"));
                usrEntity.setUserAge(jsonUserEntity.getInteger("userAge"));
                usrEntity.setUserSex(jsonUserEntity.getString("userSex"));
                usrEntity.setUserBirthday(jsonUserEntity.getString("userBirthday"));

                ResponResult respondResult_updateUser = (ResponResult) usrService.updateUser(usrEntity, jsonObject.getString("userToken"));
                result.setData(respondResult_updateUser.getData());
                result.setMessage(respondResult_updateUser.getMessage());
                break;

            case "s.getUserByToken" :
                jsonUserEntity =  jsonObject.getJSONObject("userEntity");

                usrEntity.setUserId(jsonUserEntity.getString("userId"));
                usrEntity.setUserToken(jsonUserEntity.getString("userToken"));


                ResponResult res = (ResponResult) usrService.getUserByToken(usrEntity, jsonObject.getString("userToken"));
                result.setData(res.getData());
                result.setMessage(res.getMessage());
                break;

            default:
                result.setCode(400);
                result.setStatus("Fail");
                result.setMessage("无效请求!");
                break;
        }

        String jsonString = JSONObject.toJSONString(result);
        StringBuilder sb = new StringBuilder(jsonString);

        int l = sb.toString().getBytes(StandardCharsets.UTF_8).length % 16;
        if (l > 0) {
            for(int i=0, j=16-l; i<j; i++) {
                sb.append(Utility.RamNum(1));
            }
        }

        if (sb.toString().getBytes(StandardCharsets.UTF_8).length < 640) {
            sb.append(Utility.RamNum(640));
        }

        System.err.println("Encode source : " + sb);
//        System.err.println("Encode source length = " + sb.length());
//        System.err.println("Encode soutce bytes = " + sb.toString().getBytes(StandardCharsets.UTF_8).length);

        return Utility.Encrypt_AES(sb.toString());
    }


    @RequestMapping(value = "/userservice", method = RequestMethod.POST)
    public Object serviceController(@RequestBody RequestData requestData) throws Exception {
        ResponResult result = new ResponResult();
        result.setCode(300);
        result.setStatus("Success");
        switch (requestData.getService()) {
            case "s.getUserRam" :
//                result.setMessage(usrService.getUserRam(16));
                String a = Utility.RamNum(32);
//                String a = "qertghkjgfgdtxzyut12342435409~!@";
//                String a = "~!@#$%^&*()_+`-={}[];:''|/?/.<>,";

                String b = Utility.Encode_BASE64(a.getBytes());
                result.setMessage(b);

                String c = new String(Utility.Decode_BASE64(b));
                result.setData(c);

                String d = Utility.Encode_MD5_SHA(a, "SHA-256");
                System.err.println("SHA-256: " + d);

                String e = Utility.Encrypt_AES(a);
                System.err.println("Encrypt_AES: " + e);

                String f = Utility.Decrypt_AES(e);
                System.err.println("Decrypt_AES: " + f);
                break;

            case "s.decode" :
                String mess = requestData.getMessage();
                String ret_d = Utility.Decrypt_AES(mess);
                System.err.println(ret_d);

                int start_index = ret_d.indexOf("{");
                int end_index = ret_d.lastIndexOf("}") + 1;
                ret_d = ret_d.substring(start_index, end_index);
//                System.err.println(ret_d);

                JSONObject jsObj = JSONObject.parseObject(ret_d);
                Integer code = jsObj.getInteger("code");
                if (code != null) {
                    result.setCode(jsObj.getInteger("code"));
                }
                result.setMessage(jsObj.getString("message"));
                result.setStatus(jsObj.getString("status"));

                try {
                    result.setData(jsObj.getJSONArray("data"));
                } catch (Exception exception) {
//                    exception.printStackTrace();
                    try {
                        result.setData(jsObj.getJSONObject("data"));
                    } catch (Exception exception1) {
                        result.setData(jsObj.getString("data"));
//                        result.setMessage(jsObj.getString("data"));
                    }
                }

                break;

            case "s.encode" :
                RequestData reqdata = new RequestData();
                reqdata.setService(requestData.getMessage());
                reqdata.setUserName(requestData.getUserName());
                reqdata.setUserId(requestData.getUserId());
                reqdata.setUserPsw(requestData.getUserPsw());
                reqdata.setUserEntity(requestData.getUserEntity());

                String js = JSONObject.toJSONString(reqdata);
                StringBuilder ssb = new StringBuilder(js);

                int ll = ssb.toString().getBytes(StandardCharsets.UTF_8).length % 16;
                if (ll > 0) {
                    for(int i=0, j=16-ll; i<j; i++) {
                        ssb.append(Utility.RamNum(1));
                    }
                }
                System.err.println(ssb);
                return Utility.Encrypt_AES(ssb.toString());

            default:
                result.setCode(400);
                result.setStatus("Fail");
                result.setMessage("无效请求!");
                break;
        }

        return result;
    }

}
