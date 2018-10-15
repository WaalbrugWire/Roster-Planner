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
        DayOffCalendarListener listener = new DayOffCalendarListener();
        calendarPanel.addCalendarListener(listener);
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
        
        DatePickerSettings dateSettings = calendarPanel.getSettings();
        LocalDate selectedDate = calendarPanel.getSelectedDate();
        
        if(getSingleDayRadioButtonSelected() && selectedDate != null) {
            singleDate = selectedDate;
            for( ShiftDate thisShiftDate : shiftDateList){
                if ( thisShiftDate.getDate().equals(selectedDate)){
                    shiftDate = thisShiftDate;
                }
            }
            dayOffRequest = new DayOffRequest();
            dayOffRequest.setEmployee(employee);
            dayOffRequest.setShiftDate(shiftDate);
            dayOffRequest.setWeight(1);
            
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
                    
                    dayOffRequestMap.remove(shiftDate);
                    System.out.println("DayOffRequest removed for " + employee + " requests left: " + dayOffRequestMap.size() + "total requests for all employees is " + holidays.size());
                    //JOptionPane.showMessageDialog(calendarPanel, "DayOffRequest removed for " + employee + " requests left: " + dayOffRequestMap.size() + "total requests for all employees is " + holidays.size());
                }
                dayOffRequestMap.put(shiftDate, dayOffRequest);
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
                        dayOffRequest.setWeight(1);
                        dayOffRequest.setId(max++ + 1); 
                        if (getAddRadioButtonSelected()){
                            holidays.add(dayOffRequest);
                            dayOffRequestMap.put(thisShiftDate, dayOffRequest);
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
                            dayOffRequestMap.remove(thisShiftDate);
                            System.out.println("DayOffRequest removed for " + employee + " requests left: " + dayOffRequestMap.size() + "total requests for all employees is " + holidays.size());
                            //JOptionPane.showMessageDialog(calendarPanel, "DayOffRequest removed for " + employee + " requests left: " + dayOffRequestMap.size() + "total requests for all employees is " + holidays.size());
                            
                        }
                    }
                }
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
        setSingleDayRadioButtonSelected();
    }
    
    @Override
    public void handlePeriodRadioButtonSelected(){
        setPeriodRadioButtonSelected();
    }
    
    @Override
    public void handleAddRadioButtonSelected(){
        setAddRadioButtonSelected();
    }
    
    @Override
    public void handleRemoveRadioButtonSelected(){
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
