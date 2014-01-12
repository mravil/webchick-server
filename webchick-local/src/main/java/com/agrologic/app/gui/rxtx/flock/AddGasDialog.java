/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.app.gui.rxtx.flock;

import com.agrologic.app.model.DataFormat;
import com.agrologic.app.model.Gas;
import net.sf.nachocalendar.CalendarFactory;
import net.sf.nachocalendar.components.DateField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Administrator
 */
public class AddGasDialog extends JDialog {

    private Gas currGas;
    private Long flockId;
    private DateField d1;
    private FlockManagerService flockService;

    /**
     * Creates new form AddGasDialog
     */
    public AddGasDialog(Frame parent, boolean modal) {
        this(Long.valueOf(1), parent, modal);
    }

    public AddGasDialog(Long flockId, Frame owner, boolean modal) {
        super(owner, modal);
        initComponents();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
        this.d1 = CalendarFactory.createDateField();
        d1.setDateFormat(df);
        getContentPane().add(d1);
        d1.setBounds(170, 226, 226, 20);
        flockService = new FlockManagerService();
        this.flockId = flockId;
        loadGas(this.flockId);
        tblDataTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // get the coordinates of the mouse click
                    Point p = e.getPoint();
                    // get the row index that contains that coordinate
                    int rowNumber = tblDataTable.rowAtPoint(p);
                    // get the ListSelectionModel of the JTable
                    ListSelectionModel model = tblDataTable.getSelectionModel();
                    // set the selected interval of rows. Using the "rowNumber"
                    // variable for the beginning and end selects only that one row.
                    model.setSelectionInterval(rowNumber, rowNumber);
                    Long id = (Long) tblDataTable.getModel().getValueAt(rowNumber, 0);
                    currGas = flockService.getGasById(id);
                    setForm(currGas);
                    btnAdd.setEnabled(false);
                    btnRemove.setEnabled(true);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataTable = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        txtAccountNumber = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnRemove = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add  Gas");
        setMinimumSize(new java.awt.Dimension(650, 400));
        getContentPane().setLayout(null);

        tblDataTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "ID", "Amount", "Date", "Price", "Account Number", "Total"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.Long.class, java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDataTable);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 21, 624, 160);

        btnAdd.setText("Add ");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdd);
        btnAdd.setBounds(435, 195, 105, 23);

        jLabel2.setText("Amount");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 198, 140, 20);

        jLabel3.setText("Date");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 226, 140, 20);

        jLabel4.setText("Price");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 252, 140, 20);

        jLabel5.setText("Account Number");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 278, 140, 20);

        txtAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAmountKeyReleased(evt);
            }

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAmountKeyTyped(evt);
            }
        });
        getContentPane().add(txtAmount);
        txtAmount.setBounds(170, 198, 226, 20);

        txtPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPriceKeyReleased(evt);
            }

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPriceKeyTyped(evt);
            }
        });
        getContentPane().add(txtPrice);
        txtPrice.setBounds(170, 252, 226, 20);

        txtAccountNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAccountNumberKeyReleased(evt);
            }

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAccountNumberKeyTyped(evt);
            }
        });
        getContentPane().add(txtAccountNumber);
        txtAccountNumber.setBounds(170, 278, 226, 20);

        jLabel6.setText("Total");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 304, 140, 20);

        txtTotal.setEditable(false);
        getContentPane().add(txtTotal);
        txtTotal.setBounds(170, 304, 226, 20);

        btnRemove.setText("Delete");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        getContentPane().add(btnRemove);
        btnRemove.setBounds(435, 220, 105, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!validateForm()) {
            return;
        }

        Gas gas = getFromFields();
        flockService.addGas(gas);
        loadGas(flockId);
        clearForm();

    }//GEN-LAST:event_btnAddActionPerformed

    private void calcTotal() {
        String samount = txtAmount.getText();
        String sprice = txtPrice.getText();
        int amount = Integer.parseInt(samount);
        float price = Float.parseFloat(sprice);
        float total = (amount * price);
        txtTotal.setText("" + total);
    }

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if (currGas == null) {
            return;
        }
        flockService.removeGas(currGas.getId());
        loadGas(flockId);
        clearForm();
        btnAdd.setEnabled(true);
        btnRemove.setEnabled(false);

    }//GEN-LAST:event_btnRemoveActionPerformed

    private void txtAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountKeyTyped

    }//GEN-LAST:event_txtAmountKeyTyped

    private void txtAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountKeyReleased
        FlockGUIUtil.keyPressedHandler(txtAmount, evt);
        calcTotal();
    }//GEN-LAST:event_txtAmountKeyReleased

    private void txtPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyTyped
        FlockGUIUtil.keyTypedHandler(txtPrice, evt);
    }//GEN-LAST:event_txtPriceKeyTyped

    private void txtPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyReleased
        FlockGUIUtil.keyPressedHandler(txtPrice, evt, DataFormat.DEC_2);
        calcTotal();
    }//GEN-LAST:event_txtPriceKeyReleased

    private void txtAccountNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAccountNumberKeyTyped
        FlockGUIUtil.keyPressedHandler(txtAccountNumber, evt);
    }//GEN-LAST:event_txtAccountNumberKeyTyped

    private void txtAccountNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAccountNumberKeyReleased
        FlockGUIUtil.keyPressedHandler(txtAccountNumber, evt);
    }//GEN-LAST:event_txtAccountNumberKeyReleased

    private void loadGas(Long flockId) {
        ((DefaultTableModel) tblDataTable.getModel()).getDataVector().removeAllElements();
        ((ListSelectionModel) tblDataTable.getSelectionModel()).setSelectionInterval(-1, -1);
        tblDataTable.revalidate();
        List<Gas> gasList = flockService.getAllGas(flockId);
        for (Gas g : gasList) {
            ((DefaultTableModel) tblDataTable.getModel()).insertRow(0, new Object[]{
                    g.getId(),
                    g.getAmount(),
                    g.getDate(),
                    g.getPrice(),
                    g.getNumberAccount(),
                    g.getTotal()});
        }
    }

    private Gas getFromFields() {

        String amount = txtAmount.getText();
        String date = d1.getFormattedTextField().getText();
        String price = txtPrice.getText();
        String accountNumber = txtAccountNumber.getText();
        String total = txtTotal.getText();

        currGas = new Gas();
        currGas.setAmount(Integer.parseInt(amount));
        currGas.setDate(date);
        currGas.setFlockId(flockId);
        currGas.setNumberAccount(Integer.parseInt(accountNumber));
        currGas.setPrice(Float.parseFloat(price));
        currGas.setTotal(Float.parseFloat(total));
        return currGas;
    }

    private boolean validateForm() {
        if (txtAmount.getText().equals("")
                || txtPrice.getText().equals("")
                || txtAccountNumber.getText().equals("")
                || txtTotal.getText().equals("")) {
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtAmount.setText("");
        txtPrice.setText("");
        txtAccountNumber.setText("");
        txtTotal.setText("");
    }

    private void setForm(Gas gas) {
        try {
            txtAmount.setText("" + gas.getAmount());
            txtPrice.setText("" + gas.getPrice());
            DateFormat shortDf = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
            Date date = shortDf.parse(gas.getDate());
            d1.setValue(date);
            txtAccountNumber.setText("" + gas.getNumberAccount());
            txtTotal.setText("" + gas.getTotal());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddGasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddGasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddGasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddGasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                AddGasDialog dialog = new AddGasDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnRemove;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDataTable;
    private javax.swing.JTextField txtAccountNumber;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
