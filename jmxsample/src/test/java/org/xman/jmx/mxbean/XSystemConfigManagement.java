package org.xman.jmx.mxbean;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class XSystemConfigManagement {
    private static final int DEFAULT_NO_THREADS = 10;
    private static final String DEFAULT_SCHEMA = "default";

    public static void main(String[] args) throws Exception {

	// Get the MBean server
	MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
	
	Host host = new Host();
	host.setIp("192.168.1.1");
	host.setName("xman");
	
	// register the MBean
	XSystemConfig mBean = new XSystemConfig(DEFAULT_NO_THREADS, DEFAULT_SCHEMA);
	mBean.setHost(host);
	
	ObjectName name = new ObjectName("xman:type=XSystemConfig");
	mbs.registerMBean(mBean, name);
	do {
	    Thread.sleep(3000);
	    System.out.println("Thread Count=" + mBean.getThreadCount() + ":::Schema Name=" + mBean.getSchemaName());
	} while (mBean.getThreadCount() != 0);
    }

}