package com.heitian.ssm.controller;

import com.google.gson.Gson;
import com.heitian.ssm.model.*;
import com.heitian.ssm.service.UserService;
import com.heitian.ssm.sms.SmsService;
import com.heitian.ssm.factory.TokenFactory;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        User user = userService.selectUserByPhoneOrEmail(loginRequest.getPhone());
        if (user != null) {
            user = userService.login(loginRequest);
            if (user != null) {
                return gson.toJson(new JsonResult(200, "login", user));
            }
            return gson.toJson(new JsonResult(500, "用户名或者密码有误！", null));
        }
        return gson.toJson(new JsonResult(500, "用户名不存在！", null));
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
        log.info("X-ZUMO-AUTH："+token+ "\n"+ new Gson().toJson(userRequest));
        String verifyResult = TokenFactory.verifySmsToken(token, userRequest.getPhone(), userRequest.getCode());
        if (verifyResult.equals("success")) {
            //操作记录条数，初始化为0
            int resultTotal = 0;
//            String pwd = Base64Utils.decode(userRequest.getPassword());
            resultTotal = userService.regist(new LoginRequest(userRequest.getPhone(), userRequest.getPassword()));
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
            resultTotal = userService.update(new LoginRequest(userRequest.getPhone(), userRequest.getPassword()));
            if (resultTotal > 0) {
                return gson.toJson(new JsonResult(200, "retrieve", "update is success"));
            }
        }
        return gson.toJson(new JsonResult(500, verifyResult, null));
    }
}
