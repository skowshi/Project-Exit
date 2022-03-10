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
public class UserModel {
    
    private int userID;
    private String userName;
    private String email;
    private String nic;
    private String password;
    private String role;
    public static String loginName;
    public static String userRole;
    public static String userNic;
    public static String name;
    public static int UserID;

    public UserModel(int userID, String userName, String email, String nic, String password, String role) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.nic = nic;
        this.password = password;
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getNic() {
        return nic;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
   
}
