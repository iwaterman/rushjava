package org.xman.jmx.mbean;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.xman.jmx.mbean.SystemConfigMBean;

public class Client {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:8888/jmxrmi");
	JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
	
	MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
	
	ObjectName mbeanName = new ObjectName("xman:type=SystemConfig");
	
	SystemConfigMBean mbeanProxy = JMX.newMBeanProxy(mbsc, mbeanName, SystemConfigMBean.class, true);
	int threadCount = mbeanProxy.getThreadCount();
	System.out.println("Current ThreadCount: " + threadCount);
	mbeanProxy.setThreadCount(100);
	threadCount = mbeanProxy.getThreadCount();
	System.out.println("Changed ThreadCount: " + threadCount);
	
	Thread.sleep(3);
	jmxc.close();
    }

}
