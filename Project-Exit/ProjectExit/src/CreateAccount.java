/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Models.UserModel;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Models.DatabaseConnection;
import Models.RepModel;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class CreateAccount extends javax.swing.JFrame {

    /**
     * Creates new form createAccount
     */
    DatabaseConnection dbConnect = new DatabaseConnection();
    public String temprepName = "";

    public CreateAccount() {
        initComponents();
        URL iconURL = getClass().getResource("Images/AUXANO ICON 20.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
        ShowUsers();
        ShowReps();
        fillCombo();
        fillReps();
    }

    public void fillReps() {

        try {
            Connection con = dbConnect.getConnection();
            String query1 = "select repName from reps_tab where repID NOT IN (select repID from assigned_tab);";
            DefaultTableModel model = (DefaultTableModel) jTableAvailable.getModel();
            model.setRowCount(0);
            ResultSet rs;
            PreparedStatement pst = con.prepareStatement(query1);
            rs = pst.executeQuery();

            Object[] row = new Object[1];
            while (rs.next()) {
                row[0] = rs.getString("repName");

                model.addRow(row);
            }
            con.close();
            pst.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void fillCombo() {

        try {
            Connection con = dbConnect.getConnection();
            String query1 = "select userName from user_tab where role = 'SALES MANAGER';";
            Managerscmb.removeAllItems();
            Managerscmb.addItem("");
            ResultSet rs;
            PreparedStatement pst = con.prepareStatement(query1);
            rs = pst.executeQuery();

            while (rs.next()) {
                Managerscmb.addItem(rs.getString("userName"));
            }
            con.close();
            pst.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public ArrayList<UserModel> getOrderList() {

        ArrayList<UserModel> orderList = new ArrayList<UserModel>();
        Connection con = dbConnect.getConnection();
        String query = "select * from user_tab";

        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            UserModel users;

            while (rs.next()) {
                users = new UserModel(rs.getInt("userID"), rs.getString("userName"), rs.getString("email"), rs.getString("nic"), rs.getString("password"), rs.getString("role"));
                orderList.add(users);
            }
            con.close();
            st.close();
            rs.close();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return orderList;
    }

    public void ShowUsers() {

        ArrayList<UserModel> list = getOrderList();
        DefaultTableModel model = (DefaultTableModel) tblDetailsTable.getModel();
        model.setRowCount(0);

        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getUserID();
            row[1] = list.get(i).getUserName();
            row[2] = list.get(i).getEmail();
            row[3] = list.get(i).getNic();
            row[4] = list.get(i).getPassword();
            row[5] = list.get(i).getRole();

            model.addRow(row);
        }
    }

    public ArrayList<RepModel> getRepList() {

        ArrayList<RepModel> orderList = new ArrayList<RepModel>();
        Connection con = dbConnect.getConnection();
        String query = "select * from reps_tab";

        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            RepModel reps;

            while (rs.next()) {
                reps = new RepModel(rs.getInt("repID"), rs.getString("repName"), rs.getString("repPhone"), rs.getString("repRegion"), Double.parseDouble(rs.getString("repTarget")));
                orderList.add(reps);
            }
            con.close();
            st.close();
            rs.close();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return orderList;
    }

    public void ShowReps() {

        ArrayList<RepModel> list = getRepList();
        DefaultTableModel model = (DefaultTableModel) tblReps.getModel();
        model.setRowCount(0);

        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getRepID();
            row[1] = list.get(i).getRepName();
            row[2] = list.get(i).getRepPhone();
            row[3] = list.get(i).getRepRegion();
            row[4] = list.get(i).getRepTarget();

            model.addRow(row);
        }
    }

    public int getRepID(String name) {

        int id = 0;

        try {
            Connection con = dbConnect.getConnection();

            String query = "select repID from reps_tab where repName = '" + name + "';";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt("repID");
            }
            con.close();
            statement.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No values in the database");
        }
        return id;
    }

    public int getManID(String name) {

        int id = 0;

        try {
            Connection con = dbConnect.getConnection();

            String query = "select userID from user_tab where userName = '" + name + "';";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt("userID");
            }
            con.close();
            statement.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No values in the database");
        }
        return id;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        lblProducts = new javax.swing.JLabel();
        lblPurchase = new javax.swing.JLabel();
        lblSales = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtUserName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtNIC = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        btnCreate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txtConfirmPassword = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbRole = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtUserIDMan = new javax.swing.JTextField();
        txtUserNameMan = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtEmailMan = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetailsTable = new javax.swing.JTable();
        btnClear = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cmbRoleFilter = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtNICMan = new javax.swing.JTextField();
        txtPasswordMan = new javax.swing.JTextField();
        cmbRoleMan = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtSearchManage = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAvailable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableAssigned = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Managerscmb = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtrepName = new javax.swing.JTextField();
        txtrepPhone = new javax.swing.JTextField();
        cmbrepRegion = new javax.swing.JComboBox<>();
        txtrepTarget = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblReps = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        cmbrepFilter = new javax.swing.JComboBox<>();
        txtrepSearch = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        jTextField3.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(" Auxano PVT LTD.");
        setResizable(false);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("USER");
        lblUser.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 102, 0), null, null));
        jPanel3.add(lblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 96, 86, -1));

        lblProducts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProducts.setText("PRODUCTS");
        lblProducts.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblProductsMouseClicked(evt);
            }
        });
        jPanel3.add(lblProducts, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 136, 86, -1));

        lblPurchase.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPurchase.setText("PURCHASE");
        lblPurchase.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblPurchase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPurchaseMouseClicked(evt);
            }
        });
        jPanel3.add(lblPurchase, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 176, 86, -1));

        lblSales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSales.setText("SALES");
        lblSales.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalesMouseClicked(evt);
            }
        });
        jPanel3.add(lblSales, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 216, 86, -1));

        lblStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStock.setText("STOCK");
        lblStock.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStockMouseClicked(evt);
            }
        });
        jPanel3.add(lblStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 256, 86, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/AUXANO-Logo2.png"))); // NOI18N
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 25, 98, 39));

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel22.setText("USE THE FORM BELOW TO CREATE A NEW USER");

        jPanel17.setBackground(new java.awt.Color(153, 153, 153));
        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel29.setText("CREATE NEW USER");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserNameKeyPressed(evt);
            }
        });

        jLabel15.setText("USER NAME:");

        jLabel31.setText("EMAIL");

        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });

        txtNIC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNICKeyPressed(evt);
            }
        });

        jLabel32.setText("NIC NUMBER:");

        jLabel33.setText("PASSWORD:");

        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        jLabel34.setText("CONFIRM PASSWORD:");

        btnCreate.setText("CREATE USER");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtConfirmPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtConfirmPasswordKeyPressed(evt);
            }
        });

        jLabel13.setText("ROLE:");

        cmbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "SALES MANAGER", "STOCK CONTROLLER" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(331, 331, 331)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCreate))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34)
                            .addComponent(jLabel15)
                            .addComponent(jLabel13))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPassword)
                            .addComponent(txtNIC)
                            .addComponent(txtEmail)
                            .addComponent(txtUserName)
                            .addComponent(txtConfirmPassword)
                            .addComponent(cmbRole, 0, 268, Short.MAX_VALUE))))
                .addContainerGap(339, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtNIC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(58, 58, 58)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnCancel))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel30.setText("CREATE NEW USER");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(0, 881, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CREATE USER ACCOUNT", jPanel4);

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel20.setText("USE THE FORM BELOW TO MANAGE USER ACCOUNTS");

        jPanel14.setBackground(new java.awt.Color(153, 153, 153));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel21.setText("MANAGE USERS");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(803, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel18MouseClicked(evt);
            }
        });

        jLabel10.setText("USER ID:");

        txtUserIDMan.setEditable(false);
        txtUserIDMan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserIDManActionPerformed(evt);
            }
        });

        jLabel23.setText("USER NAME: ");

        jLabel41.setText("EMAIL:");

        jLabel42.setText("NIC NUMBER:");

        btnUpdate.setText("UPDATE");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tblDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "USER ID", "USER NAME", "EMAIL", "NIC NUMBER", "PASSWORD", "ROLE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDetailsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetailsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDetailsTable);

        btnClear.setText("CLEAR");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel7.setText("FILTER BY ROLES:");

        cmbRoleFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NONE", "ADMIN", "STOCK CONTROLLER", "SALES MANAGER" }));
        cmbRoleFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRoleFilterActionPerformed(evt);
            }
        });

        jLabel8.setText("PASSOWORD:");

        txtPasswordMan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordManActionPerformed(evt);
            }
        });

        cmbRoleMan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "SALES MANAGER", "STOCK CONTROLLER" }));

        jLabel12.setText("ROLE:");

        jLabel11.setText("SEARCH:");

        btnSearch.setText("SEARCH");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbRoleFilter, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addGap(65, 65, 65)
                            .addComponent(cmbRoleMan, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                            .addComponent(txtUserIDMan, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtUserNameMan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                    .addComponent(jLabel42)
                                    .addGap(25, 25, 25))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18))
                                .addGroup(jPanel18Layout.createSequentialGroup()
                                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel41)
                                        .addComponent(jLabel23))
                                    .addGap(26, 26, 26)))
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNICMan)
                                .addComponent(txtPasswordMan, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                .addComponent(txtEmailMan)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchManage, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(btnClear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cmbRoleFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtSearchManage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtUserIDMan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtUserNameMan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(txtEmailMan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(txtNICMan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPasswordMan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbRoleMan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnClear))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel6.setText("MANAGE USER ACCOUNTS");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("MANAGE USER ACCOUNTS", jPanel7);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel9.setText("MANAGE SALES REPRESENTATIVES");

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel24.setText("USE THE FORM BELOW TO MANAGE SALES REPRESENTATIVES");

        jPanel16.setBackground(new java.awt.Color(153, 153, 153));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel25.setText("CREATE SALES REPRESENTATIVES");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("ASSIGN SALES REPRESENTATIVES");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 548, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(21, 21, 21))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jPanel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel19MouseClicked(evt);
            }
        });

        jTableAvailable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AVAILABLE SALES REPS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableAvailable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTableAvailable);
        if (jTableAvailable.getColumnModel().getColumnCount() > 0) {
            jTableAvailable.getColumnModel().getColumn(0).setResizable(false);
        }

        jTableAssigned.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ASSIGNED SALES REPS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableAssigned.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTableAssigned);
        if (jTableAssigned.getColumnModel().getColumnCount() > 0) {
            jTableAssigned.getColumnModel().getColumn(0).setResizable(false);
        }

        jButton1.setText("ASSIGN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("REMOVE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        Managerscmb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        Managerscmb.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ManagerscmbPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                ManagerscmbPopupMenuWillBecomeVisible(evt);
            }
        });
        Managerscmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManagerscmbActionPerformed(evt);
            }
        });

        jLabel3.setText("SELECT SALES MANAGER:");

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 12, 0, 0, java.awt.SystemColor.scrollbar));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel5.setText("SALES REP NAME:");

        jLabel16.setText("PHONE NUMBER:");

        jLabel17.setText("REGION:");

        jLabel19.setText("TARGET AMT:");

        cmbrepRegion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CENTRAL", "EAST", "SOUTH", "WEST", "NORTH" }));

        tblReps.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "PHONE NO", "REGION", "TARGET AMT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblReps.getTableHeader().setReorderingAllowed(false);
        tblReps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRepsMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblReps);
        if (tblReps.getColumnModel().getColumnCount() > 0) {
            tblReps.getColumnModel().getColumn(0).setResizable(false);
            tblReps.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblReps.getColumnModel().getColumn(1).setResizable(false);
            tblReps.getColumnModel().getColumn(2).setResizable(false);
            tblReps.getColumnModel().getColumn(3).setResizable(false);
            tblReps.getColumnModel().getColumn(4).setResizable(false);
        }

        jButton4.setText("ADD");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("UPDATE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("DELETE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel26.setText("FILTER BY REGION:");

        cmbrepFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "CENTRAL", "EAST", "SOUTH", "WEST", "NORTH" }));
        cmbrepFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbrepFilterActionPerformed(evt);
            }
        });

        jButton8.setText("SEARCH");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel26))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtrepPhone)
                                        .addComponent(txtrepName)
                                        .addComponent(cmbrepRegion, javax.swing.GroupLayout.Alignment.LEADING, 0, 175, Short.MAX_VALUE))
                                    .addComponent(cmbrepFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                        .addComponent(txtrepSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton8))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addGap(12, 12, 12)
                                        .addComponent(txtrepTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane4)))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)))
                .addGap(26, 26, 26)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(Managerscmb, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Managerscmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addGap(132, 132, 132)
                                        .addComponent(jButton1)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel26)
                                    .addComponent(cmbrepFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtrepSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton8))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtrepName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19)
                                    .addComponent(txtrepTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(txtrepPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(cmbrepRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton5)
                            .addComponent(jButton6))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("MANAGE SALES REPS", jPanel1);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lgoutS.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1170, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserIDManActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserIDManActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserIDManActionPerformed

    private void txtPasswordManActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordManActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordManActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        boolean email = false;
        boolean NICCheck = false;
        boolean pass = false;

        try {
            if ((txtUserName.getText().equals("")) || txtEmail.getText().equals("") || txtNIC.getText().equals("") || txtPassword.getText().equals("") || txtConfirmPassword.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Please fill out the blank fields");
            } else {
                if (!(txtEmail.getText().equals(""))) {
                    String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
                    if (!(txtEmail.getText().matches(regex))) {
                        JOptionPane.showMessageDialog(rootPane, "Please enter a valid email address.");
                    } else {
                        email = true;
                    }
                }

                if (!(txtNIC.getText().equals(""))) {
                    String NICregex = "[0-9]{9}[x|X|v|V]$";
                    if (!(txtNIC.getText().matches(NICregex))) {
                        JOptionPane.showMessageDialog(rootPane, "enter valid NIC");
                    } else {
                        NICCheck = true;
                    }
                }

                if (!(txtPassword.getText().equals(txtConfirmPassword.getText()))) {
                    JOptionPane.showMessageDialog(rootPane, "Password does not match, ERROR");
                } else {
                    pass = true;
                }
            }

            if ((pass == true) && (email == true) && (NICCheck == true)) {
                String UserName = txtUserName.getText();
                String Email = txtEmail.getText();
                String NIC = txtNIC.getText();
                String Password = txtPassword.getText();
                String ConfirmPassword = txtConfirmPassword.getText();
                String Role = cmbRole.getItemAt(cmbRole.getSelectedIndex());

                String query = "INSERT INTO user_tab(userName,email,nic,password,role) VALUES('" + UserName + "','" + Email + "','" + NIC + "','" + Password + "','" + Role + "')";
                try {
                    Connection con = dbConnect.getConnection();
                    Statement st = con.createStatement();
                    int execute = st.executeUpdate(query);

                    JOptionPane.showMessageDialog(rootPane, "Information Successfully Added");
                    con.close();
                    st.close();

                    txtUserName.setText("");
                    txtEmail.setText("");
                    txtNIC.setText("");
                    txtPassword.setText("");
                    txtConfirmPassword.setText("");
                    ShowUsers();
                    fillCombo();
                    fillReps();
                } catch (HeadlessException | SQLException a) {
                    JOptionPane.showMessageDialog(null, a);
                }
            }
        } catch (HeadlessException e) {
        }
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        txtUserName.setText("");
        txtEmail.setText("");
        txtNIC.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");

// TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed

        txtEmailMan.setText("");
        txtNICMan.setText("");
        txtUserIDMan.setText("");
        txtSearchManage.setText("");
        txtUserNameMan.setText("");
        txtPasswordMan.setText("");
        cmbRoleFilter.setSelectedIndex(0);

// TODO add your handling code here:
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

        if (tblDetailsTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Select the user record you want to Update!");
        } else {

            String UserID = txtUserIDMan.getText();
            String UserName = txtUserNameMan.getText();
            String Email = txtEmailMan.getText();
            String NIC = txtNICMan.getText();
            String Password = txtPasswordMan.getText();
            String Role = cmbRoleMan.getSelectedItem().toString();

            DefaultTableModel model = (DefaultTableModel) tblDetailsTable.getModel();

            if (!(UserID.equals("")) && !(UserName.equals("")) && !(Email.equals("")) && !(NIC.equals("")) && !(Password.equals("")) && !(Role.equals(""))) {

                String query = "UPDATE user_tab SET userName='" + UserName + "',email='" + Email + "',nic='" + NIC + "',password='" + Password + "',role='" + Role + "' WHERE userID=" + UserID + ";";
                try {
                    Connection con = dbConnect.getConnection();
                    Statement st = con.createStatement();
                    int execute = st.executeUpdate(query);

                    JOptionPane.showMessageDialog(rootPane, "Updated Successfully.");

                    con.close();
                    st.close();
                    txtEmailMan.setText("");
                    txtNICMan.setText("");
                    txtUserIDMan.setText("");
                    txtSearchManage.setText("");
                    txtUserNameMan.setText("");
                    txtPasswordMan.setText("");
                    ShowUsers();
                    fillCombo();
                    fillReps();

                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Fill in the blank fields!");
            }

        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void lblProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProductsMouseClicked
        if (UserModel.userRole.equals("ADMIN")) {
            AddProduct proframe = new AddProduct();
            proframe.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this tab.");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_lblProductsMouseClicked

    private void lblPurchaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPurchaseMouseClicked
        if ((UserModel.userRole.equals("STOCK CONTROLLER")) || (UserModel.userRole.equals("ADMIN"))) {
            Purchase frame = new Purchase();
            frame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this tab.");
        }
// TODO add your handling code here:
    }//GEN-LAST:event_lblPurchaseMouseClicked

    private void lblSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalesMouseClicked

        if ((UserModel.userRole.equals("SALES MANAGER")) || (UserModel.userRole.equals("ADMIN"))) {
            Sales frame = new Sales();
            frame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this tab.");
        }
// TODO add your handling code here:
    }//GEN-LAST:event_lblSalesMouseClicked

    private void lblStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblStockMouseClicked

        if ((UserModel.userRole.equals("STOCK CONTROLLER")) || (UserModel.userRole.equals("SALES MANAGER")) || (UserModel.userRole.equals("ADMIN"))) {
            Stock frame = new Stock();
            frame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this tab.");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_lblStockMouseClicked

    private void tblDetailsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetailsTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) tblDetailsTable.getModel();
        int row = tblDetailsTable.getSelectedRow();
        String v1 = tblDetailsTable.getModel().getValueAt(row, 0).toString();
        txtUserIDMan.setText(v1);

        String v2 = tblDetailsTable.getModel().getValueAt(row, 1).toString();
        txtUserNameMan.setText(v2);

        String v3 = tblDetailsTable.getModel().getValueAt(row, 2).toString();
        txtEmailMan.setText(v3);

        String v4 = tblDetailsTable.getModel().getValueAt(row, 3).toString();
        txtNICMan.setText(v4);

        String v5 = tblDetailsTable.getModel().getValueAt(row, 4).toString();
        txtPasswordMan.setText(v5);

        String v6 = tblDetailsTable.getModel().getValueAt(row, 5).toString();

        if (v6.equalsIgnoreCase("ADMIN")) {
            cmbRoleMan.setSelectedItem("ADMIN");
        }

        if (v6.equalsIgnoreCase("SALES MANAGER")) {
            cmbRoleMan.setSelectedItem("SALES MANAGER");
        }

        if (v6.equalsIgnoreCase("STOCK CONTROLLER")) {
            cmbRoleMan.setSelectedItem("STOCK CONTROLLER");
        }

    }//GEN-LAST:event_tblDetailsTableMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (tblDetailsTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Select the product you want to delete!");
        } else {

            int pop = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "", pop);
            if (result == 0) {
                int row = tblDetailsTable.getSelectedRow();
                int pid = Integer.parseInt(tblDetailsTable.getValueAt(row, 0).toString());

                String query = "DELETE FROM user_tab WHERE userID=" + pid + ";";
                try {
                    Connection con = dbConnect.getConnection();
                    Statement st = con.createStatement();
                    int execute = st.executeUpdate(query);

                    txtEmailMan.setText("");
                    txtNICMan.setText("");
                    txtUserIDMan.setText("");
                    txtSearchManage.setText("");
                    txtUserNameMan.setText("");
                    txtPasswordMan.setText("");

                    JOptionPane.showMessageDialog(rootPane, "Deleted Successfully");
                    con.close();
                    st.close();

                    ShowUsers();
                    fillCombo();
                    fillReps();

                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }

            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

        String search = txtSearchManage.getText();
        String roleFilter = cmbRoleFilter.getSelectedItem().toString();

        if (search.equals("")) {
            JOptionPane.showMessageDialog(null, "Search field is empty!");
        } else {
            ArrayList<UserModel> list = getOrderList();
            DefaultTableModel model = (DefaultTableModel) tblDetailsTable.getModel();
            model.setRowCount(0);

            Object[] row = new Object[6];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getUserID();
                row[1] = list.get(i).getUserName();
                row[2] = list.get(i).getEmail();
                row[3] = list.get(i).getNic();
                row[4] = list.get(i).getPassword();
                row[5] = list.get(i).getRole();

                if (roleFilter.equalsIgnoreCase("NONE")) {
                    if (list.get(i).getUserName().contains(search) || list.get(i).getEmail().contains(search)) {
                        model.addRow(row);
                    }
                }

                if (list.get(i).getRole().equals(roleFilter)) {
                    if (list.get(i).getUserName().contains(search) || list.get(i).getEmail().contains(search)) {
                        model.addRow(row);
                    }
                }

            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int pop = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", pop);
        if (result == 0) {

            UserModel.loginName = "";
            UserModel.userRole = "";

            Login frame = new Login();
            frame.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void cmbRoleFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRoleFilterActionPerformed

        String roleFilter = cmbRoleFilter.getSelectedItem().toString();
        txtEmailMan.setText("");
        txtNICMan.setText("");
        txtUserIDMan.setText("");
        txtSearchManage.setText("");
        txtUserNameMan.setText("");
        txtPasswordMan.setText("");

        ArrayList<UserModel> list = getOrderList();
        DefaultTableModel model = (DefaultTableModel) tblDetailsTable.getModel();
        model.setRowCount(0);

        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getUserID();
            row[1] = list.get(i).getUserName();
            row[2] = list.get(i).getEmail();
            row[3] = list.get(i).getNic();
            row[4] = list.get(i).getPassword();
            row[5] = list.get(i).getRole();

            if (roleFilter.equalsIgnoreCase("NONE")) {
                model.addRow(row);
            }

            if (roleFilter.equalsIgnoreCase("ADMIN")) {
                if (list.get(i).getRole().equalsIgnoreCase("ADMIN")) {
                    model.addRow(row);
                }
            }

            if (roleFilter.equalsIgnoreCase("STOCK CONTROLER")) {
                if (list.get(i).getRole().equalsIgnoreCase("STOCK CONTROLER")) {
                    model.addRow(row);
                }
            }

            if (roleFilter.equalsIgnoreCase("SALES MANAGER")) {
                if (list.get(i).getRole().equalsIgnoreCase("SALES MANAGER")) {
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_cmbRoleFilterActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        boolean phonebool = false;
        boolean targetbool = false;
        String name = txtrepName.getText();
        String phone = txtrepPhone.getText();
        String region = cmbrepRegion.getSelectedItem().toString();
        String targetval = txtrepTarget.getText();
        double target;

        if (name.equals("") || phone.equals("") || targetval.equals("")) {
            JOptionPane.showMessageDialog(null, "Fill in the blank fields!");
        } else {

            try {

                target = Double.parseDouble(targetval);
                targetbool = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid target value entered!");
            }

            try {
                int phn = Integer.parseInt(phone);
                if (phone.length() == 10) {
                    phonebool = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid phone number entered!");
            }

            if (targetbool == true && phonebool == true) {

                String query = "INSERT INTO reps_tab(repName,repPhone,repRegion,repTarget) VALUES('" + name + "','" + phone + "','" + region + "'," + Double.parseDouble(targetval) + ")";
                try {
                    Connection con = dbConnect.getConnection();
                    Statement st = con.createStatement();
                    int execute = st.executeUpdate(query);

                    con.close();
                    st.close();
                    ShowReps();
                    fillReps();

                    txtrepName.setText("");
                    txtrepPhone.setText("");
                    txtrepTarget.setText("");
                    cmbrepRegion.setSelectedIndex(0);

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error!");
                }

            }

        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        if (tblReps.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Select a row to delete!");
        } else {

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are sure you want to delete?", "Delete item", dialogButton);

            if (dialogResult == 0) {
                DefaultTableModel model = (DefaultTableModel) tblReps.getModel();
                String repID = model.getValueAt(tblReps.getSelectedRow(), 0).toString();

                String query = "DELETE FROM reps_tab WHERE repID = " + repID + ";";

                try {
                    Connection con = dbConnect.getConnection();
                    Statement st = con.createStatement();
                    int execute = st.executeUpdate(query);

                    ShowReps();
                    fillReps();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tblRepsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRepsMouseClicked
        // TODO add your handling code here:

        Managerscmb.setSelectedIndex(0);
        DefaultTableModel model = (DefaultTableModel) jTableAssigned.getModel();
        model.setRowCount(0);

        int row = tblReps.getSelectedRow();
        temprepName = tblReps.getValueAt(row, 1).toString();

        txtrepName.setText(tblReps.getValueAt(row, 1).toString());
        txtrepPhone.setText(tblReps.getValueAt(row, 2).toString());
        cmbrepRegion.setSelectedItem(tblReps.getValueAt(row, 3).toString());
        txtrepTarget.setText(tblReps.getValueAt(row, 4).toString());

    }//GEN-LAST:event_tblRepsMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        boolean phonebool = false;
        boolean targetbool = false;
        String name = txtrepName.getText();
        String phone = txtrepPhone.getText();
        String region = cmbrepRegion.getSelectedItem().toString();
        String targetval = txtrepTarget.getText();
        double target;

        if (tblReps.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Select a row to update!");

        } else {
            DefaultTableModel model = (DefaultTableModel) tblReps.getModel();
            String id = model.getValueAt(tblReps.getSelectedRow(), 0).toString();

            if (name.equals("") || phone.equals("") || targetval.equals("")) {
                JOptionPane.showMessageDialog(null, "Fill in the blank fields!");
            } else {

                try {

                    target = Double.parseDouble(targetval);
                    targetbool = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid target value entered!");
                }

                try {
                    int phn = Integer.parseInt(phone);
                    if (phone.length() == 10) {
                        phonebool = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid phone number entered!");
                }

                if (targetbool == true && phonebool == true) {

                    String query = "UPDATE reps_tab  SET repName = '" + name + "', repPhone = '" + phone + "', repRegion = '" + region + "', repTarget = " + Double.parseDouble(targetval) + " where repID = '"+id+"';";
                    try {
                        Connection con = dbConnect.getConnection();
                        Statement st = con.createStatement();
                        int execute = st.executeUpdate(query);

                        con.close();
                        st.close();
                        ShowReps();
                        fillReps();
                        temprepName = "";

                        txtrepName.setText("");
                        txtrepPhone.setText("");
                        txtrepTarget.setText("");
                        cmbrepRegion.setSelectedIndex(0);

                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }

                }

            }
        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void ManagerscmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManagerscmbActionPerformed
        // TODO add your handling code here:
        if (RepModel.control == true) {
            try {
                Connection con = dbConnect.getConnection();
                int manID = getManID(Managerscmb.getSelectedItem().toString());
                String query1 = "select a.repName from reps_tab a, assigned_tab b  where a.repID = b.repID and b.manID = " + manID + ";";
                DefaultTableModel model = (DefaultTableModel) jTableAssigned.getModel();
                model.setRowCount(0);
                ResultSet rs;
                PreparedStatement pst = con.prepareStatement(query1);
                rs = pst.executeQuery();

                Object[] row = new Object[1];
                while (rs.next()) {
                    row[0] = rs.getString("repName");

                    model.addRow(row);
                }
                con.close();
                pst.close();
                rs.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_ManagerscmbActionPerformed

    private void ManagerscmbPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ManagerscmbPopupMenuWillBecomeVisible
        // TODO add your handling code here:
        RepModel.control = true;
    }//GEN-LAST:event_ManagerscmbPopupMenuWillBecomeVisible

    private void ManagerscmbPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ManagerscmbPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        RepModel.control = false;
    }//GEN-LAST:event_ManagerscmbPopupMenuWillBecomeInvisible

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
        Managerscmb.setSelectedIndex(0);
        DefaultTableModel model = (DefaultTableModel) jTableAssigned.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTableAssigned.getModel();
        DefaultTableModel model2 = (DefaultTableModel) jTableAvailable.getModel();
        String manName = Managerscmb.getSelectedItem().toString();
        int count = jTableAssigned.getRowCount();
        Object[] row = new Object[1];
        if (jTableAvailable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Select a sales representative to assign!");
        } else if (Managerscmb.getSelectedItem().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "Select a Manager");
        } else {

            if (count < 3) {
                String repName = jTableAvailable.getValueAt(jTableAvailable.getSelectedRow(), 0).toString();
                row[0] = repName;
                model.addRow(row);
                int repID = getRepID(repName);
                int manID = getManID(manName);
                String query = "INSERT INTO assigned_tab(repID,manID) VALUES(" + repID + "," + manID + ");";
                try {
                    Connection con = dbConnect.getConnection();
                    Statement st = con.createStatement();
                    int execute = st.executeUpdate(query);
                    fillReps();

                    con.close();
                    st.close();

                } catch (SQLException e) {
                }

            } else {
                JOptionPane.showMessageDialog(null, "Max has Reached");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTableAssigned.getModel();
        DefaultTableModel model2 = (DefaultTableModel) jTableAvailable.getModel();
        String manName = Managerscmb.getSelectedItem().toString();
        int count = jTableAssigned.getRowCount();

        if (jTableAssigned.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Select a sales representative to remove!");
        } else if (Managerscmb.getSelectedItem().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "Select a Manager");
        } else {

            String repName = jTableAssigned.getValueAt(jTableAssigned.getSelectedRow(), 0).toString();
            int repID = getRepID(repName);
            String query = "DELETE FROM assigned_tab where repID = " + repID + ";";
            try {
                Connection con = dbConnect.getConnection();
                Statement st = con.createStatement();
                int execute = st.executeUpdate(query);
                fillReps();
                model.removeRow(jTableAssigned.getSelectedRow());
                con.close();
                st.close();

            } catch (SQLException e) {
            }

        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void cmbrepFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbrepFilterActionPerformed
        // TODO add your handling code here:
        String filter = cmbrepFilter.getSelectedItem().toString();
        ArrayList<RepModel> list = getRepList();
        DefaultTableModel model = (DefaultTableModel) tblReps.getModel();
        model.setRowCount(0);

        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getRepID();
            row[1] = list.get(i).getRepName();
            row[2] = list.get(i).getRepPhone();
            row[3] = list.get(i).getRepRegion();
            row[4] = list.get(i).getRepTarget();

            if (filter.equals(" ")) {
                model.addRow(row);
            }

            if (list.get(i).getRepRegion().equals(filter)) {
                model.addRow(row);
            }

        }

    }//GEN-LAST:event_cmbrepFilterActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        String search = txtrepSearch.getText();
        String filter = cmbrepFilter.getSelectedItem().toString();

        if (search.equals("")) {
            JOptionPane.showMessageDialog(null, "Search field is empty!");
        } else {
            ArrayList<RepModel> list = getRepList();
            DefaultTableModel model = (DefaultTableModel) tblReps.getModel();
            model.setRowCount(0);

            Object[] row = new Object[6];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getRepID();
                row[1] = list.get(i).getRepName();
                row[2] = list.get(i).getRepPhone();
                row[3] = list.get(i).getRepRegion();
                row[4] = list.get(i).getRepTarget();

                if (filter.equals(" ")) {
                    if (list.get(i).getRepName().contains(search)) {
                        model.addRow(row);
                    }
                }

                if (list.get(i).getRepRegion().equals(filter)) {
                    if (list.get(i).getRepName().contains(search)) {
                        model.addRow(row);
                    }
                }

            }
        }

    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtUserNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserNameKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txtEmail.requestFocus();
        }
    }//GEN-LAST:event_txtUserNameKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txtUserName.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txtNIC.requestFocus();
        }
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtNICKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNICKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txtEmail.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txtPassword.requestFocus();
        }
    }//GEN-LAST:event_txtNICKeyPressed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txtNIC.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txtConfirmPassword.requestFocus();
        }
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void txtConfirmPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConfirmPasswordKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txtPassword.requestFocus();
        }
    }//GEN-LAST:event_txtConfirmPasswordKeyPressed

    private void jPanel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseClicked
        // TODO add your handling code here
        tblDetailsTable.clearSelection();
        txtEmailMan.setText("");
        txtNICMan.setText("");
        txtUserIDMan.setText("");
        txtSearchManage.setText("");
        txtUserNameMan.setText("");
        txtPasswordMan.setText("");
    }//GEN-LAST:event_jPanel18MouseClicked

    private void jPanel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseClicked
        // TODO add your handling code here:
        tblReps.clearSelection();
        txtrepName.setText("");
        txtrepPhone.setText("");
        txtrepTarget.setText("");
        cmbrepRegion.setSelectedIndex(0);
    }//GEN-LAST:event_jPanel19MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CreateAccount().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Managerscmb;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbRole;
    private javax.swing.JComboBox<String> cmbRoleFilter;
    private javax.swing.JComboBox<String> cmbRoleMan;
    private javax.swing.JComboBox<String> cmbrepFilter;
    private javax.swing.JComboBox<String> cmbrepRegion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JTable jTableAssigned;
    private javax.swing.JTable jTableAvailable;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblProducts;
    private javax.swing.JLabel lblPurchase;
    private javax.swing.JLabel lblSales;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblUser;
    private javax.swing.JTable tblDetailsTable;
    public static javax.swing.JTable tblReps;
    private javax.swing.JTextField txtConfirmPassword;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmailMan;
    private javax.swing.JTextField txtNIC;
    private javax.swing.JTextField txtNICMan;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPasswordMan;
    private javax.swing.JTextField txtSearchManage;
    private javax.swing.JTextField txtUserIDMan;
    private javax.swing.JTextField txtUserName;
    private javax.swing.JTextField txtUserNameMan;
    private javax.swing.JTextField txtrepName;
    private javax.swing.JTextField txtrepPhone;
    private javax.swing.JTextField txtrepSearch;
    private javax.swing.JTextField txtrepTarget;
    // End of variables declaration//GEN-END:variables

    private String txtUserID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
