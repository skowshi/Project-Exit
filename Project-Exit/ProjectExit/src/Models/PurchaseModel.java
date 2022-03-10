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
public class PurchaseModel {

    private int purNo;
    private String vendorName;
    private String purchaseDate;
    private double amount;

    public PurchaseModel(int purNo, String vendorName, String purchaseDate, double amount) {

        this.purNo = purNo;
        this.vendorName = vendorName;
        this.purchaseDate = purchaseDate;
        this.amount = amount;
    }

    public int getPurNo() {
        return purNo;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public double getAmount() {
        return amount;
    }

}
