package com.wisdom.living.entity;


import java.util.Date;

/**
 * Created by zbm84 on 2017/12/4.
 */
public class OfficeMessage {

    private Integer id;
    private String message;
    private String sys_elder_user_id;
    private String status;
    private Date create_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSys_elder_user_id() {
        return sys_elder_user_id;
    }

    public void setSys_elder_user_id(String sys_elder_user_id) {
        this.sys_elder_user_id = sys_elder_user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
