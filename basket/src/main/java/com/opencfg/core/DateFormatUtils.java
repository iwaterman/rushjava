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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.FastHashMap;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * DateFormat Utils
 * <p>
 * 
 * <p>
 * #ThreadSafe#
 * </p>
 * 
 * @author Opencfg Software Foundation
 * @since 0.0.1-SNAPSHOT
 * @version $Id: DateFormatUtils.java 2011-06-14 23:21:53 reymondtu $
 */
public class DateFormatUtils {

    private static final FastHashMap datePartenMap = new FastHashMap();

    static {
	// load data parten properties files
	List<String> propList = PropertiesUtils.getSimpleList("DateFormatUtils.properties");
	datePartenMap.setFast(true);
	for (String dateParten : propList) {
	    if (StringUtils.startsWith(dateParten, "#"))
		continue;
	    datePartenMap.put(StringUtils.trim(dateParten), new SimpleDateFormat(dateParten));
	}
    }

    /**
     * String -> Date
     * 
     * @param dateParten
     *            Parse Date Parten
     * @param dateStr
     *            Parse Date String
     * @throws ParseException
     */
    public static Date safeParseDate(final String dateParten, final String dateStr) throws ParseException {
	return getFormat(dateParten).parse(dateStr);
    }

    /**
     * Date -> String
     * 
     * @param dateParten
     *            Format Date Parten
     * @param date
     *            Format java.util.Date Object
     */
    public static String safeFormatDate(final String dateParten, final Date date) {
	return getFormat(dateParten).format(date);
    }

    private static ThreadLocal<FastHashMap> threadLocal = new ThreadLocal<FastHashMap>() {
	protected synchronized FastHashMap initialValue() {
	    return (FastHashMap) datePartenMap.clone();
	}
    };

    private static DateFormat getFormat(final String dateParten) {
	return (DateFormat) threadLocal.get().get(dateParten);
    }

}
