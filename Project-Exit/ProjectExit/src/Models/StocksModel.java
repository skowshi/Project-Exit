/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author User
 */
public class StocksModel {

    private int batchNo;
    private int prodID;
    private String prodName;
    private String manfDate;
    private String expDate;
    private int quantity;

    public StocksModel(int batchNo, int prodID, String prodName, String manfDate, String expDate, int quantity) {
        this.batchNo = batchNo;
        this.prodID = prodID;
        this.prodName = prodName;
        this.manfDate = manfDate;
        this.expDate = expDate;
        this.quantity = quantity;
    }

    public int getBatchNo() {
        return batchNo;
    }

    public int getProdID() {
        return prodID;
    }

    public String getProdName() {
        return prodName;
    }

    public String getManfDate() {
        return manfDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public int getQuantity() {
        return quantity;
    }

}
