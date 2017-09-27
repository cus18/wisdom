/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.common.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class UserInfoDTO implements java.io.Serializable{

	@JSONField(name = "id")
	private String id;

	@JSONField(name = "loginName")
	private String loginName;

	@JSONField(name = "password")
	private String password;

	@JSONField(name = "gender")
	private String gender;

	@JSONField(name = "name")
	private String name;

	@JSONField(name = "age")
	private String age;

	@JSONField(name = "email")
	private String email;

	@JSONField(name = "phone")
	private String phone;

	@JSONField(name = "mobile")
	private String mobile;

	@JSONField(name = "userType")
	private String userType;

	@JSONField(name = "photo")
	private String photo;

	@JSONField(name = "area")
	private String area;

	@JSONField(name = "address")
	private String address;

	@JSONField(name = "loginIp")
	private String loginIp;

	@JSONField(name = "loginDate")
	private Date loginDate;

	@JSONField(name = "loginFlag")
	private String loginFlag;

	@JSONField(name = "createBy")
	private String createBy;

	@JSONField(name = "createDate")
	private Date createDate;

	@JSONField(name = "updateBy")
	private String updateBy;

	@JSONField(name = "updateDate")
	private Date updateDate;

	@JSONField(name = "remarks")
	private String remarks;

	@JSONField(name = "del_flag")
	private char del_flag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public char getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(char del_flag) {
		this.del_flag = del_flag;
	}
}