package com.heitian.ssm.model;

/**
 * Created by mitnick.cheng on 2016/8/25.
 */
public class UpdateRequest {
    private String phone;
    private String password;
    private String modify_time;

    public UpdateRequest(String phone, String password, String modify_time) {
        this.phone = phone;
        this.password = password;
        this.modify_time = modify_time;
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

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }
}
