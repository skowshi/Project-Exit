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
public class ProductModel {

    private int prodId;
    private String brandName;
    private String prodName;
    private String prodSize;
    private double MRPrice;
    private double WSPrice;
    private String prodCat;

    public ProductModel(int prodId, String brandName, String prodName, String prodSize, double MRPrice, double WSPrice, String prodCat) {
        this.prodId = prodId;
        this.brandName = brandName;
        this.prodName = prodName;
        this.prodSize = prodSize;
        this.MRPrice = MRPrice;
        this.WSPrice = WSPrice;
        this.prodCat = prodCat;

    }

    public int getProdId() {
        return prodId;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getProdName() {
        return prodName;
    }

    public String getProdSize() {
        return prodSize;
    }

    public double getMRPrice() {
        return MRPrice;
    }

    public double getWSPrice() {
        return WSPrice;
    }

    public String getProdCat() {
        return prodCat;
    }

}