package org.xman.http.pool;

import org.apache.http.HttpVersion;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class Main {

	
	public static DefaultHttpClient getHttpClient()
	{  		
	    // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9 
		HttpParams params = new BasicHttpParams(); 
	    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1); 
	    HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1"); 
	    HttpProtocolParams.setUseExpectContinue(params, true); 	  

	    //设置连接超时时间 
	    int REQUEST_TIMEOUT = 10*1000;	//设置请求超时10秒钟 
		int SO_TIMEOUT = 10*1000; 		//设置等待数据超时时间10秒钟 
		//HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
		//HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
	    params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);  
	    params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT); 
	  
		//设置访问协议 
		SchemeRegistry schreg = new SchemeRegistry();  
		schreg.register(new Scheme("http",80,PlainSocketFactory.getSocketFactory())); 
		schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory())); 	  
		
		//多连接的线程安全的管理器 
		PoolingClientConnectionManager pccm = new PoolingClientConnectionManager(schreg);
		pccm.setDefaultMaxPerRoute(20);	//每个主机的最大并行链接数 
		pccm.setMaxTotal(100);			//客户端总并行链接最大数    
		
		DefaultHttpClient httpClient = new DefaultHttpClient(pccm, params);  
		return httpClient;
	}
}
