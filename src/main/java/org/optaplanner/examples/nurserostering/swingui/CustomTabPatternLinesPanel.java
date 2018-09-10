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

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import org.optaplanner.examples.common.business.SolutionBusiness;
import org.optaplanner.examples.nurserostering.domain.NurseRoster;
import org.optaplanner.examples.nurserostering.domain.contract.Contract;
import org.optaplanner.examples.nurserostering.domain.contract.ContractLine;
import org.optaplanner.examples.nurserostering.domain.contract.PatternContractLine;
import org.optaplanner.examples.nurserostering.domain.pattern.Pattern;

/**
 *
 * @author Martien
 */
public class CustomTabPatternLinesPanel extends CustomTabContractLinesPanel {
    
    
    //PatternLine is an extension of ContractLine
    CustomTabPatternLinesPanel(SolutionBusiness<NurseRoster> solutionBusiness){
        super(solutionBusiness);
        mySolutionBusiness = solutionBusiness;
        //init();  no  init is part of our super so dont call it twice
        setContractLineLabel("Pattern Lines");
    }
    
    @Override
    public void handleContractListValueChanged(javax.swing.event.ListSelectionEvent evt){
        
        currentContractSelection = evt.getFirstIndex() ;
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        
        DefaultListModel listModelAssigned = new DefaultListModel();
        DefaultListModel listModelUnassigned = new DefaultListModel();
        
        List<Contract> contractList = nurseRoster.getContractList();
        List<PatternContractLine> patternContractLineList = nurseRoster.getPatternContractLineList();
        List<Pattern> patternList = nurseRoster.getPatternList();
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
                
                for (PatternContractLine patternContractLine:patternContractLineList){
                    if (contractList.get(i).getCode() == patternContractLine.getContract().getCode() ){
                        listModelAssigned.addElement(patternContractLine.toString());
                    }
                }
                
                boolean found;
                for (Pattern pattern:patternList) {
                    found = false;
                    for (PatternContractLine patternContractLine:patternContractLineList){
                        if (contractList.get(i).getCode()  == patternContractLine.getContract().getCode() ){
                            if (patternContractLine.getPattern().equals(pattern)){
                                found = true;
                            }
                        }
                    }
                    if (!found){
                        listModelUnassigned.addElement(pattern.toString());
                    }
                }
                                
                setContractLinesListBox(listModelAssigned);
                setUnassignedListBox(listModelUnassigned);
                
            }
        }
    }
    
    @Override
    public void handleAssignButtonClicked(){
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        List<Contract> contractList = nurseRoster.getContractList();
        List<PatternContractLine> patternContractLineList = nurseRoster.getPatternContractLineList();
        List<Pattern> patternList = nurseRoster.getPatternList();
        ListModel listModelUnassigned = getUnassignedListModel(); //???
        ListSelectionModel lsm =  getUnassignedListSelectionModel();
        DefaultListModel newListModelAssigned = new DefaultListModel();
        DefaultListModel newListModelUnassigned = new DefaultListModel();
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        boolean found;
        long maxId = -1l;
        for (PatternContractLine patternContractLine:patternContractLineList){
            if ( patternContractLine.getId() > maxId){
                maxId = patternContractLine.getId();
            }
        }
        for (int i = minIndex; i <= maxIndex; i++) {
            if (lsm.isSelectedIndex(i)) {
                //JOptionPane.showMessageDialog(null, "You selected item : " + i);
                //add item to contract lines
                
                Pattern thisPattern = null;
                //ContractLineType thisContractLineType = ContractLineType.ALTERNATIVE_SKILL_CATEGORY;
                
//JOptionPane.showMessageDialog(null, "You selected item to add : " + i + listModelUnassigned.getElementAt(i));
                
                found = false;
                for (Pattern pattern:patternList){
                    if (pattern.toString().equals(listModelUnassigned.getElementAt(i))) {
                        thisPattern = pattern;
                        found = true;
                    }
                }
                //JOptionPane.showMessageDialog(null, "Selection found ?  : " + found);
                
                PatternContractLine patternContractLine = new PatternContractLine();
                //contractLine.setContractLineType(thisContractLineType);
                patternContractLine.setContract(currentContract);
                patternContractLine.setPattern(thisPattern);
                patternContractLine.setId(maxId + 1);
                //contractLine.setEnabled(true);
                //contractLine.setWeight(1);
                patternContractLineList.add(patternContractLine);
                
                //refreshing the lists
                for (PatternContractLine thisPatternContractLine:patternContractLineList){
                    if (contractList.get(currentContractSelection).getCode().equals(thisPatternContractLine.getContract().getCode()) ){
                        newListModelAssigned.addElement(thisPatternContractLine.toString());
                    }
                }
                
                for (Pattern pattern:patternList) {
                    found = false;
                    for (PatternContractLine thisPatternContractLine:patternContractLineList){
                        if (contractList.get(currentContractSelection).getCode().equals(thisPatternContractLine.getContract().getCode()) ){
                            if (thisPatternContractLine.getPattern().equals(pattern)){
                                found = true;
                            }
                        }
                    }
                    if (!found){
                        newListModelUnassigned.addElement(pattern.toString());
                    }
                }
            }
        }
        setContractLinesListBox(newListModelAssigned);
        setUnassignedListBox(newListModelUnassigned); 
        
    }
    
    @Override
    public void handleUnassignButtonClicked(){
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        List<Contract> contractList = nurseRoster.getContractList();
        List<Pattern> patternList = nurseRoster.getPatternList();
        List<PatternContractLine> patternContractLineList = nurseRoster.getPatternContractLineList();
        ListModel listModelContractLines = getContractLinesListModel(); 
        ListSelectionModel lsm =  getContractLinesListSelectionModel();  //still same lsm as it is derived from ContractLinesListBox
        DefaultListModel newListModelAssigned = new DefaultListModel();
        DefaultListModel newListModelUnassigned = new DefaultListModel();
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        
        for (int i = minIndex; i <= maxIndex; i++) {
            if (lsm.isSelectedIndex(i)) {
                //JOptionPane.showMessageDialog(null, "You selected item : " + i + listModelContractLines.getElementAt(i));
                //delete item to contract lines
                PatternContractLine deleted = null;
                for (PatternContractLine patternContractLine:patternContractLineList){
                    if ( //contractLine.getContract().getCode().equals(currentContractSelection) &&
                            patternContractLine.toString().equals(listModelContractLines.getElementAt(i))){
                        deleted = patternContractLine;
                        //JOptionPane.showMessageDialog(null, "You unassigned item : " + patternContractLine.toString());
                        //deleted = contractLine;
                    }
                }
                if ( deleted != null ) {
                    patternContractLineList.remove(deleted);
                }                
                
                //refreshing the lists
                //JOptionPane.showMessageDialog(null, "Refreshing ContractLines");
                for (PatternContractLine thisPatternContractLine:patternContractLineList){
                    if (contractList.get(currentContractSelection).getCode().equals(thisPatternContractLine.getContract().getCode()) ){
                        newListModelAssigned.addElement(thisPatternContractLine.toString());
                    }
                }
                //JOptionPane.showMessageDialog(null, "Refreshing Unassigned Lines");
                boolean found;
                for (Pattern pattern:patternList) {
                    found = false;
                    for (PatternContractLine thisPatternContractLine:patternContractLineList){
                        if (contractList.get(currentContractSelection).getCode().equals(thisPatternContractLine.getContract().getCode()) ){
                            if (thisPatternContractLine.getPattern().equals(pattern)){
                                found = true;
                            }
                        }
                    }
                    if (!found){
                        newListModelUnassigned.addElement(pattern.toString());
                    }
                }
            }
        }
        setContractLinesListBox(newListModelAssigned);
        setUnassignedListBox(newListModelUnassigned); 
        
    }
   
    
}
