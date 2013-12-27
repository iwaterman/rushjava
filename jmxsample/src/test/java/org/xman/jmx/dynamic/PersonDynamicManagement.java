package org.xman.jmx.dynamic;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * 启动jmx管理服务器
 * 
 */
public class PersonDynamicManagement {
    static final String DOMAIN = "MyMBeanDynamic";

    public static void main(String[] args) {
	try {
	    // 创建一个MBean服务对象，DOMAIN类似于java里面的公共package部分
	    // MBeanServer server = MBeanServerFactory.createMBeanServer(DOMAIN);
	    
	    MBeanServer server = ManagementFactory.getPlatformMBeanServer();
	    
	    // 创建DynamicMBean对象
	    PersonDynamic personDynamic = new PersonDynamic();
	    
	    // 创建一个web服务器，表示我们MBean服务通过web形式来提供给用户管理
	    HtmlAdaptorServer htmlServer = new HtmlAdaptorServer();
	    htmlServer.setPort(8080);// 设置web端口为8080
	    
	    // ObjctName对象类似于完整的package
	    ObjectName personObjName = new ObjectName(DOMAIN + ":name=PersonDynamic");
	    ObjectName htmlObjName = new ObjectName(DOMAIN + ":name=HtmlAdaptor");
	   
	    // 将需要被管理的MBean注册到服务里面去
	    server.registerMBean(personDynamic, personObjName);
	    // 将web服务器注册到服务里面去
	    server.registerMBean(htmlServer, htmlObjName);
	    
	    // 启动web服务器
	    htmlServer.start();
	    System.out.println("正在启动HtmlAdaptor..");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
