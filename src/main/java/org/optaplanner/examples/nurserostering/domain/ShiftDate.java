/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
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

package org.optaplanner.examples.nurserostering.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.examples.common.domain.AbstractPersistable;

//extra imports for waalbrugwire 19-2-2018
import org.jdom.Element;
//end of extra imports

@XStreamAlias("ShiftDate")
public class ShiftDate extends AbstractPersistable {

    private static final DateTimeFormatter LABEL_FORMATTER = DateTimeFormatter.ofPattern("E d MMM");
    private static final DateTimeFormatter LABEL_LONG_FORMATTER = DateTimeFormatter.ofPattern("E d MMM YYYY");  //WaalbrugWire, 5-5-2018, added year (YYYY) to format for label, but LABEL_FORMATTER is used in heading as well

    //setting dayIndex to 0 as it gives problems later when we change the daterange WaalbrugWire, 5-5-2018
    private int dayIndex = 0; // TODO check if still needed/faster now that we use LocalDate instead of java.util.Date
    private LocalDate date;

    private List<Shift> shiftList;

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
        this.dayIndex = 0; //first step in disabling dayIndex , WaalbrugWire, 5-5-2018
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DayOfWeek getDayOfWeek() {
        return date.getDayOfWeek();
    }

    public List<Shift> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    public int getWeekendSundayIndex() {
        switch (date.getDayOfWeek()) {
            case MONDAY:
                return dayIndex - 1;
            case TUESDAY:
                return dayIndex - 2;
            case WEDNESDAY:
                return dayIndex - 3;
            case THURSDAY:
                return dayIndex + 3;
            case FRIDAY:
                return dayIndex + 2;
            case SATURDAY:
                return dayIndex + 1;
            case SUNDAY:
                return dayIndex;
            default:
                throw new IllegalArgumentException("The dayOfWeek (" + date.getDayOfWeek() + ") is not valid.");
        }
    }

    public String getLabel() {
        return date.format(LABEL_FORMATTER);
    }
    
    //added label function to return year as well, WaalbrugWire, 5-5-2018
    public String getLongLabel() {
        return date.format(LABEL_LONG_FORMATTER);
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ISO_DATE);
    }
    
    //helper function shiftDateToElement to help build a new NurseRoster, waalbrugwire, 19-2-2018
    public Element shiftDateToElement() {
        Element element = new Element("date");
        element.setText(this.getDate().toString());
        return element;
    }

}
