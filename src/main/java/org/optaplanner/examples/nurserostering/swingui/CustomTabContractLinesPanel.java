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
import java.util.Collection;
import java.util.List;
import javax.swing.JOptionPane;
//import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
//import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

import org.optaplanner.examples.common.business.SolutionBusiness;
import org.optaplanner.examples.common.domain.AbstractPersistable;

import org.optaplanner.examples.nurserostering.domain.Employee;
import org.optaplanner.examples.nurserostering.domain.NurseRoster;
import org.optaplanner.examples.nurserostering.domain.WeekendDefinition;
import org.optaplanner.examples.nurserostering.domain.contract.BooleanContractLine;
import org.optaplanner.examples.nurserostering.domain.contract.Contract;
import org.optaplanner.examples.nurserostering.domain.contract.ContractLine;
import org.optaplanner.examples.nurserostering.domain.contract.ContractLineType;
import org.optaplanner.examples.nurserostering.domain.contract.MinMaxContractLine;
import org.optaplanner.examples.nurserostering.domain.contract.PatternContractLine;


/**
 *
 * @author Martien
 */
public class CustomTabContractLinesPanel extends TabContractPanel{
    SolutionBusiness<NurseRoster> mySolutionBusiness = null;
     
    Contract currentContract;
    String initialCode;
    String initialDescription;
    String initialWeekendDefinition;
    int currentContractSelection = -1;
    int currentContractLineSelection = -1;
    
    CustomTabContractLinesPanel(SolutionBusiness<NurseRoster> solutionBusiness){
        super();
        mySolutionBusiness = solutionBusiness;
        init();
    }
        
    public void init(){
        //populate list boxes
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        setContractLineListModel();  //set to DefaultListModel
        DefaultListModel listModel = new DefaultListModel();
        
        List<Contract> contractList = nurseRoster.getContractList();
        List<ContractLine> contractLineList = nurseRoster.getContractLineList();
        ListSelectionModel lsm =  getContractListSelectionModel();
        
        //String[] listContractData = new String[contractList.size()];
        
        int i = 0;
        
        //JOptionPane.showMessageDialog(null, "Filling Contract list");
        emptyContractListBox();
        emptyContractLineListBox();
        
       
        for (Contract contract:contractList){
            listModel.addElement(contract.getDescription());
            
        }
        setContractListBox(listModel);
        
        /*
        i = 0;
        initialCode = contractList.get(i).getCode();
        setCode(initialCode);
        initialDescription = contractList.get(i).getDescription();
        setDescription(initialDescription);
        initialWeekendDefinition = contractList.get(i).getWeekendDefinition().toString();
        setWeekendDefinition(initialWeekendDefinition);
        */
        
        setContractListBoxSelection(0);  //set first item in list which triggers the event handler so handleContractListValueChanged populates ContractLinesListBox
        
    }
    
    @Override
    public void handleContractListValueChanged(javax.swing.event.ListSelectionEvent evt){
        
        currentContractSelection = evt.getFirstIndex() ;
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        //JOptionPane.showMessageDialog(null, "Selection changed in Contract list");
        
        DefaultListModel listModelAssigned = new DefaultListModel();
        DefaultListModel listModelUnassigned = new DefaultListModel();
        List<Contract> contractList = nurseRoster.getContractList();
        List<ContractLine> contractLineList = nurseRoster.getContractLineList();
        List<ContractLineType> contractTypeList;
        ListSelectionModel lsm =  getContractListSelectionModel();
        
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        for (int i = minIndex; i <= maxIndex; i++) {
            if (lsm.isSelectedIndex(i)) {
                currentContractSelection = i;
                currentContract = contractList.get(i);
                //JOptionPane.showMessageDialog(null, "You selected item : " + i);
                initialCode = contractList.get(i).getCode();
                setCode(initialCode);
                initialDescription = contractList.get(i).getDescription();
                setDescription(initialDescription);
                initialWeekendDefinition = contractList.get(i).getWeekendDefinition().toString();
                setWeekendDefinition(initialWeekendDefinition);
                
                //JOptionPane.showMessageDialog(null, "Populating Contractlines for this contract");
                for (ContractLine contractLine:contractLineList){
                    if (contractList.get(i).getCode().equals(contractLine.getContract().getCode()) ){
                        listModelAssigned.addElement(contractLine.toString());
                    }
                }
                //ContractLineType is an enum and not a list
                
                boolean found;
                for (ContractLineType contractLineType:ContractLineType.values()) {
                    found = false;
                    for (ContractLine contractLine:contractLineList){
                        if (contractList.get(i).getCode().equals(contractLine.getContract().getCode()) ){
                            if (contractLine.getContractLineType().equals(contractLineType)){
                                found = true;
                            }
                        }
                    }
                    if (!found){
                        listModelUnassigned.addElement(contractLineType.toString());
                    }
                }
                //JOptionPane.showMessageDialog(null, "Setting list in Listbox");
                                
                setContractLinesListBox(listModelAssigned);
                setUnassignedListBox(listModelUnassigned);
                //JOptionPane.showMessageDialog(null, "List is ready in Listbox");
            }
        }
    }
    
    
    
    @Override
    public void handleAssignButtonClicked(){
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        List<Contract> contractList = nurseRoster.getContractList();
        List<ContractLine> contractLineList = nurseRoster.getContractLineList();
        ListModel listModelUnassigned = getUnassignedListModel(); //???
        ListSelectionModel lsm =  getUnassignedListSelectionModel();
        DefaultListModel newListModelAssigned = new DefaultListModel();
        DefaultListModel newListModelUnassigned = new DefaultListModel();
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        boolean found;
        long maxId = -1l;
        for (ContractLine contractLine:contractLineList){
            if ( contractLine.getId() > maxId){
                maxId = contractLine.getId();
            }
        }
        for (int i = minIndex; i <= maxIndex; i++) {
            if (lsm.isSelectedIndex(i)) {
                //JOptionPane.showMessageDialog(null, "You selected item : " + i);
                //add item to contract lines
                ContractLineType thisContractLineType = ContractLineType.ALTERNATIVE_SKILL_CATEGORY;
                //JOptionPane.showMessageDialog(null, "You selected item to add : " + i + listModelUnassigned.getElementAt(i));
                
                found = false;
                for (ContractLineType contractLineType:ContractLineType.values()){
                    if (contractLineType.toString().equals(listModelUnassigned.getElementAt(i))) {
                        thisContractLineType = contractLineType;
                        found = true;
                    }
                }
                //JOptionPane.showMessageDialog(null, "Selection found ?  : " + found);
                
                BooleanContractLine contractLine = new BooleanContractLine();
                contractLine.setContractLineType(thisContractLineType);
                contractLine.setContract(currentContract);
                contractLine.setId(maxId + 1);
                contractLine.setEnabled(true);
                contractLine.setWeight(1);
                contractLineList.add(contractLine);
                
                //refreshing the lists
                for (ContractLine thisContractLine:contractLineList){
                    if (contractList.get(currentContractSelection).getCode().equals(thisContractLine.getContract().getCode()) ){
                        newListModelAssigned.addElement(thisContractLine.toString());
                    }
                }
                
                for (ContractLineType contractLineType:ContractLineType.values()) {
                    found = false;
                    for (ContractLine thisContractLine:contractLineList){
                        if (contractList.get(currentContractSelection).getCode().equals(thisContractLine.getContract().getCode()) ){
                            if (thisContractLine.getContractLineType().equals(contractLineType)){
                                found = true;
                            }
                        }
                    }
                    if (!found){
                        newListModelUnassigned.addElement(contractLineType.toString());
                    }
                }
            }
        }
        setContractLinesListBox(newListModelAssigned);
        setUnassignedListBox(newListModelUnassigned); 
        
    }
    
    
    /*
    //@Override
    public void handleAssignButtonClicked( Collection<AbstractPersistable> contractLineList){
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        List<Contract> contractList = nurseRoster.getContractList();
        //List<ContractLine> contractLineList = nurseRoster.getContractLineList();
        ListModel listModelUnassigned = getUnassignedListModel(); //???
        ListSelectionModel lsm =  getUnassignedListSelectionModel();
        DefaultListModel newListModelAssigned = new DefaultListModel();
        DefaultListModel newListModelUnassigned = new DefaultListModel();
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        boolean found;
        long maxId = -1l;
        //getId is NOT a method of <T>  !!!
        for (AbstractPersistable contractLine:contractLineList){
            if ( contractLine.getId() > maxId){
                maxId = contractLine.getId();
            }
        }
        for (int i = minIndex; i <= maxIndex; i++) {
            if (lsm.isSelectedIndex(i)) {
                //JOptionPane.showMessageDialog(null, "You selected item : " + i);
                //add item to contract lines
                ContractLineType thisContractLineType = ContractLineType.ALTERNATIVE_SKILL_CATEGORY;
                //JOptionPane.showMessageDialog(null, "You selected item to add : " + i + listModelUnassigned.getElementAt(i));
                
                found = false;
                for (ContractLineType contractLineType:ContractLineType.values()){
                    if (contractLineType.toString().equals(listModelUnassigned.getElementAt(i))) {
                        thisContractLineType = contractLineType;
                        found = true;
                    }
                }
                //JOptionPane.showMessageDialog(null, "Selection found ?  : " + found);
                
                BooleanContractLine contractLine = new BooleanContractLine();
                contractLine.setContractLineType(thisContractLineType);
                contractLine.setContract(currentContract);
                contractLine.setId(maxId + 1);
                contractLine.setEnabled(true);
                contractLine.setWeight(1);
                contractLineList.add(contractLine);
                
                //refreshing the lists
                for (AbstractPersistable thisContractLine:contractLineList){
                    if (contractList.get(currentContractSelection).getCode().equals(thisContractLine.getContract().getCode()) ){
                        newListModelAssigned.addElement(thisContractLine.toString());
                    }
                }
                
                for (ContractLineType contractLineType:ContractLineType.values()) {
                    found = false;
                    for (ContractLine thisContractLine:contractLineList){
                        if (contractList.get(currentContractSelection).getCode().equals(thisContractLine.getContract().getCode()) ){
                            if (thisContractLine.getContractLineType().equals(contractLineType)){
                                found = true;
                            }
                        }
                    }
                    if (!found){
                        newListModelUnassigned.addElement(contractLineType.toString());
                    }
                }
            }
        }
        setContractLinesListBox(newListModelAssigned);
        setUnassignedListBox(newListModelUnassigned); 
        
    }
    */
    
    
    
    
    @Override
    public void handleUnassignButtonClicked(){
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        List<Contract> contractList = nurseRoster.getContractList();
        List<ContractLine> contractLineList = nurseRoster.getContractLineList();
        ListModel listModelContractLines = getContractLinesListModel(); 
        ListSelectionModel lsm =  getContractLinesListSelectionModel();
        DefaultListModel newListModelAssigned = new DefaultListModel();
        DefaultListModel newListModelUnassigned = new DefaultListModel();
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        
        for (int i = minIndex; i <= maxIndex; i++) {
            if (lsm.isSelectedIndex(i)) {
                //JOptionPane.showMessageDialog(null, "You selected item : " + i + listModelContractLines.getElementAt(i));
                //delete item to contract lines
                ContractLine deleted = null;
                for (ContractLine contractLine:contractLineList){
                    if ( //contractLine.getContract().getCode().equals(currentContractSelection) &&
                            contractLine.toString().equals(listModelContractLines.getElementAt(i))){
                        deleted = contractLine;
                        //JOptionPane.showMessageDialog(null, "You unassigned item : " + contractLine.toString());
                        //deleted = contractLine;
                    }
                }
                if ( deleted != null ) {
                    contractLineList.remove(deleted);
                }                
                
                //refreshing the lists
                //JOptionPane.showMessageDialog(null, "Refreshing ContractLines");
                for (ContractLine thisContractLine:contractLineList){
                    if (contractList.get(currentContractSelection).getCode().equals(thisContractLine.getContract().getCode()) ){
                        newListModelAssigned.addElement(thisContractLine.toString());
                    }
                }
                //JOptionPane.showMessageDialog(null, "Refreshing Unassigned Lines");
                boolean found;
                for (ContractLineType contractLineType:ContractLineType.values()) {
                    found = false;
                    for (ContractLine thisContractLine:contractLineList){
                        if (contractList.get(currentContractSelection).getCode().equals(thisContractLine.getContract().getCode()) ){
                            if (thisContractLine.getContractLineType().equals(contractLineType)){
                                found = true;
                            }
                        }
                    }
                    if (!found){
                        newListModelUnassigned.addElement(contractLineType.toString());
                    }
                }
            }
        }
        setContractLinesListBox(newListModelAssigned);
        setUnassignedListBox(newListModelUnassigned); 
        
    }
    
    public boolean isEqual(){
        if ( initialCode.equals(getCode()) &&
             initialDescription.equals(getDescription()) &&
             initialWeekendDefinition.equals(getWeekendDefinition()) ) {
            return true;
        }
        else
            return false;
    }
    
    @Override
    public void handleBooleanContractLineRadioButtonClicked(){
        setBooleanContractLineRadioButtonSelected();
    }
    
    @Override
    public void handleMinMaxContractLineRadioButtonClicked(){
        setMinMaxContractLineRadioButtonSelected();
    }
    
    @Override
    public void handleCancelButtonClicked(){
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to cancel changes? \n",
                            "Cancel changes.",
                            JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
        //regenerateNurseRoster();
            setCode(initialCode);
            setDescription(initialDescription);
            setWeekendDefinition(initialWeekendDefinition);
        }
                
    }
    
    @Override
    public void handleOKButtonClicked(){
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        List<Contract> contractList = nurseRoster.getContractList();
        
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to commit changes? \n",
                            "Contract has changed.",
                            JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            //contractList.get(currentContractSelection).setCode(getCode());
            currentContract.setCode(getCode());
            currentContract.setDescription(getDescription());
            for(WeekendDefinition w:WeekendDefinition.values()){
                if( w.toString().equals(getWeekendDefinition())){
                    currentContract.setWeekendDefinition(w);
                }
            }
            initialCode = getCode();
            initialDescription = getDescription();
            initialWeekendDefinition = getWeekendDefinition();
        }
    }
    
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
            /*
            try {
                contract.setWeekendDefinition(WeekendDefinition.valueOf(initialWeekendDefinition));  //weekend definition might not be found in WeekendDefinition ENUM
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Unable to set weekend defintion. (Try SATURDAY_SUNDAY) \n"+ "Full error : " + e );
            }
            */
            contract.setId(contract.getMaxId(contractList) + 1l);
            contract.setContractLineList(contractLineList);
            contractList.add(contract);
            //remember the new settings or new contract will trigger a false contract changed 
            initialCode = getCode();
            initialDescription = getDescription();
            initialWeekendDefinition = getWeekendDefinition();
        }
    }
    
    @Override
    public void handleDeleteButtonClicked(){
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        List<Contract> contractList = nurseRoster.getContractList();
        
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to delete this contract?",
                            "Delete Contract.",
                            JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            boolean succes = false;
            if ( currentContract != null ){
                succes = contractList.remove(currentContract);
            }
            if (succes){
                JOptionPane.showMessageDialog(null, "Contract removed");
            }
            else {
                JOptionPane.showMessageDialog(null, "Contract was not removed!");
            }
        }
    }
    
    
}
