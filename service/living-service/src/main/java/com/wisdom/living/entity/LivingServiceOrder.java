package com.wisdom.living.entity;


import com.wisdom.common.dto.basic.DataEntity;

/**
 * Created by zbm84 on 2017/12/1.
 */
public class LivingServiceOrder{

    private Integer id;
    private Integer livingservice_id;
    private String phone;
    private String name;
    private String care;
    private String address;
    private String remarks;
    private String openid;
    private String sys_elder_user_id;
    private String update_date;
    private String create_date;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLivingservice_id() {
        return livingservice_id;
    }

    public void setLivingservice_id(Integer livingservice_id) {
        this.livingservice_id = livingservice_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCare() {
        return care;
    }

    public void setCare(String care) {
        this.care = care;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSys_elder_user_id() {
        return sys_elder_user_id;
    }

    public void setSys_elder_user_id(String sys_elder_user_id) {
        this.sys_elder_user_id = sys_elder_user_id;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
