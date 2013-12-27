package org.xman.jmx.mxbean;

public class XSystemConfig  implements XSystemConfigMXBean{
    private Host host;
    
    private int threadCount;
    private String schemaName;

    public XSystemConfig(int numThreads, String schema) {
	this.threadCount = numThreads;
	this.schemaName = schema;
    }

    public void setThreadCount(int noOfThreads) {
	this.threadCount = noOfThreads;
    }

    public int getThreadCount() {
	return this.threadCount;
    }

    public void setSchemaName(String schemaName) {
	this.schemaName = schemaName;
    }

    public String getSchemaName() {
	return this.schemaName;
    }

    public Host getHost() {
	return host;
    }
    
    public void setHost(Host host) {
	this.host = host;
    }

    
    @Override
    public String doConfig() {
	return "threadCount=" + this.threadCount 
		+ " and schemaName=" + this.schemaName
		+ " and hostname=" + this.getHost().getName()
		;
    }
    


}
