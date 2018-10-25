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
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Martien
 */
public class TabMinMaxContractLinesPanel extends javax.swing.JPanel {

    /**
     * Creates new form TabMinMaxContractLinePanel
     */
    public TabMinMaxContractLinesPanel() {
        initComponents();
    }
    
    public void handleOKButtonClicked(){
    //Override in customMinMaxContractLinePanel
    }
    
    public void handleCancelButtonClicked(){
    //Override in customMinMaxContractLinePanel
    }
    
    public void handleNewButtonClicked(){
    //Override in customMinMaxContractLinePanel
    }
    
    public void handleDeleteButtonClicked(){
    //Override in customMinMaxContractLinePanel   
    };
    
    public void handleContractLinesListValueChanged(javax.swing.event.ListSelectionEvent evt){
    //Override in customMinMaxContractLinePanel       
    };
    
    public void handleNextButtonClicked(){
    //Override in customMinMaxContractLinePanel 
    };
    
    public void handlePrevButtonClicked(){
    //Override in customMinMaxContractLinePanel 
    };

    public JCheckBox getjMaxEnabledCheckBox() {
        return jMaxEnabledCheckBox;
    }
    
    public Boolean getMaxEnabledCheckBox() {
        return jMaxEnabledCheckBox.isEnabled();
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
    
    public void setContractLinesListBox(String[] listData){
        jContractLinesList.setListData(listData);
    }
    
    public void setContractLinesListBoxSelection(int selection){
        jContractLinesList.setSelectedIndex(selection);
    }
    
    public void setContractLinesListBox(DefaultListModel listData){
        jContractLinesList.setModel(listData);
    }
    
    public void setContractLinesListBoxMode() {
        jContractLinesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public ListSelectionModel getContractLinesListSelectionModel(){
        return jContractLinesList.getSelectionModel();
    }
    
    public ListModel getContractLinesListModel(){
        return jContractLinesList.getModel();
    }
    

    public void setjMaxEnabledCheckBox(JCheckBox jMaxEnabledCheckBox) {
        this.jMaxEnabledCheckBox = jMaxEnabledCheckBox;
    }
    
    public void setMaxEnabledCheckBox(Boolean enabled) {
        this.jMaxEnabledCheckBox.setEnabled(enabled);
    }

    public JTextField getjMaxValueTextField() {
        return jMaxValueTextField;
    }
    
    public String getMaxValue() {
        return jMaxValueTextField.getText();
    }
    
    public void setjMaxValueTextField(JTextField jMaxValueTextField) {
        this.jMaxValueTextField = jMaxValueTextField;
    }
    
    public void setMaxValue(String text) {
        this.jMaxValueTextField.setText(text);
    }

    public JTextField getjMaxWeightTextField() {
        return jMaxWeightTextField;
    }
    
    public String getMaxWeight() {
        return jMaxWeightTextField.getText();
    }
    
    public void setjMaxWeightTextField(JTextField jMaxWeightTextField) {
        this.jMaxWeightTextField = jMaxWeightTextField;
    }
    
    public void setMaxWeight(String text) {
        this.jMaxWeightTextField.setText(text);
    }

    public JCheckBox getjMinEnabledCheckBox() {
        return jMinEnabledCheckBox;
    }
    
    public Boolean getMinEnabledCheckBox() {
        return jMinEnabledCheckBox.isEnabled();
    }
    
    public void setjMinEnabledCheckBox(JCheckBox jMinEnabledCheckBox) {
        this.jMinEnabledCheckBox = jMinEnabledCheckBox;
    }
    
    public void setMinEnabledCheckBox(Boolean enabled) {
        this.jMinEnabledCheckBox.setEnabled(enabled);
    }
    
    public JTextField getjMinValueTextField() {
        return jMinValueTextField;
    }
    
    public String getMinValue() {
        return jMinValueTextField.getText();
    }

    public void setjMinValueTextField(JTextField jMinValueTextField) {
        this.jMinValueTextField = jMinValueTextField;
    }
    
    public void setMinValue(String text) {
        this.jMinValueTextField.setText(text);
    }

    public JTextField getjMinWeightTextField() {
        return jMinWeightTextField;
    }
    
    public String getMinWeight() {
        return jMinWeightTextField.getText();
    }

    public void setjMinWeightTextField(JTextField jMinWeightTextField) {
        this.jMinWeightTextField = jMinWeightTextField;
    }
    
    public void setMinWeight(String text) {
        this.jMinWeightTextField.setText(text);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMinValueLabel = new javax.swing.JLabel();
        jMinWeightLabel = new javax.swing.JLabel();
        jMaxValueLabel = new javax.swing.JLabel();
        jMaxWeightLabel = new javax.swing.JLabel();
        jMinValueTextField = new javax.swing.JTextField();
        jMinWeightTextField = new javax.swing.JTextField();
        jMaxValueTextField = new javax.swing.JTextField();
        jMaxWeightTextField = new javax.swing.JTextField();
        jMinMaxContractLinesLabel = new javax.swing.JLabel();
        jMinEnabledCheckBox = new javax.swing.JCheckBox();
        jMaxEnabledCheckBox = new javax.swing.JCheckBox();
        jNewButton = new javax.swing.JButton();
        jDeleteButton = new javax.swing.JButton();
        jOKButton = new javax.swing.JButton();
        jCancelButton = new javax.swing.JButton();
        jNextButton = new javax.swing.JButton();
        jPrevButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jContractLinesList = new javax.swing.JList<>();
        jContractLinesLabel = new javax.swing.JLabel();

        jMinValueLabel.setText("MinValue");

        jMinWeightLabel.setText("MinWeight");

        jMaxValueLabel.setText("MaxValue");

        jMaxWeightLabel.setText("MaxWeight");

        jMinValueTextField.setText("minValue");
        jMinValueTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMinValueTextFieldActionPerformed(evt);
            }
        });

        jMinWeightTextField.setText("minWeight");
        jMinWeightTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMinWeightTextFieldActionPerformed(evt);
            }
        });

        jMaxValueTextField.setText("maxValue");

        jMaxWeightTextField.setText("maxWeight");
        jMaxWeightTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMaxWeightTextFieldActionPerformed(evt);
            }
        });

        jMinMaxContractLinesLabel.setText("MinMaxContractLines");

        jMinEnabledCheckBox.setText("MinEnabled");

        jMaxEnabledCheckBox.setText("MaxEnabled");

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

        jNextButton.setText("Next");

        jPrevButton.setText("Prev");

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
        jScrollPane1.setViewportView(jContractLinesList);

        jContractLinesLabel.setText("ContractLines");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jContractLinesLabel))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jMinWeightLabel)
                    .addComponent(jMinMaxContractLinesLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jMinValueTextField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jMinWeightTextField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jMinValueLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jMinEnabledCheckBox)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jOKButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCancelButton))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jNewButton)
                            .addGap(28, 28, 28)
                            .addComponent(jDeleteButton))))
                .addGap(91, 91, 91)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jNextButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPrevButton))
                    .addComponent(jMaxWeightLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jMaxWeightTextField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jMaxValueTextField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jMaxValueLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jMaxEnabledCheckBox))
                .addContainerGap(201, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jMinMaxContractLinesLabel)
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jMinValueLabel)
                    .addComponent(jMaxValueLabel)
                    .addComponent(jContractLinesLabel))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jMinValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jMaxValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jMinWeightLabel)
                            .addComponent(jMaxWeightLabel))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jMinWeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jMaxWeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jMinEnabledCheckBox)
                            .addComponent(jMaxEnabledCheckBox)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jNewButton)
                    .addComponent(jDeleteButton)
                    .addComponent(jNextButton)
                    .addComponent(jPrevButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jOKButton)
                    .addComponent(jCancelButton))
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jMinValueTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMinValueTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMinValueTextFieldActionPerformed

    private void jMinWeightTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMinWeightTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMinWeightTextFieldActionPerformed

    private void jMaxWeightTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMaxWeightTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMaxWeightTextFieldActionPerformed

    private void jDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteButtonActionPerformed
        // TODO add your handling code here:
        handleDeleteButtonClicked();
    }//GEN-LAST:event_jDeleteButtonActionPerformed

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

    private void jContractLinesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jContractLinesListValueChanged
        // TODO add your handling code here:
        handleContractLinesListValueChanged(evt);
    }//GEN-LAST:event_jContractLinesListValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jCancelButton;
    private javax.swing.JLabel jContractLinesLabel;
    private javax.swing.JList<String> jContractLinesList;
    private javax.swing.JButton jDeleteButton;
    private javax.swing.JCheckBox jMaxEnabledCheckBox;
    private javax.swing.JLabel jMaxValueLabel;
    private javax.swing.JTextField jMaxValueTextField;
    private javax.swing.JLabel jMaxWeightLabel;
    private javax.swing.JTextField jMaxWeightTextField;
    private javax.swing.JCheckBox jMinEnabledCheckBox;
    private javax.swing.JLabel jMinMaxContractLinesLabel;
    private javax.swing.JLabel jMinValueLabel;
    private javax.swing.JTextField jMinValueTextField;
    private javax.swing.JLabel jMinWeightLabel;
    private javax.swing.JTextField jMinWeightTextField;
    private javax.swing.JButton jNewButton;
    private javax.swing.JButton jNextButton;
    private javax.swing.JButton jOKButton;
    private javax.swing.JButton jPrevButton;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
