package com.heitian.ssm.model;

/**
 * Created by mitnick.cheng on 2016/8/25.
 */
public class RegistRequest {
    private String userid;
    private String phone;
    private String password;
    private String create_time;

    public RegistRequest(String userid, String phone, String password, String create_time) {
        this.userid = userid;
        this.phone = phone;
        this.password = password;
        this.create_time = create_time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
