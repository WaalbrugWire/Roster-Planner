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

package org.optaplanner.examples.nurserostering.domain;

import java.time.LocalDate;
import java.util.ArrayList;

/**

 @author Roel
 */
public class CalculateHolidays
{
    public CalculateHolidays()
    {
        
    }
    
    public ArrayList<LocalDate> calculateHolidays(ArrayList<LocalDate> _holiday, int _year)
    {
        LocalDate easter = calculateEaster(_year);

        // nieuw jaar
        _holiday.add(LocalDate.of(_year, 01, 01));

        // goede vrijdag
        _holiday.add(easter.minusDays(2));

        // paasen
        _holiday.add(easter);
        _holiday.add(easter.plusDays(1));

        // Koningsdag
        _holiday.add(LocalDate.of(_year, 04, 27));

        // bevrijdingsdag
        _holiday.add(LocalDate.of(_year, 05, 05));

        // hemelvaart
        _holiday.add(easter.plusDays(39));

        // pinksteren
        _holiday.add(easter.plusDays(49));
        _holiday.add(easter.plusDays(50));

        // kerst
        _holiday.add(LocalDate.of(_year, 12, 25));
        _holiday.add(LocalDate.of(_year, 12, 26));
        
        return _holiday;
    }
    
    private LocalDate calculateEaster(int _year)
    {
        int day;
        int month;
        int golden; // g is the position within the 19 year lunar cycle; known as the golden number
        int century; // the current century
        int lunar; //  days between the equinox and the next full moon
        int i; //  days between the full moon after the equinox and the first sunday after that full moon

        golden = _year % 19;
        century = _year / 100;
        lunar = (century - (int) (century / 4) - (int) ((8 * century + 13) / 25) + 19 * golden + 15) % 30;
        i = lunar - (int) (lunar / 28) * (1 - (int) (lunar / 28) * (int) (29 / (lunar + 1)) * (int) ((21 - golden) / 11));
        
        day = i - ((_year + (int) (_year / 4) + i + 2 - century + (int) (century / 4)) % 7) + 28;
        month = 3;
        
        if (day > 31)
        {
            month++;
            day -= 31;
        }
        
        return LocalDate.of(_year, month, day);
    }
}
