package com.agrologic.app.gui.rxtx.flock;

import com.agrologic.app.dao.DaoType;
import com.agrologic.app.gui.rxtx.FileChooser;
import com.agrologic.app.i18n.LocaleManager;
import com.agrologic.app.model.Flock;
import com.agrologic.app.service.excel.ExcelService;
import com.agrologic.app.service.excel.ExportToExcelException;
import com.agrologic.app.util.Windows;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class FlockGraphs extends JFrame implements ChangeListener, ChartProgressListener {

    private JSlider slider;
    private FlockEntry currFlockEntry;
    private List<Flock> flocks;
    private FlockEntry[] flockEntries;
    private FlockManagerService flockService;
    private ExcelService excelService;
    private ResourceBundle bundle; // NOI18N

    public FlockGraphs() {
        initComponents();
        bundle = ResourceBundle.getBundle(LocaleManager.UI_RESOURCE); // NOI18N
        Windows.centerOnScreen(this);
        Windows.setWindowsLAF(this);
        setTitle(bundle.getString("flock.graphs"));
        flockService = new FlockManagerService();
        excelService = new ExcelService(DaoType.DERBY);
        loadFlocks(null);

        //radio bitton history by 24 hours
        rdo24HourHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnViewActionPerformed();
                Long flockId = currFlockEntry.getId();
                List<HistoryEntry> historyList = flockService.createHistoryEntry24List(flockId, (Integer) spnGrowday.getModel().getValue(), 1L);
                if (historyList.size() == 0){
                    JOptionPane.showMessageDialog(FlockGraphs.this, "There is no history for this grow day");
                    Integer growDayMin = flockService.getFirstUpdatedGrowDay24(flockId);
                    if (growDayMin != 1 && growDayMin != null) {
                        spnGrowday.setValue(Integer.valueOf(growDayMin));
                        historyList = flockService.createHistoryEntry24List(flockId, growDayMin, 1L);
                    }
                }
                lstHistoryList.setListData(historyList.toArray());
            }
        });

        // radion button history by grow day
        rdoGrowDayHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnViewActionPerformed();
                Long flockId = currFlockEntry.getId();
                List<HistoryEntry> historyList = flockService.createHistoryEntryList(flockId);
                lstHistoryList.setListData(historyList.toArray());
            }
        });

        // Grow day chooser
        spnGrowday.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnViewActionPerformed();
                rdo24HourHistory.setSelected(true);
                rdoGrowDayHistory.setSelected(false);
                Long flockId = currFlockEntry.getId();
                List<HistoryEntry> historyList = flockService.createHistoryEntry24List(flockId, (Integer) spnGrowday.getModel().getValue(), 1L);
                if (historyList.size() == 0){
                    JOptionPane.showMessageDialog(FlockGraphs.this, "There is no history for this grow day");
                    Integer growDayMin = flockService.getFirstUpdatedGrowDay24(flockId);
                    if (growDayMin != 1 && growDayMin != null) {
                        spnGrowday.setValue(Integer.valueOf(growDayMin));
                        historyList = flockService.createHistoryEntry24List(flockId, growDayMin, 1L);
                    }
                }
                lstHistoryList.setListData(historyList.toArray());
            }
        });

        //button EXCEL
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {// jButton3 = excel
                FileChooser fileChooser = new FileChooser();
                // Now open chooser
                // Show dialog; this method does not return until dialog is closed
                File filePath = new File(".");
                int result = fileChooser.showSaveDialog(FlockGraphs.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile();
                    if (filePath.exists()) {
//                        int response = JOptionPane.showConfirmDialog(null, "Override existing file ?", "",
                        int response = JOptionPane.showConfirmDialog(null, bundle.getString("flock.graphs.override.existing.file"), "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.CANCEL_OPTION) {
                            return;
                        }
                    }
                    try {
                        if (rdoGrowDayHistory.isSelected() && !(rdo24HourHistory.isSelected())) {
                            excelService.writeHistoryPerDayToExcelFile(filePath.getAbsolutePath(), currFlockEntry.getId(), 1L);
                        } else {
                            if (rdo24HourHistory.isSelected() && !(rdoGrowDayHistory.isSelected())) {
                                Integer growDay = null;
                                growDay = (Integer) spnGrowday.getModel().getValue();
                                if (growDay != null) {
                                    excelService.writeHistoryPerHourToExcelFile(filePath.getAbsolutePath(), currFlockEntry.getId(), growDay, 1L);
                                }
                            }
                        }
                    } catch (ExportToExcelException e1) {
                        JOptionPane.showMessageDialog(FlockGraphs.this, e1.getMessage());
                    }
                }
            }
        });
    }

    /**
     * Load flocks
     */
    private void loadFlocks(Long flockId) {
        // clear items to avoid duplicate items
        cmbFlockHouses.removeAllItems();

        // get flocks
        flocks = flockService.getFlocks();
        flockEntries = new FlockEntry[flocks.size()];
        for (int i = 0; i < flockEntries.length; i++) {
            Flock f = flocks.get(i);
            flockEntries[i] = new FlockEntry(f.getFlockId(), f.getFlockName());
            if (flockId != null && flockId.equals(f.getFlockId())) {
                currFlockEntry = flockEntries[i];
            }
            cmbFlockHouses.addItem(flockEntries[i]);
        }
        cmbFlockHouses.setSelectedItem(currFlockEntry);

        if (flockId != null) {
            Integer growDays = flockService.getLastUpdatedGrowDay(flockId);
        }

        if(rdo24HourHistory.isSelected() && !(rdoGrowDayHistory.isSelected())){
            List<HistoryEntry> historyList = flockService.createHistoryEntry24List(flockId, (Integer) spnGrowday.getModel().getValue(), 1L);
            lstHistoryList.setListData(historyList.toArray());
        } else {
            if (rdoGrowDayHistory.isSelected() && !(rdo24HourHistory.isSelected())){
                List<HistoryEntry> historyList = flockService.createHistoryEntryList(flockId);
                lstHistoryList.setListData(historyList.toArray());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        bundle = ResourceBundle.getBundle(LocaleManager.UI_RESOURCE); // NOI18N
        historyGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        tabPaneHistory = new javax.swing.JTabbedPane();
        pnlCurrent = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstHistoryList = new javax.swing.JList();
        pnlStandard = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        cmbFlockHouses = new javax.swing.JComboBox();
        btnView = new javax.swing.JButton();
//        btnClear = new javax.swing.JButton();
        spnGrowday = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        rdoGrowDayHistory = new javax.swing.JRadioButton();
        rdo24HourHistory = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblRange = new javax.swing.JLabel();
        txtFrom = new javax.swing.JTextField();
        txtTo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        chbPuton = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        pnlGraph = new javax.swing.JPanel();
        pnlSlider = new javax.swing.JPanel();
        textRect1 = new com.agrologic.app.gui.rxtx.flock.TextRect();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lstHistoryList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = {bundle.getString("flock.graphs.feed"), bundle.getString("flock.graphs.water")};

            public int getSize() {
                return strings.length;
            }

            public Object getElementAt(int i) {
                return strings[i];
            }
        });
        lstHistoryList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstHistoryListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstHistoryList);

        javax.swing.GroupLayout pnlCurrentLayout = new javax.swing.GroupLayout(pnlCurrent);
        pnlCurrent.setLayout(pnlCurrentLayout);
        pnlCurrentLayout.setHorizontalGroup(
                pnlCurrentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlCurrentLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pnlCurrentLayout.setVerticalGroup(
                pnlCurrentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlCurrentLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                                .addContainerGap())
        );

//        tabPaneHistory.addTab("Current", pnlCurrent); // 08/01/2018
        tabPaneHistory.addTab(bundle.getString("flock.graphs.current"), pnlCurrent); // 08/01/2018
//        jButton1.setText("Add Standard");
//
//        javax.swing.GroupLayout pnlStandardLayout = new javax.swing.GroupLayout(pnlStandard);
//        pnlStandard.setLayout(pnlStandardLayout);
//        pnlStandardLayout.setHorizontalGroup(
//                pnlStandardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(pnlStandardLayout.createSequentialGroup()
//                                .addGap(35, 35, 35)
//                                .addComponent(jButton1)
//                                .addContainerGap(52, Short.MAX_VALUE))
//        );
//        pnlStandardLayout.setVerticalGroup(
//                pnlStandardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(pnlStandardLayout.createSequentialGroup()
//                                .addContainerGap()
//                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addContainerGap(290, Short.MAX_VALUE))
//        );
//
//        tabPaneHistory.addTab("Standard", pnlStandard);

        cmbFlockHouses.setFocusable(false);
        cmbFlockHouses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFlockHousesActionPerformed(evt);
            }
        });

//        btnView.setText("View");
//        btnView.setText("Clear"); // 08/01/2018
        btnView.setText(bundle.getString("flock.graphs.clear")); // 08/01/2018
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnViewActionPerformed(evt);
                btnViewActionPerformed();
            }
        });

//        btnClear.setText("Clear");
//        btnClear.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnClearActionPerformed(evt);
//            }
//        });

        spnGrowday.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
//        spnGrowday.addChangeListener(new javax.swing.event.ChangeListener() {
//            public void stateChanged(javax.swing.event.ChangeEvent evt) {
//                spnGrowdayStateChanged(evt);
//            }
//        });

//        jLabel1.setText("Grow day "); // 08/01/2018
        jLabel1.setText(bundle.getString("flock.graphs.grow.day")); // 08/01/2018

        historyGroup.add(rdoGrowDayHistory);
        rdoGrowDayHistory.setSelected(true);
//        rdoGrowDayHistory.setText("Show by grow day"); // 08/01/2018
        rdoGrowDayHistory.setText(bundle.getString("flock.graphs.show.by.grow.day")); // 08/01/2018
//        rdoGrowDayHistory.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                rdoGrowDayHistoryActionPerformed(evt);
//            }
//        });

        historyGroup.add(rdo24HourHistory);
//        rdo24HourHistory.setText("Show by 24 hour"); // 08/01/2018
        rdo24HourHistory.setText(bundle.getString("flock.graphs.show.by.24.hours")); // 08/01/2018
//        rdo24HourHistory.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                rdo24HourHistoryActionPerformed(evt);
//            }
//        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cmbFlockHouses, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(spnGrowday)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)///
                                                .addComponent(btnView))
//                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                                .addComponent(btnClear))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(rdoGrowDayHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(rdo24HourHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(tabPaneHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cmbFlockHouses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnView)
                                        .addComponent(spnGrowday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
//                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)///
//                                        .addComponent(btnClear))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoGrowDayHistory)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdo24HourHistory)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tabPaneHistory)
                                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

//        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
//
//        lblRange.setText("<html>\nRange <p>\n(days)\n</html>");
//
//        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel2.setText("From");
//        jLabel2.setMaximumSize(new java.awt.Dimension(12, 14));
//        jLabel2.setMinimumSize(new java.awt.Dimension(12, 14));
//        jLabel2.setPreferredSize(new java.awt.Dimension(12, 14));
//
//        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel3.setText("To");
//        jLabel3.setToolTipText("");
//
//        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
//        jPanel6.setLayout(jPanel6Layout);
//        jPanel6Layout.setHorizontalGroup(
//                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(jPanel6Layout.createSequentialGroup()
//                                .addContainerGap()
//                                .addComponent(lblRange, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                                        .addComponent(txtFrom)
//                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                                        .addComponent(txtTo)
//                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
//                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//
//        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3});
//
//        jPanel6Layout.setVerticalGroup(
//                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(jPanel6Layout.createSequentialGroup()
//                                .addContainerGap()
//                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
//                                        .addComponent(lblRange, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addGroup(jPanel6Layout.createSequentialGroup()
//                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                                        .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                                        .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
//                                .addContainerGap())
//        );
//
//        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3});
//
//        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
//
//        chbPuton.setText("Put on");
//        chbPuton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
////                chbPutonActionPerformed(evt);
//            }
//        });
//
//        jCheckBox2.setText("Legend");
//
//        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
//        jPanel7.setLayout(jPanel7Layout);
//        jPanel7Layout.setHorizontalGroup(
//                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(jPanel7Layout.createSequentialGroup()
//                                .addContainerGap()
//                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                                        .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                        .addComponent(chbPuton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        jPanel7Layout.setVerticalGroup(
//                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(jPanel7Layout.createSequentialGroup()
//                                .addContainerGap()
//                                .addComponent(chbPuton)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(jCheckBox2)
//                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

//        jButton2.setText("Print");

//        jButton3.setText("Excel"); //08/01/2018
        jButton3.setText(bundle.getString("flock.graphs.excel")); //08/01/2018

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
//                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3)
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(486, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlGraph.setBackground(new java.awt.Color(204, 204, 255));
        pnlGraph.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout pnlGraphLayout = new javax.swing.GroupLayout(pnlGraph);
        pnlGraph.setLayout(pnlGraphLayout);
        pnlGraphLayout.setHorizontalGroup(
                pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlGraphLayout.setVerticalGroup(
                pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlSlider.setLayout(new java.awt.BorderLayout());

//        textRect1.setBorder(javax.swing.BorderFactory.createTitledBorder("Values on Graph")); // 08/01/2018
        textRect1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("flock.graphs.values.on.graphs"))); // 08/01/2018

        javax.swing.GroupLayout textRect1Layout = new javax.swing.GroupLayout(textRect1);
        textRect1.setLayout(textRect1Layout);
        textRect1Layout.setHorizontalGroup(
                textRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 104, Short.MAX_VALUE)
        );
        textRect1Layout.setVerticalGroup(
                textRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(pnlSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                                                        .addComponent(pnlGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(textRect1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(pnlGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pnlSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(textRect1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>

//    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {
    private void btnViewActionPerformed() {

        final ChartPanel cp = GraphFactory.createXYChartPanel();
        Rectangle rect = pnlGraph.getBounds();
        rect.setLocation(0, 0);
        cp.setBounds(rect);
        pnlGraph.addHierarchyBoundsListener(new HierarchyBoundsListener() {

            @Override
            public void ancestorMoved(HierarchyEvent e) {
                // moving window will
                Rectangle rect = pnlGraph.getBounds();
                rect.setLocation(0, 0);
                cp.setBounds(pnlGraph.getBounds());
                Rectangle rect2 = slider.getBounds();
                slider.setBounds(rect.x, rect.y, rect.width, rect2.height);
                // call this event
            }

            @Override
            public void ancestorResized(HierarchyEvent e) {
                // change size will
                Rectangle rect = pnlGraph.getBounds();
                rect.setLocation(0, 0);
                cp.setBounds(rect);
                Rectangle rect2 = slider.getBounds();
                slider.setBounds(rect.x, rect.y, rect.width, rect2.height);
                // call this event
            }
        });

        pnlGraph.removeAll();
        pnlGraph.add(cp, BorderLayout.CENTER);
        slider = new JSlider(0, 100, 50);
        slider.addChangeListener(this);
        cp.getChart().addProgressListener(this);
        pnlSlider.add(this.slider, BorderLayout.CENTER);
        pnlSlider.validate();
        pnlGraph.validate();
        repaint();
        textRect1.clearSet();
    }

    private void spnGrowdayStateChanged(javax.swing.event.ChangeEvent evt) {
//        if (evt.getSource() instanceof JSpinner) {
//            JSpinner spiner = (JSpinner) evt.getSource();
//            currFlockGrowDay = (Integer) spiner.getModel().getValue();
//        }
//        if (rdo24HourHistory.isSelected()) {
//            ChartPanel cp = (ChartPanel) pnlGraph.getComponent(0);
//            XYPlot plot = (XYPlot) cp.getChart().getPlot();
//
//            GraphFactory.setNumberAxis("Hour", plot);
//            GraphFactory.setRenderer(plot);
//
//            XYSeriesCollection xyseries = (XYSeriesCollection) plot.getDataset();
////            boolean selected = chbPuton.getModel().isSelected(); // chPuton "Check Box "Put On"
////            if (!selected) {
////                xyseries.removeAllSeries();
////            }
//            slider.setMinimum(1);
//            slider.setMaximum(24);
//            slider.setValue(24 / 2);
//
//            /////////////////////////////////////////////////////
//
//            ///////////////////////////////////////////////////////
//
//            int i = lstHistoryList.getSelectedIndex();
//            ListModel model = lstHistoryList.getModel();
//            if (model.getSize() >= i) {
//                HistoryEntry entry = (HistoryEntry) lstHistoryList.getModel().getElementAt(i);
//                Integer growDay = (Integer) spnGrowday.getModel().getValue();
//                entry.getValues24ByGrowDay(growDay);
//
//                GraphFactory.setRangeAxis(entry.getTitle(), plot);
//                XYSeries xySeries = GraphFactory.createXYDataset(entry.getTitle(), entry.getValues24ByGrowDay(growDay));
//                xyseries.addSeries(xySeries);
//            }
//        }
    }

    private void lstHistoryListMouseClicked(java.awt.event.MouseEvent evt) {
        // if no graphs there does not do nothing
        if (pnlGraph.getComponents().length == 0) {
            btnView.doClick();
//            btnClear.doClick();
        } else if (pnlGraph.getComponents().length > 1) {
            System.out.println();
        }


//        if (evt.getClickCount() == ONE_CLICK && rdo24HourHistory.isSelected()) {
//        if (evt.getClickCount() == ONE_CLICK) {
        if (rdo24HourHistory.isSelected() && !(rdoGrowDayHistory.isSelected())) {
            ChartPanel cp = (ChartPanel) pnlGraph.getComponent(0);
            XYPlot plot = (XYPlot) cp.getChart().getPlot();

            GraphFactory.setNumberAxis("Hour", plot); // 08/01/2017
//            GraphFactory.setNumberAxis(bundle.getString("flock.graphs.hour"), plot); // 08/01/2017
            GraphFactory.setRenderer(plot);

            XYSeriesCollection seriesCollection = (XYSeriesCollection) plot.getDataset();
//            boolean selected = chbPuton.getModel().isSelected();
//            if (!selected) {
//                xyseries.removeAllSeries();
//            }
            slider.setMinimum(1);
            slider.setMaximum(24);
            slider.setValue(24 / 2);
            int i = lstHistoryList.getSelectedIndex();
            HistoryEntry entry = (HistoryEntry) lstHistoryList.getModel().getElementAt(i); // IndexOfBoundException -1
            Integer growDay = (Integer) spnGrowday.getModel().getValue();
//            entry.getValues24ByGrowDay(growDay);
            //entry.setValues24(generateHistory24Values());
            GraphFactory.setRangeAxis(entry.getTitle(), plot);
            XYSeries xySeries = GraphFactory.createXYDataset(entry.getTitle(), entry.getValues24ByGrowDay(growDay));
            seriesCollection.addSeries(xySeries);
//            xyseries.addSeries(xySeries);
//        } else if (evt.getClickCount() == DOUBLE_CLICK && rdoGrowDayHistory.isSelected()) {
        } else if (rdoGrowDayHistory.isSelected() && !(rdo24HourHistory.isSelected())) {
            ChartPanel cp = (ChartPanel) pnlGraph.getComponent(0);
            XYPlot plot = (XYPlot) cp.getChart().getPlot();
            int index = lstHistoryList.getSelectedIndex();

            GraphFactory.setNumberAxis("Grow Day", plot); // 08/01/2018
//            GraphFactory.setNumberAxis(bundle.getString("flock.graphs.grow.day"), plot); // 08/01/2018
            GraphFactory.setRenderer(plot);

            XYSeriesCollection seriesCollection = (XYSeriesCollection) plot.getDataset();
//            boolean selected = chbPuton.getModel().isSelected();
//            if (!selected) {
//                seriesCollection.removeAllSeries();
//            }

            HistoryEntry historyEntry = (HistoryEntry) lstHistoryList.getModel().getElementAt(index);
            int maxSize = historyEntry.getValues().size();
            slider.setMinimum(1);
            slider.setMaximum(maxSize);
            slider.setValue(maxSize / 2);

            GraphFactory.setRangeAxis(historyEntry.getTitle(), plot);
            XYSeries xySeries = GraphFactory.createXYDataset(historyEntry.getTitle(), historyEntry.getValues());
            seriesCollection.addSeries(xySeries);
        }
    }

    private void cmbFlockHousesActionPerformed(java.awt.event.ActionEvent evt) {
        JComboBox comboBox = (JComboBox) evt.getSource();
        FlockEntry entry = (FlockEntry) comboBox.getSelectedItem();
        if (entry != null && !entry.equals(currFlockEntry)) {
            currFlockEntry = entry;
            loadFlocks(currFlockEntry.getId());
        }
    }

    private void rdoGrowDayHistoryActionPerformed(java.awt.event.ActionEvent evt) {
        FlockEntry entry = (FlockEntry) cmbFlockHouses.getSelectedItem();
        currFlockEntry = entry;
        loadFlocks(currFlockEntry.getId());
    }

    private void rdo24HourHistoryActionPerformed(java.awt.event.ActionEvent evt){
//        List<HistoryEntry> historyList = flockService.createHistoryEntry24List(currFlockEntry.getId());
        List<HistoryEntry> historyList = flockService.createHistoryEntry24List(currFlockEntry.getId(), (Integer) spnGrowday.getModel().getValue(), null);
        lstHistoryList.setListData(historyList.toArray());
    }

//    private void chbPutonActionPerformed(java.awt.event.ActionEvent evt) {
        //        boolean selected = chbPuton.getModel().isSelected();
        //        if (selected) {
        //            System.out.println("selected");
        //        }
//    }

    public void showDialog(Long flockId) {
        loadFlocks(flockId);
        setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        int i = slider.getValue();
        ChartPanel cp = (ChartPanel) pnlGraph.getComponent(0);
        XYPlot plot = (XYPlot) cp.getChart().getPlot();
        double d = slider.getValue();
        plot.setDomainCrosshairValue(d);
    }

    @Override
    public void chartProgress(ChartProgressEvent cpe) {
        ChartPanel cp = (ChartPanel) pnlGraph.getComponent(0);
        XYPlot plot = (XYPlot) cp.getChart().getPlot();
        double d1 = plot.getDomainCrosshairValue();
        int l1 = (int) d1;
        XYDataset dataSet = plot.getDataset();
        if (dataSet.getSeriesCount() > 0) {
            XYSeriesCollection xyseries = (XYSeriesCollection) plot.getDataset();
            XYItemRenderer xyir = plot.getRenderer();
            int size = dataSet.getSeriesCount();
            for (int i = 0; i < size; i++) {
                XYSeries series = xyseries.getSeries(i);
                Color color = (Color) xyir.getSeriesPaint(i);
                if (series.getItemCount() > l1) {
                    Number num = series.getY(l1 - 1);
                    if (color != null) {
                        textRect1.set(color, num);
                    }
                }
            }
        }
    }

    /**
     * Help class to show flock names and id
     */
    class FlockEntry {

        private Long id;
        private String name;

        public FlockEntry(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final FlockEntry other = (FlockEntry) obj;
            if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
            return hash;
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
            java.util.logging.Logger.getLogger(FlockGraphs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FlockGraphs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FlockGraphs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FlockGraphs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FlockGraphs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
//    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnView;
    private javax.swing.JCheckBox chbPuton;
    private javax.swing.JComboBox cmbFlockHouses;
    private javax.swing.ButtonGroup historyGroup;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRange;
    private javax.swing.JList lstHistoryList;
    private javax.swing.JPanel pnlCurrent;
    private javax.swing.JPanel pnlGraph;
    private javax.swing.JPanel pnlSlider;
    private javax.swing.JPanel pnlStandard;
    private javax.swing.JRadioButton rdo24HourHistory;
    private javax.swing.JRadioButton rdoGrowDayHistory;
    private javax.swing.JSpinner spnGrowday;
    private javax.swing.JTabbedPane tabPaneHistory;
    private com.agrologic.app.gui.rxtx.flock.TextRect textRect1;
    private javax.swing.JTextField txtFrom;
    private javax.swing.JTextField txtTo;
    // End of variables declaration                   
}
