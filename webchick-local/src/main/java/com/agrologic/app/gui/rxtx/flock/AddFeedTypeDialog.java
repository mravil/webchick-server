/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.app.gui.rxtx.flock;

import com.agrologic.app.config.Configuration;
import com.agrologic.app.model.DataFormat;
import com.agrologic.app.model.FeedType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class AddFeedTypeDialog extends JDialog {
    private Configuration conf;
    private Long cellinkId;
    private FeedType currFeedType;
    private FlockManagerService flockService;

    /**
     * Creates new form AddSpreadDialog
     */
    public AddFeedTypeDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        flockService = new FlockManagerService();
        conf = new Configuration();
        cellinkId = Long.parseLong(conf.getCellinkId());
        loadFeedType(cellinkId);
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
                    currFeedType = flockService.getFeedTypeById(id);
                    setForm(currFeedType);
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

        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataTable = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFeedType = new javax.swing.JTextField();
        btnRemove = new javax.swing.JButton();
        txtFeedTypePrice = new javax.swing.JTextField();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Feed Type");
        setMinimumSize(new java.awt.Dimension(390, 300));
        getContentPane().setLayout(null);

        tblDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Feed Type", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDataTable);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 11, 360, 170);

        btnAdd.setText("Add ");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdd);
        btnAdd.setBounds(260, 200, 105, 23);

        jLabel2.setText("Feed Type");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 200, 65, 20);

        jLabel4.setText("Price");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 230, 65, 20);
        getContentPane().add(txtFeedType);
        txtFeedType.setBounds(100, 200, 140, 20);

        btnRemove.setText("Delete");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        getContentPane().add(btnRemove);
        btnRemove.setBounds(260, 230, 105, 23);

        txtFeedTypePrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFeedTypePriceKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFeedTypePriceKeyTyped(evt);
            }
        });
        getContentPane().add(txtFeedTypePrice);
        txtFeedTypePrice.setBounds(100, 230, 140, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!validateForm()) {
            return;
        }

        FeedType feedType = getFromFields();
        flockService.addFeedType(feedType);
        loadFeedType(cellinkId);
        clearForm();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if(currFeedType == null) {
            return;
        }

        flockService.removeFeedType(currFeedType.getId());
        loadFeedType(cellinkId);
        clearForm();
        btnAdd.setEnabled(true);
        btnRemove.setEnabled(false);
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void txtFeedTypePriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFeedTypePriceKeyTyped
        FlockGUIUtil.keyTypedHandler(txtFeedTypePrice, evt);
    }//GEN-LAST:event_txtFeedTypePriceKeyTyped

    private void txtFeedTypePriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFeedTypePriceKeyReleased
        FlockGUIUtil.keyPressedHandler(txtFeedTypePrice, evt, DataFormat.DEC_2);
    }//GEN-LAST:event_txtFeedTypePriceKeyReleased

    private void loadFeedType(Long cellinkId) {
        ((DefaultTableModel) tblDataTable.getModel()).getDataVector().removeAllElements();
        ((ListSelectionModel) tblDataTable.getSelectionModel()).setSelectionInterval(-1, -1);
        tblDataTable.revalidate();
        List<FeedType> feedTypeList = flockService.getAllFeedType(cellinkId);
        for (FeedType f : feedTypeList) {
            ((DefaultTableModel) tblDataTable.getModel()).insertRow(0, new Object[]{
                        f.getId(),
                        f.getFeedType(),
                        f.getPrice()});
        }
    }

    private FeedType getFromFields() {
        String sprice = txtFeedTypePrice.getText();
        Float price = Float.parseFloat(sprice);
        String type = txtFeedType.getText();

        currFeedType = new FeedType();
        currFeedType.setCellinkId(cellinkId);
        currFeedType.setFeedType(type);
        currFeedType.setPrice(price);
        return currFeedType;
    }

    private boolean validateForm() {
        if (txtFeedType.getText().equals("")
                || txtFeedTypePrice.getText().equals("")) {
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtFeedType.setText("");
        txtFeedTypePrice.setText("");
    }

    private void setForm(FeedType fuel) {
        txtFeedType.setText("" + currFeedType.getFeedType());
        txtFeedTypePrice.setText("" + currFeedType.getPrice());
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
            java.util.logging.Logger.getLogger(AddFeedTypeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddFeedTypeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddFeedTypeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddFeedTypeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                AddFeedTypeDialog dialog = new AddFeedTypeDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDataTable;
    private javax.swing.JTextField txtFeedType;
    private javax.swing.JTextField txtFeedTypePrice;
    // End of variables declaration//GEN-END:variables
}
