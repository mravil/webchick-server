/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package help.examples;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.smartcardio.Card;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.agrologic.app.util.Windows;

/**
 *
 * @author Administrator
 */
public class ScroolPaneFrame extends javax.swing.JFrame implements ActionListener {

    ButtonPanel btnPanel;
    MainScreenPanel mainScreenContainer;

    /**
     * Creates new form ScroolPaneFrame
     */
    public ScroolPaneFrame() {
        initComponents();
        Windows.setWindowsLAF(this);
        setSize(Windows.screenResolution());

        Dimension dim = Windows.screenResolution();
//        screensPanel.setSize(1024 - 35, 768 - 145);
        screensPanel.setSize(dim.width - 35, dim.height - 145);
        screensPanel.setLocation(10, 40);
        // status bar
        statusSeparator.setBounds(0, dim.height - 85, dim.width, 30);

        // main screen container
        mainScreenContainer = new MainScreenPanel();
        screensPanel.add(mainScreenContainer,"Main Screen");
        ((CardLayout)screensPanel.getLayout()).show(screensPanel, "Main Screen");

        // button panel
        btnPanel = new ButtonPanel(this, new FlowLayout());
        add(btnPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        statusSeparator = new javax.swing.JSeparator();
        screensPanel = new javax.swing.JPanel();
        secondScreen = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(statusSeparator);
        statusSeparator.setBounds(0, 300, 400, 25);

        screensPanel.setLayout(new java.awt.CardLayout());
        screensPanel.add(secondScreen, "card3");

        getContentPane().add(screensPanel);
        screensPanel.setBounds(0, 40, 390, 250);

        jScrollPane2.setBorder(null);
        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(530, 110, 150, 160);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help/examples/close.png"))); // NOI18N
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(130, 313, 100, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

    }//GEN-LAST:event_jButton1MouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        /*
//         * Set the Nimbus look and feel
//         */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /*
//         * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see
//         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ScroolPaneFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ScroolPaneFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ScroolPaneFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ScroolPaneFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /*
//         * Create and display the form
//         */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new ScroolPaneFrame().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel screensPanel;
    private javax.swing.JPanel secondScreen;
    private javax.swing.JSeparator statusSeparator;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        ((CardLayout) screensPanel.getLayout()).show(screensPanel, btn.getText());
        // updateButtonColor(btn);
    }
}
