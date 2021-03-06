
import Models.DatabaseConnection;
import Models.SalesModel;
import Models.UserModel;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Illyas
 */
public class SalesReviewWindow extends javax.swing.JFrame {

    /**
     * Creates new form SalesReviewWindow
     */
    DatabaseConnection dbConnect = new DatabaseConnection();

    public SalesReviewWindow() {
        initComponents();
        URL iconURL = getClass().getResource("Images/AUXANO ICON 20.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        lblErrorOS.setVisible(false);
        lblErrorRD.setVisible(false);
        dpReqDate.setVisible(false);
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
        DefaultTableModel model = (DefaultTableModel) Sales.tblReviewSales.getModel();
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
        DefaultTableModel model = (DefaultTableModel) Sales.tblViewSalesOrders.getModel();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        lblErrorOS = new javax.swing.JLabel();
        lblErrorRD = new javax.swing.JLabel();
        dpReqDate = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        m1 = new javax.swing.JTextField();
        y1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        d1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnCanecl = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(" Auxano PVT LTD.");
        setResizable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("UPDATE SALES ORDER"));

        jLabel2.setText("STATUS:");

        jLabel3.setText("REQUIRED DATE:");

        btnSave.setText("SAVE");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        btnSave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSaveKeyPressed(evt);
            }
        });

        lblErrorOS.setText("*Invalid");

        lblErrorRD.setText("*Invalid");

        jLabel8.setText("-");

        m1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                m1KeyTyped(evt);
            }
        });

        y1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                y1KeyTyped(evt);
            }
        });

        jLabel7.setText("-");

        jLabel6.setForeground(new java.awt.Color(102, 102, 102));

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AWAITING FULFILLMENT", "COMPLETED", "CANCELLED" }));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/AUXANO-Logo2.png"))); // NOI18N

        btnCanecl.setText("CANCEL");
        btnCanecl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaneclActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dpReqDate, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(btnCanecl, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(y1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(m1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(d1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(lblErrorOS)
                    .addComponent(lblErrorRD))
                .addGap(114, 114, 114))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblErrorRD)
                    .addComponent(dpReqDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(y1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(d1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6))
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblErrorOS)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCanecl)
                    .addComponent(btnSave))
                .addGap(85, 85, 85))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        int index = Sales.tblReviewSales.getSelectedRow();

        String reqDate = y1.getText() + "-" + m1.getText() + "-" + d1.getText();
        String orderStatus = (String) cmbStatus.getSelectedItem();
        String sonumb = dpReqDate.getText();
        boolean day = false;

        Date today = new Date();
        Date d, current;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formated = sdf.format(today);

        try {
            current = sdf.parse(formated);
            d = sdf.parse(reqDate);
            if (d.compareTo(current) >= 0) {
                day = true;
                jLabel6.setText("");
            } else {
                jLabel6.setText("Invalid");
            }
        } catch (Exception e) {
            jLabel6.setText("Invalid");
        }

        if ((day == true) && (!orderStatus.equals(""))) {

//            ((DefaultTableModel) Sales.tblReviewSales.getModel()).setValueAt(reqDate, index, 2);
//            ((DefaultTableModel) Sales.tblReviewSales.getModel()).setValueAt(orderStatus, index, 6);
            String query = "UPDATE sales_tab SET orderStatus='" + orderStatus + "',reqDate='" + reqDate + "' WHERE soNumber=" + sonumb + ";";

            try {
                DefaultTableModel model = (DefaultTableModel) Sales.tblReviewSales.getModel();
                Connection con = dbConnect.getConnection();
                Statement st = con.createStatement();
                int execute = st.executeUpdate(query);
                if (orderStatus.equals("CANCELLED")) {
                    String query2 = "select batchNo, quantity from salesItems_tab where soNumber = " + sonumb + "";
                    ResultSet rs = st.executeQuery(query2);

                    while (rs.next()) {
                        String bchNo = rs.getString("batchNo");
                        int quan = rs.getInt("quantity");
                        String query3 = "UPDATE stocks_tab SET quantity = quantity + " + quan + " WHERE batchNo = '" + bchNo + "';";
                        int execute2 = st.executeUpdate(query3);
                    }
                    rs.close();
                }
                ShowReviewSales();
                ShowSales();
                con.close();
                st.close();
                dispose();

            } catch (HeadlessException | SQLException e) {
            }

        } else {
            JOptionPane.showMessageDialog(rootPane, "Fill in the blanks");
        }


    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSaveKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveKeyPressed

    private void btnCaneclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaneclActionPerformed
        // TODO add your handling code here:

        this.dispose();

    }//GEN-LAST:event_btnCaneclActionPerformed

    private void y1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_y1KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (y1.getText().length() >= 3 && c != KeyEvent.VK_BACK_SPACE) {
            m1.requestFocus();
        }
    }//GEN-LAST:event_y1KeyTyped

    private void m1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_m1KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (m1.getText().length() >= 1 && c != KeyEvent.VK_BACK_SPACE) {
            d1.requestFocus();
        }
    }//GEN-LAST:event_m1KeyTyped

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
            java.util.logging.Logger.getLogger(SalesReviewWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesReviewWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesReviewWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesReviewWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SalesReviewWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCanecl;
    private javax.swing.JButton btnSave;
    public javax.swing.JComboBox<String> cmbStatus;
    public javax.swing.JTextField d1;
    public javax.swing.JTextField dpReqDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblErrorOS;
    private javax.swing.JLabel lblErrorRD;
    public javax.swing.JTextField m1;
    public javax.swing.JTextField y1;
    // End of variables declaration//GEN-END:variables
}
