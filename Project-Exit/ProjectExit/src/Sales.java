/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.DefaultCellEditor;
import java.sql.Connection;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Models.DatabaseConnection;
import Models.SalesModel;
import Models.UserModel;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

//Check changes
/**
 *
 * @author it16350342
 */
public class Sales extends javax.swing.JFrame {

    /**
     * Creates new form
     */
    SalesItemsView viewItems = new SalesItemsView();
    SalesItemsEdit editItems = new SalesItemsEdit();
    SalesItemsAdd addItems = new SalesItemsAdd();
    SalesReviewWindow reviewSales = new SalesReviewWindow();
    SalesReport saleReport = new SalesReport();

    public Sales() {
        initComponents();
        URL iconURL = getClass().getResource("Images/AUXANO ICON 20.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
        ShowSales();
        ShowReviewSales();
        fillreps();
        fillReviewreps();
        fillViewreps();
        fillViewmans();
        txtUser.setText(UserModel.loginName);
        lblQtySum.setText(Integer.toString(getTotalQuantity()));
        lblTotalAmt.setText(Integer.toString(getTotalAmount()));

        tblCreateSO.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.DELETE || e.getType() == TableModelEvent.UPDATE) {
                    lblQtySum.setText(getTotalQuantity() + "");
                    lblTotalAmt.setText(getTotalAmount() + "");
                }
            }
        });

    }

    public ArrayList<SalesModel> getOrderList() {

        ArrayList<SalesModel> orderList = new ArrayList<SalesModel>();
        Connection con = dbConnect.getConnection();
        String query = "SELECT * FROM sales_tab";

        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            SalesModel sales;

            while (rs.next()) {
                sales = new SalesModel(rs.getInt("soNumber"), rs.getString("orderedDate"), rs.getString("customerName"), rs.getString("customerPhone"), rs.getString("reqDate"), rs.getString("salesRep"), rs.getString("region"), rs.getString("orderCreatedBy"), rs.getString("orderStatus"), rs.getInt("total"));
                orderList.add(sales);
            }
            con.close();
            st.close();
            rs.close();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return orderList;
    }

    public void ShowReviewSales() {

        ArrayList<SalesModel> list = getOrderList();
        DefaultTableModel model = (DefaultTableModel) tblReviewSales.getModel();
        model.setRowCount(0);
        String login = UserModel.loginName;

        Object[] row = new Object[8];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getSONo();
            row[1] = list.get(i).getOrderDate();
            row[2] = list.get(i).getReqDate();
            row[3] = list.get(i).getCusName();
            row[4] = list.get(i).getOrderCreator();
            row[5] = list.get(i).getRepName();
            row[6] = list.get(i).getTotal();
            row[7] = list.get(i).getStatus();

            if (UserModel.userRole.equals("SALES MANAGER")) {
                if (list.get(i).getOrderCreator().equals(login)) {
                    model.addRow(row);
                }
            }
            if (UserModel.userRole.equals("ADMIN")) {
                model.addRow(row);
            }

        }
    }

    public void ShowSales() {

        ArrayList<SalesModel> list = getOrderList();
        DefaultTableModel model = (DefaultTableModel) tblViewSalesOrders.getModel();
        model.setRowCount(0);

        Object[] row = new Object[9];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getSONo();
            row[1] = list.get(i).getOrderDate();
            row[2] = list.get(i).getReqDate();
            row[3] = list.get(i).getCusName();
            row[4] = list.get(i).getOrderCreator();
            row[5] = list.get(i).getRepName();
            row[6] = list.get(i).getRegion();
            row[7] = list.get(i).getStatus();
            row[8] = list.get(i).getTotal();

            model.addRow(row);
        }
    }

    public boolean getValidation(int soNum) {

        Connection con = dbConnect.getConnection();
        Statement st;
        ResultSet rs;
        Boolean stat = false;
        String req = "SELECT soNumber FROM sales_tab where soNumber = " + soNum + "";
        try {
            st = con.createStatement();
            rs = st.executeQuery(req);
            rs.isBeforeFirst();
            stat = rs.isBeforeFirst();

        } catch (SQLException e) {
        }
        return stat;
    }

    DatabaseConnection dbConnect = new DatabaseConnection();

    public String search;
    public String searchCmb1;
    public String searchCmb2;
    public String searchCmb3;
    public String searchCmb4;
    public String search1;
    public String search2;

    public int count;
    public int index;

    public int getTotalQuantity() {
        int rowcount = tblCreateSO.getRowCount();
        int total = rowcount;
        return total;
    }

    public int getTotalAmount() {
        int rowcount = tblCreateSO.getRowCount();
        int total = 0;
        for (int i = 0; i < rowcount; i++) {
            total += Integer.parseInt(tblCreateSO.getValueAt(i, 5).toString());
        }
        return total;
    }

    public void fillreps() {
        try {
            ResultSet rs;
            PreparedStatement pst;
            cmbSalesRep.removeAllItems();
            String query = "";
            if (UserModel.userRole.equals("SALES MANAGER")) {
                query = "select a.repName from reps_tab a, assigned_tab b  where a.repID = b.repID and b.manID = " + UserModel.UserID + ";";
            }
            if (UserModel.userRole.equals("ADMIN")) {
                query = "select a.repName from reps_tab a, assigned_tab b  where a.repID = b.repID;";
            }
            try (Connection con = dbConnect.getConnection()) {

                pst = con.prepareStatement(query);
                rs = pst.executeQuery();
                while (rs.next()) {
                    cmbSalesRep.addItem(rs.getString("repName"));
                }
            }
            pst.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void fillReviewreps() {
        try {
            ResultSet rs;
            PreparedStatement pst;
            cmbSearchSalesRep.removeAllItems();
            cmbSearchSalesRep.addItem("NONE");
            String query = "";
            if (UserModel.userRole.equals("SALES MANAGER")) {
                query = "select a.repName from reps_tab a, assigned_tab b  where a.repID = b.repID and b.manID = " + UserModel.UserID + ";";
            }
            if (UserModel.userRole.equals("ADMIN")) {
                query = "select a.repName from reps_tab a, assigned_tab b  where a.repID = b.repID;";
            }
            try (Connection con = dbConnect.getConnection()) {

                pst = con.prepareStatement(query);
                rs = pst.executeQuery();
                while (rs.next()) {
                    cmbSearchSalesRep.addItem(rs.getString("repName"));
                }
            }
            pst.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void fillViewreps() {
        try {
            ResultSet rs;
            PreparedStatement pst;
            cmbFilterSaleRep2.removeAllItems();
            cmbFilterSaleRep2.addItem("NONE");
            String query = "";
            if (UserModel.userRole.equals("SALES MANAGER")) {
                query = "select a.repName from reps_tab a, assigned_tab b  where a.repID = b.repID and b.manID = " + UserModel.UserID + ";";
            }
            if (UserModel.userRole.equals("ADMIN")) {
                query = "select a.repName from reps_tab a, assigned_tab b  where a.repID = b.repID;";
            }
            try (Connection con = dbConnect.getConnection()) {

                pst = con.prepareStatement(query);
                rs = pst.executeQuery();
                while (rs.next()) {
                    cmbFilterSaleRep2.addItem(rs.getString("repName"));
                }
            }
            pst.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void fillViewmans() {
        if (UserModel.userRole.equals("SALES MANAGER")) {
            cmbFilterSalesMan.removeAllItems();
            cmbFilterSalesMan.addItem(UserModel.loginName);
        } else {

            try {
                ResultSet rs;
                PreparedStatement pst;
                cmbFilterSalesMan.removeAllItems();
                String query = "";
                cmbFilterSalesMan.addItem("NONE");
                query = "select userName from user_tab where role = 'SALES MANAGER' ";

                try (Connection con = dbConnect.getConnection()) {

                    pst = con.prepareStatement(query);
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        cmbFilterSalesMan.addItem(rs.getString("userName"));
                    }
                }
                pst.close();
                rs.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox9 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        lblProducts = new javax.swing.JLabel();
        lblPurchase = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtCustomerName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtCustomerPhone = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSONumber = new javax.swing.JTextField();
        dpReqDate = new org.jdesktop.swingx.JXDatePicker();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        cmbSalesRep = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCreateSO = new javax.swing.JTable();
        btnCreate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cmbRegion = new javax.swing.JComboBox<>();
        lblErrorCN = new javax.swing.JLabel();
        lblErrorCP = new javax.swing.JLabel();
        lblErrorRD = new javax.swing.JLabel();
        lblErrorSR = new javax.swing.JLabel();
        lblErrorR = new javax.swing.JLabel();
        lblErrorOS = new javax.swing.JLabel();
        btnAddTab = new javax.swing.JButton();
        btnEditTab = new javax.swing.JButton();
        btnDeleteTab = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblQtySum = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTotalAmt = new javax.swing.JLabel();
        cmbOrderStatus = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        lblErrorSON = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblReviewSales = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtSearchSONum = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        dpFrom = new org.jdesktop.swingx.JXDatePicker();
        jLabel16 = new javax.swing.JLabel();
        dpTo = new org.jdesktop.swingx.JXDatePicker();
        btnUpdateSales = new javax.swing.JButton();
        btnDeleteSales = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        cmbSearchSalesRep = new javax.swing.JComboBox<>();
        btnViewTab = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnClearFilters = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblViewSalesOrders = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        dpFrom2 = new org.jdesktop.swingx.JXDatePicker();
        jLabel26 = new javax.swing.JLabel();
        dpTo2 = new org.jdesktop.swingx.JXDatePicker();
        cmbFilterSalesMan = new javax.swing.JComboBox<>();
        cmbFilerStatus = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        cmbFilterSaleRep2 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(" Auxano PVT LTD.");
        setResizable(false);

        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("USER");
        lblUser.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserMouseClicked(evt);
            }
        });

        lblProducts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProducts.setText("PRODUCTS");
        lblProducts.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblProductsMouseClicked(evt);
            }
        });

        lblPurchase.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPurchase.setText("PURCHASE");
        lblPurchase.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblPurchase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPurchaseMouseClicked(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("SALES");
        jLabel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 102, 51), null, null));

        lblStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStock.setText("STOCK");
        lblStock.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStockMouseClicked(evt);
            }
        });

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/AUXANO-Logo2.png"))); // NOI18N

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lgoutS.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPurchase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(lblUser)
                .addGap(18, 18, 18)
                .addComponent(lblProducts)
                .addGap(18, 18, 18)
                .addComponent(lblPurchase)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(lblStock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addContainerGap())
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel15.setPreferredSize(new java.awt.Dimension(1112, 595));

        jLabel22.setText("USE THE FORM BELOW TO CREATE SALES ORDERS");

        jPanel17.setBackground(new java.awt.Color(153, 153, 153));
        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel29.setText("SALES ORDER");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(780, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel15.setText("CUSTOMER NAME:");

        jLabel33.setText("CUSTOMER PHONE:");

        jLabel34.setText("REQUIRED DATE:");

        jLabel9.setText("S.O. NUMBER:");

        txtSONumber.setEditable(false);

        jLabel7.setText("SALES REPRESENTATIVE:");

        jLabel8.setText("ORDER CREATED BY:");

        txtUser.setEditable(false);

        cmbSalesRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSalesRepActionPerformed(evt);
            }
        });

        tblCreateSO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITEM CODE", "ITEM", "BATCH NO", "QUANTITY", "RATE", "SUB TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCreateSO.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tblCreateSO);
        if (tblCreateSO.getColumnModel().getColumnCount() > 0) {
            tblCreateSO.getColumnModel().getColumn(0).setResizable(false);
            tblCreateSO.getColumnModel().getColumn(1).setResizable(false);
            tblCreateSO.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(jComboBox9));
            tblCreateSO.getColumnModel().getColumn(2).setResizable(false);
            tblCreateSO.getColumnModel().getColumn(3).setResizable(false);
            tblCreateSO.getColumnModel().getColumn(4).setResizable(false);
            tblCreateSO.getColumnModel().getColumn(5).setResizable(false);
        }

        btnCreate.setText("CREATE");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnClear.setText("CLEAR");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel13.setText("ORDER STATUS:");

        jLabel31.setText("REGION:");

        cmbRegion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NORTH", "SOUTH", "EAST", "WEST", "CENTRAL" }));

        btnAddTab.setText("ADD");
        btnAddTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTabActionPerformed(evt);
            }
        });

        btnEditTab.setText("EDIT");
        btnEditTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditTabActionPerformed(evt);
            }
        });

        btnDeleteTab.setText("DELETE");
        btnDeleteTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTabActionPerformed(evt);
            }
        });

        jLabel1.setText("NO. OF ITEMS:");

        jLabel2.setText("TOTAL AMOUNT:");

        cmbOrderStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AWAITING FULFILLMENT", "COMPLETED" }));

        jButton1.setText("#");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel9)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCustomerPhone)
                            .addComponent(txtCustomerName)
                            .addComponent(txtSONumber, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dpReqDate, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblErrorCP)
                                    .addComponent(lblErrorRD)
                                    .addComponent(lblErrorCN)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblErrorSON)))
                        .addGap(78, 78, 78)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addGap(62, 62, 62))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(jLabel31)
                                            .addGap(133, 133, 133)))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(87, 87, 87)))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbSalesRep, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtUser)
                                    .addComponent(cmbRegion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 944, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDeleteTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblErrorSR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorOS))
                        .addGap(48, 48, 48))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(lblQtySum)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotalAmt)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(cmbSalesRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblErrorSR))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(cmbRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblErrorR))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(lblErrorOS)
                            .addComponent(cmbOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblErrorSON)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblErrorCN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblErrorCP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblErrorRD))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSONumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(txtCustomerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(dpReqDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(35, 35, 35)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnAddTab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditTab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteTab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 79, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblQtySum)
                    .addComponent(jLabel2)
                    .addComponent(lblTotalAmt))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel22)
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel30.setText("CREATE SALES ORDER");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(168, 168, 168))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("CREATE SALES ORDERS", jPanel4);

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel12.setPreferredSize(new java.awt.Dimension(1112, 595));

        jLabel20.setText("USE THE FORM BELOW TO MANAGE SALES ORDERS");

        jPanel14.setBackground(new java.awt.Color(153, 153, 153));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel14.setPreferredSize(new java.awt.Dimension(1066, 42));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel21.setText("REVIEW ORDERS");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(780, Short.MAX_VALUE))
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

        tblReviewSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.O. NUMBER", "ORDER DATE", "REQUIRED DATE", "CUSTOMER", "SALES MAN", "SALES REP", "TOTAL", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblReviewSales.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tblReviewSales);

        jLabel12.setText("SEARCH:");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setText("FILTER BY REQUIRED DATE");

        jLabel11.setText("DATE:");

        dpFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dpFromActionPerformed(evt);
            }
        });

        jLabel16.setText("TO");

        dpTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dpToActionPerformed(evt);
            }
        });

        btnUpdateSales.setText("UPDATE");
        btnUpdateSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSalesActionPerformed(evt);
            }
        });

        btnDeleteSales.setText("DELETE");
        btnDeleteSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSalesActionPerformed(evt);
            }
        });

        jLabel17.setText("SALES REP:");

        cmbSearchSalesRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSearchSalesRepActionPerformed(evt);
            }
        });

        btnViewTab.setText("VIEW");
        btnViewTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewTabActionPerformed(evt);
            }
        });

        btnSearch.setText("SEARCH");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnClearFilters.setText("CLEAR");
        btnClearFilters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFiltersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 933, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dpFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dpTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(216, 216, 216)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(txtSearchSONum, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(cmbSearchSalesRep, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnClearFilters, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(btnDeleteSales, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateSales, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnViewTab)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txtSearchSONum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(dpFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(dpTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(cmbSearchSalesRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearFilters, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdateSales, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeleteSales, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnViewTab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(270, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
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
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel6.setText("REVIEW SALES ORDERS");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("REVIEW SALES ORDERS", jPanel7);

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel13.setPreferredSize(new java.awt.Dimension(1115, 595));

        jLabel23.setText("VIEW ALL ORDERS BELOW");

        jPanel16.setBackground(new java.awt.Color(153, 153, 153));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel24.setText("VIEW ORDERS");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(783, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel19MouseClicked(evt);
            }
        });

        tblViewSalesOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.O. NUMBER", "ORD DATE", "REQ DATE", "CUS NAME", "SALES MAN", "SALES REP", "REGION", "ORD STATUS", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblViewSalesOrders.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tblViewSalesOrders);
        if (tblViewSalesOrders.getColumnModel().getColumnCount() > 0) {
            tblViewSalesOrders.getColumnModel().getColumn(0).setResizable(false);
            tblViewSalesOrders.getColumnModel().getColumn(1).setResizable(false);
            tblViewSalesOrders.getColumnModel().getColumn(2).setResizable(false);
            tblViewSalesOrders.getColumnModel().getColumn(3).setResizable(false);
            tblViewSalesOrders.getColumnModel().getColumn(4).setResizable(false);
            tblViewSalesOrders.getColumnModel().getColumn(5).setResizable(false);
            tblViewSalesOrders.getColumnModel().getColumn(6).setResizable(false);
        }

        jButton8.setText("GENERATE SALES REPORT");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel18.setText("SALES MANAGER:");

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel19.setText("FILTER BY REQUIRED DATE");

        jLabel25.setText("DATE:");

        dpFrom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dpFrom2ActionPerformed(evt);
            }
        });

        jLabel26.setText("TO");

        dpTo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dpTo2ActionPerformed(evt);
            }
        });

        cmbFilterSalesMan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFilterSalesManActionPerformed(evt);
            }
        });

        cmbFilerStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NONE", "AWAITING FULFILLMENT", "COMPLETED", "CANCELLED" }));
        cmbFilerStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFilerStatusActionPerformed(evt);
            }
        });

        jLabel32.setText("FILTER BY STATUS:");

        jLabel35.setText("SALES REP:");

        cmbFilterSaleRep2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFilterSaleRep2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dpFrom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dpTo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jLabel18)
                            .addComponent(jLabel32))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbFilterSaleRep2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbFilerStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbFilterSalesMan, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap(42, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 957, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel32)
                        .addComponent(cmbFilerStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(dpFrom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(dpTo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(cmbFilterSalesMan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(cmbFilterSaleRep2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel27.setText("VIEW SALES ORDERS");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("VIEW SALES ORDERS", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1170, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserMouseClicked
        if (UserModel.userRole.equals("ADMIN")) {
            CreateAccount frame = new CreateAccount();
            frame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this tab.");
        }
    }//GEN-LAST:event_lblUserMouseClicked

    private void lblProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProductsMouseClicked
        if (UserModel.userRole.equals("STOCK CONTOLLER") || (UserModel.userRole.equals("ADMIN"))) {
            AddProduct frame = new AddProduct();
            frame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this tab.");
        }
    }//GEN-LAST:event_lblProductsMouseClicked

    private void lblPurchaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPurchaseMouseClicked
        if ((UserModel.userRole.equals("STOCK CONTROLLER")) || (UserModel.userRole.equals("ADMIN"))) {
            Purchase frame = new Purchase();
            frame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this tab.");
        }
    }//GEN-LAST:event_lblPurchaseMouseClicked

    private void lblStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblStockMouseClicked
        if ((UserModel.userRole.equals("STOCK CONTROLLER")) || (UserModel.userRole.equals("SALES MANAGER")) || (UserModel.userRole.equals("ADMIN"))) {
            Stock frame = new Stock();
            frame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this tab.");
        }
    }//GEN-LAST:event_lblStockMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:

        txtSONumber.setText("");
        txtCustomerName.setText("");
        txtCustomerPhone.setText("");
        cmbOrderStatus.setSelectedIndex(-1);
        cmbRegion.setSelectedIndex(-1);
        cmbSalesRep.setSelectedIndex(-1);
        dpReqDate.setDate(null);

        lblErrorSON.setText("");
        lblErrorCN.setText("");
        lblErrorCP.setText("");
        lblErrorSR.setText("");
        lblErrorR.setText("");
        lblErrorOS.setText("");
        lblErrorRD.setText("");

//        tblCreateSO.clearSelection();
        DefaultTableModel model = (DefaultTableModel) tblCreateSO.getModel();
        model.setRowCount(0);


    }//GEN-LAST:event_btnClearActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        if (txtSONumber.getText().equals("") || txtCustomerName.getText().equals("") || txtCustomerPhone.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Form is incomplete");
        } else {
            Connection con = dbConnect.getConnection();

            int soNum;
            int cusPho;
            boolean soNo = false;
            boolean cusPno = false;
            boolean reqDat = false;

            String soNumber = txtSONumber.getText();
            String customerName = txtCustomerName.getText();
            String customerPhone = txtCustomerPhone.getText();
            String salesRep = (String) cmbSalesRep.getSelectedItem();
            String region = (String) cmbRegion.getSelectedItem();
            String orderStatus = (String) cmbOrderStatus.getSelectedItem();
            String orderCreatedBy = txtUser.getText();
            Date reqDate;

            Date soDate = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String orderDate = df.format(soDate);
            String rd = "";
            String r = "";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date requiredDate, ordDate;
            try {
                reqDate = dpReqDate.getDate();
                rd = sdf.format(reqDate);
                requiredDate = sdf.parse(rd);
                ordDate = sdf.parse(orderDate);

                if (requiredDate.before(ordDate)) {
                    JOptionPane.showMessageDialog(null, "Select a proper date!");
                } else {
                    r = rd;
                    lblErrorRD.setText("");
                    reqDat = true;
                }

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Select a proper date!");
            }

            try {
                soNum = Integer.parseInt(soNumber);
                if ((soNum > 9999) && (soNum < 1000000)) {
                    soNo = true;
                }
            } catch (NumberFormatException e) {
            }
            try {
                cusPho = Integer.parseInt(soNumber);
                if (customerPhone.length() == 10) {
                    cusPno = true;
                    lblErrorCP.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Phone number entered is not valid!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Select a proper date!");
            }

            if (cmbSalesRep.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Sales rep is not selected!");
            } else {
                lblErrorSR.setText("");
            }

            if (cmbRegion.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Region is not selected");
            } else {
                lblErrorR.setText("");
            }

            if (cmbOrderStatus.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Status is not selected");
            } else {
                lblErrorOS.setText("");
            }

            if (txtCustomerName.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Customer name field is empty");
            } else {
                lblErrorCN.setText("");
            }

            String total = lblTotalAmt.getText();

            if (!(txtSONumber.getText().equals("")) && !(txtCustomerName.getText().equals("")) && !(txtCustomerPhone.getText().equals("")) && !(cmbSalesRep.getSelectedIndex() == -1)
                    && !(cmbRegion.getSelectedIndex() == -1) && !(cmbOrderStatus.getSelectedIndex() == -1) && !(dpReqDate.getDate() == null)) {

                int rows = tblCreateSO.getRowCount();

                if ((getValidation(Integer.parseInt(soNumber)))) {

                    JOptionPane.showMessageDialog(null, "SO Number Already Exists!");

                } else if ((soNo == true) && (cusPno == true) && (reqDat == true)) {

                    try {

                        if (tblCreateSO.getRowCount() != 0) {
                            Statement st = con.createStatement();
                            String query = "INSERT INTO sales_tab(orderedDate,customerName,customerPhone,reqDate,salesRep,region,orderCreatedBy,orderStatus,total) VALUES('" + orderDate + "','" + customerName + "','" + customerPhone + "','" + r + "','" + salesRep + "','" + region + "','" + orderCreatedBy + "','" + orderStatus + "','" + total + "')";
                            String messageQuery = "CALL generate_messages();";
                            String messageQuery2 = "CALL generate_emptyStockMessages();";
                            int execute = st.executeUpdate(query);

                            for (int row = 0; row < rows; row++) {

                                String itemCode = tblCreateSO.getValueAt(row, 0).toString();
                                String itemName = tblCreateSO.getValueAt(row, 1).toString();
                                String batchNum = tblCreateSO.getValueAt(row, 2).toString();
                                int qty = Integer.parseInt(tblCreateSO.getValueAt(row, 3).toString());
                                double rate = Double.parseDouble(tblCreateSO.getValueAt(row, 4).toString());
                                double subt = Double.parseDouble(tblCreateSO.getValueAt(row, 5).toString());

                                String Query2 = "INSERT INTO salesItems_tab(soNumber, prodID, prodName, batchNo, unitPrice, quantity) VALUES('" + soNumber + "','" + itemCode + "','" + itemName + "','" + batchNum + "','" + rate + "','" + qty + "')";
                                String Query3 = "UPDATE stocks_tab SET quantity = quantity - " + qty + " where batchNo = '" + batchNum + "'; ";
                                int execute2 = st.executeUpdate(Query2);
                                int execute3 = st.executeUpdate(Query3);

                            }

                            int execute5 = st.executeUpdate(messageQuery);
                            int execute6 = st.executeUpdate(messageQuery2);

                            JOptionPane.showMessageDialog(rootPane, "Sales order recorded!");
                            ShowSales();
                            ShowReviewSales();
                            txtSONumber.setText("");
                            txtCustomerName.setText("");
                            txtCustomerPhone.setText("");
                            cmbOrderStatus.setSelectedIndex(-1);
                            cmbRegion.setSelectedIndex(-1);
                            cmbSalesRep.setSelectedIndex(-1);
                            dpReqDate.setDate(null);
                            DefaultTableModel model = (DefaultTableModel) tblCreateSO.getModel();
                            model.setRowCount(0);
                        } else {
                            JOptionPane.showMessageDialog(null, "No Items Added");
                        }
                    } catch (HeadlessException | NumberFormatException | SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Enter Correct Values!");
                }

            } else {
                JOptionPane.showMessageDialog(rootPane, "Fill in the blanks");
            }
        }


    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnAddTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTabActionPerformed
        // TODO add your handling code here:

        DefaultTableModel model = (DefaultTableModel) tblCreateSO.getModel();

        count = tblCreateSO.getRowCount();
        addItems.setVisible(true);
        addItems.pack();
        addItems.setLocationRelativeTo(null);

        addItems.txtBatchNo.setSelectedIndex(-1);
        addItems.txtItemName.setSelectedIndex(-1);
        addItems.txtQuantity.setText("");


    }//GEN-LAST:event_btnAddTabActionPerformed

    private void btnEditTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditTabActionPerformed
        // TODO add your handling code here:

        index = tblCreateSO.getSelectedRow();
        if (tblCreateSO.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Select a row to edit!");
        } else {

            DefaultTableModel model = (DefaultTableModel) tblCreateSO.getModel();
            editItems.txtItemName.setText(model.getValueAt(tblCreateSO.getSelectedRow(), 1).toString());
            editItems.txtQuantity.setText(model.getValueAt(tblCreateSO.getSelectedRow(), 3).toString());
            editItems.txtBatchNo.setText(model.getValueAt(tblCreateSO.getSelectedRow(), 2).toString());

            editItems.setVisible(true);
            editItems.pack();
            editItems.setLocationRelativeTo(null);
        }

    }//GEN-LAST:event_btnEditTabActionPerformed

    private void btnDeleteTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTabActionPerformed
        // TODO add your handling code here:

        if (tblCreateSO.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Select a row delete!");
        } else {

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Delete item", dialogButton);
            if (dialogResult == 0) {
                DefaultTableModel model = (DefaultTableModel) tblCreateSO.getModel();
                model.removeRow(tblCreateSO.getSelectedRow());
            }

        }

    }//GEN-LAST:event_btnDeleteTabActionPerformed

    private void btnViewTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewTabActionPerformed
        // TODO add your handling code here:

        if (tblReviewSales.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Select a row to view!");
        } else {

            DefaultTableModel model = (DefaultTableModel) tblReviewSales.getModel();
            DefaultTableModel model2 = (DefaultTableModel) viewItems.tblViewSalesOrder.getModel();
            String num = model.getValueAt(tblReviewSales.getSelectedRow(), 0).toString();

            viewItems.lblSONum.setText(model.getValueAt(tblReviewSales.getSelectedRow(), 0).toString());
            viewItems.lblDateOfOrder.setText(model.getValueAt(tblReviewSales.getSelectedRow(), 1).toString());
            viewItems.lblRequiredDate.setText(model.getValueAt(tblReviewSales.getSelectedRow(), 2).toString());
            viewItems.lblCustomerName.setText(model.getValueAt(tblReviewSales.getSelectedRow(), 3).toString());
            viewItems.lblSalesManager.setText(model.getValueAt(tblReviewSales.getSelectedRow(), 4).toString());
            viewItems.lblSalesRep.setText(model.getValueAt(tblReviewSales.getSelectedRow(), 5).toString());
            viewItems.lblSum.setText(model.getValueAt(tblReviewSales.getSelectedRow(), 6).toString());
            viewItems.lblOrderStatus.setText(model.getValueAt(tblReviewSales.getSelectedRow(), 7).toString());

            String SONum = model.getValueAt(Sales.tblReviewSales.getSelectedRow(), 0).toString();

            String[] results = new String[4];

            String query = "SELECT * FROM salesItems_tab WHERE soNumber=" + SONum + ";";
            String query2 = "SELECT * FROM sales_tab WHERE soNumber=" + SONum + ";";
            try {
                Connection con = dbConnect.getConnection();
                Statement st = con.createStatement();
                Statement st2 = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                ResultSet rs2 = st2.executeQuery(query2);
                if (rs2.next()) {

                    viewItems.lblRegion.setText(rs2.getString("region"));
                    viewItems.lblCustomerPhone.setText(rs2.getString("customerPhone"));
                }

                while (rs.next()) {
                    results[0] = rs.getString("prodName");
                    results[1] = rs.getString("batchNo");
                    results[2] = rs.getString("quantity");

                    int price = rs.getInt("unitPrice");
                    int quant = rs.getInt("quantity");
                    int subtotal = price * quant;

                    String subt = Integer.toString(subtotal);

                    results[3] = subt;

                    model2.addRow(results);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }

            viewItems.setVisible(true);
            viewItems.pack();
            viewItems.setLocationRelativeTo(null);

        }

    }//GEN-LAST:event_btnViewTabActionPerformed

    private void btnUpdateSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSalesActionPerformed

        index = tblReviewSales.getSelectedRow();
        if (tblReviewSales.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Select a row to update!");
        } else {

            DefaultTableModel model = (DefaultTableModel) tblReviewSales.getModel();

            reviewSales.y1.setText(model.getValueAt(Sales.tblReviewSales.getSelectedRow(), 2).toString().substring(0, 4));
            reviewSales.m1.setText(model.getValueAt(Sales.tblReviewSales.getSelectedRow(), 2).toString().substring(5, 7));
            reviewSales.d1.setText(model.getValueAt(Sales.tblReviewSales.getSelectedRow(), 2).toString().substring(8, 10));
            reviewSales.dpReqDate.setText(model.getValueAt(Sales.tblReviewSales.getSelectedRow(), 0).toString());

            String chkStat = model.getValueAt(tblReviewSales.getSelectedRow(), 6).toString();
            if (chkStat.equals("AWAITING FULFILLMENT")) {

                reviewSales.cmbStatus.setSelectedIndex(0);

            } else if (chkStat.equals("COMPLETED")) {

                reviewSales.cmbStatus.setSelectedIndex(1);

            } else if (chkStat.equals("CANCELLED")) {

                reviewSales.cmbStatus.setSelectedIndex(2);

            }

            reviewSales.setVisible(true);
            reviewSales.pack();
            reviewSales.setLocationRelativeTo(null);
        }

    }//GEN-LAST:event_btnUpdateSalesActionPerformed

    private void btnDeleteSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSalesActionPerformed

        Connection con = dbConnect.getConnection();

        if (tblReviewSales.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Select a row to delete!");
        } else {

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are sure you want to delete?", "Delete item", dialogButton);

            if (dialogResult == 0) {
                DefaultTableModel model = (DefaultTableModel) tblReviewSales.getModel();
                String SONum = model.getValueAt(Sales.tblReviewSales.getSelectedRow(), 0).toString();

                String query = "DELETE FROM sales_tab WHERE soNumber=" + SONum + ";";
                try {
                    Statement st = con.createStatement();
                    int execute = st.executeUpdate(query);

                    model.removeRow(tblReviewSales.getSelectedRow());
                    ShowSales();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btnDeleteSalesActionPerformed

    private void dpFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dpFromActionPerformed
        dpTo.setDate(null);
        if (dpFrom.getDate() == null) {

        } else {
            Date today = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formated = df.format(today);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date pd1 = dpFrom.getDate();

            String date1 = "";
            String date2 = "";
            Date current, from, pDate, to;
            try {

                date1 = df.format(pd1);
                from = sdf.parse(date1);
                current = sdf.parse(formated);

                if (from.after(current)) {
                    JOptionPane.showMessageDialog(null, "Select a proper date!");
                } else {

                    ArrayList<SalesModel> list = getOrderList();
                    DefaultTableModel model = (DefaultTableModel) tblReviewSales.getModel();
                    model.setRowCount(0);

                    Object[] row = new Object[8];
                    for (int i = 0; i < list.size(); i++) {
                        row[0] = list.get(i).getSONo();
                        row[1] = list.get(i).getOrderDate();
                        row[2] = list.get(i).getReqDate();
                        row[3] = list.get(i).getCusName();
                        row[4] = list.get(i).getOrderCreator();
                        row[5] = list.get(i).getRepName();
                        row[6] = list.get(i).getTotal();
                        row[7] = list.get(i).getStatus();

                        pDate = sdf.parse(list.get(i).getReqDate());

                        if (UserModel.userRole.equals("ADMIN")) {

                            if (from.compareTo(pDate) <= 0 && dpTo.getDate() == null) {
                                model.addRow(row);
                            }
                        }

                        if (UserModel.userRole.equals("SALES MANAGER")) {

                            if (list.get(i).getOrderCreator().equals(UserModel.loginName)) {
                                if (from.compareTo(pDate) <= 0 && dpTo.getDate() == null) {
                                    model.addRow(row);
                                }
                            }
                        }
                    }
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }

    }//GEN-LAST:event_dpFromActionPerformed

    private void dpToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dpToActionPerformed
        // TODO add your handling code here:
        if (dpTo.getDate() != null) {

            Date today = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formated = df.format(today);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date pd2 = dpTo.getDate();

            String date2 = "";
            String date1 = "";
            Date current, from, pDate, to;
            try {

                date2 = df.format(pd2);
                to = sdf.parse(date2);
                current = sdf.parse(formated);

                if (to.before(current)) {
                    JOptionPane.showMessageDialog(null, "Select a proper date!");
                } else {

                    ArrayList<SalesModel> list = getOrderList();
                    DefaultTableModel model = (DefaultTableModel) tblReviewSales.getModel();
                    model.setRowCount(0);

                    Object[] row = new Object[8];
                    for (int i = 0; i < list.size(); i++) {
                        row[0] = list.get(i).getSONo();
                        row[1] = list.get(i).getOrderDate();
                        row[2] = list.get(i).getReqDate();
                        row[3] = list.get(i).getCusName();
                        row[4] = list.get(i).getOrderCreator();
                        row[5] = list.get(i).getRepName();
                        row[6] = list.get(i).getTotal();
                        row[7] = list.get(i).getStatus();

                        pDate = sdf.parse(list.get(i).getReqDate().toString());

                        if (UserModel.userRole.equals("ADMIN")) {

                            if (to.compareTo(pDate) >= 0 && dpFrom.getDate() == null) {
                                model.addRow(row);
                            }

                            if (to.compareTo(pDate) >= 0 && dpFrom.getDate() != null) {
                                date1 = df.format(dpFrom.getDate());
                                from = sdf.parse(date1);
                                if (to.compareTo(from) >= 0 && from.compareTo(pDate) <= 0) {
                                    model.addRow(row);
                                }
                            }
                        }

                        if (UserModel.userRole.equals("SALES MANAGER")) {

                            if (list.get(i).getOrderCreator().equals(UserModel.loginName)) {

                                if (to.compareTo(pDate) >= 0 && dpFrom.getDate() == null) {
                                    model.addRow(row);
                                }

                                if (to.compareTo(pDate) >= 0 && dpFrom.getDate() != null) {
                                    date1 = df.format(dpFrom.getDate());
                                    from = sdf.parse(date1);
                                    if (to.compareTo(from) >= 0 && from.compareTo(pDate) <= 0) {
                                        model.addRow(row);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }//GEN-LAST:event_dpToActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

        if (txtSearchSONum.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Search field is empty!");
        } else {
            String num = txtSearchSONum.getText();
            ArrayList<SalesModel> list = getOrderList();
            DefaultTableModel model = (DefaultTableModel) tblReviewSales.getModel();
            model.setRowCount(0);

            Object[] row = new Object[8];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getSONo();
                row[1] = list.get(i).getOrderDate();
                row[2] = list.get(i).getReqDate();
                row[3] = list.get(i).getCusName();
                row[4] = list.get(i).getOrderCreator();
                row[5] = list.get(i).getRepName();
                row[6] = list.get(i).getTotal();
                row[7] = list.get(i).getStatus();

                if (list.get(i).getSONo() == Integer.parseInt(num)) {
                    model.addRow(row);
                }
            }
        }

    }//GEN-LAST:event_btnSearchActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
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

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:

        saleReport.setVisible(true);
        saleReport.pack();
        saleReport.setLocationRelativeTo(null);

    }//GEN-LAST:event_jButton8ActionPerformed

    private void dpFrom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dpFrom2ActionPerformed
        dpTo2.setDate(null);
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formated = df.format(today);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date pd1 = dpFrom2.getDate();

        String date1 = "";
        String date2 = "";
        Date current, from, pDate, to;
        try {

            date1 = df.format(pd1);
            from = sdf.parse(date1);
            current = sdf.parse(formated);

            if (from.after(current)) {
                JOptionPane.showMessageDialog(null, "Select a proper date!");
            } else {

                ArrayList<SalesModel> list = getOrderList();
                DefaultTableModel model = (DefaultTableModel) tblViewSalesOrders.getModel();
                model.setRowCount(0);

                Object[] row = new Object[9];
                for (int i = 0; i < list.size(); i++) {
                    row[0] = list.get(i).getSONo();
                    row[1] = list.get(i).getOrderDate();
                    row[2] = list.get(i).getReqDate();
                    row[3] = list.get(i).getCusName();
                    row[4] = list.get(i).getOrderCreator();
                    row[5] = list.get(i).getRepName();
                    row[6] = list.get(i).getRegion();
                    row[7] = list.get(i).getStatus();
                    row[8] = list.get(i).getTotal();

                    pDate = sdf.parse(list.get(i).getReqDate());

                    if (dpFrom2.getDate() == null && dpTo2.getDate() == null) {
                        model.addRow(row);
                    }

                    if (from.compareTo(pDate) <= 0 && dpTo2.getDate() != null) {
                        date2 = df.format(dpTo2.getDate());
                        to = sdf.parse(date2);
                        if (from.compareTo(to) <= 0 && to.compareTo(from) > 0 && to.compareTo(pDate) > 0) {
                            model.addRow(row);
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_dpFrom2ActionPerformed

    private void dpTo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dpTo2ActionPerformed
        // TODO add your handling code here:

        Date today = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formated = df.format(today);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date pd2 = dpTo.getDate();

        String date2 = "";
        String date1 = "";
        Date current, from, pDate, to;
        try {

            date2 = df.format(pd2);
            to = sdf.parse(date2);
            current = sdf.parse(formated);

            if (to.after(current)) {
                JOptionPane.showMessageDialog(null, "Select a proper date!");
            } else {

                ArrayList<SalesModel> list = getOrderList();
                DefaultTableModel model = (DefaultTableModel) tblViewSalesOrders.getModel();
                model.setRowCount(0);

                Object[] row = new Object[9];
                for (int i = 0; i < list.size(); i++) {
                    row[0] = list.get(i).getSONo();
                    row[1] = list.get(i).getOrderDate();
                    row[2] = list.get(i).getReqDate();
                    row[3] = list.get(i).getCusName();
                    row[4] = list.get(i).getOrderCreator();
                    row[5] = list.get(i).getRepName();
                    row[6] = list.get(i).getRegion();
                    row[7] = list.get(i).getStatus();
                    row[8] = list.get(i).getTotal();

                    pDate = sdf.parse(list.get(i).getReqDate());

                    if (dpFrom2.getDate() == null && dpTo2.getDate() == null) {
                        model.addRow(row);
                    }

                    if (to.compareTo(pDate) >= 0 && dpFrom2.getDate() == null) {
                        model.addRow(row);
                    }

                    if (to.compareTo(pDate) >= 0 && dpFrom2.getDate() != null) {
                        date1 = df.format(dpFrom2.getDate());
                        from = sdf.parse(date1);
                        if (to.compareTo(from) >= 0 && from.compareTo(pDate) <= 0) {
                            model.addRow(row);
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_dpTo2ActionPerformed

    private void cmbSearchSalesRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSearchSalesRepActionPerformed

        ArrayList<SalesModel> list = getOrderList();
        DefaultTableModel model = (DefaultTableModel) tblReviewSales.getModel();
        model.setRowCount(0);
        String rep = cmbSearchSalesRep.getSelectedItem().toString();

        Object[] row = new Object[8];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getSONo();
            row[1] = list.get(i).getOrderDate();
            row[2] = list.get(i).getReqDate();
            row[3] = list.get(i).getCusName();
            row[4] = list.get(i).getOrderCreator();
            row[5] = list.get(i).getRepName();
            row[6] = list.get(i).getTotal();
            row[7] = list.get(i).getStatus();

            if (rep.equals("NONE") && UserModel.userRole.equals("SALES MANAGER") && UserModel.loginName.equals(list.get(i).getOrderCreator())) {
                model.addRow(row);
            }

            if (rep.equals("NONE") && UserModel.userRole.equals("ADMIN")) {
                model.addRow(row);
            }

            if (list.get(i).getRepName().equals(rep) && UserModel.userRole.equals("ADMIN")) {
                model.addRow(row);
            }

            if (list.get(i).getRepName().equals(rep) && UserModel.userRole.equals("SALES MANAGER")) {
                model.addRow(row);
            }
        }

    }//GEN-LAST:event_cmbSearchSalesRepActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        int val, id = 0;

        try {
            Connection con = dbConnect.getConnection();

            String query = "select max(soNumber) from sales_tab";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No values in the database");
        }
        val = id + 1;
        if (val != 1) {
            txtSONumber.setText(val + "");
        } else {
            val = 10000;
            txtSONumber.setText(val + "");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbFilterSaleRep2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFilterSaleRep2ActionPerformed
        // TODO add your handling code here:

        String repName = cmbFilterSaleRep2.getSelectedItem().toString();
        ArrayList<SalesModel> list = getOrderList();
        DefaultTableModel model = (DefaultTableModel) tblViewSalesOrders.getModel();
        model.setRowCount(0);

        Object[] row = new Object[9];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getSONo();
            row[1] = list.get(i).getOrderDate();
            row[2] = list.get(i).getReqDate();
            row[3] = list.get(i).getCusName();
            row[4] = list.get(i).getOrderCreator();
            row[5] = list.get(i).getRepName();
            row[6] = list.get(i).getRegion();
            row[7] = list.get(i).getStatus();
            row[8] = list.get(i).getTotal();

            if (repName.equals("NONE")) {
                model.addRow(row);
            }
            if (list.get(i).getRepName().equals(repName)) {
                model.addRow(row);
            }

        }

    }//GEN-LAST:event_cmbFilterSaleRep2ActionPerformed

    private void cmbFilerStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFilerStatusActionPerformed
        // TODO add your handling code here:
        String status = cmbFilerStatus.getSelectedItem().toString();
        ArrayList<SalesModel> list = getOrderList();
        DefaultTableModel model = (DefaultTableModel) tblViewSalesOrders.getModel();
        model.setRowCount(0);

        Object[] row = new Object[9];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getSONo();
            row[1] = list.get(i).getOrderDate();
            row[2] = list.get(i).getReqDate();
            row[3] = list.get(i).getCusName();
            row[4] = list.get(i).getOrderCreator();
            row[5] = list.get(i).getRepName();
            row[6] = list.get(i).getRegion();
            row[7] = list.get(i).getStatus();
            row[8] = list.get(i).getTotal();

            if (status.equals("NONE")) {
                model.addRow(row);
            }
            if (list.get(i).getStatus().equals(status)) {
                model.addRow(row);
            }

        }

    }//GEN-LAST:event_cmbFilerStatusActionPerformed

    private void cmbFilterSalesManActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFilterSalesManActionPerformed
        // TODO add your handling code here:
        String manName = cmbFilterSalesMan.getSelectedItem().toString();
        ArrayList<SalesModel> list = getOrderList();
        DefaultTableModel model = (DefaultTableModel) tblViewSalesOrders.getModel();
        model.setRowCount(0);

        Object[] row = new Object[9];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getSONo();
            row[1] = list.get(i).getOrderDate();
            row[2] = list.get(i).getReqDate();
            row[3] = list.get(i).getCusName();
            row[4] = list.get(i).getOrderCreator();
            row[5] = list.get(i).getRepName();
            row[6] = list.get(i).getRegion();
            row[7] = list.get(i).getStatus();
            row[8] = list.get(i).getTotal();

            if (manName.equals("NONE")) {
                model.addRow(row);
            }
            if (list.get(i).getOrderCreator().equals(manName)) {
                model.addRow(row);
            }

        }

    }//GEN-LAST:event_cmbFilterSalesManActionPerformed

    private void btnClearFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFiltersActionPerformed

        dpFrom.setDate(null);
        dpTo.setDate(null);
        cmbSearchSalesRep.setSelectedIndex(0);
        txtSearchSONum.setText("");
        ShowReviewSales();
    }//GEN-LAST:event_btnClearFiltersActionPerformed

    private void jPanel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseClicked

        tblReviewSales.clearSelection();
    }//GEN-LAST:event_jPanel18MouseClicked

    private void jPanel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseClicked

        tblViewSalesOrders.clearSelection();
    }//GEN-LAST:event_jPanel19MouseClicked

    private void cmbSalesRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSalesRepActionPerformed
        // TODO add your handling code here:
        if (cmbSalesRep.getSelectedIndex() != -1) {
            Connection con = dbConnect.getConnection();
            String query = "select repRegion from reps_tab where repName = '" + cmbSalesRep.getSelectedItem().toString() + "'";

            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    String region = rs.getString("repRegion");

                    if (region.equals("NORTH")) {
                        cmbRegion.setSelectedIndex(0);
                    }
                    if (region.equals("SOUTH")) {
                        cmbRegion.setSelectedIndex(1);
                    }
                    if (region.equals("EAST")) {
                        cmbRegion.setSelectedIndex(2);
                    }
                    if (region.equals("WEST")) {
                        cmbRegion.setSelectedIndex(3);
                    }
                    if (region.equals("CENTRAL")) {
                        cmbRegion.setSelectedIndex(4);
                    }

                }
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_cmbSalesRepActionPerformed

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
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Sales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTab;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearFilters;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDeleteSales;
    private javax.swing.JButton btnDeleteTab;
    private javax.swing.JButton btnEditTab;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdateSales;
    private javax.swing.JButton btnViewTab;
    public javax.swing.JComboBox<String> cmbFilerStatus;
    public javax.swing.JComboBox<String> cmbFilterSaleRep2;
    public javax.swing.JComboBox<String> cmbFilterSalesMan;
    private javax.swing.JComboBox<String> cmbOrderStatus;
    private javax.swing.JComboBox<String> cmbRegion;
    private javax.swing.JComboBox<String> cmbSalesRep;
    private javax.swing.JComboBox<String> cmbSearchSalesRep;
    public org.jdesktop.swingx.JXDatePicker dpFrom;
    private org.jdesktop.swingx.JXDatePicker dpFrom2;
    private org.jdesktop.swingx.JXDatePicker dpReqDate;
    public org.jdesktop.swingx.JXDatePicker dpTo;
    private org.jdesktop.swingx.JXDatePicker dpTo2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblErrorCN;
    private javax.swing.JLabel lblErrorCP;
    private javax.swing.JLabel lblErrorOS;
    private javax.swing.JLabel lblErrorR;
    private javax.swing.JLabel lblErrorRD;
    private javax.swing.JLabel lblErrorSON;
    private javax.swing.JLabel lblErrorSR;
    private javax.swing.JLabel lblProducts;
    private javax.swing.JLabel lblPurchase;
    private javax.swing.JLabel lblQtySum;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblTotalAmt;
    private javax.swing.JLabel lblUser;
    public static javax.swing.JTable tblCreateSO;
    public static javax.swing.JTable tblReviewSales;
    public static javax.swing.JTable tblViewSalesOrders;
    public javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtCustomerPhone;
    public javax.swing.JTextField txtSONumber;
    private javax.swing.JTextField txtSearchSONum;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
