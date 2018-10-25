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

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import org.optaplanner.examples.common.business.SolutionBusiness;
import org.optaplanner.examples.nurserostering.domain.NurseRoster;
import org.optaplanner.examples.nurserostering.domain.WeekendDefinition;
import org.optaplanner.examples.nurserostering.domain.contract.Contract;
import org.optaplanner.examples.nurserostering.domain.contract.ContractLine;
import org.optaplanner.examples.nurserostering.domain.contract.ContractLineType;
import org.optaplanner.examples.nurserostering.domain.contract.MinMaxContractLine;

/**
 *
 * @author Martien
 */
public class CustomTabMinMaxContractLinesPanel extends TabMinMaxContractLinesPanel{
    SolutionBusiness<NurseRoster> mySolutionBusiness = null;
     
    MinMaxContractLine currentMinMaxContractLine;
    int initialMinValue;
    int initialMaxValue;
    int initialMinWeight;
    int initialMaxWeight;
    boolean initialMinEnabledChecked;
    boolean initialMaxEnabledChecked;
    
    int currentContractSelection = -1;
    int currentMinMaxContractLineSelection = -1;
    
    CustomTabMinMaxContractLinesPanel(SolutionBusiness<NurseRoster> solutionBusiness){
        super();
        mySolutionBusiness = solutionBusiness;
        init();
    }
        
    public void init(){
        //populate list boxes
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        //are we gonna use a ListModel?  Needs work!!!   commented out for now
        
        setContractLineListModel();  //set to DefaultListModel
        DefaultListModel listModel = new DefaultListModel();
        
        //List<Contract> contractList = nurseRoster.getContractList();
        List<ContractLine> contractLineList = nurseRoster.getContractLineList();
        List<MinMaxContractLine> minMaxContractLineList = new ArrayList<>();
        //minMaxContractLineList.clear();
        //MinMaxContractLine minMaxContractLine;
        
        for (ContractLine contractLine:contractLineList){
            if ( contractLine instanceof MinMaxContractLine ){
                //minMaxContractLine = (MinMaxContractLine) contractLine;
                minMaxContractLineList.add((MinMaxContractLine)contractLine);    
            }
        }
        ListSelectionModel lsm =  getContractLinesListSelectionModel();
        
        //String[] listContractData = new String[contractList.size()];
        
        int i = 0;
        
        //JOptionPane.showMessageDialog(null, "Filling Contract list");
        //emptyContractListBox();
        emptyContractLineListBox();
        
       
        for (ContractLine contractLine:contractLineList){
            if ( contractLine instanceof MinMaxContractLine ){
                listModel.addElement(contractLine.getContractLineType());
            }
        }
        setContractLinesListBox(listModel);
        
        /*
        i = 0;
        initialCode = contractList.get(i).getCode();
        setCode(initialCode);
        initialDescription = contractList.get(i).getDescription();
        setDescription(initialDescription);
        initialWeekendDefinition = contractList.get(i).getWeekendDefinition().toString();
        setWeekendDefinition(initialWeekendDefinition);
        */
        
        setContractLinesListBoxSelection(0);  //set first item in list which triggers the event handler so handleContractListValueChanged populates ContractLinesListBox
        
    }
    
    @Override
    public void handleContractLinesListValueChanged(javax.swing.event.ListSelectionEvent evt){
        
        currentMinMaxContractLineSelection = evt.getFirstIndex() ;
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        //JOptionPane.showMessageDialog(null, "Selection changed in Contract list");
        
        DefaultListModel listModelMinMaxContractLines = new DefaultListModel();
        
        List<Contract> contractList = nurseRoster.getContractList();
        List<ContractLine> contractLineList = nurseRoster.getContractLineList();
        List<MinMaxContractLine> minMaxContractLineList  = new ArrayList<>();
        List<ContractLineType> contractTypeList;
        ListSelectionModel lsm =  getContractLinesListSelectionModel();
        
        MinMaxContractLine minMaxContractLine; 
        String min;
        String max;
        
        for (ContractLine contractLine:contractLineList){
            if ( contractLine instanceof MinMaxContractLine ){
                minMaxContractLine = (MinMaxContractLine) contractLine;
                minMaxContractLineList.add(minMaxContractLine);
                if (minMaxContractLine.isMinimumEnabled()){
                    min = Integer.toString(minMaxContractLine.getMinimumValue());
                } 
                else {
                    min = "-";
                }
                if (minMaxContractLine.isMaximumEnabled()){
                    max = Integer.toString(minMaxContractLine.getMaximumValue());
                } 
                else {
                    max = "-";
                }
                    
                listModelMinMaxContractLines.addElement( min + "/" + max + " - " + minMaxContractLine.toString());
            }
        }
        
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        for (int i = minIndex; i <= maxIndex; i++) {
            if (lsm.isSelectedIndex(i)) {
                //currentMinMaxContractLineSelection = i;
                currentMinMaxContractLine = minMaxContractLineList.get(i);
                //JOptionPane.showMessageDialog(null, "You selected item : " + i);
                initialMinWeight = minMaxContractLineList.get(i).getMinimumWeight();
                setMinWeight(Integer.toString(initialMinWeight));
                initialMaxWeight = minMaxContractLineList.get(i).getMaximumWeight();
                setMaxWeight(Integer.toString(initialMaxWeight));
                initialMinValue = minMaxContractLineList.get(i).getMinimumValue();
                setMinValue(Integer.toString(initialMinValue));
                initialMaxValue = minMaxContractLineList.get(i).getMaximumValue();
                setMaxValue(Integer.toString(initialMaxValue));
                initialMinEnabledChecked = minMaxContractLineList.get(i).isMinimumEnabled();
                setMinEnabledCheckBox(initialMinEnabledChecked);
                initialMaxEnabledChecked = minMaxContractLineList.get(i).isMaximumEnabled();
                setMaxEnabledCheckBox(initialMaxEnabledChecked);
                
                setContractLinesListBox(listModelMinMaxContractLines);
            }
        }
    }
    
    public boolean isEqual(){
        if ( Integer.toString(initialMinValue).equals(getMinValue()) &&
             Integer.toString(initialMaxValue).equals(getMaxValue()) &&
             Integer.toString(initialMinWeight).equals(getMinWeight()) &&
             Integer.toString(initialMaxWeight).equals(getMaxWeight()) &&
             initialMinEnabledChecked == getMinEnabledCheckBox() &&
             initialMaxEnabledChecked == getMaxEnabledCheckBox() 
                ) {
            return true;
        }
        else
            return false;
    }
    
    @Override
    public void handleCancelButtonClicked(){
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to cancel changes? \n",
                            "Cancel changes.",
                            JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
        //regenerateNurseRoster();
            setMinValue(Integer.toString(initialMinValue));
            setMaxValue(Integer.toString(initialMaxValue));
            setMinWeight(Integer.toString(initialMinWeight));
            setMaxWeight(Integer.toString(initialMaxWeight));
            setMinEnabledCheckBox(initialMinEnabledChecked);
            setMaxEnabledCheckBox(initialMaxEnabledChecked);
        }
    }
    
    
    @Override
    public void handleOKButtonClicked(){
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        List<Contract> contractList = nurseRoster.getContractList();
        int parsedValue;
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to commit changes? \n",
                            "MinMaxContractLine has changed.",
                            JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            //contractList.get(currentContractSelection).setCode(getCode());
            try {
                parsedValue = Integer.parseInt(getMinValue());
                currentMinMaxContractLine.setMinimumValue(parsedValue);
                initialMinValue = parsedValue;
            }
            catch (NumberFormatException e) { 
                JOptionPane.showMessageDialog(null, "Could not parse string into int for minValue in MinMaxContractLines" + "\nUse numbers only! " + getMinValue() + " is not a number", "Parse Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Could not parse string into int for minValue in MinMaxContractLines : " + getMinValue());
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Unknown Exception occurred parsing string into int for minValue in MinMaxContractLines" + "\nUse numbers only!", "Parse Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Unknown Exception occurred parsing string into int for minValue in MinMaxContractLines");
            }
            
            try {
                parsedValue = Integer.parseInt(getMaxValue());
                currentMinMaxContractLine.setMaximumValue(parsedValue);
                initialMaxValue = parsedValue;
            }
            catch (NumberFormatException e) { 
                JOptionPane.showMessageDialog(null, "Could not parse string into int for maxValue in MinMaxContractLines" + "\nUse numbers only! "  + getMaxValue() + " is not a number", "Parse Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Could not parse string into int for maxValue in MinMaxContractLines : " + getMaxValue());
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Unknown Exception occurred parsing string into int for maxValue in MinMaxContractLines" + "\nUse numbers only!", "Parse Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Unknown Exception occurred parsing string into int for maxValue in MinMaxContractLines");
            }
            
            try {
                parsedValue = Integer.parseInt(getMinWeight());
                currentMinMaxContractLine.setMinimumWeight(parsedValue);
                initialMinWeight = parsedValue;
            }
            catch (NumberFormatException e) { 
                JOptionPane.showMessageDialog(null, "Could not parse string into int for minimumWeight in MinMaxContractLines" + "\nUse numbers only! "  + getMinWeight() + " is not a number", "Parse Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Could not parse string into int for minimumWeight in MinMaxContractLines : " + getMinWeight());
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Unknown Exception occurred parsing string into int for minimumWeight in MinMaxContractLines" + "\nUse numbers only!", "Parse Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Unknown Exception occurred parsing string into int for minimumWeight in MinMaxContractLines");
            }
            
            try {
                parsedValue = Integer.parseInt(getMaxWeight());
                currentMinMaxContractLine.setMaximumWeight(parsedValue);
                initialMaxWeight = parsedValue;
            }
            catch (NumberFormatException e) { 
                JOptionPane.showMessageDialog(null, "Could not parse string into int for maximumWeight in MinMaxContractLines" + "\nUse numbers only! "  + getMaxWeight() + " is not a number", "Parse Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Could not parse string into int for maximumWeight in MinMaxContractLines : " + getMaxWeight());
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Unknown Exception occurred parsing string into int for maximumWeight in MinMaxContractLines" + "\nUse numbers only!", "Parse Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Unknown Exception occurred parsing string into int for maximumWeight in MinMaxContractLines");
            }
            
            currentMinMaxContractLine.setMinimumEnabled(getMinEnabledCheckBox());
            currentMinMaxContractLine.setMaximumEnabled(getMaxEnabledCheckBox());
            
        }
    }
    
    //TODO implement New and Delete
    /*
    
    @Override
    public void handleNewButtonClicked(){
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        List<Contract> contractList = nurseRoster.getContractList();
        List<ContractLine> contractLineList = nurseRoster.getContractLineList();
        
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to add this contract? \n",
                            "Adding Contract.",
                            JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            Contract contract = new Contract();
            contract.setCode(getCode());
            contract.setDescription(getDescription());
            boolean weekendDefinitionFound = false;
            for(WeekendDefinition w : WeekendDefinition.values() ){
                if ( w.getCode().equals(getWeekendDefinition())){
                    contract.setWeekendDefinition(w);
                    weekendDefinitionFound = true;
                    break;
                }
            }
            if ( !weekendDefinitionFound ){
                JOptionPane.showMessageDialog(null, "Unable to set weekend defintion. (Try SaturdaySunday)" );
            }
            
            //try {
            //    contract.setWeekendDefinition(WeekendDefinition.valueOf(initialWeekendDefinition));  //weekend definition might not be found in WeekendDefinition ENUM
            //}
            //catch (Exception e) {
            //    JOptionPane.showMessageDialog(null, "Unable to set weekend defintion. (Try SATURDAY_SUNDAY) \n"+ "Full error : " + e );
            //}
            
            contract.setId(contract.getMaxId(contractList) + 1l);
            contract.setContractLineList(contractLineList);
            contractList.add(contract);
            //remember the new settings or new contract will trigger a false contract changed 
            initialCode = getCode();
            initialDescription = getDescription();
            initialWeekendDefinition = getWeekendDefinition();
        }
    }
    */

    @Override
    public void handleDeleteButtonClicked(){
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        List<ContractLine> contractLineList = nurseRoster.getContractLineList();
                
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to delete this MinMaxContractLine?",
                            "Delete MinMaxContractLine.",
                            JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION){
            boolean succes = false;
            if ( currentMinMaxContractLine != null ){
                succes = contractLineList.remove(currentMinMaxContractLine);
            }
            if (succes){
                JOptionPane.showMessageDialog(null, "MinMaxContractLine removed");
            }
            else {
                JOptionPane.showMessageDialog(null, "MinMaxContractLine was not removed!");
            }
        }
    }
}
