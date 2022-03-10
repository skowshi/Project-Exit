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
public class RepModel {
    
    private int repID;
    private String repName;
    private String repPhone;
    private String repRegion;
    private double repTarget;
    public static boolean control = false;

    public RepModel(int repID, String repName, String repPhone, String repRegion, double repTarget) {
        this.repID = repID;
        this.repName = repName;
        this.repPhone = repPhone;
        this.repRegion = repRegion;
        this.repTarget = repTarget;
    }

    public int getRepID() {
        return repID;
    }

    public String getRepName() {
        return repName;
    }

    public String getRepPhone() {
        return repPhone;
    }

    public String getRepRegion() {
        return repRegion;
    }

    public double getRepTarget() {
        return repTarget;
    }
    
}
