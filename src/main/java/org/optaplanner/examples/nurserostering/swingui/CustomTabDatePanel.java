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
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.jdom.JDOMException;

import org.optaplanner.examples.common.business.SolutionBusiness;
import org.optaplanner.examples.nurserostering.domain.Employee;

import org.optaplanner.examples.nurserostering.domain.NurseRoster;
import org.optaplanner.examples.nurserostering.domain.NurseRosterParametrization;
import org.optaplanner.examples.nurserostering.domain.Shift;
import org.optaplanner.examples.nurserostering.domain.ShiftDate;
import org.optaplanner.examples.nurserostering.domain.request.DayOffRequest;
import org.optaplanner.examples.nurserostering.persistence.NurseRosteringImporter;
import org.optaplanner.examples.nurserostering.swingui.NurseRosteringPanel;

/**
 *
 * @author Martien
 */

public class CustomTabDatePanel extends TabDatePanel{
    SolutionBusiness<NurseRoster> mySolutionBusiness = null;
    ShiftDate startDate;
    ShiftDate endDate;
    
    CustomTabDatePanel(SolutionBusiness<NurseRoster> solutionBusiness){
        super();
        mySolutionBusiness = solutionBusiness;
        init();
    }
    
    public void setStartDateTextField() {
        startDate = mySolutionBusiness.getSolution().getNurseRosterParametrization().getFirstShiftDate();
        this.setjStartDateTextField(startDate.getDate().toString());
    }
    
    public String getStartDateTextField() {
        return this.getjStartDateTextField();
    }
    
    public void setEndDateTextField() {
        endDate = mySolutionBusiness.getSolution().getNurseRosterParametrization().getLastShiftDate();
        this.setjEndDateTextField(endDate.getDate().toString());
    }
    
    public String getEndDateTextField() {
        return this.getjEndDateTextField();
    }
    
    public void init() {
        setStartDateTextField();
        setEndDateTextField();
    }
    
    public boolean isEqual(){
        return ( startDate.getDate().toString().equals(getStartDateTextField()) &&
                 endDate.getDate().toString().equals(getEndDateTextField()) );
    }
    
    public boolean isBefore(){
        LocalDate lDate01 = null;
        LocalDate lDate02 = null;
        ShiftDate firstDate = new ShiftDate();
        ShiftDate lastDate = new ShiftDate();
        firstDate.setDate(lDate01.parse(getStartDateTextField()));
        lastDate.setDate(lDate02.parse(getEndDateTextField()));
        return ( firstDate.getDate().isBefore(lastDate.getDate()) );
    }
    
    @Override
    public void handleOKButtonClicked(){
        LocalDate lDate01 = null;
        LocalDate lDate02 = null;
        ShiftDate firstDate = new ShiftDate();
        ShiftDate lastDate = new ShiftDate();
        firstDate.setDate(lDate01.parse(getStartDateTextField()));
        lastDate.setDate(lDate02.parse(getEndDateTextField()));
        if (!isEqual()) {
            if(isBefore()) {
                //showConfirmDialog(null, panel, "Edit Roster Parameters",
                //JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                int result = JOptionPane.showConfirmDialog( this.getTopLevelAncestor(), "Do you want to save the new dates?",
                            "Dates changed.",
                            JOptionPane.OK_CANCEL_OPTION);
                //, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION){
                    startDate = firstDate;
                    endDate = lastDate;
                    regenerateNurseRoster();
                    //still needs a resetPanel which we do in invokeEditDialogBox
                }
                else {
                    init();
                }
            }
            else {
                JOptionPane.showMessageDialog( this.getTopLevelAncestor(), 
                            "Start Date must be before End Date." );
            }
        }
    }
    
    @Override
    public void handleCancelButtonClicked(){
        init();
    }
    
    private void regenerateNurseRoster(){
        LocalDate lDate01 = null;
        LocalDate lDate02 = null;
        ShiftDate firstDate = new ShiftDate();
        ShiftDate lastDate = new ShiftDate();
        firstDate.setDate(lDate01.parse(getStartDateTextField()));
        lastDate.setDate(lDate02.parse(getEndDateTextField()));
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        NurseRoster oldNurseRoster = nurseRoster;
        NurseRosteringImporter.NurseRosteringInputBuilder obj = new NurseRosteringImporter.NurseRosteringInputBuilder();
        //nurseRoster.setShiftDateList(imp.generateShiftDateList( nurseRoster, firstDate.shiftDateToElement(), lastDate.shiftDateToElement() ));
        nurseRoster.setId(0L);
        nurseRoster.setCode(oldNurseRoster.getCode());
        try {
            //only regenerate if dates changed
            //or we will not find matches for the dayoffrequests as the shiftdates do not longer match
            //might rework setting the backcolor to something more robust without getting a performance hit
            
            //buggy so trying again without checking for date change andalways regen shiftDateList, WaalbrugWire, 12-5-2018
            /*
            if ( !( firstDate.equals(nurseRoster.getNurseRosterParametrization().getFirstShiftDate()) && 
                    lastDate.equals(nurseRoster.getNurseRosterParametrization().getLastShiftDate())  )
                    ) {
            */
                obj.generateShiftDateList(nurseRoster, firstDate.shiftDateToElement(), lastDate.shiftDateToElement());
                System.out.println( "Rebuilded the ShiftDateList.");
            //}
        }
        catch(JDOMException e) {
            System.out.println( "Trying to rebuild the ShiftDateList for rebuild of NurseRoster failed: " + e);
        };
        obj.generateNurseRosterInfo(nurseRoster);
        nurseRoster.setSkillList(oldNurseRoster.getSkillList());
        //nurseRoster.setShiftTypeList(oldNurseRoster.getShiftTypeList());
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
        purgeOldDayOffRequests(nurseRoster); 
        obj.createShiftAssignmentList(nurseRoster);
        //resetPanel(nurseRoster);  
    }
    
    /*
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
    */
    
    private void generateShiftRequirements(NurseRoster nurseRoster, int dummy__put_template_here_later ) {
        Map<LocalDate, ShiftDate> shiftDateMap;
        List<Shift> shiftList = nurseRoster.getShiftList();
            
        //template should be a 3 dimensional of <ShiftType, DayOfWeek, int requirement>
        for(Shift shift : shiftList) {
            if (shift.getShiftDate().getDayOfWeek() == DayOfWeek.SATURDAY ||
                shift.getShiftDate().getDayOfWeek() == DayOfWeek.SUNDAY    ){
                if(shift.getShiftType().getCode().contentEquals("wk") ||
                   shift.getShiftType().getCode().contentEquals("E") ||
                   shift.getShiftType().getCode().contentEquals("L") ||
                   shift.getShiftType().getCode().contentEquals("N") ){
                    shift.setRequiredEmployeeSize(1);
                }
            }
            if (shift.getShiftDate().getDayOfWeek() == DayOfWeek.MONDAY ||
                shift.getShiftDate().getDayOfWeek() == DayOfWeek.TUESDAY ||
                shift.getShiftDate().getDayOfWeek() == DayOfWeek.WEDNESDAY ||
                shift.getShiftDate().getDayOfWeek() == DayOfWeek.THURSDAY ||
                shift.getShiftDate().getDayOfWeek() == DayOfWeek.FRIDAY){
                if(shift.getShiftType().getCode().contentEquals("1") ||
                   shift.getShiftType().getCode().contentEquals("2") ||
                   shift.getShiftType().getCode().contentEquals("E") ||
                   shift.getShiftType().getCode().contentEquals("L") ||
                   shift.getShiftType().getCode().contentEquals("N") ){
                   shift.setRequiredEmployeeSize(1);
                }
            else if(shift.getShiftType().getCode().contentEquals("D")) {
                shift.setRequiredEmployeeSize(6);
                }
            else if(shift.getShiftType().getCode().contentEquals("DH")) {
                shift.setRequiredEmployeeSize(2);
                }
                    
            }       
        }
        nurseRoster.setShiftList(shiftList);
    }
    
    private void generateShiftRequirements(NurseRoster nurseRoster){
        generateShiftRequirements(nurseRoster, 0);
    }
    
    private int purgeOldDayOffRequests(NurseRoster nurseRoster){
        int count = 0;
        List <Employee> employeeList = nurseRoster.getEmployeeList();
        List <DayOffRequest> dayOffRequests = nurseRoster.getDayOffRequestList();
        count = dayOffRequests.size();
        dayOffRequests.removeIf(dayOffRequest -> (dayOffRequest.getShiftDate().getDate().isBefore(startDate.getDate()) ||
                dayOffRequest.getShiftDate().getDate().isAfter(endDate.getDate())));
        /*
        for (DayOffRequest dayOffRequest : dayOffRequests){
            if ( dayOffRequest.getShiftDate().getDate().isBefore(startDate.getDate())){
                dayOffRequests.remove(dayOffRequest);
                count++;
            }
            if ( dayOffRequest.getShiftDate().getDayIndex() != 0){
                //JOptionPane.showMessageDialog(this, "Found dayOffRequest with dayIndex not 0 " + dayOffRequest.getShiftDate());
                dayOffRequest.getShiftDate().setDayIndex(0);
            }
        }
        */
        System.out.println("Purge: Removed " + (count - dayOffRequests.size()) + " old day off requests);");
        //JOptionPane.showMessageDialog(this, "Removed " + (count - dayOffRequests.size()) + " old day off requests);");
                    
        for (Employee employee : employeeList){
            count = 0;
            Map<ShiftDate, DayOffRequest> dayOffRequestMap = employee.getDayOffRequestMap();
            for (Map.Entry<ShiftDate, DayOffRequest> entry : dayOffRequestMap.entrySet())
            {
                //System.out.println(entry.getKey() + "/" + entry.getValue());
                if ( entry.getKey().getDate().isBefore(startDate.getDate())){
                    dayOffRequestMap.remove(entry);
                    count++;
                }
                //else entry.getKey().setDayIndex(0);
            }
            System.out.println("Purge: Removed " + count + " day off requests for employee " );
            //JOptionPane.showMessageDialog(this, "Removed " + count + " day off requests for employee " + employee);
        }
        
        return count;
    }
    
}
