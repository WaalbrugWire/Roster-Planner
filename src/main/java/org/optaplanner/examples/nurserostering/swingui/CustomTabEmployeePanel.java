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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import org.optaplanner.examples.common.business.SolutionBusiness;

import org.optaplanner.examples.nurserostering.domain.Employee;
import org.optaplanner.examples.nurserostering.domain.NurseRoster;
import org.optaplanner.examples.nurserostering.domain.Shift;
import org.optaplanner.examples.nurserostering.domain.ShiftAssignment;
import org.optaplanner.examples.nurserostering.domain.ShiftDate;
import org.optaplanner.examples.nurserostering.domain.ShiftType;
import org.optaplanner.examples.nurserostering.domain.contract.Contract;
import org.optaplanner.examples.nurserostering.domain.request.DayOffRequest;
import org.optaplanner.examples.nurserostering.domain.request.DayOnRequest;
import org.optaplanner.examples.nurserostering.domain.request.ShiftOffRequest;
import org.optaplanner.examples.nurserostering.domain.request.ShiftOnRequest;

//import com.github.lgooddatepicker;
/**
 *
 * @author Martien
 */
public class CustomTabEmployeePanel extends TabEmployeePanel{
    
    SolutionBusiness<NurseRoster> mySolutionBusiness = null;
    //ScoreDirector<NurseRoster> scoreDirector;
    
    Employee currentEmployee;
    List<Employee> toAddEmployee = new ArrayList<Employee>();
    List<Employee> toDeleteEmployee = new ArrayList<Employee>();
    String initialCode;
    String initialName;
    String initialContract;
    int currentSelection = -1;
    boolean isInitalized = false;
    
    CustomTabEmployeePanel(SolutionBusiness<NurseRoster> solutionBusiness){
        super();
        mySolutionBusiness = solutionBusiness;
        init();
    }
    
    public void init(){
        //populate ShiftType list box
        List<Employee> employeeList = mySolutionBusiness.getSolution().getEmployeeList();
        String[] listData = new String[employeeList.size()];
        initListBox();
        if ( employeeList.size() > 0 ){
            currentSelection = 0;
            setEmployeeListBoxSelection(currentSelection);
        }
        isInitalized = true;
        //JOptionPane.showMessageDialog(null, "Initalizing Employee Tab finished" );
    }
    
    public void initListBox(){
        List<Employee> employeeList = mySolutionBusiness.getSolution().getEmployeeList();
        String[] listData = new String[employeeList.size()];
        emptyListBox();
        int i = 0;
        for (Employee employee:employeeList){
            if (employee.getName().isEmpty() ){
                listData[i] = "code : " + employee.getCode();
            } 
            else{ 
                listData[i] = employee.getName();
            }
            i++;
        }
        setListBox(listData);
    }
    
    public boolean isEqual(){
        return (initialCode.equals(getCode()) && initialName.equals(getName()) && initialContract.equals(getContract()) );
    }
    
    public boolean isInitialized() {
        return isInitalized;
    }
    
    @Override
    public void handleEmployeeListValueChanged(javax.swing.event.ListSelectionEvent evt){
        //JOptionPane.showMessageDialog(null, "debug: handleEmployeeListValueChanged called. Refreshing the list box content to show the changes.");
        List<Employee> employeeList = mySolutionBusiness.getSolution().getEmployeeList();
        ListSelectionModel lsm = getListSelectionModel();
        boolean isSingleSelectionMode = false;
        isSingleSelectionMode = ( lsm.getSelectionMode() == ListSelectionModel.SINGLE_SELECTION);
        int firstIndex = evt.getFirstIndex();
        int lastIndex = evt.getLastIndex();
        boolean isAdjusting = evt.getValueIsAdjusting();
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        for (int i = minIndex; i <= maxIndex; i++) {
            if (lsm.isSelectedIndex(i)) {
                currentSelection = i;
                currentEmployee = employeeList.get(i);
                initialCode = employeeList.get(i).getCode();
                setCode(initialCode);
                initialName = employeeList.get(i).getName();
                setName(initialName);
                initialContract = employeeList.get(i).getContract().toString();
                setContract(initialContract);
            }
        }
    }
    
        
    @Override
    public void handleOKButtonClicked(){
        //ignore changes to code ??
        /* already there 
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to commit changes? \n",
                            "Commit changes.", JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        */
        {
            List<Employee> employeeList = mySolutionBusiness.getSolution().getEmployeeList();
            List<Contract> contractList = mySolutionBusiness.getSolution().getContractList();
            DefaultListModel listModel = new DefaultListModel();
            Employee employee = employeeList.get(currentSelection);
            Contract contract = new Contract();
            contract.setCode(getContract());
            boolean isContractFound = false;
            boolean isCodeChanged = false;
            boolean isCodeUnique = true;
            boolean isNameChanged = false;
            boolean isContractChanged = false;  //Another contract is assigned to Employee, the original contract is not changed

            if ( !getCode().equals(employee.getCode())) {
                //check code unique
                for(Employee thisEmployee:employeeList){
                    if (thisEmployee.getCode().equals(getCode())){
                        isCodeUnique = false;
                        JOptionPane.showMessageDialog(null, "Employee code is not unique. Please select another code");
                    } 
                }
                if (isCodeUnique){
                    isCodeChanged = true;
                }
            }
            if ( !getName().equals(employee.getName())) {
                isNameChanged = true;
            }
            if ( !getContract().equals(employee.getContract().toString())) {
                isContractChanged = true;
                for ( Contract existingContract:contractList){
                        if(contract.getCode().equals(existingContract.getCode())){
                            isContractFound = true;
                            contract = existingContract;  //we need the other values in contract as well or we get a nullpointer exception later
                        }
                }
                if ( !isContractFound ){
                            JOptionPane.showMessageDialog(null, "No contract with this contract code. \nPlease change contract code.");
                            isContractChanged = false;
                }
            }


            if ( isCodeChanged || isNameChanged || isContractChanged ) {
                //JOptionPane.showMessageDialog(null, "debug info: Things have changed "  + "code : " + getCode() + " empinfo code : " + employee.getCode() + " name : " + getName() + " contract : " + getContract() + " empinfo : " + employee.getContract().toString());
                int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to commit? \n \n",
                                "Employee changed.",
                                JOptionPane.OK_CANCEL_OPTION);
                    //, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    if( isCodeChanged ) {
                            changeCode(employee, getCode());
                            initialCode = getCode();
                    }
                    if( isNameChanged ) {
                            changeName(employee, getName());
                            initialName = getName();
                    }
                    if( isContractChanged && isContractFound ) {
                            changeContract(employee, contract);
                            initialContract = getContract();
                    }

                    for (Employee emp:employeeList){
                        listModel.addElement(emp.getName());
                    }
                    //JOptionPane.showMessageDialog(null, "Setting list in Listbox");

                    setEmployeeListBox(listModel);
                    //int tempSelection = currentSelection;
                    //JOptionPane.showMessageDialog(null, "debug: refreshing the list box content to show the changes.");
                    //is same issue as in contract lines  refresh how it is done there
                    //handleEmployeeListValueChanged(evt);
                    //currentSelection = tempSelection;
                }
                else {
                     //resetting values
                     handleCancelButtonClicked();
                }
            }
        }
    }
    
    @Override
    public void handleCancelButtonClicked(){
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to cancel changes? \n",
                            "Cancel changes.", JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            setCode(initialCode);
            setName(initialName);
            setContract(initialContract);
        }
    }
    
    @Override
    public void handleDeleteButtonClicked(){
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to delete this employee? \n",
                            "Delete employee.", JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            List<Employee> employeeList = mySolutionBusiness.getSolution().getEmployeeList();
            Employee employee = employeeList.get(currentSelection);
            toDeleteEmployee.add(employee);
            JOptionPane.showMessageDialog(null, "Employee deleted");
        }
    }
    
    @Override
    public void handleNewButtonClicked(){
        int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to add this employee? \n",
                            "Add employee.",
                            JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            List<Employee> employeeList = mySolutionBusiness.getSolution().getEmployeeList();
            List<Contract> contractList = mySolutionBusiness.getSolution().getContractList();
            Map<ShiftDate, DayOffRequest> dayOffRequestMap = new HashMap<>();
            Map<ShiftDate, DayOnRequest> dayOnRequestMap = new HashMap<>();
            Map<Shift, ShiftOffRequest> shiftOffRequestMap = new HashMap<>();
            Map<Shift, ShiftOnRequest> shiftOnRequestMap = new HashMap<>();
            //= new HashMap<>();
            
            Employee employee = new Employee();
            Contract contract = new Contract();
            
            //contract.setCode(getContract());
            boolean uniqueCode = true;
            boolean contractFound = false;
            for (Contract thisContract:contractList){
                if (thisContract.getCode().equals(getContract())){
                    contract = thisContract;
                    contractFound = true;
                }
            }
            if (!contractFound){
                JOptionPane.showMessageDialog(null, "Contract not found. Employee can not be added");
            }
            //long maxId = -1l;
            for (Employee thisEmployee:employeeList){
                if (thisEmployee.getCode().equals(getCode()) ){
                    uniqueCode = false;
                    JOptionPane.showMessageDialog(null, "Employee code is not unique. Please select another code");
                }
            }
            if (uniqueCode && contractFound) {
                employee.setCode(getCode());
                employee.setContract(contract);
                employee.setName(getName());
                //maxId = employee.getMaxId(employeeList);
                //employee.setId(maxId++);
                employee.setId(employee.getMaxId(employeeList) + 1);
                //set dayoff/dayon/shiftoff/shifton lists as well!!!
                //it is a new emp so cant have holidays yet
                employee.setDayOffRequestMap(dayOffRequestMap);
                employee.setDayOnRequestMap(dayOnRequestMap);
                employee.setShiftOffRequestMap(shiftOffRequestMap);
                employee.setShiftOnRequestMap(shiftOnRequestMap);
                toAddEmployee.add(employee);
                JOptionPane.showMessageDialog(null, "Employee scheduled for adding");
                
                /* temp temp temp
                 public void setDayOffRequestMap(Map<ShiftDate, DayOffRequest> dayOffRequestMap) {
                    this.dayOffRequestMap = dayOffRequestMap;
                }

                public Map<ShiftDate, DayOnRequest> getDayOnRequestMap() {
                    return dayOnRequestMap;
                }

                public void setDayOnRequestMap(Map<ShiftDate, DayOnRequest> dayOnRequestMap) {
                    this.dayOnRequestMap = dayOnRequestMap;
                }

                public Map<Shift, ShiftOffRequest> getShiftOffRequestMap() {
                    return shiftOffRequestMap;
                }

                public void setShiftOffRequestMap(Map<Shift, ShiftOffRequest> shiftOffRequestMap) {
                    this.shiftOffRequestMap = shiftOffRequestMap;
                }

                public Map<Shift, ShiftOnRequest> getShiftOnRequestMap() {
                    return shiftOnRequestMap;
                }

                public void setShiftOnRequestMap(Map<Shift, ShiftOnRequest> shiftOnRequestMap) {
                    this.shiftOnRequestMap = shiftOnRequestMap;
                }
                */
                
            }
        }
    }
    
    
    
    @Override
    public void handleDayOffButtonClicked(){
        JDialog dayOffPicker = new JDialog();
        CustomTabDayOffPicker tabDayOffPicker = new CustomTabDayOffPicker(mySolutionBusiness, currentEmployee);
        dayOffPicker.add(tabDayOffPicker);
        dayOffPicker.setLocationRelativeTo(this.getTopLevelAncestor());
        dayOffPicker.pack();
        dayOffPicker.setVisible(true);
    }
    
    public void changeName(Employee employee, String name) {
            mySolutionBusiness.doProblemFactChange(scoreDirector -> {
                NurseRoster nurseRoster = scoreDirector.getWorkingSolution();
                Employee workingEmployee = scoreDirector.lookUpWorkingObject(employee);
                if (workingEmployee == null) {
                    // The employee does not exist, so do nothing
                    return;
                }
                // First change the problem fact property for all planning entities that use it
                for (ShiftAssignment shiftAssignment : nurseRoster.getShiftAssignmentList()) {
                    if (shiftAssignment.getEmployee() == workingEmployee) {
                        scoreDirector.beforeVariableChanged(shiftAssignment, "employee");
                        shiftAssignment.getEmployee().setName(getName());
                        scoreDirector.afterVariableChanged(shiftAssignment, "employee");
                    }
                }
                
                // Change the problem fact property itself
                //JOptionPane.showMessageDialog(null, "debug info: Changing the name");

                //chnage properties one at a time! Really ONE ONLY
                //should build a functioon for each property then?
                scoreDirector.beforeProblemPropertyChanged(workingEmployee);
                workingEmployee.setName(getName());
                scoreDirector.afterProblemPropertyChanged(workingEmployee);

                scoreDirector.triggerVariableListeners();
                
            });
    }
    
    public void changeCode(Employee employee, String code) {
            mySolutionBusiness.doProblemFactChange(scoreDirector -> {
                NurseRoster nurseRoster = scoreDirector.getWorkingSolution();
                Employee workingEmployee = scoreDirector.lookUpWorkingObject(employee);
                if (workingEmployee == null) {
                    // The employee does not exist, so do nothing
                    return;
                }
                // First change the problem fact property for all planning entities that use it
                for (ShiftAssignment shiftAssignment : nurseRoster.getShiftAssignmentList()) {
                    if (shiftAssignment.getEmployee() == workingEmployee) {
                        scoreDirector.beforeVariableChanged(shiftAssignment, "employee");
                        shiftAssignment.getEmployee().setCode(getCode());
                        scoreDirector.afterVariableChanged(shiftAssignment, "employee");
                    }
                }
                
                // Change the problem fact property itself
                //JOptionPane.showMessageDialog(null, "debug info: Changing the code");

                //chnage properties one at a time! Really ONE ONLY
                //should build a functioon for each property then?
                scoreDirector.beforeProblemPropertyChanged(workingEmployee);
                workingEmployee.setCode(getCode());
                scoreDirector.afterProblemPropertyChanged(workingEmployee);

                scoreDirector.triggerVariableListeners();
                
            });
    }
    
    public void changeContract(Employee employee, Contract contract) {
            mySolutionBusiness.doProblemFactChange(scoreDirector -> {
                NurseRoster nurseRoster = scoreDirector.getWorkingSolution();
                Employee workingEmployee = scoreDirector.lookUpWorkingObject(employee);
                if (workingEmployee == null) {
                    // The employee does not exist, so do nothing
                    return;
                }
                // First remove the problem fact property for all planning entities that use it
                for (ShiftAssignment shiftAssignment : nurseRoster.getShiftAssignmentList()) {
                    if (shiftAssignment.getEmployee() == workingEmployee) {
                        scoreDirector.beforeVariableChanged(shiftAssignment, "employee");
                        shiftAssignment.getEmployee().setContract(contract);
                        scoreDirector.afterVariableChanged(shiftAssignment, "employee");
                    }
                }
                

                // Change the problem fact property itself
                // JOptionPane.showMessageDialog(null, "debug info: Changing the assigned contract");

                //chnage properties one at a time! Really ONE ONLY
                //should build a functioon for each property then?
                scoreDirector.beforeProblemPropertyChanged(workingEmployee);
                workingEmployee.setContract(contract);
                scoreDirector.afterProblemPropertyChanged(workingEmployee);

                scoreDirector.triggerVariableListeners();
                
            });
    }
    
    
}
