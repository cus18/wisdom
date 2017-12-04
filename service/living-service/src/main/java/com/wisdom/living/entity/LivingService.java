package com.wisdom.living.entity;


import com.wisdom.common.dto.basic.DataEntity;
import com.wisdom.common.dto.core.Page;

import java.util.Date;

/**
 * Created by zbm84 on 2017/9/18.
 */
public class LivingService {

    private Integer id;
    private String name;
    private String description;
    private String type;
    private String status;
    private String payment;
    private String price;
    private String priceUnit;
    private String special;
    private String information;
    private String sys_office_id;
    private String del_flag;
    private Date update_date;
    private Date create_date;

    private String officeName;
    private String searchType;
    private String searchValue;
    private Integer lastNo;
    private Integer nextNo;



    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getSys_office_id() {
        return sys_office_id;
    }

    public void setSys_office_id(String sys_office_id) {
        this.sys_office_id = sys_office_id;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flage) {
        this.del_flag = del_flage;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLastNo() {
        return lastNo;
    }

    public void setLastNo(Integer lastNo) {
        this.lastNo = lastNo;
    }

    public Integer getNextNo() {
        return nextNo;
    }

    public void setNextNo(Integer nextNo) {
        this.nextNo = nextNo;
    }
}
