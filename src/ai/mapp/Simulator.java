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

package ai.mapp;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Abhineet Kumar
 */
public class Simulator extends javax.swing.JFrame implements Runnable, KeyListener {
    
    public interface SimulatorLaunchListener {
        void simulatorLaunched();
    }
    
    final private SimulationSpeedController speedController = new SimulationSpeedController(10);
    
    final private SimulatorLaunchListener launchListener;

    final private Map map;
    
    private Thread thread; 
    
    //true when simulation has completed
    private boolean halt = false;
   
    /**
     * Constructor to create simulator with randomly distributed grid-map.
     * @param rowsCount number of rows in grid
     * @param columnsCount number of columns in grid
     * @param targetCount number of targets/bots
     * @param obstacleProbability probability of finding an obstacle at a given
     * grid element
     * @param launchListener
     */
    public Simulator(int rowsCount, int columnsCount, int targetCount, 
            float obstacleProbability, SimulatorLaunchListener launchListener) {
        
        super("MAPP Simulator: Random Map");
        init();
        map =  RandomMapGenerator.createRandomMap(rowsCount, columnsCount, targetCount, obstacleProbability);
        this.launchListener = launchListener;
    }
        
    /**
     * Constructor to create simulator with one of the predefined grid-maps
     * given by <code>warehouse</code>.
     * @param warehouse warehouse definition
     * @param launchListener
     */
    public Simulator(Warehouse warehouse, SimulatorLaunchListener launchListener) {
        super("MAPP Simulator: " + warehouse.getName());
        init();
        map = new Map(warehouse);
        this.launchListener = launchListener;
    }
    
    private void init() {
        initComponents();
        Canvas.addKeyListener(this);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent we) {
                launchListener.simulatorLaunched();
            }
        });
    }
    
    final public void start() {
        map.assignTargets();
        setVisible(true);
        Canvas.grabFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                playPause();
                break;
                
            case KeyEvent.VK_R:
                reset();
                break;
                
            case KeyEvent.VK_N:
                updateFrame();
                break;
                
            case KeyEvent.VK_UP:
                speedController.speedUp();
                SpeedSlider.setValue(speedController.getSpeedMultiplier());
                break;
                
            case KeyEvent.VK_DOWN:
                speedController.slowDown();
                SpeedSlider.setValue(speedController.getSpeedMultiplier());
                break;
        }
    }
    
    private void playPause() {
        if (halt)
            return;
        
        synchronized (this) {
            if (thread == null) {
                setPlayButtonToPause();
                (thread = new Thread(this)).start();
            }
            else {
                thread.interrupt();
                thread = null;
                setPlayButtonToPlay();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            try {
                Thread.sleep(speedController.getFrameInterval());
            } catch (InterruptedException ex) {
                break;
            }
            
            if (Thread.interrupted()) 
                break;
            
            if(!updateFrame()) {
                thread.interrupt();
                thread = null;
                setPlayButtonToPlay();
                break;
            }
        }
    }
    
    private void setPlayButtonToPlay() {
        PlayPauseButton.setIcon(SingletonResources.PLAY_ICON);
        PlayPauseButton.setToolTipText("Play (Enter)");
    }
    
    private void setPlayButtonToPause() {
        PlayPauseButton.setIcon(SingletonResources.PAUSE_ICON);
        PlayPauseButton.setToolTipText("Pause (Enter)");
    }
    
    private void reset() {
        synchronized (this) {
            if (thread != null) {
                thread.interrupt();
                thread = null;
                setPlayButtonToPlay();
            }
        }
        map.reset();
        Canvas.repaint();
        halt = false;
        PlayPauseButton.setEnabled(true);
        NextFrameButton.setEnabled(true);
    }
    
    boolean updateFrame() {
        if (halt)
            return !halt;
        halt = !map.update();
        if (halt) {
            PlayPauseButton.setEnabled(false);
            NextFrameButton.setEnabled(false);
        }
        Canvas.repaint();
        return !halt;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        ResetButton = new javax.swing.JButton();
        NextFrameButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Canvas = new javax.swing.JPanel(){

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                map.draw(Canvas, g);
            }
        };
        SpeedSlider = new javax.swing.JSlider();
        SpeedLabel = new javax.swing.JLabel();
        SpeedSpinner = new javax.swing.JSpinner();
        PlayPauseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(300, 200, 0, 0));
        setMinimumSize(new java.awt.Dimension(370, 300));

        ResetButton.setMnemonic(KeyEvent.VK_R);
        ResetButton.setToolTipText("Reset (R)");
        ResetButton.setFocusable(false);
        ResetButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        ResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButtonActionPerformed(evt);
            }
        });
        try {
            ResetButton.setIcon(SingletonResources.RESET_ICON);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        NextFrameButton.setMnemonic(KeyEvent.VK_N);
        NextFrameButton.setToolTipText("Next Frame (N)");
        NextFrameButton.setFocusable(false);
        NextFrameButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        NextFrameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextFrameButtonActionPerformed(evt);
            }
        });
        try {
            NextFrameButton.setIcon(SingletonResources.NEXT_FRAME_ICON);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Canvas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CanvasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout CanvasLayout = new javax.swing.GroupLayout(Canvas);
        Canvas.setLayout(CanvasLayout);
        CanvasLayout.setHorizontalGroup(
            CanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1388, Short.MAX_VALUE)
        );
        CanvasLayout.setVerticalGroup(
            CanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3249, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(Canvas);

        SpeedSlider.setMaximum(ai.mapp.SimulationSpeedController.SPEED_MULTIPLIER_MAX);
        SpeedSlider.setMinimum(1);
        SpeedSlider.setToolTipText("Speed (Up/Down)");
        SpeedSlider.setValue(speedController.getSpeedMultiplier());
        SpeedSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SpeedSlider.setFocusable(false);
        SpeedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SpeedSliderStateChanged(evt);
            }
        });

        SpeedLabel.setText("Speed:");
        SpeedLabel.setFocusable(false);

        SpeedSpinner.setModel(new javax.swing.SpinnerNumberModel(speedController.getSpeedMultiplier(), 1, ai.mapp.SimulationSpeedController.SPEED_MULTIPLIER_MAX, 1));
        SpeedSpinner.setToolTipText("Speed (Up/Down)");
        SpeedSpinner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        javax.swing.JSpinner.DefaultEditor editor = new javax.swing.JSpinner.DefaultEditor(SpeedSpinner);
        editor.getTextField().setEditable(false);
        editor.getTextField().setFocusable(false);
        SpeedSpinner.setEditor(editor);
        SpeedSpinner.setFocusable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, SpeedSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), SpeedSpinner, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        PlayPauseButton.setMnemonic(KeyEvent.VK_P);
        PlayPauseButton.setFocusable(false);
        PlayPauseButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        PlayPauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayPauseButtonActionPerformed(evt);
            }
        });
        setPlayButtonToPlay();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PlayPauseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NextFrameButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SpeedLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SpeedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SpeedSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(PlayPauseButton)
                        .addComponent(NextFrameButton)
                        .addComponent(SpeedSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                        .addComponent(SpeedSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SpeedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(ResetButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetButtonActionPerformed
        reset();
    }//GEN-LAST:event_ResetButtonActionPerformed

    private void NextFrameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextFrameButtonActionPerformed
        updateFrame();
    }//GEN-LAST:event_NextFrameButtonActionPerformed

    private void SpeedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SpeedSliderStateChanged
        speedController.setSpeedMultiplier(SpeedSlider.getValue());
    }//GEN-LAST:event_SpeedSliderStateChanged

    private void PlayPauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayPauseButtonActionPerformed
        playPause();
    }//GEN-LAST:event_PlayPauseButtonActionPerformed

    private void CanvasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CanvasMouseClicked
        Canvas.grabFocus();
    }//GEN-LAST:event_CanvasMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Canvas;
    private javax.swing.JButton NextFrameButton;
    private javax.swing.JButton PlayPauseButton;
    private javax.swing.JButton ResetButton;
    private javax.swing.JLabel SpeedLabel;
    private javax.swing.JSlider SpeedSlider;
    private javax.swing.JSpinner SpeedSpinner;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
