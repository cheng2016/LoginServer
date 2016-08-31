package com.heitian.ssm.model.request;

import java.util.Date;

/**
 * Created by mitnick.cheng on 2016/8/25.
 */
public class RegistRequest {
    private String phone;
    private String password;
    private Date createTime;

    public RegistRequest() {
    }

    public RegistRequest(String phone, String password, Date createTime) {
        this.phone = phone;
        this.password = password;
        this.createTime = createTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
