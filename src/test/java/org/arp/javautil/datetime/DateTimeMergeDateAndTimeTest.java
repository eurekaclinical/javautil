/*
 * #%L
 * JavaUtil
 * %%
 * Copyright (C) 2012 - 2013 Emory University
 * %%
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
 * #L%
 */
package org.arp.javautil.datetime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author Andrew Post
 */
public class DateTimeMergeDateAndTimeTest {

    private java.sql.Date date;
    private java.sql.Time time;

    @Before
    public void setUp() {
        Calendar cal = Calendar.getInstance();

        cal.clear();
        cal.set(Calendar.YEAR, 2004);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 16);

        date = new java.sql.Date(cal.getTimeInMillis());

        cal.clear();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 20);
        cal.set(Calendar.SECOND, 22);

        time = new java.sql.Time(cal.getTimeInMillis());
    }

    @After
    public void tearDown() {
        date = null;
        time = null;
    }

    @Test
    public void testMergeDateAndTimeYear() {
        Calendar cal = Calendar.getInstance();
        Date result = DateTime.mergeDateAndTime(date, time);
        cal.setTime(result);
        assertEquals(2004, cal.get(Calendar.YEAR));
    }

    @Test
    public void testMergeDateAndTimeMonth() {
        Calendar cal = Calendar.getInstance();
        Date result = DateTime.mergeDateAndTime(date, time);
        cal.setTime(result);
        assertEquals(Calendar.DECEMBER, cal.get(Calendar.MONTH));
    }

    @Test
    public void testMergeDateAndTimeDate() {
        Calendar cal = Calendar.getInstance();
        Date result = DateTime.mergeDateAndTime(date, time);
        cal.setTime(result);
        assertEquals(16, cal.get(Calendar.DATE));
    }

    @Test
    public void testMergeDateAndTimeHour() {
        Calendar cal = Calendar.getInstance();
        Date result = DateTime.mergeDateAndTime(date, time);
        cal.setTime(result);
        assertEquals(0, cal.get(Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testMergeDateAndTimeMinute() {
        Calendar cal = Calendar.getInstance();
        Date result = DateTime.mergeDateAndTime(date, time);
        cal.setTime(result);
        assertEquals(20, cal.get(Calendar.MINUTE));
    }

    @Test
    public void testMergeDateAndTimeSecond() {
        Calendar cal = Calendar.getInstance();
        Date result = DateTime.mergeDateAndTime(date, time);
        cal.setTime(result);
        assertEquals(22, cal.get(Calendar.SECOND));
    }
}
