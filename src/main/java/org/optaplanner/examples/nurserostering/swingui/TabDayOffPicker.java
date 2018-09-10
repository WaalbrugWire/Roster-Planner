/*
 * Copyright 2018 JBoss by Red Hat.
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
package org.optaplanner.examples.nurserostering.swingui;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePicker;
import java.time.Month;
import java.time.YearMonth;
import javax.swing.JRadioButton;
import org.optaplanner.examples.nurserostering.domain.ShiftDate;

/**
 *
 * @author Martien
 */
public class TabDayOffPicker extends javax.swing.JPanel {

    /**
     * Creates new form TabDayOffPicker
     */
    public TabDayOffPicker() {
        initComponents();
    }
    
    public void handleSingleDayRadioButtonSelected(){
    //Override in CustomTabDayOffPicker    
    }
    
    public void handlePeriodRadioButtonSelected(){
    //Override in CustomTabDayOffPicker     
    }
    
    public void handleAddRadioButtonSelected(){
    //Override in CustomTabDayOffPicker    
    }
    
    public void handleRemoveRadioButtonSelected(){
    //Override in CustomTabDayOffPicker    
    }
    
    public void handleCalendarPropertyChange(java.beans.PropertyChangeEvent evt){
    //Override in CustomTabDayOffPicker     
    }
    
    public void handleCalendarPanelMouseClicked(java.awt.event.MouseEvent evt){
    //Override in CustomTabDayOffPicker
    }
    
    public void handleSingleDayRadioButtonStateChanged(javax.swing.event.ChangeEvent evt){
    //Override in CustomTabDayOffPicker    
    }
    
    public void handlePeriodRadioButtonStateChanged(javax.swing.event.ChangeEvent evt){
    //Override in CustomTabDayOffPicker    
    }
    
    public void handleAddRadioButtonStateChanged(javax.swing.event.ChangeEvent evt){
    //Override in CustomTabDayOffPicker    
    }
    
    public void handleRemoveRadioButtonStateChanged(javax.swing.event.ChangeEvent evt){
    //Override in CustomTabDayOffPicker    
    }
    
    public void setCalendarPanelStartDay(ShiftDate startDate){
        calendarPanel.setDisplayedYearMonth(YearMonth.of(startDate.getDate().getYear(),startDate.getDate().getMonthValue()));
    }

    public CalendarPanel getCalendarPanel() {
        return calendarPanel;
    }

    public void setCalendarPanel(CalendarPanel calendarPanel) {
        this.calendarPanel = calendarPanel;
    }

    public DatePicker getDatePicker() {
        return fromDatePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.fromDatePicker = datePicker;
    }

    public JRadioButton getjPeriodRadioButton() {
        return jPeriodRadioButton;
    }

    public void setjPeriodRadioButton(JRadioButton jPeriodRadioButton) {
        this.jPeriodRadioButton = jPeriodRadioButton;
    }
    
    public boolean getPeriodRadioButtonSelected() {
        return this.jPeriodRadioButton.isSelected();
    }
    
    public void setPeriodRadioButtonSelected() {
        this.jPeriodRadioButton.setSelected(true);
        this.jSingleDayRadioButton.setSelected(false);
    }
    
    public JRadioButton getjSingleDayRadioButton() {
        return jSingleDayRadioButton;
    }

    public void setjSingleDayRadioButton(JRadioButton jSingleDayRadioButton) {
        this.jSingleDayRadioButton = jSingleDayRadioButton;
    }
    
    public boolean getSingleDayRadioButtonSelected() {
        return this.jSingleDayRadioButton.isSelected();
    }
    
    public void setSingleDayRadioButtonSelected() {
        this.jPeriodRadioButton.setSelected(false);
        this.jSingleDayRadioButton.setSelected(true);
    }
    
    public JRadioButton getjAddRadioButton() {
        return jAddRadioButton;
    }

    public void setjAddRadioButton(JRadioButton jAddRadioButton) {
        this.jAddRadioButton = jAddRadioButton;
    }
    
    public boolean getAddRadioButtonSelected() {
        return this.jAddRadioButton.isSelected();
    }
    
    public void setAddRadioButtonSelected() {
        this.jRemoveRadioButton.setSelected(false);
        this.jAddRadioButton.setSelected(true);
    }
    
    public JRadioButton getjRemoveRadioButton() {
        return jRemoveRadioButton;
    }

    public void setjRemoveRadioButton(JRadioButton jRemoveRadioButton) {
        this.jRemoveRadioButton = jSingleDayRadioButton;
    }
    
    public boolean getRemoveRadioButtonSelected() {
        return this.jRemoveRadioButton.isSelected();
    }
    
    public void setRemoveRadioButtonSelected() {
        this.jAddRadioButton.setSelected(false);
        this.jRemoveRadioButton.setSelected(true);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        fromDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        calendarPanel = new com.github.lgooddatepicker.components.CalendarPanel();
        jSingleDayRadioButton = new javax.swing.JRadioButton();
        jPeriodRadioButton = new javax.swing.JRadioButton();
        jFromDateLabel = new javax.swing.JLabel();
        jDayOffLabel = new javax.swing.JLabel();
        toDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        jToDateLabel = new javax.swing.JLabel();
        jAddRadioButton = new javax.swing.JRadioButton();
        jRemoveRadioButton = new javax.swing.JRadioButton();

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, calendarPanel, org.jdesktop.beansbinding.ELProperty.create("${selectedDateWithoutShowing}"), calendarPanel, org.jdesktop.beansbinding.BeanProperty.create("selectedDate"));
        bindingGroup.addBinding(binding);

        calendarPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                calendarPanelMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                calendarPanelMouseMoved(evt);
            }
        });
        calendarPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                calendarPanelMouseWheelMoved(evt);
            }
        });
        calendarPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calendarPanelMouseClicked(evt);
            }
        });
        calendarPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calendarPanelPropertyChange(evt);
            }
        });

        jSingleDayRadioButton.setText("SingleDay");
        jSingleDayRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSingleDayRadioButtonStateChanged(evt);
            }
        });
        jSingleDayRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSingleDayRadioButtonActionPerformed(evt);
            }
        });

        jPeriodRadioButton.setText("Period");
        jPeriodRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPeriodRadioButtonActionPerformed(evt);
            }
        });

        jFromDateLabel.setText("From");

        jDayOffLabel.setText("Days Off");

        jToDateLabel.setText("To");

        jAddRadioButton.setText("Add");
        jAddRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jAddRadioButtonStateChanged(evt);
            }
        });
        jAddRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAddRadioButtonActionPerformed(evt);
            }
        });

        jRemoveRadioButton.setText("Remove");
        jRemoveRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRemoveRadioButtonStateChanged(evt);
            }
        });
        jRemoveRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRemoveRadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(calendarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(fromDatePicker, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jSingleDayRadioButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jPeriodRadioButton)))
                                    .addComponent(jFromDateLabel))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jToDateLabel)
                                            .addComponent(toDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(jAddRadioButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jRemoveRadioButton))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jDayOffLabel)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jDayOffLabel)
                .addGap(18, 18, 18)
                .addComponent(calendarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jPeriodRadioButton)
                        .addComponent(jAddRadioButton)
                        .addComponent(jRemoveRadioButton))
                    .addComponent(jSingleDayRadioButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFromDateLabel)
                    .addComponent(jToDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fromDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void jSingleDayRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSingleDayRadioButtonActionPerformed
        // TODO add your handling code here:
        handleSingleDayRadioButtonSelected();
    }//GEN-LAST:event_jSingleDayRadioButtonActionPerformed

    private void jPeriodRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPeriodRadioButtonActionPerformed
        // TODO add your handling code here:
        handlePeriodRadioButtonSelected();
    }//GEN-LAST:event_jPeriodRadioButtonActionPerformed

    private void calendarPanelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calendarPanelPropertyChange
        // TODO add your handling code here:
        handleCalendarPropertyChange(evt);
    }//GEN-LAST:event_calendarPanelPropertyChange

    private void calendarPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calendarPanelMouseClicked
        // TODO add your handling code here:
        handleCalendarPanelMouseClicked(evt);
    }//GEN-LAST:event_calendarPanelMouseClicked

    private void calendarPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calendarPanelMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_calendarPanelMouseDragged

    private void calendarPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calendarPanelMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_calendarPanelMouseMoved

    private void calendarPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_calendarPanelMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_calendarPanelMouseWheelMoved

    private void jSingleDayRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSingleDayRadioButtonStateChanged
        // TODO add your handling code here:
        handleSingleDayRadioButtonStateChanged(evt);
    }//GEN-LAST:event_jSingleDayRadioButtonStateChanged

    private void jAddRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jAddRadioButtonStateChanged
        // TODO add your handling code here:
        handleAddRadioButtonStateChanged(evt);
    }//GEN-LAST:event_jAddRadioButtonStateChanged

    private void jRemoveRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRemoveRadioButtonStateChanged
        // TODO add your handling code here:
        handleRemoveRadioButtonStateChanged(evt);
    }//GEN-LAST:event_jRemoveRadioButtonStateChanged

    private void jAddRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAddRadioButtonActionPerformed
        // TODO add your handling code here:
        handleAddRadioButtonSelected();
    }//GEN-LAST:event_jAddRadioButtonActionPerformed

    private void jRemoveRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRemoveRadioButtonActionPerformed
        // TODO add your handling code here:
        handleRemoveRadioButtonSelected();
    }//GEN-LAST:event_jRemoveRadioButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private com.github.lgooddatepicker.components.CalendarPanel calendarPanel;
    private com.github.lgooddatepicker.components.DatePicker fromDatePicker;
    private javax.swing.JRadioButton jAddRadioButton;
    private javax.swing.JLabel jDayOffLabel;
    private javax.swing.JLabel jFromDateLabel;
    private javax.swing.JRadioButton jPeriodRadioButton;
    private javax.swing.JRadioButton jRemoveRadioButton;
    private javax.swing.JRadioButton jSingleDayRadioButton;
    private javax.swing.JLabel jToDateLabel;
    private com.github.lgooddatepicker.components.DatePicker toDatePicker;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
