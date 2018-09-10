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

/*
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import com.github.lgooddatepicker.zinternaltools.WrapLayout;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.optionalusertools.CalendarBorderProperties;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import com.privatejgoodies.forms.factories.CC;
import javax.swing.border.LineBorder;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
*/

import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import java.awt.Color;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.optaplanner.examples.nurserostering.domain.Employee;
import org.optaplanner.examples.nurserostering.domain.request.DayOffRequest;

/**
 *
 * @author Martien
 * 
 * implementing functions for HighLighting Calendar for LGoodDatePicker
 * 
 */
public class DayOffOnHighLightPolicy implements DateHighlightPolicy {
    /**
     * SampleHighlightPolicy, A highlight policy is a way to visually highlight certain dates in the
     * calendar. These may be holidays, or weekends, or other significant dates.
     */
    
        /**
         * getHighlightInformationOrNull, Implement this function to indicate if a date should be
         * highlighted, and what highlighting details should be used for the highlighted date.
         *
         * If a date should be highlighted, then return an instance of HighlightInformation. If the
         * date should not be highlighted, then return null.
         *
         * You may (optionally) fill out the fields in the HighlightInformation class to give any
         * particular highlighted day a unique foreground color, background color, or tooltip text.
         * If the color fields are null, then the default highlighting colors will be used. If the
         * tooltip field is null (or empty), then no tooltip will be displayed.
         *
         * Dates that are passed to this function will never be null.
         */
    
    Employee employee;
    List<DayOffRequest> holidays;
    
    public DayOffOnHighLightPolicy(Employee employee, List<DayOffRequest> holidays){
        //DayOffOnHighLightPolicy newPolicy = new DayOffOnHighLightPolicy(); //??????
        this.employee = employee;
        this.holidays = holidays;
    }
    
    @Override
    public HighlightInformation getHighlightInformationOrNull(LocalDate date) {
        // Highlight a chosen date, with a tooltip and a red background color.
        //if (date.getDayOfMonth() == 25) {
        //    return new HighlightInformation(Color.red, null, "It's the 25th!");
        //}
        // Highlight all Saturdays with a unique background and foreground color.
        //if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
        //    return new HighlightInformation(Color.orange, Color.yellow, "It's Saturday!");
        //}
        // Highlight all Sundays with default colors and a tooltip.
        //if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
        //    return new HighlightInformation(null, null, "It's Sunday!");
        //}
        // All other days should not be highlighted.
        //return null;
        
        for( DayOffRequest holiday : holidays ){
            if ( holiday.getEmployee().equals(employee) && holiday.getShiftDate().getDate().equals(date) ){
                return new HighlightInformation(Color.green, null, "Holiday");
            }
            
        }
        return null;
    }
}
    
