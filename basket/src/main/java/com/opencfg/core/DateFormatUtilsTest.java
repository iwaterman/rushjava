/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.opencfg.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Before;
import org.junit.Test;

/**
 * DateFormatUtils JUnit4 Test
 * 
 * @author Opencfg Software Foundation
 * @since 0.0.1-SNAPSHOT
 * @version $Id: DateFormatUtilsTest.java 2011-06-15 00:36:53 reymondtu $
 */
public class DateFormatUtilsTest {
    private final int TEST_SIZE = 1000000;
    private Date[] dateArray = new Date[TEST_SIZE];
    private String[] stringArray = new String[TEST_SIZE];

    @Before
    public void setUp() {
	for (int i = 0; i < TEST_SIZE; i++) {
	    dateArray[i] = new Date();
	}

	for (int i = 0; i < TEST_SIZE; i++) {
	    stringArray[i] = DateFormatUtils.safeFormatDate("yyyy-MM-dd HH:mm:ss", new Date());
	}
    }

    /* Format */

    @Test
    public void test_JDK_SimpleDateFormat_format() {
	long starttime = System.currentTimeMillis();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	for (Date date : dateArray) {
	    sdf.format(date);
	}
	long endtime = System.currentTimeMillis();
	System.out.println("JDK     SimpleDateFormat format cost:" + (endtime - starttime) + "ms");
    }

    @Test
    public void test_Opencfg_DateFormatUtils_format() {
	long starttime = System.currentTimeMillis();
	for (Date date : dateArray) {
	    DateFormatUtils.safeFormatDate("yyyy-MM-dd HH:mm:ss", date);
	}
	long endtime = System.currentTimeMillis();
	System.out.println("Opencfg DateFormatUtils  format cost:" + (endtime - starttime) + "ms");
    }

    @Test
    public void test_Apache_FastDateFormat_format() {
	FastDateFormat fastDateFormat = FastDateFormat.getInstance();
	long starttime = System.currentTimeMillis();
	for (Date date : dateArray) {
	    fastDateFormat.format(date);
	}
	long endtime = System.currentTimeMillis();
	System.out.println("Apache  FastDateFormat   format cost:" + (endtime - starttime) + "ms");
    }

    /* Parse */

    @Test
    public void test_JDK_SimpleDateFormat_parse() throws ParseException {
	long starttime = System.currentTimeMillis();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	for (String str : stringArray) {
	    sdf.parse(str);
	}
	long endtime = System.currentTimeMillis();
	System.out.println("JDK     SimpleDateFormat parse  cost:" + (endtime - starttime) + "ms");
    }

    @Test
    public void test_Opencfg_DateFormatUtils_parse() throws ParseException {
	long starttime = System.currentTimeMillis();
	for (String str : stringArray) {
	    DateFormatUtils.safeParseDate("yyyy-MM-dd HH:mm:ss", str);
	}
	long endtime = System.currentTimeMillis();
	System.out.println("Opencfg DateFormatUtils  parse  cost:" + (endtime - starttime) + "ms");
    }

    @Test
    public void test_Apache_DateUtils_parse() throws ParseException {
	long starttime = System.currentTimeMillis();
	String[] dateParten = new String[] { "yyyy-MM-dd HH:mm:ss" };
	for (String str : stringArray) {
	    org.apache.commons.lang.time.DateUtils.parseDate(str, dateParten);
	}
	long endtime = System.currentTimeMillis();
	System.out.println("Apache  DateUtils        parse  cost:" + (endtime - starttime) + "ms");
    }

}
