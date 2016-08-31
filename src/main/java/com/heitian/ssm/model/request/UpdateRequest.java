package com.heitian.ssm.model.request;

import java.util.Date;

/**
 * Created by mitnick.cheng on 2016/8/25.
 */
public class UpdateRequest {
    private String phone;
    private String password;
    private Date modifyTime;

    public UpdateRequest() {
    }

    public UpdateRequest(String phone, String password, Date modifyTime) {
        this.phone = phone;
        this.password = password;
        this.modifyTime = modifyTime;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
