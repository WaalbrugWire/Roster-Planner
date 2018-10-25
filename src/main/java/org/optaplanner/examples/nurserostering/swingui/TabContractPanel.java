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

import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Martien
 */
public class TabContractPanel extends javax.swing.JPanel {

    /**
     * Creates new form TabContractPanel
     */
    public TabContractPanel() {
        initComponents();
    }
    
    public void handleOKButtonClicked(){
    //Override in customTabContractPanel
    }
    
    public void handleCancelButtonClicked(){
    //Override in customTabContractPanel
    }
    
    public void handleNewButtonClicked(){
    //Override in customTabContractPanel
    }
    
    public void handleDeleteButtonClicked(){
    //Override in customTabContractPanel   
    }
    
    public void handleAssignButtonClicked(){
    //Override in customTabContractPanel    
    }
    
    public void handleUnassignButtonClicked(){
    //Override in customTabContractPanel    
    }
    
    public void handleBooleanContractLineRadioButtonClicked(){
    //Override in customTabContractPanel   
    }
    
    public void handleMinMaxContractLineRadioButtonClicked(){
    //Override in customTabContractPanel   
    }

    public void handleContractListValueChanged(javax.swing.event.ListSelectionEvent evt){
    //Override in customTabContractPanel
    }
    
    public void handleContractLinesListValueChanged(javax.swing.event.ListSelectionEvent evt){
    //Override in customTabContractPanel
    }
    
    public void emptyContractListBox(){
        String[] listData = null;
        jContractList.setListData(listData);
    }
    
    public void emptyContractLineListBox(){
        String[] listData = null;
        jContractLinesList.setListData(listData);
    }
    
    public void setContractLineLabel(String label) {
        jContractLinesLabel.setText(label);
    }
    
    public void setContractLineListModel(){
         jContractLinesList.setModel(new DefaultListModel());
    }
    
    public void setContractListBox(String[] listData){
        jContractList.setListData(listData);
    }
    
    public void setContractListBox(DefaultListModel listData){
        jContractList.setModel(listData);
    }
    
    public void setUnassignedListBox(DefaultListModel listData){
        jUnassignedList.setModel(listData);
    }
    
    //public void setContractLinesListBoxHorizontalSlider(Boolean useHorizontalSlider){
    //    jContractLinesList.setAutoscrolls(true); //?????? how to get slider
    //}
    
    public void setContractLinesListBox(String[] listData){
        jContractLinesList.setListData(listData);
    }
    
    public void setContractLinesListBox(DefaultListModel listData){
        jContractLinesList.setModel(listData);
    }
    
    public void setContractListBoxMode() {
        jContractList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void setContractLinesListBoxMode() {
        jContractLinesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void setContractListBoxSelection(int selection){
        jContractList.setSelectedIndex(selection);
    }
    
    public ListSelectionModel getContractListSelectionModel(){
        return jContractList.getSelectionModel();
    }
    
    public ListModel getContractListModel(){
        return jContractList.getModel();
    }
    
    public ListSelectionModel getContractLinesListSelectionModel(){
        return jContractLinesList.getSelectionModel();
    }
    
    public ListModel getContractLinesListModel(){
        return jContractLinesList.getModel();
    }
    
    public ListSelectionModel getUnassignedListSelectionModel(){
        return jUnassignedList.getSelectionModel();
    }
    
    public ListModel getUnassignedListModel(){
        return jUnassignedList.getModel();
    }
    

    //public JTextField getCode() {
    //    return jCodeTextField;
    //}
    
    public String getCode(){
        return jCodeTextField.getText();
    }

    public void setCode(String text) {
        this.jCodeTextField.setText(text);
    }

    public String getDescription() {
        return jDescriptionTextField.getText();
    }

    public void setDescription(String text) {
        this.jDescriptionTextField.setText(text);
    }

    public String getWeekendDefinition() {
        return jWeekendDefinitionTextField.getText();
    }

    public void setWeekendDefinition(String text) {
        this.jWeekendDefinitionTextField.setText(text);
    }
    
    public boolean getBooleanContractLineRadioButtonSelected() {
        return this.jBooleanContractLineRadioButton.isSelected();
    }
    
    public void setBooleanContractLineRadioButtonSelected() {
        this.jMinMaxContractLineRadioButton.setSelected(false);
        this.jBooleanContractLineRadioButton.setSelected(true);
    }
    
    public boolean getMinMaxContractLineRadioButtonSelected() {
        return this.jMinMaxContractLineRadioButton.isSelected();
    }
    
    public void setMinMaxContractLineRadioButtonSelected() {
        this.jBooleanContractLineRadioButton.setSelected(false);
        this.jMinMaxContractLineRadioButton.setSelected(true);
    }
    
    /*  some getters anbd setters from DayOff as example
    
    public void setjSingleDayRadioButton(JRadioButton jSingleDayRadioButton) {
        this.jSingleDayRadioButton = jSingleDayRadioButton;
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
    
    */
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jContractList = new javax.swing.JList<>();
        jContractLabel = new javax.swing.JLabel();
        jContractLinesLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jContractLinesList = new javax.swing.JList<>();
        jEditLabel = new javax.swing.JLabel();
        jDescriptionLabel = new javax.swing.JLabel();
        jWeekendDefinitionLabel = new javax.swing.JLabel();
        jCodeTextField = new javax.swing.JTextField();
        jDescriptionTextField = new javax.swing.JTextField();
        jWeekendDefinitionTextField = new javax.swing.JTextField();
        jOKButton = new javax.swing.JButton();
        jCancelButton = new javax.swing.JButton();
        jNewButton = new javax.swing.JButton();
        jDeleteButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jUnassignedList = new javax.swing.JList<>();
        jUnassignedLinesLabel = new javax.swing.JLabel();
        jAssignButton = new javax.swing.JButton();
        jUnassignButton = new javax.swing.JButton();
        jBooleanContractLineRadioButton = new javax.swing.JRadioButton();
        jMinMaxContractLineRadioButton = new javax.swing.JRadioButton();

        jContractList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jContractList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jContractListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jContractList);

        jContractLabel.setText("Contract");

        jContractLinesLabel.setText("Contract Lines");

        jContractLinesList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jContractLinesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jContractLinesListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jContractLinesList);

        jEditLabel.setText("Code");

        jDescriptionLabel.setText("Description");

        jWeekendDefinitionLabel.setText("WeekendDefinition");

        jCodeTextField.setText("code");
        jCodeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCodeTextFieldActionPerformed(evt);
            }
        });

        jDescriptionTextField.setText("description");

        jWeekendDefinitionTextField.setText("weekenddefinition");

        jOKButton.setText("OK");
        jOKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOKButtonActionPerformed(evt);
            }
        });

        jCancelButton.setText("Cancel");
        jCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCancelButtonActionPerformed(evt);
            }
        });

        jNewButton.setText("New");
        jNewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewButtonActionPerformed(evt);
            }
        });

        jDeleteButton.setText("Delete");
        jDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteButtonActionPerformed(evt);
            }
        });

        jUnassignedList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jUnassignedList.setToolTipText("Use the buttons ( < > ) to select or deselect lines");
        jScrollPane3.setViewportView(jUnassignedList);

        jUnassignedLinesLabel.setText("UnassignedLines");

        jAssignButton.setText("<");
        jAssignButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAssignButtonActionPerformed(evt);
            }
        });

        jUnassignButton.setText(">");
        jUnassignButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUnassignButtonActionPerformed(evt);
            }
        });

        jBooleanContractLineRadioButton.setText("Boolean Contract Line");
        jBooleanContractLineRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBooleanContractLineRadioButtonActionPerformed(evt);
            }
        });

        jMinMaxContractLineRadioButton.setText("MinMax Contract Line");
        jMinMaxContractLineRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMinMaxContractLineRadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jContractLabel)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jEditLabel)
                            .addComponent(jDescriptionLabel)
                            .addComponent(jWeekendDefinitionLabel))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jContractLinesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2)
                            .addComponent(jWeekendDefinitionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(jDescriptionTextField)
                            .addComponent(jCodeTextField)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jNewButton)
                        .addGap(18, 18, 18)
                        .addComponent(jDeleteButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jOKButton)
                        .addGap(28, 28, 28)
                        .addComponent(jCancelButton)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jMinMaxContractLineRadioButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jAssignButton)
                            .addComponent(jUnassignButton))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jUnassignedLinesLabel)))
                    .addComponent(jBooleanContractLineRadioButton))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jContractLabel)
                    .addComponent(jContractLinesLabel)
                    .addComponent(jUnassignedLinesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jAssignButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jUnassignButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jEditLabel)
                    .addComponent(jCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jDescriptionLabel)
                    .addComponent(jDescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jWeekendDefinitionLabel)
                    .addComponent(jWeekendDefinitionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jNewButton)
                    .addComponent(jDeleteButton)
                    .addComponent(jBooleanContractLineRadioButton))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jOKButton)
                    .addComponent(jCancelButton)
                    .addComponent(jMinMaxContractLineRadioButton))
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCodeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCodeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCodeTextFieldActionPerformed

    private void jCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCancelButtonActionPerformed
        // TODO add your handling code here:
        handleCancelButtonClicked();
    }//GEN-LAST:event_jCancelButtonActionPerformed

    private void jNewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewButtonActionPerformed
        // TODO add your handling code here:
        handleNewButtonClicked();
    }//GEN-LAST:event_jNewButtonActionPerformed

    private void jOKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOKButtonActionPerformed
        // TODO add your handling code here:
        handleOKButtonClicked();
    }//GEN-LAST:event_jOKButtonActionPerformed

    private void jDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteButtonActionPerformed
        // TODO add your handling code here:
        handleDeleteButtonClicked();
    }//GEN-LAST:event_jDeleteButtonActionPerformed

    private void jContractListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jContractListValueChanged
        // TODO add your handling code here:
        handleContractListValueChanged(evt);
    }//GEN-LAST:event_jContractListValueChanged

    private void jContractLinesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jContractLinesListValueChanged
        // TODO add your handling code here:
        handleContractLinesListValueChanged(evt);
    }//GEN-LAST:event_jContractLinesListValueChanged

    private void jAssignButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAssignButtonActionPerformed
        // TODO add your handling code here:
        handleAssignButtonClicked();
    }//GEN-LAST:event_jAssignButtonActionPerformed

    private void jUnassignButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUnassignButtonActionPerformed
        // TODO add your handling code here:
        handleUnassignButtonClicked();
    }//GEN-LAST:event_jUnassignButtonActionPerformed

    private void jBooleanContractLineRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBooleanContractLineRadioButtonActionPerformed
        // TODO add your handling code here:
        handleBooleanContractLineRadioButtonClicked();
    }//GEN-LAST:event_jBooleanContractLineRadioButtonActionPerformed

    private void jMinMaxContractLineRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMinMaxContractLineRadioButtonActionPerformed
        // TODO add your handling code here:
        handleMinMaxContractLineRadioButtonClicked();
    }//GEN-LAST:event_jMinMaxContractLineRadioButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAssignButton;
    private javax.swing.JRadioButton jBooleanContractLineRadioButton;
    private javax.swing.JButton jCancelButton;
    private javax.swing.JTextField jCodeTextField;
    private javax.swing.JLabel jContractLabel;
    private javax.swing.JLabel jContractLinesLabel;
    private javax.swing.JList<String> jContractLinesList;
    private javax.swing.JList<String> jContractList;
    private javax.swing.JButton jDeleteButton;
    private javax.swing.JLabel jDescriptionLabel;
    private javax.swing.JTextField jDescriptionTextField;
    private javax.swing.JLabel jEditLabel;
    private javax.swing.JRadioButton jMinMaxContractLineRadioButton;
    private javax.swing.JButton jNewButton;
    private javax.swing.JButton jOKButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jUnassignButton;
    private javax.swing.JLabel jUnassignedLinesLabel;
    private javax.swing.JList<String> jUnassignedList;
    private javax.swing.JLabel jWeekendDefinitionLabel;
    private javax.swing.JTextField jWeekendDefinitionTextField;
    // End of variables declaration//GEN-END:variables
}
