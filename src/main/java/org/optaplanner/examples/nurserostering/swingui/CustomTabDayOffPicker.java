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

/**
 *
 * @author Martien
 */


import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import org.optaplanner.examples.common.business.SolutionBusiness;
import org.optaplanner.examples.nurserostering.domain.Employee;
import org.optaplanner.examples.nurserostering.domain.NurseRoster;
import org.optaplanner.examples.nurserostering.domain.ShiftDate;
import org.optaplanner.examples.nurserostering.domain.request.DayOffRequest;


public class CustomTabDayOffPicker extends TabDayOffPicker{
    
    SolutionBusiness<NurseRoster> mySolutionBusiness = null;
    Employee employee;
    List<DayOffRequest> holidays = null; 
    
    //we should get the real dateLabels out of the CalendarPanel
    private List<JLabel> dateLabels;
    
    LocalDate firstDate;
    LocalDate lastDate;
    LocalDate singleDate;
    LocalDate temp;
    
    public CustomTabDayOffPicker(SolutionBusiness<NurseRoster> solutionBusiness, Employee employee){
        super();
        mySolutionBusiness = solutionBusiness;
        this.employee = employee;
        this.holidays = mySolutionBusiness.getSolution().getDayOffRequestList();
        init();
    }
    
    public void init() {
        ShiftDate startDate = mySolutionBusiness.getSolution().getNurseRosterParametrization().getFirstShiftDate();
        ShiftDate endDate = mySolutionBusiness.getSolution().getNurseRosterParametrization().getLastShiftDate();
        
        setSingleDayRadioButtonSelected();
        setAddRadioButtonSelected();
        this.setCalendarPanelStartDay(startDate);
        CalendarPanel calendarPanel = getCalendarPanel();
        DatePickerSettings dateSettings = new DatePickerSettings();
        //dateSettings.setDateRangeLimits(startDate.getDate(), endDate.getDate());
        dateSettings.setColor(DateArea.CalendarBackgroundSelectedDate, Color.RED);
        dateSettings.setHighlightPolicy(new DayOffOnHighLightPolicy(employee, holidays));
        calendarPanel.setSettings(dateSettings);
        ArrayList<CalendarListener> calendarListeners = calendarPanel.getCalendarListeners();
        //JOptionPane.showMessageDialog(this, "Calendar has " + calendarListeners.size() + " listeners. Adding one listener.");
        DayOffCalendarListener listener = new DayOffCalendarListener();
        calendarPanel.addCalendarListener(listener);
        //JOptionPane.showMessageDialog(this, "Calendar now has " + calendarListeners.size() + " listeners.");  //still says 0
        
    }
    
    @Override
    public void handleCalendarPanelMouseClicked(java.awt.event.MouseEvent evt){
        
    }
    
    @Override
    public void handleCalendarPropertyChange(java.beans.PropertyChangeEvent evt){
        
        CalendarPanel calendarPanel = getCalendarPanel();
        boolean isLeapYear = calendarPanel.getDisplayedYearMonth().isLeapYear();
        int daysOfSelectedMonth = calendarPanel.getDisplayedYearMonth().getMonth().length(isLeapYear);
        ShiftDate shiftDate = null;
        NurseRoster nurseRoster = mySolutionBusiness.getSolution();
        List<ShiftDate> shiftDateList = nurseRoster.getShiftDateList();
        DayOffRequest dayOffRequest = null; 
        Map<ShiftDate, DayOffRequest> dayOffRequestMap = employee.getDayOffRequestMap();
        
        dateLabels = new ArrayList<>(daysOfSelectedMonth);
        
        /* 
            problem will be here we dont have a reference to the actual dateLabels
            inside the calendar so how are we gonna paint their backgrounds???
         */
        
        
        DatePickerSettings dateSettings = calendarPanel.getSettings();
        LocalDate selectedDate = calendarPanel.getSelectedDate();
        
        //there are more PropertyChanges so we not always will have a selectedDate
        //will we have a selectedDate???? MouseClicked handle never fired???
        
        //if ( selectedDate != null ) {
        //    JOptionPane.showMessageDialog(this, "You selected a date!!! " + selectedDate.toString());  
        //}
        if(getSingleDayRadioButtonSelected() && selectedDate != null) {
            singleDate = selectedDate;
            for( ShiftDate thisShiftDate : shiftDateList){
                if ( thisShiftDate.getDate().equals(selectedDate)){
                    shiftDate = thisShiftDate;
                }
            }
            //shiftDate = new ShiftDate();
            //shiftDate.setDate(selectedDate);
            dayOffRequest = new DayOffRequest();
            dayOffRequest.setEmployee(employee);
            dayOffRequest.setShiftDate(shiftDate);
            dayOffRequest.setWeight(1);
            /* old, use getMaxId() now
            long max = -1l;
            for(DayOffRequest holiday : holidays){
                if( holiday.getId() > max){
                    max = holiday.getId();
                }
            }
            */
            dayOffRequest.setId(dayOffRequest.getMaxId(holidays) + 1);
            //only add day offs for current roster period
            if (shiftDate != null){
                if (getAddRadioButtonSelected()){
                    holidays.add(dayOffRequest);
                    dayOffRequestMap.put(shiftDate, dayOffRequest);
                }
                else //if (getRemoveRadioButtonSelected())
                {
                    DayOffRequest requestToRemove = null;
                    for(DayOffRequest thisDayOffRequest:holidays){
                        if( thisDayOffRequest.getEmployee().equals(employee) &&
                                thisDayOffRequest.getShiftDate().equals(shiftDate)){
                            requestToRemove = thisDayOffRequest;
                        }
                    }
                    if ( requestToRemove != null) {
                            holidays.remove(requestToRemove);
                    }
                    //dayOffRequestMap.remove(shiftDate, dayOffRequest);  //dayOffRequest isnt the same object here
                    dayOffRequestMap.remove(shiftDate);
                    JOptionPane.showMessageDialog(calendarPanel, "DayOffRequest removed for " + employee + " requests left: " + dayOffRequestMap.size() + "total requests for all employees is " + holidays.size());
                }
                
                //JOptionPane.showMessageDialog(calendarPanel, "DayOffRequests for " + employee + " is " + dayOffRequestMap.size());
                //int sizeBefore = dayOffRequestMap.size();
                dayOffRequestMap.put(shiftDate, dayOffRequest);
                //int sizeAfter = dayOffRequestMap.size();
                /* this was more for debug reasons
                if ( sizeAfter >= sizeBefore && dayOffRequestMap.containsKey(shiftDate))
                    JOptionPane.showMessageDialog(calendarPanel, "added DayOffRequests  " + employee + " date " + shiftDate + " is " + dayOffRequestMap.size());
                //employee.setDayOffRequestMap(dayOffRequestMap);  //should not be needed
                if (employee.getDayOffRequestMap().containsKey(shiftDate)){
                    JOptionPane.showMessageDialog(calendarPanel, "added DayOffRequests  " + employee + " date " + shiftDate + " is " + dayOffRequestMap.size());
                }
                else {
                    JOptionPane.showMessageDialog(calendarPanel, "DayOffRequest no longer found??? " + employee + " date " + shiftDate + " is " + dayOffRequestMap.size());
                
                }
                */
                //JOptionPane.showMessageDialog(calendarPanel, "DayOffRequests for " + employee + " is " + dayOffRequestMap.size());
            }
            
            //JOptionPane.showMessageDialog(this, "You addeed a single holiday " + selectedDate.toString() + " with index " + shiftDate.getDayIndex() + " total holidays " + holidays.size());
        }
        else {
            if ( firstDate == null && selectedDate != null ) {
                firstDate = selectedDate;
                JOptionPane.showMessageDialog(this, "You selected the first day " + selectedDate.toString());
            }
            else if ( selectedDate != null ) {
                lastDate = selectedDate;
                JOptionPane.showMessageDialog(this, "You selected the last day " + selectedDate.toString() + " total holidays " + holidays.size());
                if ( firstDate.isAfter(lastDate) ) {
                    temp = firstDate;
                    firstDate = lastDate;
                    lastDate = temp;
                }
                LocalDate thisDate = firstDate;
                dayOffRequest = new DayOffRequest();
                long max = dayOffRequest.getMaxId(holidays);
                //-1l;
                
                dayOffRequestMap = employee.getDayOffRequestMap();
                for ( ShiftDate thisShiftDate : shiftDateList ) {
                    if ( !thisShiftDate.getDate().isBefore(firstDate) && !thisShiftDate.getDate().isAfter(lastDate) ) {
                        dayOffRequest = new DayOffRequest();
                        dayOffRequest.setEmployee(employee);
                        dayOffRequest.setShiftDate(thisShiftDate);
                        //if (dayOffRequest.getShiftDate() == thisShiftDate ){
                        //    JOptionPane.showMessageDialog(null, "Shiftdates for day off equal");
                        //}
                        //else
                        //    JOptionPane.showMessageDialog(null, "Shiftdates for day off do not equal");
                        
                        dayOffRequest.setWeight(1);
                        dayOffRequest.setId(max++ + 1); 
                        if (getAddRadioButtonSelected()){
                            holidays.add(dayOffRequest);
                            //JOptionPane.showMessageDialog(calendarPanel, "DayOffRequests for " + employee + " is " + dayOffRequestMap.size());
                            dayOffRequestMap.put(thisShiftDate, dayOffRequest);
                            //employee.setDayOffRequestMap(dayOffRequestMap);  //not sure if needed and if it will generate new instances
                        }
                        else 
                            //if (getRemoveRadioButtonSelected())
                        {
                            DayOffRequest requestToRemove = null;
                            for(DayOffRequest thisDayOffRequest:holidays){
                                if( thisDayOffRequest.getEmployee().equals(employee) &&
                                    thisDayOffRequest.getShiftDate().equals(thisShiftDate)){
                                    requestToRemove = thisDayOffRequest;
                                }
                            }
                            if ( requestToRemove != null ){
                                holidays.remove(requestToRemove);
                            }
                            //dayOffRequestMap.remove(shiftDate, dayOffRequest);  //dayOffRequest isnt the same object here
                            dayOffRequestMap.remove(thisShiftDate);
                            JOptionPane.showMessageDialog(calendarPanel, "DayOffRequest removed for " + employee + " requests left: " + dayOffRequestMap.size() + "total requests for all employees is " + holidays.size());
                            /*
                            holidays.remove(dayOffRequest);
                            //if(dayOffRequestMap.containsKey(thisShiftDate))
                            dayOffRequestMap.remove(thisShiftDate, dayOffRequest);
                            JOptionPane.showMessageDialog(calendarPanel, "DayOffRequests removed for " + employee + " requests left : " + dayOffRequestMap.size());
                            */
                        }
                    }
                }
                /* old but bad creating a different shiftday
                while ( !thisDate.isAfter(lastDate )){
                    shiftDate = new ShiftDate();  //use actual shifTdate from shiftDate list or we can not find the key in the mapping later
                    shiftDate.setDate(thisDate);
                    dayOffRequest = new DayOffRequest();
                    dayOffRequest.setEmployee(employee);
                    dayOffRequest.setShiftDate(shiftDate);
                    dayOffRequest.setWeight(1);
                    dayOffRequest.setId(max++ + 1);  //max++ + 1 or just max++???
                    holidays.add(dayOffRequest);
                    dayOffRequestMap = employee.getDayOffRequestMap();
                    //JOptionPane.showMessageDialog(calendarPanel, "DayOffRequests for " + employee + " is " + dayOffRequestMap.size());
                    dayOffRequestMap.put(shiftDate, dayOffRequest);
                    employee.setDayOffRequestMap(dayOffRequestMap);
                    //JOptionPane.showMessageDialog(calendarPanel, "DayOffRequests for " + employee + " is " + dayOffRequestMap.size());
                    thisDate = thisDate.plusDays(1);
                }
                */

            }
        }
        
        //clear current selection if necessary
        if ( lastDate != null ){
            firstDate = null;
            lastDate = null;
        }
        calendarPanel.repaint();
       
    }
        
    
    @Override
    public void handleSingleDayRadioButtonSelected(){
        //this.jSingleDayRadioButton.setSelected(true);
        //this.jPeriodRadioButton.setSelected(false);
        setSingleDayRadioButtonSelected();
    }
    
    @Override
    public void handlePeriodRadioButtonSelected(){
        //this.jSingleDayRadioButton.setSelected(true);
        //this.jPeriodRadioButton.setSelected(false);
        setPeriodRadioButtonSelected();
    }
    
    @Override
    public void handleAddRadioButtonSelected(){
        //this.jSingleDayRadioButton.setSelected(true);
        //this.jPeriodRadioButton.setSelected(false);
        setAddRadioButtonSelected();
    }
    
    @Override
    public void handleRemoveRadioButtonSelected(){
        //this.jSingleDayRadioButton.setSelected(true);
        //this.jPeriodRadioButton.setSelected(false);
        setRemoveRadioButtonSelected();
    }
    
    
    
    /*
    
    code from LGoodDatePicker example
    
    private static class SampleDateVetoPolicy implements DateVetoPolicy {

        //
        // isDateAllowed, Return true if a date should be allowed, or false if a date should be
        // vetoed.
        //
        @Override
        public boolean isDateAllowed(LocalDate date) {
            // Disallow days 7 to 11.
            if ((date.getDayOfMonth() >= 7) && (date.getDayOfMonth() <= 11)) {
                return false;
            }
            // Disallow odd numbered saturdays.
            if ((date.getDayOfWeek() == DayOfWeek.SATURDAY) && ((date.getDayOfMonth() % 2) == 1)) {
                return false;
            }
            // Allow all other days.
            return true;
        }
    }
    */



    

    
}
