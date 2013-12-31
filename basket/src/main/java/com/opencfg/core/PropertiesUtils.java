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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Properties Utils
 * <p>
 * 
 * <p>
 * #ThreadSafe#
 * </p>
 * 
 * @author Opencfg Software Foundation
 * @since 0.0.1-SNAPSHOT
 * @version $Id: PropertiesUtils.java 2011-06-15 01:22:53 reymondtu $
 */
public class PropertiesUtils {

    /**
     * Load Properties File
     * 
     * @throws ConfigurationException
     */
    public static PropertiesConfiguration loadClassPathProperties(final String fileName, final long refreshInterval)
	    throws ConfigurationException {
	URL url = PropertiesUtils.class.getClassLoader().getResource(fileName);
	File f = new File(url.getFile());
	if (!f.exists()) {
	    throw new IllegalArgumentException("can not load file " + fileName);
	}
	PropertiesConfiguration prop = new PropertiesConfiguration(url.getFile());
	FileChangedReloadingStrategy fcrs = null;
	fcrs = new FileChangedReloadingStrategy();
	fcrs.setRefreshDelay(refreshInterval);
	prop.setReloadingStrategy(fcrs);
	return prop;
    }

    public static List<String> getSimpleList(final String fileName) {
	try {
	    PropertiesConfiguration prop = loadClassPathProperties(fileName, 500);
	    BufferedReader reader = new BufferedReader(new FileReader(prop.getFile()));
	    List<String> list = new ArrayList<String>();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		if (StringUtils.startsWith(line, "#")) {
		    continue;
		}
		list.add(line);
		line = reader.readLine();
	    }
	    return list;
	} catch (Exception e) {
	    throw new IllegalArgumentException("can not load file " + fileName);
	}
    }

}
