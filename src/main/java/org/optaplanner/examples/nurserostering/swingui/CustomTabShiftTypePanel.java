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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import org.jdom.JDOMException;

import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.examples.common.business.SolutionBusiness;
import org.optaplanner.examples.nurserostering.domain.Employee;

import org.optaplanner.examples.nurserostering.domain.NurseRoster;
import org.optaplanner.examples.nurserostering.domain.Shift;
import org.optaplanner.examples.nurserostering.domain.ShiftAssignment;
import org.optaplanner.examples.nurserostering.domain.ShiftDate;
import org.optaplanner.examples.nurserostering.domain.ShiftType;
import org.optaplanner.examples.nurserostering.persistence.NurseRosteringImporter;

//import org.optaplanner.examples.nurserostering.swingui.CustomShiftTypePanel;
//import org.optaplanner.examples.nurserostering.swingui.NurseRosteringPanel;

/**
 *
 * @author Martien
 */
public class CustomTabShiftTypePanel extends TabShiftTypePanel{
    SolutionBusiness<NurseRoster> mySolutionBusiness = null;
    //ScoreDirector<NurseRoster> scoreDirector;
    ShiftDate startDate;
    ShiftDate endDate;
    ShiftType currentShiftType;
    List<ShiftType> toAddShiftType = new ArrayList<ShiftType>();
    List<ShiftType> toDeleteShiftType = new ArrayList<ShiftType>();
    String initialCode;
    String initialIndex;
    Boolean initialNight;
    String initialStartTime;
    String initialEndTime;
    String initialDescription;
    int currentSelection = -1;
    Boolean isInitialized = false;
    
    CustomTabShiftTypePanel(SolutionBusiness<NurseRoster> solutionBusiness){
        super();
        mySolutionBusiness = solutionBusiness;
        init();
    }
    
    
    /*
    customShiftTypePanel(ScoreDirector<NurseRoster> scoreDirector){
        super();
        this.scoreDirector = scoreDirector;
        init();
    }
    */
    
    public void init(){
        //populate ShiftType list box
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        //ShiftType shiftType = new ShiftType();
        List<ShiftType> shiftTypeList = nurseRoster.getShiftTypeList();
        String[] listData = new String[shiftTypeList.size()];
        emptyListBox();
        int i = 0;
        for (ShiftType shiftType:shiftTypeList){
            listData[i] = shiftType.getCode();
            i++;
        }
        setListBox(listData);
        if (shiftTypeList.size() > 0) {
            currentSelection = 0;
            setShiftTypeListBoxSelection(currentSelection);
        }
        //setListBoxMode();  //not working, overriden property in super designer to set value to SINGLE_SELECTION
        //Trying to set to SINGLE_SELECTION did not work
    /*
    jShiftTypeList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
    */
        isInitialized = true;
    }
    
    public boolean isEqual(){
        if( initialCode.equals(getCode()) &&
                initialIndex.equals(getIndex()) &&
                initialNight == getNight() &&
                initialStartTime.equals(getStartTime()) &&
                initialEndTime.equals(getEndTime()) &&
                initialDescription.equals(getDescription())
                ){
            return true;
        }
        else
            return false;
    }
    
    public Boolean isInitialized() {
        return isInitialized;
    }
    
    @Override
    public void handleShiftTypeListValueChanged(javax.swing.event.ListSelectionEvent evt){
        List<ShiftType> shiftTypeList = mySolutionBusiness.getSolution().getShiftTypeList();
        ListSelectionModel lsm = getListSelectionModel();
        //(ListSelectionModel)evt.getSource();
        boolean isSingleSelectionMode = false;
        isSingleSelectionMode = ( lsm.getSelectionMode() == ListSelectionModel.SINGLE_SELECTION);
        //JOptionPane.showMessageDialog(null, "Selection model is SINGLE_SELECTION = " + isSingleSelectionMode + " current int value " + lsm.getSelectionMode());
        int firstIndex = evt.getFirstIndex();
        int lastIndex = evt.getLastIndex();
        boolean isAdjusting = evt.getValueIsAdjusting();
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        for (int i = minIndex; i <= maxIndex; i++) {
            if (lsm.isSelectedIndex(i)) {
                currentSelection = i;
                currentShiftType = shiftTypeList.get(i);
                //JOptionPane.showMessageDialog(null, "You selected item : " + i);
                initialCode = shiftTypeList.get(i).getCode();
                setCode(initialCode);
                Integer val = shiftTypeList.get(i).getIndex();
                initialIndex = val.toString();
                setIndex(initialIndex);
                initialNight = shiftTypeList.get(i).isNight();
                setNight(initialNight);
                initialEndTime = shiftTypeList.get(i).getEndTimeString();
                setEndTime(initialEndTime);
                initialStartTime = shiftTypeList.get(i).getStartTimeString();
                setStartTime(initialStartTime);
                initialDescription = shiftTypeList.get(i).getDescription();
                setDescription(initialDescription);
            }
        }
    }
    
    @Override
    public void handleOKButtonClicked(){
        //ignore changes to code or index??
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to apply changes? \n",
                            "Shift Type changed.", JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            List<ShiftType> shiftTypeList = mySolutionBusiness.getSolution().getShiftTypeList();
            Integer val = shiftTypeList.get(currentSelection).getIndex();
            /*
            if ( getCode() == shiftTypeList.get(currentSelection).getCode() &&
                 getIndex() == val.toString() &&
                 getNight() == shiftTypeList.get(currentSelection).isNight() &&
                 getEndTime() == shiftTypeList.get(currentSelection).getEndTimeString() &&
                 getStartTime() == shiftTypeList.get(currentSelection).getStartTimeString() &&
                 getDescription() == shiftTypeList.get(currentSelection).getDescription() ) {
            }
            else
            */
            if (!isEqual())
            {
                JOptionPane.showMessageDialog(null, "ShiftType has changed. Applying changes");
                //TODO: maybe check if start and endtime is correct??? Or user resposibilitiy?
                //if ((getNight() &&  getStartTime().)){
                    shiftTypeList.get(currentSelection).setCode(getCode());
                    //id or index change not supported (yet) but if id != index -> might give problems
                    shiftTypeList.get(currentSelection).setNight(getNight());
                    shiftTypeList.get(currentSelection).setDescription(getDescription());
                    shiftTypeList.get(currentSelection).setStartTimeString(getStartTime());
                    shiftTypeList.get(currentSelection).setEndTimeString(getEndTime());
                //}
            }
        }
    }
    
    @Override
    public void handleNewButtonClicked(){
        //ignore changes to code or index??
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to add Shift Type? \n",
                            "New Shift Type.", JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            List<ShiftType> shiftTypeList = mySolutionBusiness.getSolution().getShiftTypeList();
            ShiftType shiftType = new ShiftType();
            shiftType.setCode(getCode());
            Integer max = -1;
            Long maxId = -1L;
            for (ShiftType s:shiftTypeList){
                if (s.getIndex() > max){
                    max = s.getIndex();
                }
                if (s.getId() > maxId){
                    maxId = s.getId();
                }
            }
            shiftType.setId(maxId + 1);
            shiftType.setIndex(max + 1);
            //shiftType.setIndex(shiftTypeList.size());  //buggy, try to get the max!!!
            //shiftType.setIndex(initialIndex.to);
            shiftType.setNight(getNight());
            shiftType.setStartTimeString(getStartTime());
            shiftType.setEndTimeString(getEndTime());
            shiftType.setDescription(getDescription());


            //scoreDirector.beforeEntityAdded(shiftType);
            shiftTypeList.add(shiftType);
            //scoreDirector.afterEntityAdded(shiftType);

            //toAddShiftType.add(shiftType);
            //JOptionPane.showMessageDialog(null, "ShiftType to add is set on the to do list");
            JOptionPane.showMessageDialog(null, "Shift Type added");
            //regenerateNurseRoster();
            //resetPanel();
            /*
            doProblemFactChange(scoreDirector -> {
                NurseRoster nurseRoster = scoreDirector.getWorkingSolution();
                Employee workingEmployee = scoreDirector.lookUpWorkingObject(employee);
                if (workingEmployee == null) {
                    // The employee has already been deleted (the UI asked to changed the same employee twice), so do nothing
                    return;
                }
                // First remove the problem fact from all planning entities that use it
                for (ShiftAssignment shiftAssignment : nurseRoster.getShiftAssignmentList()) {
                    if (shiftAssignment.getEmployee() == workingEmployee) {
                        scoreDirector.beforeVariableChanged(shiftAssignment, "employee");
                        shiftAssignment.setEmployee(null);
                        scoreDirector.afterVariableChanged(shiftAssignment, "employee");
                    }
                }
                // A SolutionCloner does not clone problem fact lists (such as employeeList)
                // Shallow clone the employeeList so only workingSolution is affected, not bestSolution or guiSolution
                ArrayList<Employee> employeeList = new ArrayList<>(nurseRoster.getEmployeeList());
                nurseRoster.setEmployeeList(employeeList);
                // Remove it the problem fact itself
                scoreDirector.beforeProblemFactRemoved(workingEmployee);
                employeeList.remove(workingEmployee);
                scoreDirector.afterProblemFactRemoved(workingEmployee);
                scoreDirector.triggerVariableListeners();
            });
            */
        }
    }
    
    @Override
    public void handleDeleteButtonClicked(){
     //Override in customShiftTypePanel
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to delete Shift Type? \n",
                            "Delete Shift Type.", JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            List<ShiftType> shiftTypeList = mySolutionBusiness.getSolution().getShiftTypeList();
            toDeleteShiftType.add(shiftTypeList.get(currentSelection));
            JOptionPane.showMessageDialog(null, "ShiftType to delete is set on the to do list");
        }
    };
    
    @Override
    public void handleCancelButtonClicked(){
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to cancel changes? \n",
                            "Cancel changes.", JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            setCode(initialCode);
            setIndex(initialIndex);
            setNight(initialNight);
            setEndTime(initialEndTime);
            setStartTime(initialStartTime);
            setDescription(initialDescription);
        }
    }
 
    
    private void regenerateNurseRoster(){
        //LocalDate lDate01 = null;
        //LocalDate lDate02 = null;
        //ShiftDate firstDate = new ShiftDate();
        //ShiftDate lastDate = new ShiftDate();
        //firstDate.setDate(lDate01.parse(getStartDateTextField()));
        //lastDate.setDate(lDate02.parse(getEndDateTextField()));
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        NurseRoster oldNurseRoster = nurseRoster;
        NurseRosteringImporter.NurseRosteringInputBuilder obj = new NurseRosteringImporter.NurseRosteringInputBuilder();
        //nurseRoster.setShiftDateList(imp.generateShiftDateList( nurseRoster, firstDate.shiftDateToElement(), lastDate.shiftDateToElement() ));
        nurseRoster.setId(0L);
        nurseRoster.setCode(oldNurseRoster.getCode());
        //try {
        //    obj.generateShiftDateList(nurseRoster, firstDate.shiftDateToElement(), lastDate.shiftDateToElement());
        //}
        //catch(JDOMException e) {
        //    System.out.println( "Trying to rebuild the ShiftDateList for rebuild of NurseRoster failed: " + e);
        //};
        obj.generateNurseRosterInfo(nurseRoster);
        nurseRoster.setSkillList(oldNurseRoster.getSkillList());
        nurseRoster.setShiftTypeList(oldNurseRoster.getShiftTypeList());
        //we need a new shiftList as well
        try {
            obj.generateShiftList(nurseRoster);
        }
        catch(JDOMException e) {
            System.out.println( "Trying to rebuild the ShiftList for rebuild of NurseRoster failed: " + e);
        };
        nurseRoster.setPatternList(oldNurseRoster.getPatternList());
        nurseRoster.setContractList(oldNurseRoster.getContractList());
        nurseRoster.setEmployeeList(oldNurseRoster.getEmployeeList());
        //no getters and setter for Requires Sizes
        //nurseRoster.setRequiredEmployeeSizes(oldNurseRoster.getRequiredEmployeeSizes());
                            
        generateShiftRequirements(nurseRoster);
        //DayOff DayOn RequestList might be no longer valid
        //nurseRoster.setDayOffRequestList();.setEmployeeList(oldNurseRoster.getEmployeeList());
        nurseRoster.setShiftOffRequestList(oldNurseRoster.getShiftOffRequestList());
        nurseRoster.setShiftOnRequestList(oldNurseRoster.getShiftOnRequestList());
        obj.createShiftAssignmentList(nurseRoster);
        //resetPanel(nurseRoster);  
    }
    
    private void generateShiftRequirements(NurseRoster nurseRoster, int dummy__put_template_here_later ) {
            Map<LocalDate, ShiftDate> shiftDateMap;
            List<Shift> shiftList = nurseRoster.getShiftList();
            
            //template should be a 3 dimensional of <ShiftType, DayOfWeek, int requirement>
            for(Shift shift : shiftList) {
                if (shift.getShiftDate().getDayOfWeek() == DayOfWeek.SATURDAY ||
                    shift.getShiftDate().getDayOfWeek() == DayOfWeek.SUNDAY    ){
                    if(shift.getShiftType().getCode().contentEquals("E") ||
                       shift.getShiftType().getCode().contentEquals("L") ||
                       shift.getShiftType().getCode().contentEquals("N") ){
                        shift.setRequiredEmployeeSize(1);
                    }
                    else{
                       shift.setRequiredEmployeeSize(2); 
                    }
                }
                if (shift.getShiftDate().getDayOfWeek() == DayOfWeek.MONDAY ||
                    shift.getShiftDate().getDayOfWeek() == DayOfWeek.TUESDAY ||
                    shift.getShiftDate().getDayOfWeek() == DayOfWeek.WEDNESDAY ||
                    shift.getShiftDate().getDayOfWeek() == DayOfWeek.THURSDAY ||
                    shift.getShiftDate().getDayOfWeek() == DayOfWeek.FRIDAY){
                    if(shift.getShiftType().getCode().contentEquals("E") ||
                       shift.getShiftType().getCode().contentEquals("L") ||
                       shift.getShiftType().getCode().contentEquals("N") ){
                        shift.setRequiredEmployeeSize(5);
                    }
                    else if(shift.getShiftType().getCode().contentEquals("D")) {
                        shift.setRequiredEmployeeSize(6);
                    }
                    else if(shift.getShiftType().getCode().contentEquals("DH")) {
                        shift.setRequiredEmployeeSize(2);
                    }
                    else {
                        shift.setRequiredEmployeeSize(3);
                    }
                }
                    
            }
            nurseRoster.setShiftList(shiftList);
        }
    
    private void generateShiftRequirements(NurseRoster nurseRoster){
        generateShiftRequirements(nurseRoster, 0);
    }
   
    
        
    
}
