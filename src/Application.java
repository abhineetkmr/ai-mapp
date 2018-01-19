/*
 * Copyright 2018 Abhineet Kumar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import ai.mapp.Simulator;
import ai.mapp.Warehouses;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Ankit and Abhineet
 */
public class Application extends javax.swing.JFrame implements Simulator.SimulatorLaunchListener{
    
    final private static ai.mapp.Warehouse[] WAREHOUSES = {
        Warehouses.WAREHOUSE1,
        Warehouses.WAREHOUSE2,
        Warehouses.WAREHOUSE3,
        Warehouses.WAREHOUSE4,
        Warehouses.WAREHOUSE5,
        Warehouses.WAREHOUSE6,
        Warehouses.WAREHOUSE7,
        Warehouses.WAREHOUSE8,
        Warehouses.WAREHOUSE9,
        Warehouses.WAREHOUSE10,
        Warehouses.WAREHOUSE11
    };
    
    final private static LoadingScreen LOADING_SCREEN = new LoadingScreen();
    
    private int selectedIndex = 0; // index of selected item in Warehouse Combo Box
    
    /* trick to avert problems caused by Button Action and Mouse events while
    trying to avoid another simulator launches before already-triggered launch
    finishes. generateButtonActionEnabled is used in conjunction with
    GenerateButtonMousePressed(evt) and Simulator.SimulatorLaunchListener */
    private boolean generateButtonActionEnabled = true;

    /**
     * Creates new form Application
     */
    public Application() {
        initComponents();
        ai.mapp.SingletonResources.doNothing();
        String list[] = new String[WAREHOUSES.length + 1];
        for (int i = 0; i < WAREHOUSES.length; i++)
            list[i] = WAREHOUSES[i].getName();
        list[WAREHOUSES.length] =  "Random";
        WarehousesComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(list));
        hideRandomMapSettings();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent we) {
                LOADING_SCREEN.setVisible(false);
            }
        });
    }

    @Override
    public void simulatorLaunched() {
        generateButtonActionEnabled = true;
    }
    
    private void hideRandomMapSettings() {
        TargetCountLabel.setVisible(false);
        TargetCountSpinner.setVisible(false);
        ProbabilityLabel.setVisible(false);
        ProbabilitySpinner.setVisible(false);
        jSeparator1.setVisible(false);
        pack();
    }
    
    private void showRandomMapSettings() {
        TargetCountLabel.setVisible(true);
        TargetCountSpinner.setVisible(true);
        ProbabilityLabel.setVisible(true);
        ProbabilitySpinner.setVisible(true);
        jSeparator1.setVisible(true);
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        WarehousesComboBox = new javax.swing.JComboBox<>();
        SimulateButton = new javax.swing.JButton();
        RowCountSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        ColumnCountSpinner = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        ProbabilitySpinner = new javax.swing.JSpinner();
        ProbabilityLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        TargetCountLabel = new javax.swing.JLabel();
        TargetCountSpinner = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Multi-Agent Path Planning (MAPP) Simulation");
        setBounds(new java.awt.Rectangle(10, 10, 0, 0));
        setResizable(false);

        jLabel1.setText("Warehouse:");

        WarehousesComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WarehousesComboBoxActionPerformed(evt);
            }
        });

        SimulateButton.setMnemonic('s');
        SimulateButton.setText("Simulate");
        SimulateButton.setToolTipText("Create Simulator (Alt-G)");
        SimulateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SimulateButtonMousePressed(evt);
            }
        });
        SimulateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SimulateButtonActionPerformed(evt);
            }
        });

        RowCountSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 3, 64, 1));
        RowCountSpinner.setValue(WAREHOUSES[0].getRowCount());
        RowCountSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RowCountSpinnerStateChanged(evt);
            }
        });

        jLabel2.setText("rows  X");

        ColumnCountSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 3, 64, 1));
        ColumnCountSpinner.setValue(WAREHOUSES[0].getColumnCount());
        ColumnCountSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ColumnCountSpinnerStateChanged(evt);
            }
        });

        jLabel3.setText("columns");

        ProbabilitySpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.25f), Float.valueOf(0.0f), Float.valueOf(0.5f), Float.valueOf(0.01f)));
        ProbabilitySpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ProbabilitySpinnerStateChanged(evt);
            }
        });

        ProbabilityLabel.setText("Obstacle Probability:");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        TargetCountLabel.setText("Number of targets:");

        TargetCountSpinner.setModel(new javax.swing.SpinnerNumberModel(10, 1, ai.mapp.RandomMapGenerator.MAX_TARGET_COUNT, 1));

        jLabel5.setText("Grid Size:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WarehousesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RowCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ColumnCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TargetCountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TargetCountSpinner)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ProbabilityLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ProbabilitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SimulateButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(WarehousesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(RowCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(ColumnCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(ProbabilitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ProbabilityLabel)
                        .addComponent(jSeparator2)
                        .addComponent(TargetCountLabel)
                        .addComponent(TargetCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SimulateButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void WarehousesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WarehousesComboBoxActionPerformed
        selectedIndex = WarehousesComboBox.getSelectedIndex();
        if (selectedIndex < WAREHOUSES.length) { //Preset
            ColumnCountSpinner.setValue(WAREHOUSES[selectedIndex].getColumnCount());
            RowCountSpinner.setValue(WAREHOUSES[selectedIndex].getRowCount());
            hideRandomMapSettings();
        } else { //Random
            showRandomMapSettings();
        }
    }//GEN-LAST:event_WarehousesComboBoxActionPerformed

    private void RowCountSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RowCountSpinnerStateChanged
        if(selectedIndex < WAREHOUSES.length)
            WarehousesComboBox.setSelectedIndex(WAREHOUSES.length);
        checkTargetCountSpinner();
    }//GEN-LAST:event_RowCountSpinnerStateChanged

    private void ColumnCountSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ColumnCountSpinnerStateChanged
        if(selectedIndex < WAREHOUSES.length)
            WarehousesComboBox.setSelectedIndex(WAREHOUSES.length);
        checkTargetCountSpinner();
    }//GEN-LAST:event_ColumnCountSpinnerStateChanged

    private void SimulateButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SimulateButtonMousePressed
        GenerateButtonAction();
    }//GEN-LAST:event_SimulateButtonMousePressed

    // to allow enter key to work on the button
    private void SimulateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SimulateButtonActionPerformed
        GenerateButtonAction();
    }//GEN-LAST:event_SimulateButtonActionPerformed

    private void ProbabilitySpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ProbabilitySpinnerStateChanged
        checkTargetCountSpinner();
    }//GEN-LAST:event_ProbabilitySpinnerStateChanged

    private void checkTargetCountSpinner() {
        int val = (int)TargetCountSpinner.getValue();
        int max = ai.mapp.RandomMapGenerator.maxTargetCount(
                (int)RowCountSpinner.getValue(), 
                (int)ColumnCountSpinner.getValue(), 
                (float)ProbabilitySpinner.getValue()
        );
        
        val = (val > max) ? max : val;
        TargetCountSpinner.setModel(new javax.swing.SpinnerNumberModel(val, 1, max, 1));
    }
    
    private void GenerateButtonAction() {
        if(!generateButtonActionEnabled)
            return;
        
        generateButtonActionEnabled = false;
        
        if (selectedIndex < WAREHOUSES.length)
            new Simulator(WAREHOUSES[selectedIndex], this).start();
        else
            new Simulator(Integer.parseInt(RowCountSpinner.getValue().toString()), 
                    Integer.parseInt(ColumnCountSpinner.getValue().toString()), 
                    Integer.parseInt(TargetCountSpinner.getValue().toString()), 
                    Float.parseFloat(ProbabilitySpinner.getValue().toString()),
                    this
            ).start();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        LOADING_SCREEN.setVisible(true);
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Application().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner ColumnCountSpinner;
    private javax.swing.JLabel ProbabilityLabel;
    private javax.swing.JSpinner ProbabilitySpinner;
    private javax.swing.JSpinner RowCountSpinner;
    private javax.swing.JButton SimulateButton;
    private javax.swing.JLabel TargetCountLabel;
    private javax.swing.JSpinner TargetCountSpinner;
    private javax.swing.JComboBox<String> WarehousesComboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}