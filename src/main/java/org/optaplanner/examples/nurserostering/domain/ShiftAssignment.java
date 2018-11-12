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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.examples.nurserostering.domain.contract.Contract;
import org.optaplanner.examples.nurserostering.domain.solver.EmployeeStrengthComparator;
import org.optaplanner.examples.nurserostering.domain.solver.MovableShiftAssignmentSelectionFilter;
import org.optaplanner.examples.nurserostering.domain.solver.ShiftAssignmentDifficultyComparator;

@PlanningEntity(movableEntitySelectionFilter = MovableShiftAssignmentSelectionFilter.class,
		difficultyComparatorClass = ShiftAssignmentDifficultyComparator.class)

@XStreamAlias("ShiftAssignment")
public class ShiftAssignment extends AbstractPersistable
{
	// Planning variables: changes during planning, between score calculations.
	@PlanningVariable(valueRangeProviderRefs = 	{"employeeRange" },
		strengthComparatorClass = EmployeeStrengthComparator.class)
	
	private Shift shift;
	private int indexInShift;
	private ArrayList<LocalDate> hollydays;
	
	private Employee employee;

	public Shift getShift()
	{
		return shift;
	}

	public void setShift(Shift shift)
	{
		this.shift = shift;
	}

	public int getIndexInShift()
	{
		return indexInShift;
	}

	public void setIndexInShift(int indexInShift)
	{
		this.indexInShift = indexInShift;
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}

	// ************************************************************************
	// Complex methods
	// ************************************************************************
	public ShiftDate getShiftDate()
	{
		return shift.getShiftDate();
	}

	public ShiftType getShiftType()
	{
		return shift.getShiftType();
	}

	public int getShiftDateDayIndex()
	{
		return shift.getShiftDate().getDayIndex();
	}

	public DayOfWeek getShiftDateDayOfWeek()
	{
		return shift.getShiftDate().getDayOfWeek();
	}

	public Contract getContract()
	{
		if (employee == null)
		{
			return null;
		}
		return employee.getContract();
	}

	public boolean isWeekend()
	{
		if (employee == null)
			return false;
		
		// TODO: add hollyday calculation less hacky
		calculateHollydays(shift.getShiftDate().getDate());
		if (hollydays.contains(shift.getShiftDate().getDate()))
			return true;
		
		WeekendDefinition weekendDefinition = employee.getContract().getWeekendDefinition();
		DayOfWeek dayOfWeek = shift.getShiftDate().getDayOfWeek();
		
		return weekendDefinition.isWeekend(dayOfWeek);
	}

	public int getWeekendSundayIndex()
	{
		return shift.getShiftDate().getWeekendSundayIndex();
	}

	@Override
	public String toString()
	{
		return shift.toString();
	}

	private void calculateHollydays(LocalDate _date)
	{
		hollydays = new ArrayList<>();
		LocalDate easter = calculateEaster(_date.getYear());
				
		// nieuw jaar
		hollydays.add(LocalDate.of(_date.getYear(), 01, 01));

		// goede vrijdag
		hollydays.add(easter.minusDays(2));

		// paasen
		hollydays.add(easter);
		hollydays.add(easter.plusDays(1));

		// Koningsdag
		hollydays.add(LocalDate.of(_date.getYear(), 04, 27));

		// bevrijdingsdag
		hollydays.add(LocalDate.of(_date.getYear(), 05, 05));

		// hemelvaart
		hollydays.add(easter.plusDays(39));

		// pinksteren
		hollydays.add(easter.plusDays(50));
		hollydays.add(easter.plusDays(51));

		// kerst	
		hollydays.add(LocalDate.of(_date.getYear(), 12, 25));
		hollydays.add(LocalDate.of(_date.getYear(), 12, 26));
	}
	
	private LocalDate calculateEaster(int _year)
	{
		int day = 0;
		int month = 0;
		int golden = 0; // g is the position within the 19 year lunar cycle; known as the golden number
		int century = 0; // the current century
		int lunar = 0; //  days between the equinox and the next full moon
		int i = 0; //  days between the full moon after the equinox and the first sunday after that full moon

		golden = _year % 19;
		century = _year / 100;
		lunar = (century - (int)(century / 4) - (int)((8 * century + 13) / 25) + 19 * golden + 15) % 30;
		i = lunar - (int)(lunar / 28) * (1 - (int)(lunar / 28) * (int)(29 / (lunar + 1)) * (int)((21 - golden) / 11));

		day = i - ((_year + (int)(_year / 4) + i + 2 - century + (int)(century / 4)) % 7) + 28;
		month = 3;

		if (day > 31)
		{
			month++;
			day -= 31;
		}
		
		LocalDate date = LocalDate.of(_year, month, day);
		return date;
	}
}
