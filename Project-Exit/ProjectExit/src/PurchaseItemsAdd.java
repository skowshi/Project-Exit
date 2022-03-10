
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Models.DatabaseConnection;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class PurchaseItemsAdd extends javax.swing.JFrame {

    /**
     * Creates new form PurchaseItemsAdd
     */
    public PurchaseItemsAdd() {
        initComponents();
        URL iconURL = getClass().getResource("Images/AUXANO ICON 20.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
        fillCombo();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    DatabaseConnection dbConnect = new DatabaseConnection();

    public void fillCombo() {

        try {
            Connection con = dbConnect.getConnection();
            String query1 = "select * from products_tab";
            ResultSet rs;
            PreparedStatement pst = con.prepareStatement(query1);
            rs = pst.executeQuery();

            while (rs.next()) {
                String itemN = rs.getString("prodName");
                jComboBox1.addItem(itemN);
            }
            con.close();
            pst.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        d1error = new javax.swing.JLabel();
        d2error = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        y1 = new javax.swing.JTextField();
        y2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        m1 = new javax.swing.JTextField();
        m2 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        d1 = new javax.swing.JTextField();
        d2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pr = new javax.swing.JTextField();
        berror = new javax.swing.JLabel();
        qerror = new javax.swing.JLabel();
        uerror = new javax.swing.JLabel();
        ierror = new javax.swing.JLabel();
        mnerror = new javax.swing.JLabel();
        exerror = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(" Auxano PVT LTD.");
        setResizable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("ADD ITEM"));

        jLabel2.setText("BATCH NO:");

        jLabel3.setText("ITEM NAME:");

        jLabel4.setText("MANF DATE:");

        jLabel5.setText("EXP DATE:");

        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jLabel9.setText("QUANTITY:");

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }
        });

        d1error.setForeground(new java.awt.Color(0, 0, 0));

        d2error.setForeground(new java.awt.Color(0, 0, 0));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        y1.setText("YYYY");
        y1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                y1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                y1FocusLost(evt);
            }
        });
        y1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                y1KeyTyped(evt);
            }
        });

        y2.setText("YYYY");
        y2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                y2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                y2FocusLost(evt);
            }
        });
        y2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                y2KeyTyped(evt);
            }
        });

        jLabel13.setText("-");

        jLabel14.setText("-");

        m1.setText("MM");
        m1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                m1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                m1FocusLost(evt);
            }
        });
        m1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                m1KeyTyped(evt);
            }
        });

        m2.setText("MM");
        m2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                m2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                m2FocusLost(evt);
            }
        });
        m2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                m2KeyTyped(evt);
            }
        });

        jLabel15.setText("-");

        jLabel16.setText("-");

        d1.setText("DD");
        d1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                d1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                d1FocusLost(evt);
            }
        });
        d1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                d1KeyTyped(evt);
            }
        });

        d2.setText("DD");
        d2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                d2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                d2FocusLost(evt);
            }
        });
        d2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                d2KeyTyped(evt);
            }
        });

        jLabel7.setText("UNIT PRICE:");

        pr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                prKeyPressed(evt);
            }
        });

        berror.setForeground(new java.awt.Color(0, 0, 0));

        qerror.setForeground(new java.awt.Color(0, 0, 0));

        uerror.setForeground(new java.awt.Color(0, 0, 0));

        ierror.setForeground(new java.awt.Color(0, 0, 0));

        mnerror.setForeground(new java.awt.Color(0, 0, 0));

        exerror.setForeground(new java.awt.Color(0, 0, 0));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/AUXANO-Logo2.png"))); // NOI18N

        jButton2.setText("CANCEL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(y1, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                    .addComponent(y2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(m1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(d1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(m2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(d2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(pr))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(berror)
                            .addComponent(qerror)
                            .addComponent(uerror)
                            .addComponent(ierror)
                            .addComponent(d1error, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(d2error, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(exerror)
                                    .addComponent(mnerror)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(94, 94, 94)))
                .addGap(89, 113, Short.MAX_VALUE))
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
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(berror))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ierror))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(y1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(m1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(d1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(d1error)
                    .addComponent(mnerror))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(y2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(m2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(d2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(d2error)
                    .addComponent(exerror))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qerror))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(pr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uerror))
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
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
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        DefaultTableModel model = (DefaultTableModel) Purchase.jTable9.getModel();
        Connection con = dbConnect.getConnection();

        String batchNo = jTextField1.getText();
        String itemName = jComboBox1.getSelectedItem().toString();
        String manf = y1.getText() + "-" + m1.getText() + "-" + d1.getText();
        String exp = y2.getText() + "-" + m2.getText() + "-" + d2.getText();
        String quantity = jTextField3.getText();
        String Unit = pr.getText();
        int pid = 0;
        double price = 0;
        int quan = 0;
        boolean datVal1 = false;
        boolean datVal2 = false;
        boolean qVal = false;
        boolean pVal = false;
        boolean bVal = false;

        try {
            quan = Integer.parseInt(quantity);
            if (quan > 0) {
                qVal = true;
                qerror.setText("");
            }
        } catch (NumberFormatException e) {
            qerror.setText("*invalid");
        }

        if (jTextField1.getText().equals("")) {
            berror.setText("*invalid");
        } else {
            bVal = true;
            berror.setText("");
        }

        try {
            price = Double.parseDouble(Unit);
            if (price > 0) {
                pVal = true;

                uerror.setText("");
            }
        } catch (NumberFormatException e) {
            uerror.setText("*invalid");
        }

        if (jComboBox1.getSelectedIndex() == 0) {
            ierror.setText("*invalid");
        } else {
            ierror.setText("");
        }

        Date d = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(d);
        LocalDate bef = LocalDate.now().minusDays(30);
        LocalDate aft = LocalDate.now().plusDays(365);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String beforeDate = bef.toString();
        String afterDate = bef.toString();
        Date date1, date2, current, befDate, afDate;
        try {
            date1 = sdf.parse(manf);
            current = sdf.parse(formattedDate);
            befDate = sdf.parse(beforeDate);

            if ((date1.after(befDate)) && (date1.before(current))) {
                datVal1 = true;
                d1error.setText("");
            } else {
                d1error.setText("*invalid");
            }

        } catch (ParseException ex) {
            d1error.setText("*invalid");
        }

        try {
            date2 = sdf.parse(exp);
            current = sdf.parse(formattedDate);
            afDate = sdf.parse(afterDate);

            if ((date2.after(current)) && (date2.after(afDate))) {
                datVal2 = true;
                d2error.setText("");
            } else {
                d2error.setText("*invalid");
            }

        } catch (ParseException ex) {
            d2error.setText("*invalid");
        }

        //dif(Integer.parseInt(quantity)) 
        String query = "select prodID from products_tab where prodName =? ";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, jComboBox1.getSelectedItem().toString());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                pid = rs.getInt("prodID");
            }

            pst.close();
            rs.close();
            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "NO VALUES");
        }

        if (!(jTextField1.getText().equals("")) && !(jComboBox1.getSelectedIndex() == 0) && !(y1.getText().equals("YYYY")) && !(m1.getText().equals("MM")) && !(d1.getText().equals("DD"))
                && !(y2.getText().equals("YYYY")) && !(m2.getText().equals("MM")) && !(d2.getText().equals("DD")) && !(jTextField3.getText().equals("")) && !(pr.getText().equals(""))) {

            if (datVal1 == true && datVal2 == true && qVal == true && bVal == true && pVal == true) {

                model.addRow(new Object[]{batchNo, pid, itemName,
                    y1.getText() + "-" + m1.getText() + "-" + d1.getText(),
                    y2.getText() + "-" + m2.getText() + "-" + d2.getText(),
                    jTextField3.getText(), Unit, price * quan});

                dispose();

            } else {

                JOptionPane.showMessageDialog(rootPane, "Enter Correct Values!");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Fill in the blanks");
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void y1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_y1FocusGained
        // TODO add your handling code here:
        if (y1.getText().trim().toLowerCase().equals("yyyy")) {
            y1.setText("");
        }
    }//GEN-LAST:event_y1FocusGained

    private void y1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_y1FocusLost
        // TODO add your handling code here:
        if ((y1.getText().trim().toLowerCase().equals("")) || (y1.getText().trim().toLowerCase().equals(""))) {
            y1.setText("YYYY");
        }
    }//GEN-LAST:event_y1FocusLost

    private void m1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_m1FocusGained
        // TODO add your handling code here:
        if (m1.getText().trim().toLowerCase().equals("mm")) {
            m1.setText("");
        }
    }//GEN-LAST:event_m1FocusGained

    private void m1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_m1FocusLost
        // TODO add your handling code here:
        if ((m1.getText().trim().toLowerCase().equals("")) || (m1.getText().trim().toLowerCase().equals(""))) {
            m1.setText("MM");
        }
    }//GEN-LAST:event_m1FocusLost

    private void d1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_d1FocusGained
        // TODO add your handling code here:
        if (d1.getText().trim().toLowerCase().equals("dd")) {
            d1.setText("");
        }
    }//GEN-LAST:event_d1FocusGained

    private void d1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_d1FocusLost
        // TODO add your handling code here:
        if ((d1.getText().trim().toLowerCase().equals("")) || (d1.getText().trim().toLowerCase().equals(""))) {
            d1.setText("DD");
        }
    }//GEN-LAST:event_d1FocusLost

    private void y2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_y2FocusGained
        // TODO add your handling code here:
        if (y2.getText().trim().toLowerCase().equals("yyyy")) {
            y2.setText("");
        }
    }//GEN-LAST:event_y2FocusGained

    private void y2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_y2FocusLost
        // TODO add your handling code here:
        if ((y2.getText().trim().toLowerCase().equals("")) || (y2.getText().trim().toLowerCase().equals(""))) {
            y2.setText("YYYY");
        }
    }//GEN-LAST:event_y2FocusLost

    private void m2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_m2FocusGained
        // TODO add your handling code here:
        if (m2.getText().trim().toLowerCase().equals("mm")) {
            m2.setText("");
        }
    }//GEN-LAST:event_m2FocusGained

    private void m2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_m2FocusLost
        // TODO add your handling code here:
        if ((m2.getText().trim().toLowerCase().equals("")) || (m2.getText().trim().toLowerCase().equals(""))) {
            m2.setText("MM");
        }
    }//GEN-LAST:event_m2FocusLost

    private void d2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_d2FocusGained
        // TODO add your handling code here:
        if (d2.getText().trim().toLowerCase().equals("dd")) {
            d2.setText("");
        }
    }//GEN-LAST:event_d2FocusGained

    private void d2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_d2FocusLost
        // TODO add your handling code here:
        if ((d2.getText().trim().toLowerCase().equals("")) || (d2.getText().trim().toLowerCase().equals(""))) {
            d2.setText("DD");
        }
    }//GEN-LAST:event_d2FocusLost

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

    private void d1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_d1KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (d1.getText().length() >= 1 && c != KeyEvent.VK_BACK_SPACE) {
            y2.requestFocus();
        }
    }//GEN-LAST:event_d1KeyTyped

    private void y2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_y2KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (y2.getText().length() >= 3 && c != KeyEvent.VK_BACK_SPACE) {
            m2.requestFocus();
        }
    }//GEN-LAST:event_y2KeyTyped

    private void m2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_m2KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (m2.getText().length() >= 1 && c != KeyEvent.VK_BACK_SPACE) {
            d2.requestFocus();
        }
    }//GEN-LAST:event_m2KeyTyped

    private void d2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_d2KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (d2.getText().length() >= 1 && c != KeyEvent.VK_BACK_SPACE) {
            jTextField3.requestFocus();
        }
    }//GEN-LAST:event_d2KeyTyped

    private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            pr.requestFocus();
        }
    }//GEN-LAST:event_jTextField3KeyPressed

    private void prKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jTextField3.requestFocus();
        }
    }//GEN-LAST:event_prKeyPressed

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
            java.util.logging.Logger.getLogger(PurchaseItemsAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PurchaseItemsAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PurchaseItemsAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PurchaseItemsAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PurchaseItemsAdd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel berror;
    public javax.swing.JTextField d1;
    private javax.swing.JLabel d1error;
    public javax.swing.JTextField d2;
    private javax.swing.JLabel d2error;
    private javax.swing.JLabel exerror;
    private javax.swing.JLabel ierror;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    public javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField3;
    public javax.swing.JTextField m1;
    public javax.swing.JTextField m2;
    private javax.swing.JLabel mnerror;
    public static javax.swing.JTextField pr;
    private javax.swing.JLabel qerror;
    private javax.swing.JLabel uerror;
    public javax.swing.JTextField y1;
    public javax.swing.JTextField y2;
    // End of variables declaration//GEN-END:variables
}
