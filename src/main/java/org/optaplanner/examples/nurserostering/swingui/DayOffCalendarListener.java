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

import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Martien
 */


public class DayOffCalendarListener implements CalendarListener{
    
    @Override
    public void selectedDateChanged(CalendarSelectionEvent evt){
        //JOptionPane.showMessageDialog(null, "Listener detected you picked another date" + "\n\n" + "From : " + evt.getOldDate() + "\n\n" + "To : " + evt.getNewDate() );
        // need different handle here  mouse clicked wont do  handleCalendarPanelMouseClicked(evt.getSource().g);
    }
    
    @Override
    public void yearMonthChanged(YearMonthChangeEvent evt){
        //JOptionPane.showMessageDialog(null, "Listener detected you picked another month" + "\n\n" + "From : " + evt.getOldYearMonth() + "\n\n" + "To : " + evt.getNewYearMonth() );
    }
}
