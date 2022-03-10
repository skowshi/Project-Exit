/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Models.PurchaseModel;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Models.DatabaseConnection;
import Models.UserModel;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author User
 */
public final class Purchase extends javax.swing.JFrame {

    PurchaseItemsEdit editProduct = new PurchaseItemsEdit();
    PurchaseItemsAdd addProduct = new PurchaseItemsAdd();
    PurchaseItemsView viewOrder = new PurchaseItemsView();
    DatabaseConnection dbConnect = new DatabaseConnection();
    PurchaseReport purReport = new PurchaseReport();

    public Purchase() {
        initComponents();
        URL iconURL = getClass().getResource("Images/AUXANO ICON 20.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
        jPanel1.setVisible(false);

        ShowPurchases();
        ShowVals();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        jTable9.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.DELETE || e.getType() == TableModelEvent.UPDATE) {
                    sum.setText(getSum() + "");
                }
            }
        });

    }

    public int index;
    public int count;
    public int order;

    public double getSum() {

        int rowsCount = jTable9.getRowCount();
        double sum = 0;
        for (int i = 0; i < rowsCount; i++) {
            double totval = Double.parseDouble(jTable9.getValueAt(i, 7).toString());
            sum = sum + totval;
        }
        return sum;

    }

    public boolean getValidation(int pidVal) {

        Connection con = dbConnect.getConnection();
        Statement st;
        ResultSet rs;
        Boolean r = false;
        String req = "SELECT purNo FROM purchase_tab where purNo = " + pidVal + "";
        try {
            st = con.createStatement();
            rs = st.executeQuery(req);
            rs.isBeforeFirst();
            r = rs.isBeforeFirst();

            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "no");
        }
        return r;
    }

    public ArrayList<PurchaseModel> getOrderList() {

        ArrayList<PurchaseModel> orderList = new ArrayList<PurchaseModel>();
        Connection con = dbConnect.getConnection();
        String query = "SELECT * FROM purchase_tab";

        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            PurchaseModel purchase;

            while (rs.next()) {
                purchase = new PurchaseModel(rs.getInt("purNo"), rs.getString("vendorName"), rs.getString("purchaseDate"), Double.parseDouble(rs.getString("amount")));
                orderList.add(purchase);
            }
            con.close();
            st.close();
            rs.close();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return orderList;
    }

    public void ShowPurchases() {

        ArrayList<PurchaseModel> list = getOrderList();
        DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
        model.setRowCount(0);

        Object[] row = new Object[4];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getPurNo();
            row[1] = list.get(i).getVendorName();
            row[2] = list.get(i).getPurchaseDate();
            row[3] = list.get(i).getAmount();

            model.addRow(row);
        }
    }

    public void ShowVals() {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String query = "select message, messageVal from message_tab;";
        model.setRowCount(0);

        try {
            Statement statement;
            ResultSet rs;
            try (Connection con = dbConnect.getConnection()) {
                statement = con.createStatement();
                rs = statement.executeQuery(query);
                while (rs.next()) {
                    if (rs.getInt("messageVal") == 0) {
                        model.addRow(new String[]{
                            "<html><font color=red>" + rs.getString("message") + " is out of stock, please reorder now.</font><br><br>", "<html><u><font color=blue>Close</font></u></html>"});
                    } else {
                        model.addRow(new String[]{
                            "<html>" + rs.getString("message") + " stock is running low, please reorder now.<br><br>", "<html><u><font color=blue>Close</font></u></html>"});
                    }
                }
            }
            statement.close();
            rs.close();
        } catch (SQLException e) {

        }

        if (jTable1.getRowCount() > 0) {
            jLabel2.setText(jTable1.getRowCount() + " New Messages Available");
        } else {
            jLabel2.setText("No New Messages Available");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        lblProducts = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblSales = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        vn = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pn = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pd = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        sum = new javax.swing.JLabel();
        venerror = new javax.swing.JLabel();
        purerror = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jButton15 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

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

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("PURCHASE");
        jLabel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 102, 0), null, null));

        lblSales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSales.setText("SALES");
        lblSales.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalesMouseClicked(evt);
            }
        });

        lblStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStock.setText("STOCK");
        lblStock.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStockMouseClicked(evt);
            }
        });

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/AUXANO-Logo2.png"))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lgoutS.png"))); // NOI18N
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(lblUser)
                .addGap(18, 18, 18)
                .addComponent(lblProducts)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(lblSales)
                .addGap(18, 18, 18)
                .addComponent(lblStock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(50);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(170);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(1);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel22.setText("USE THE FORM BELOW TO RECORD PURCHASE ORDERS");

        jPanel17.setBackground(new java.awt.Color(153, 153, 153));
        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel29.setText("PURCHASE ORDER");

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

        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel15.setText("VENDOR NAME:");

        jLabel9.setText("P.O. NUMBER:");

        pn.setEditable(false);

        jLabel7.setText("PURCHASE DATE:");

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BATCH NO", "PROD ID", "ITEM NAME", "MANF DATE", "EXP DATE", "QUANTITY", "UNIT PRICE", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(jTable9);
        if (jTable9.getColumnModel().getColumnCount() > 0) {
            jTable9.getColumnModel().getColumn(1).setResizable(false);
            jTable9.getColumnModel().getColumn(2).setResizable(false);
            jTable9.getColumnModel().getColumn(3).setResizable(false);
            jTable9.getColumnModel().getColumn(4).setResizable(false);
            jTable9.getColumnModel().getColumn(5).setResizable(false);
            jTable9.getColumnModel().getColumn(6).setResizable(false);
            jTable9.getColumnModel().getColumn(7).setResizable(false);
        }

        jButton2.setText("ADD");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("EDIT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("DELETE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("CREATE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setText("SUM:");

        sum.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        venerror.setForeground(new java.awt.Color(0, 0, 0));

        purerror.setForeground(new java.awt.Color(0, 0, 0));

        jButton8.setText("GET  P.O NO");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel9))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(vn, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(venerror, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jButton8)
                                .addGap(49, 49, 49)
                                .addComponent(jLabel7)
                                .addGap(45, 45, 45)
                                .addComponent(pd, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(purerror, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(84, 84, 84))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sum, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(56, 56, 56))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel9))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(pd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(purerror))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(pn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton8)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(venerror))))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addGap(0, 159, Short.MAX_VALUE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(1, 1, 1)
                .addComponent(jButton5)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1066, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel30.setText("CREATE PURCHASE ORDER");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/closed-envelope.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(1126, 1126, 1126)
                .addComponent(jLabel1))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel30))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(840, 840, 840)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("CREATE PURCHASE ORDER", jPanel4);

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel20.setText("VIEW PURCHASE ORDERS ");

        jPanel14.setBackground(new java.awt.Color(153, 153, 153));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel21.setText("PURCHASE ORDER HISTORY");

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

        jPanel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel22MouseClicked(evt);
            }
        });

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "P.O NUMBER", "VENDOR NAME", "PURCHASE DATE", "AMOUNT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(jTable8);

        jButton15.setText("GENERATE PURCHASE REPORT");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel38.setText("FILTER BY DATE:");

        jLabel39.setText("DATE:");

        jLabel40.setText("TO");

        jLabel17.setText("SEARCH  BY  P.O NUMBER:");

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        jXDatePicker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker2ActionPerformed(evt);
            }
        });

        jButton6.setText("VIEW");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("SEARCH");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton15))
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7))
                            .addComponent(jScrollPane9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addContainerGap())))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel39)
                        .addComponent(jLabel40)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton7)))
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton15)
                        .addContainerGap())
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jButton6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel6.setText("VIEW PURCHASE ORDERS");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(114, 114, 114))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("VIEW PURCHASE ORDERS", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
                .addGap(2, 2, 2))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        DefaultTableModel model = (DefaultTableModel) jTable9.getModel();

        count = jTable9.getRowCount();
        addProduct.setVisible(true);
        addProduct.pack();
        addProduct.setLocationRelativeTo(null);

        addProduct.jTextField1.setText("");
        addProduct.jComboBox1.setSelectedIndex(0);
        addProduct.y1.setText("YYYY");
        addProduct.m1.setText("MM");
        addProduct.d1.setText("DD");
        addProduct.d2.setText("DD");
        addProduct.m2.setText("MM");
        addProduct.y2.setText("YYYY");
        addProduct.jTextField3.setText("");
        addProduct.pr.setText("");


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        if (jTable9.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Select a Row to Edit");
        } else {

            DefaultTableModel model = (DefaultTableModel) jTable9.getModel();
            editProduct.jTextField1.setText(model.getValueAt(jTable9.getSelectedRow(), 0).toString());
            editProduct.jTextField4.setText(model.getValueAt(jTable9.getSelectedRow(), 1).toString());
            editProduct.jTextField2.setText(model.getValueAt(jTable9.getSelectedRow(), 2).toString());
            editProduct.y1.setText(model.getValueAt(jTable9.getSelectedRow(), 3).toString().substring(0, 4));
            editProduct.m1.setText(model.getValueAt(jTable9.getSelectedRow(), 3).toString().substring(5, 7));
            editProduct.d1.setText(model.getValueAt(jTable9.getSelectedRow(), 3).toString().substring(8, 10));

            editProduct.y2.setText(model.getValueAt(jTable9.getSelectedRow(), 4).toString().substring(0, 4));
            editProduct.m2.setText(model.getValueAt(jTable9.getSelectedRow(), 4).toString().substring(5, 7));
            editProduct.d2.setText(model.getValueAt(jTable9.getSelectedRow(), 4).toString().substring(8, 10));
            editProduct.jTextField3.setText(model.getValueAt(jTable9.getSelectedRow(), 5).toString());
            editProduct.jTextField5.setText(model.getValueAt(jTable9.getSelectedRow(), 6).toString());

            editProduct.setVisible(true);
            editProduct.pack();
            editProduct.setLocationRelativeTo(null);
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        if (jTable9.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Select the row you want to delete!");
        } else {

            DefaultTableModel model = (DefaultTableModel) jTable9.getModel();
            model.removeRow(jTable9.getSelectedRow());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (pd.getDate() == null || pn.getText().equals("") || vn.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Form is incomplete");
        } else {
            Connection con = dbConnect.getConnection();
            Date dat;                                                //Getting the date from the date picker
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");                     //Date Format setter
            String date = "";                                                       //Assigning the date format to the selected Date

            double Sum = getSum();
            int pno, p = 0;

            int rows = jTable9.getRowCount();
            String pid = pn.getText();
            String ven = vn.getText();
            String v = "";
            boolean dval = false;
            boolean pval = false;
            boolean vVal = false;

            Date d = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(d);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1, current;
            try {
                dat = pd.getDate();
                date = df.format(dat);
                date1 = sdf.parse(date);
                current = sdf.parse(formattedDate);
                if ((date1.after(current)) && (date1.before(current))) {

                    purerror.setText("*invalid");
                } else {
                    purerror.setText("");
                    dval = true;
                }

            } catch (ParseException ex) {
                purerror.setText("*invalid");
            }

            try {
                pno = Integer.parseInt(pid);
                if (pno > 9999 && pno < 1000000) {
                    p = pno;
                    pval = true;
                }

            } catch (NumberFormatException e) {
                purerror.setText("*invalid");
            }

            if (vn.getText().equals("")) {
                venerror.setText("*invalid");
            } else if (!(vn.getText().matches("[a-zA-Z ]*"))) {
                venerror.setText("*invalid");
            } else {
                venerror.setText("");
                vVal = true;
            }

            if (pn.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "P.O Number field is empty");
            }

            if (!(pn.getText().equals("")) && !(vn.getText().equals("")) && !(pd.getDate() == null)) {

                if (getValidation(p)) {
                    JOptionPane.showMessageDialog(null, "Purchase Order Number Already Exists!");
                } else {

                    if (pval == true && dval == true && vVal == true) {
                        try {

                            if (jTable9.getRowCount() != 0) {

                                Statement st;
                                Statement st1;
                                st = con.createStatement();
                                st1 = con.createStatement();

                                String Query = "INSERT INTO purchase_tab(vendorName, purchaseDate, amount)VALUES('" + ven + "','" + date + "'," + Sum + ")";
                                int execute = st.executeUpdate(Query);

                                for (int row = 0; row < rows; row++) {

                                    String batchNO = jTable9.getValueAt(row, 0).toString();
                                    int pId = Integer.parseInt(jTable9.getValueAt(row, 1).toString());
                                    String itemName = jTable9.getValueAt(row, 2).toString();
                                    String manfDate = jTable9.getValueAt(row, 3).toString();
                                    String expDate = jTable9.getValueAt(row, 4).toString();
                                    int quantity = Integer.parseInt(jTable9.getValueAt(row, 5).toString());
                                    double unitp = Double.parseDouble(jTable9.getValueAt(row, 6).toString());
                                    double price = Double.parseDouble(jTable9.getValueAt(row, 7).toString());

                                    String Query2 = "INSERT INTO purchaseItems_tab(purNo, batchNo, prodID, prodName, manfDate, expDate, quantity, unitPrice, price) VALUES(" + p + ",'" + batchNO + "'," + pId + ",'" + itemName + "','" + manfDate + "','" + expDate + "'," + quantity + "," + unitp + "," + price + ")";
                                    String Query3 = "INSERT INTO stocks_tab (batchNo, prodID, prodName, manfDate, expDate, quantity) VALUES('" + batchNO + "', " + pId + ", '" + itemName + "', '" + manfDate + "','" + expDate + "'," + quantity + ")";// ON DUPLICATE KEY UPDATE  quantity = quantity + " + quantity + " ";

                                    int execute2 = st1.executeUpdate(Query2);
                                    int execute3 = st.executeUpdate(Query3);
                                }

                                JOptionPane.showMessageDialog(null, "Successfully Created");

                                pn.setText("");
                                vn.setText("");
                                pd.setDate(null);
                                DefaultTableModel model = (DefaultTableModel) jTable9.getModel();
                                model.setRowCount(0);
                                ShowPurchases();
                                st.close();
                                st1.close();
                                con.close();
                            } else {
                                JOptionPane.showMessageDialog(null, "No Items Added");
                            }
                        } catch (HeadlessException | NumberFormatException | SQLException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter correct values");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Fill in the blanks");
            }
        }

    }//GEN-LAST:event_jButton5ActionPerformed

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
        if ((UserModel.userRole.equals("STOCK CONTOLLER")) || (UserModel.userRole.equals("ADMIN"))) {
            AddProduct frame = new AddProduct();
            frame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this tab.");
        }
    }//GEN-LAST:event_lblProductsMouseClicked

    private void lblSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalesMouseClicked
        if ((UserModel.userRole.equals("SALES MANAGER")) || (UserModel.userRole.equals("ADMIN"))) {
            Sales frame = new Sales();
            frame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this tab.");
        }
    }//GEN-LAST:event_lblSalesMouseClicked

    private void lblStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblStockMouseClicked
        if ((UserModel.userRole.equals("STOCK CONTROLLER")) || (UserModel.userRole.equals("ADMIN"))) {
            Stock frame = new Stock();
            frame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this tab.");
        }
    }//GEN-LAST:event_lblStockMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        int column = 0;

        if (jTable8.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Select the Purchase Order you want to view");
        } else {

            String[] results = new String[9];
            int row = jTable8.getSelectedRow();
            String value = jTable8.getModel().getValueAt(row, column).toString();
            int n = Integer.parseInt(value);

            String query = "select * from purchaseItems_tab where purNo = " + n + " ";
            try {

                Connection con = dbConnect.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    results[0] = rs.getString("purNo");
                    results[1] = rs.getString("batchNo");
                    results[2] = rs.getString("prodID");
                    results[3] = rs.getString("prodName");
                    results[4] = rs.getString("manfDate");
                    results[5] = rs.getString("expDate");
                    results[6] = rs.getString("quantity");
                    results[7] = rs.getString("unitPrice");
                    results[8] = rs.getString("price");

                    DefaultTableModel model3 = (DefaultTableModel) PurchaseItemsView.jTable1.getModel();

                    model3.addRow(results);

                }
                st.close();
                rs.close();
                con.close();
            } catch (SQLException e) {

            }

            DefaultTableModel model = (DefaultTableModel) jTable8.getModel();

            viewOrder.jTextField1.setText(model.getValueAt(jTable8.getSelectedRow(), 0).toString());
            viewOrder.jTextField2.setText(model.getValueAt(jTable8.getSelectedRow(), 1).toString());
            viewOrder.jTextField3.setText(model.getValueAt(jTable8.getSelectedRow(), 2).toString());
            viewOrder.jLabel9.setText(model.getValueAt(jTable8.getSelectedRow(), 3).toString());

            viewOrder.setVisible(true);
            viewOrder.pack();
            viewOrder.setLocationRelativeTo(null);

        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        purReport.setVisible(true);
        purReport.pack();
        purReport.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if (jTextField1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Search field is empty!");
        } else {
            String num = jTextField1.getText();
            ArrayList<PurchaseModel> list = getOrderList();
            DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
            model.setRowCount(0);

            Object[] row = new Object[4];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getPurNo();
                row[1] = list.get(i).getVendorName();
                row[2] = list.get(i).getPurchaseDate();
                row[3] = list.get(i).getAmount();

                if (list.get(i).getPurNo() == Integer.parseInt(num)) {
                    model.addRow(row);
                }
            }
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        int val, id = 0;

        try {
            Connection con = dbConnect.getConnection();

            String query = "select max(purNo) from purchase_tab";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt(1);
            }

            con.close();
            statement.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No values in the database");
        }
        val = id + 1;
        if (val != 1) {
            pn.setText(val + "");
        } else {
            val = 10000;
            pn.setText(val + "");
        }

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed

        jXDatePicker2.setDate(null);
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formated = df.format(today);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date pd1;

        String date1 = "";
        String date2 = "";
        Date current, from, pDate, to;
        try {
            pd1 = jXDatePicker1.getDate();
            date1 = df.format(pd1);
            from = sdf.parse(date1);
            current = sdf.parse(formated);

            if (from.after(current)) {
                JOptionPane.showMessageDialog(null, "Select a proper date!");
            } else {
                ArrayList<PurchaseModel> list = getOrderList();
                DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
                model.setRowCount(0);

                Object[] row = new Object[4];
                for (int i = 0; i < list.size(); i++) {
                    row[0] = list.get(i).getPurNo();
                    row[1] = list.get(i).getVendorName();
                    row[2] = list.get(i).getPurchaseDate();
                    row[3] = list.get(i).getAmount();

                    pDate = sdf.parse(list.get(i).getPurchaseDate().toString());

                    if (pDate.compareTo(from) >= 0) {
                        model.addRow(row);
                    }

                }
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    private void jXDatePicker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker2ActionPerformed
        // TODO add your handling code here:

        Date today = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formated = df.format(today);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date pd2;

        String date2 = "";
        String date1 = "";

        Date current, from, pDate, to;
        try {
            pd2 = jXDatePicker2.getDate();
            date2 = df.format(pd2);
            to = sdf.parse(date2);
            current = sdf.parse(formated);

            if (to.after(current)) {
                JOptionPane.showMessageDialog(null, "Select a proper date!");
            } else {
                ArrayList<PurchaseModel> list = getOrderList();
                DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
                model.setRowCount(0);

                Object[] row = new Object[4];
                for (int i = 0; i < list.size(); i++) {
                    row[0] = list.get(i).getPurNo();
                    row[1] = list.get(i).getVendorName();
                    row[2] = list.get(i).getPurchaseDate();
                    row[3] = list.get(i).getAmount();

                    pDate = sdf.parse(list.get(i).getPurchaseDate().toString());

                    if (pDate.compareTo(to) <= 0 && jXDatePicker1.getDate() == null) {
                        model.addRow(row);
                    }

                    if (pDate.compareTo(to) <= 0 && jXDatePicker1.getDate() != null) {
                        date1 = sdf.format(jXDatePicker1.getDate());
                        from = sdf.parse(date1);
                        if (to.compareTo(from) >= 0 && pDate.compareTo(from) >= 0 && from.before(current)) {
                            model.addRow(row);
                        } else {
                            JOptionPane.showMessageDialog(null, "Select proper dates!");
                        }
                    }

                }
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jXDatePicker2ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        int pop = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", pop);
        if (result == 0) {

            UserModel.loginName = "";
            UserModel.userRole = "";

            Login frame = new Login();
            frame.setVisible(true);
            this.dispose();
        }

    }//GEN-LAST:event_jButton1MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        if (jPanel1.isVisible()) {
            jPanel1.setVisible(false);
            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/closed-envelope.png")));
        } else {
            jPanel1.setVisible(true);
            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/open-envelop.png")));
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String val = model.getValueAt(jTable1.getSelectedRow(), 0).toString();
        String product = "";
        String filter = "";

        if (val.contains("stock is running low, please reorder now.")) {
            filter = val.replace("<html>", "");
            product = filter.replace(" stock is running low, please reorder now.<br><br>", "");
        } else {
            filter = val.replace("<html><font color=red>", "");
            product = filter.replace(" is out of stock, please reorder now.</font><br><br>", "");
        }

        String query = "DELETE FROM message_tab WHERE message = '" + product + "';";

        try {
            Connection con = dbConnect.getConnection();
            Statement st = con.createStatement();
            int execute = st.executeUpdate(query);
            model.removeRow(jTable1.getSelectedRow());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        if (jTable1.getRowCount() == 0) {
            jLabel2.setText("No New Messages Available");
        } else {
            jLabel2.setText(jTable1.getRowCount() + " Messages Available");
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jPanel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel22MouseClicked
        // TODO add your handling code here:
        jTable8.clearSelection();
    }//GEN-LAST:event_jPanel22MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
        pn.setText("");
        vn.setText("");
        pd.setDate(null);
        if (jPanel1.isVisible()) {
            jPanel1.setVisible(false);
            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/closed-envelope.png")));
        }
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
        pn.setText("");
        vn.setText("");
        pd.setDate(null);
        if (jPanel1.isVisible()) {
            jPanel1.setVisible(false);
            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/closed-envelope.png")));
        }
    }//GEN-LAST:event_jPanel4MouseClicked

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
            java.util.logging.Logger.getLogger(Purchase.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Purchase.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Purchase.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Purchase.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Purchase().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTable jTable8;
    public static javax.swing.JTable jTable9;
    private javax.swing.JTextField jTextField1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private javax.swing.JLabel lblProducts;
    private javax.swing.JLabel lblSales;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblUser;
    private org.jdesktop.swingx.JXDatePicker pd;
    private javax.swing.JTextField pn;
    private javax.swing.JLabel purerror;
    public javax.swing.JLabel sum;
    private javax.swing.JLabel venerror;
    private javax.swing.JTextField vn;
    // End of variables declaration//GEN-END:variables
}
