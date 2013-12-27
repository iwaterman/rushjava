package org.xman.jmx.mxbean;

public interface XSystemConfigMXBean {
    
    public void setThreadCount(int noOfThreads);
    public int getThreadCount();

    public void setSchemaName(String schemaName);
    public String getSchemaName();

    public String doConfig();
    
    // user datatype
    public Host getHost();
    
    public void setHost(Host host);
}
