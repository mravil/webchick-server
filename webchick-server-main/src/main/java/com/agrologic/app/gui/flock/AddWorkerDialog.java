/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.app.gui.flock;

import com.agrologic.app.config.Configuration;
import com.agrologic.app.model.DataFormat;
import com.agrologic.app.model.Worker;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class AddWorkerDialog extends JDialog {

    private Configuration conf;
    private Worker currWorker;
    private Long cellinkId;
    private Long flockId;
    private FlockManagerService flockService;

    /**
     * Creates new form AddWorkerDialog
     */
    public AddWorkerDialog(Frame parent, boolean modal) {
        this(Long.valueOf(1), parent, modal);
    }

    public AddWorkerDialog(Long flockId, Frame owner, boolean modal) {
        super(owner, modal);
        initComponents();
        conf = new Configuration();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
        flockService = new FlockManagerService();
        this.flockId = flockId;
        cellinkId = Long.parseLong(conf.getCellinkId());
        loadWorker(cellinkId);
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
                    currWorker = flockService.getWorkerById(id);
                    setForm(currWorker);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtHourCost = new javax.swing.JTextField();
        btnRemove = new javax.swing.JButton();
        txtPhone = new javax.swing.JTextField();
        txtDefine = new javax.swing.JTextField();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Worker");
        setMinimumSize(new java.awt.Dimension(650, 400));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(null);

        tblDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Define", "Phone", "Hour Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
        jScrollPane1.setBounds(10, 11, 624, 170);

        btnAdd.setText("Add ");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdd);
        btnAdd.setBounds(435, 195, 105, 23);

        jLabel2.setText("Name");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 198, 140, 20);

        jLabel3.setText("Define");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 226, 140, 20);

        jLabel4.setText("Phone");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 252, 140, 20);

        jLabel5.setText("Hour Cost");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 278, 140, 20);
        getContentPane().add(txtName);
        txtName.setBounds(170, 198, 220, 20);

        txtHourCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHourCostKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHourCostKeyTyped(evt);
            }
        });
        getContentPane().add(txtHourCost);
        txtHourCost.setBounds(170, 278, 220, 20);

        btnRemove.setText("Delete");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        getContentPane().add(btnRemove);
        btnRemove.setBounds(435, 220, 105, 23);

        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhoneKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneKeyTyped(evt);
            }
        });
        getContentPane().add(txtPhone);
        txtPhone.setBounds(170, 250, 220, 20);
        getContentPane().add(txtDefine);
        txtDefine.setBounds(170, 225, 220, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!validateForm()) {
            return;
        }
        Worker worker = getFromFields();
        flockService.addWorker(worker);
        loadWorker(cellinkId);
        clearForm();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if(currWorker == null) {
            return;
        }
        flockService.removeWorker(currWorker.getId());
        loadWorker(cellinkId);
        clearForm();
        btnAdd.setEnabled(true);
        btnRemove.setEnabled(false);
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        loadWorker(cellinkId);
    }//GEN-LAST:event_formWindowActivated

    private void txtPhoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyReleased
    }//GEN-LAST:event_txtPhoneKeyReleased

    private void txtPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneKeyTyped

    private void txtHourCostKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHourCostKeyReleased

    }//GEN-LAST:event_txtHourCostKeyReleased

    private void txtHourCostKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHourCostKeyTyped

    }//GEN-LAST:event_txtHourCostKeyTyped

    private void loadWorker(Long cellinkId) {
        ((DefaultTableModel) tblDataTable.getModel()).getDataVector().removeAllElements();
        ((ListSelectionModel) tblDataTable.getSelectionModel()).setSelectionInterval(-1, -1);
        tblDataTable.revalidate();
        List<Worker> fuelList = flockService.getAllWorker(cellinkId);
        for (Worker w : fuelList) {
            ((DefaultTableModel) tblDataTable.getModel()).insertRow(0, new Object[]{
                        w.getId(),
                        w.getName(),
                        w.getDefine(),
                        w.getPhone(),
                        w.getHourCost()});
        }

    }

    private Worker getFromFields() {
        String name = txtName.getText();
        String hourCost = txtHourCost.getText();
        String define = txtDefine.getText();
        String phone = txtPhone.getText();

        currWorker = new Worker();
        currWorker.setCellinkId(cellinkId);
        currWorker.setName(name);
        currWorker.setDefine(define);
        currWorker.setHourCost(Float.parseFloat(hourCost));
        currWorker.setPhone(phone);
        return currWorker;
    }

    private boolean validateForm() {
        if (txtName.getText().equals("")
                || txtHourCost.getText().equals("")
                || txtDefine.getText().equals("")
                || txtPhone.getText().equals("")) {
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtName.setText("");
        txtHourCost.setText("");
        txtDefine.setText("");
        txtPhone.setText("");
        txtHourCost.setText("");
    }

    private void setForm(Worker worker) {
        txtName.setText("" + worker.getName());
        txtHourCost.setText("" + worker.getDefine());
        txtDefine.setText("" + worker.getDefine());
        txtPhone.setText("" + worker.getPhone());
        txtHourCost.setText("" + worker.getHourCost());
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
            java.util.logging.Logger.getLogger(AddWorkerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddWorkerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddWorkerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddWorkerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                AddWorkerDialog dialog = new AddWorkerDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDataTable;
    private javax.swing.JTextField txtDefine;
    private javax.swing.JTextField txtHourCost;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
