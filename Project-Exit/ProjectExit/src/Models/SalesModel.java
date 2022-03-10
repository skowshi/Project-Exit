/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Date;

/**
 *
 * @author User
 */
public class SalesModel {

    private int SONo;
    private String orderDate;
    private String cusName;
    private String cusPhone;
    private String reqDate;
    private String repName;
    private String region;
    private String orderCreator;
    private String status;
    private int total;

    public SalesModel(int SONo, String orderDate, String cusName, String cusPhone, String reqDate, String repName, String region, String orderCreator, String status, int total) {
        this.SONo = SONo;
        this.orderDate = orderDate;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.reqDate = reqDate;
        this.repName = repName;
        this.region = region;
        this.orderCreator = orderCreator;
        this.status = status;
        this.total = total;
    }

    public int getSONo() {
        return SONo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getCusName() {
        return cusName;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public String getReqDate() {
        return reqDate;
    }

    public String getRepName() {
        return repName;
    }

    public String getRegion() {
        return region;
    }

    public String getOrderCreator() {
        return orderCreator;
    }

    public String getStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

}
