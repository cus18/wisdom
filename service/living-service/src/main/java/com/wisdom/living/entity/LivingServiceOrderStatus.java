package com.wisdom.living.entity;

import java.util.Date;

/**
 * Created by zbm84 on 2017/12/4.
 */
public class LivingServiceOrderStatus {

    private String id;
    private String orderID;
    private String name;
    private String price;
    private String priceUnit;
    private String payment;
    private Date  crate_date;
    private String nurse;
    private Date  serviceTime;
    private String status;
    private String discountPrice;
    private String discountPriceUnit;
    private String officeName;
    private String unpaid;
    private String refusal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Date getCrate_date() {
        return crate_date;
    }

    public void setCrate_date(Date crate_date) {
        this.crate_date = crate_date;
    }

    public String getNurse() {
        return nurse;
    }

    public void setNurse(String nurse) {
        this.nurse = nurse;
    }

    public Date getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Date serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountPriceUnit() {
        return discountPriceUnit;
    }

    public void setDiscountPriceUnit(String discountPriceUnit) {
        this.discountPriceUnit = discountPriceUnit;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(String unpaid) {
        this.unpaid = unpaid;
    }

    public String getRefusal() {
        return refusal;
    }

    public void setRefusal(String refusal) {
        this.refusal = refusal;
    }
}
