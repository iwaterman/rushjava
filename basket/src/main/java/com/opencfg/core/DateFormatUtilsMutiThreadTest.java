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
 * @version $Id: DateFormatUtilsMutiThreadTest.java 2011-07-18 23:06:53
 *          reymondtu $
 */
public class DateFormatUtilsMutiThreadTest {

    private final int TEST_SIZE = 1000000;
    private final int TEST_THREAD_COUNT = 10;
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
    public void test_Apache_FastDateFormat_format() throws InterruptedException {
	final FastDateFormat fastDateFormat = FastDateFormat.getInstance();
	long starttime = System.currentTimeMillis();
	int avr = TEST_SIZE / TEST_THREAD_COUNT;
	for (int i = 0; i < TEST_THREAD_COUNT; i++) {
	    final int start = i * avr;
	    final int end = (i + 1) * avr;
	    Thread t = new Thread(new Runnable() {
		@Override
		public void run() {
		    for (int j = start; j < end; j++) {
			fastDateFormat.format(dateArray[j]);
		    }
		}
	    });
	    t.start();
	    t.join();
	}
	long endtime = System.currentTimeMillis();
	System.out.println("Apache  Muti-Thread FastDateFormat   format cost:" + (endtime - starttime) + "ms");
    }

    @Test
    public void test_JDK_SimpleDateFormat_format() throws InterruptedException {
	long starttime = System.currentTimeMillis();
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	int avr = TEST_SIZE / TEST_THREAD_COUNT;
	for (int i = 0; i < TEST_THREAD_COUNT; i++) {
	    final int start = i * avr;
	    final int end = (i + 1) * avr;
	    Thread t = new Thread(new Runnable() {
		@Override
		public void run() {
		    for (int j = start; j < end; j++) {
			sdf.format(dateArray[j]);
		    }
		}
	    });
	    t.start();
	    t.join();
	}
	long endtime = System.currentTimeMillis();
	System.out.println("JDK     Muti-Thread SimpleDateFormat format cost:" + (endtime - starttime) + "ms");
    }

    @Test
    public void test_Opencfg_DateFormatUtils_format() throws InterruptedException {
	long starttime = System.currentTimeMillis();
	int avr = TEST_SIZE / TEST_THREAD_COUNT;
	for (int i = 0; i < TEST_THREAD_COUNT; i++) {
	    final int start = i * avr;
	    final int end = (i + 1) * avr;
	    Thread t = new Thread(new Runnable() {
		@Override
		public void run() {
		    for (int j = start; j < end; j++) {
			DateFormatUtils.safeFormatDate("yyyy-MM-dd HH:mm:ss", dateArray[j]);
		    }
		}
	    });
	    t.start();
	    t.join();
	}
	long endtime = System.currentTimeMillis();
	System.out.println("Opencfg Muti-Thread DateFormatUtils  format cost:" + (endtime - starttime) + "ms");
    }

    /* Parse */

    @Test
    public void test_Apache_DateUtils_parse() throws ParseException, InterruptedException {
	long starttime = System.currentTimeMillis();
	final String[] dateParten = new String[] { "yyyy-MM-dd HH:mm:ss" };
	int avr = TEST_SIZE / TEST_THREAD_COUNT;
	for (int i = 0; i < TEST_THREAD_COUNT; i++) {
	    final int start = i * avr;
	    final int end = (i + 1) * avr;
	    Thread t = new Thread(new Runnable() {
		@Override
		public void run() {
		    for (int j = start; j < end; j++) {
			try {
			    org.apache.commons.lang.time.DateUtils.parseDate(stringArray[j], dateParten);
			} catch (ParseException e) {
			    e.printStackTrace();
			}
		    }
		}
	    });
	    t.start();
	    t.join();
	}
	long endtime = System.currentTimeMillis();
	System.out.println("Apache  Muti-Thread DateUtils        parse  cost:" + (endtime - starttime) + "ms");
    }

    @Test
    public void test_JDK_SimpleDateFormat_parse() throws ParseException, InterruptedException {
	long starttime = System.currentTimeMillis();
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	int avr = TEST_SIZE / TEST_THREAD_COUNT;
	for (int i = 0; i < TEST_THREAD_COUNT; i++) {
	    final int start = i * avr;
	    final int end = (i + 1) * avr;
	    Thread t = new Thread(new Runnable() {
		@Override
		public void run() {
		    for (int j = start; j < end; j++) {
			try {
			    sdf.parse(stringArray[j]);
			} catch (ParseException e) {
			    e.printStackTrace();
			}
		    }
		}
	    });
	    t.start();
	    t.join();
	}
	long endtime = System.currentTimeMillis();
	System.out.println("JDK     Muti-Thread SimpleDateFormat parse  cost:" + (endtime - starttime) + "ms");
    }

    @Test
    public void test_Opencfg_DateFormatUtils_parse() throws ParseException, InterruptedException {
	long starttime = System.currentTimeMillis();
	int avr = TEST_SIZE / TEST_THREAD_COUNT;
	for (int i = 0; i < TEST_THREAD_COUNT; i++) {
	    final int start = i * avr;
	    final int end = (i + 1) * avr;
	    Thread t = new Thread(new Runnable() {
		@Override
		public void run() {
		    for (int j = start; j < end; j++) {
			try {
			    DateFormatUtils.safeParseDate("yyyy-MM-dd HH:mm:ss", stringArray[j]);
			} catch (ParseException e) {
			    e.printStackTrace();
			}
		    }
		}
	    });
	    t.start();
	    t.join();
	}
	long endtime = System.currentTimeMillis();
	System.out.println("Opencfg Muti-Thread DateFormatUtils  parse  cost:" + (endtime - starttime) + "ms");
    }

}
