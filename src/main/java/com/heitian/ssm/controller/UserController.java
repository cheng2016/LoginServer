package com.heitian.ssm.controller;

import com.google.gson.Gson;
import com.heitian.ssm.model.request.LoginRequest;
import com.heitian.ssm.model.request.RegistRequest;
import com.heitian.ssm.model.request.UpdateRequest;
import com.heitian.ssm.model.request.UserRequest;
import com.heitian.ssm.model.response.JsonResult;
import com.heitian.ssm.model.response.User;
import com.heitian.ssm.model.Account;
import com.heitian.ssm.service.UserService;
import com.heitian.ssm.sms.SmsService;
import com.heitian.ssm.factory.TokenFactory;
import com.heitian.ssm.utils.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Zhangxq on 2016/7/15.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger log = Logger.getLogger(UserController.class);

    private Gson gson = new Gson();

    @Resource
    private UserService userService;

    @RequestMapping(value = "/showUser", produces = "application/json; charset=utf-8")
    public @ResponseBody String showUser() {
        List<User> userList = userService.selectAllUser();
        log.info("showUser() 查ft询所有用户信息" + new Gson().toJson(userList));
        return new Gson().toJson(userList);
    }

    @RequestMapping("/showAllUser")
    public String showAllUser(HttpServletRequest request, Model model) {
        log.info("showAllUser() 查询所有用户信息");
        List<User> userList = userService.selectAllUser();
        model.addAttribute("userList", userList);
        return "showUser";
    }

    @RequestMapping(value = "/login", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody LoginRequest loginRequest) {
//        {"phone":"18202745852","password":"213123"}
        User user = userService.selectUserByPhoneOrEmail(loginRequest.getPhone());
        if (user != null) {
            user = userService.login(loginRequest);
            if (user != null) {
                String accessToken = TokenFactory.generatorToken(user.getId()+"",TokenFactory.ACCESS);
                return gson.toJson(new JsonResult(200, "login", new Account(user.getId(),user.getUserName(),accessToken)));
            }
            return gson.toJson(new JsonResult(500, "用户名或者密码有误！", null));
        }
        return gson.toJson(new JsonResult(500, "用户名不存在！", null));
    }

    @RequestMapping(value = "/checkLogin", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String checkLogin(@RequestBody Account account) {
//        {"id":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJzdGFydCI6MTQ3MjYxNTg2NDc3MiwiaXNzIjoiaHR0cHM6Ly93d3cuZnR4LmNvbS9hcGkvIiwidHlwZSI6ImFjY2VzcyIsImVuZCI6MTQ3MzIyMDY2NDc3Mn0.N7WbyZClg4WlMkncZC0uMm5tcaWy6HF6asWH29bFoXU"}
        long id = account.getId();
        String accessToken = account.getAccessToken();
        if(id<=0){
            return gson.toJson(new JsonResult(500, "id不能小于等于零", null));
        }
        if(accessToken==null && accessToken.equals("")){
            return gson.toJson(new JsonResult(500, "token不能为空", null));
        }

        if(!TokenFactory.verifyToken(accessToken)){
            return gson.toJson(new JsonResult(500, "token过期或无效", null));
        }else if(id != Long.valueOf(TokenFactory.getTokenId(account.getAccessToken()))){
            return gson.toJson(new JsonResult(500, "id校验失败", null));
        }else{
            User user = userService.selectUserById(id);
            if(user !=null){
                return gson.toJson(new JsonResult(200, "checkLogin", "验证通过"));
            }
            return gson.toJson(new JsonResult(500, "用户不存在", null));
        }
    }

    @RequestMapping(value = "/getCode", produces = "application/jso; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String getCode(@Param("phone") String phone,@Param("type") String type) {
        User user = userService.selectUserByPhoneOrEmail(phone);
        log.info("phone："+phone+"type："+type + "" + new Gson().toJson(user));
        if(type.equals("regist")){
            if (user == null) {
                return SmsService.sendMessage(phone);
            }
            return new Gson().toJson(new JsonResult(500, "手机号已注册", null));
        }else if(type.equals("retrieve")){
            if(user!=null){
                return SmsService.sendMessage(phone);
            }
            return new Gson().toJson(new JsonResult(500, "手机号未注册", null));
        }
        return new Gson().toJson(new JsonResult(500, "未知类型错误", null));
    }

    @RequestMapping(value = "/regist", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String regist(@RequestHeader("X-ZUMO-AUTH") String token, @RequestBody UserRequest userRequest) {
//        {"phone":"18202745852","password":"213123","code":"960594"}
        log.info("X-ZUMO-AUTH："+token+ "\n"+ new Gson().toJson(userRequest));
        String verifyResult = TokenFactory.verifySmsToken(token, userRequest.getPhone(), userRequest.getCode());
        if (verifyResult.equals("success")) {
            //操作记录条数，初始化为0
            int resultTotal = 0;
            resultTotal = userService.regist(new RegistRequest(userRequest.getPhone(), userRequest.getPassword(), DateUtils.getCurrentDate()));
            if (resultTotal > 0) {
                return new Gson().toJson(new JsonResult(200, "regist", "注册成功"));
            }
            return new Gson().toJson(new JsonResult(500, "注册失败", null));
        }
        return new Gson().toJson(new JsonResult(500, verifyResult, null));
    }

    @RequestMapping(value = "/retrieve", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String retrieve(@RequestHeader("X-ZUMO-AUTH") String token, @RequestBody UserRequest userRequest) {
        log.info("X-ZUMO-AUTH：" + userRequest.getPhone() +"\n"+ new Gson().toJson(userRequest));
        String verifyResult = TokenFactory.verifySmsToken(token, userRequest.getPhone(), userRequest.getCode());
        if (verifyResult.equals("success")) {
            //操作记录条数，初始化为0
            int resultTotal = 0;
            resultTotal = userService.update(new UpdateRequest(userRequest.getPhone(), userRequest.getPassword(),DateUtils.getCurrentDate()));
            if (resultTotal > 0) {
                return gson.toJson(new JsonResult(200, "retrieve", "update is success"));
            }
            return new Gson().toJson(new JsonResult(500, "修改密码失败", null));
        }
        return gson.toJson(new JsonResult(500, verifyResult, null));
    }


    @RequestMapping(value = "/qqLogin", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String qqLogin(@Param("name") String name) {
        User user = userService.selectByUserName(name);
        if(user == null){
            //操作记录条数，初始化为0
            int resultTotal = 0;
            resultTotal = userService.insertByUserName(name,DateUtils.getCurrentDate());
            if (resultTotal > 0) {
                User bean = userService.selectByUserName(name);
                String accessToken = TokenFactory.generatorToken(bean.getId()+"",TokenFactory.ACCESS);
                return gson.toJson(new JsonResult(200, "qqLogin", new Account(bean.getId(),name,accessToken)));
            }
            log.error("操作数据库操作失败！");
            return gson.toJson(new JsonResult(500, "qq登录失败！", null));
        }
        String accessToken = TokenFactory.generatorToken(user.getId()+"",TokenFactory.ACCESS);
        return gson.toJson(new JsonResult(200, "qqLogin", new Account(user.getId(),name,accessToken)));
    }
}