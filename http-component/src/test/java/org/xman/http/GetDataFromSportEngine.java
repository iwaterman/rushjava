package org.xman.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GetDataFromSportEngine {
	private static String SUB_REQ = "http://xxx.xxxx.com:8081/xmlfeed?requestType=SubscribeRequest&subscriptionSpecificationName=football";
	private static String GET_NEXT_INIT_DATA_REQ = "http://xxx.xxxx.com:8081/xmlfeed?requestType=GetNextInitialDataRequest&subscriptionId =";
	private static String GET_NEXT_UPDATE_DATA_RQE = "http://xxx.xxxx.com:8081/xmlfeed?requestType=GetNextUpdateDataRequest&subscriptionId =";
	private static String UNSUB_REQ = "http://xxx.xxxx.com:8081/xmlfeed?requestType=UnsubscribeRequest&subscriptionSpecificationName=football&subscriptionId =";
	private static String localPath = "d:\\localPath";

	public static void main(String args[]) throws Exception {
		getData();
	}

	public static void getData() throws Exception {
		DefaultHttpClient httpclient = getLongConnect();
		System.out.println("********** Begin crash data from BetBrain");
		String subscriptionId = getSubscriptionId(httpclient);
		if (subscriptionId == null) {
			return;
		}

		// 5.Steps 3 and 4 repeat until the initial data dump is over.
		getInitSub(httpclient, subscriptionId);

		// 6.Client sends to Sports Engine a get-next-update-data-request SDQL
		// Construct.
		// 7.Sports Engine sends to Client a get-next-update-data-response SDQL
		// Construct.
		getNextUpdate(httpclient, subscriptionId);

		// 8.Steps 6 and 7 repeat until Client sends an unsubscribe-request SDQL
		// Construct.
		sendUnSub(httpclient, subscriptionId);
	}

	public static void sendUnSub(DefaultHttpClient httpclient, String subscriptionId) throws Exception {
		HttpGet httpGet = new HttpGet(UNSUB_REQ + subscriptionId);
		HttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();

		// List <String> lines = IOUtils.readLines (in, "UTF-8");
		// Iterator <String> iter = lines.iterator ();
		// StringBuffer xmlStr = null;
		// While (iter.hasNext ()) {
		// XmlStr.append (iter.next ());
		// }
		String xmlStr = unGZip(in);
		System.out.println("****** The end, get the UnSub xml:" + xmlStr.toString());
	}

	private static String parseXMLAndGetResponseCode(String xmlStr) {
		String code = null;
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
			Element root = document.getRootElement();
			Iterator<Element> iter = root.elementIterator("UnsubscribeResponse");
			while (iter.hasNext()) {
				Element ele = iter.next();
				Attribute attr = ele.attribute("code");
				code = attr.getStringValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}

	public static void getNextUpdate(DefaultHttpClient httpclient, String subscriptionId) throws Exception {
		System.out.println(". 3 Begin getNextUpdate");

		int count = 0;
		boolean exit = false;

		while (!exit) {
			HttpGet httpGet2 = new HttpGet(GET_NEXT_UPDATE_DATA_RQE + subscriptionId);

			HttpResponse response2 = httpclient.execute(httpGet2);
			HttpEntity entity2 = response2.getEntity();
			InputStream in2 = entity2.getContent();
			String fileName = "nextUpdate_" + System.currentTimeMillis() + ". Gz";
			// String xmlStr = unGZip (in2);
			// List <String> lines = new ArrayList <String> ();
			// Lines.add (xmlStr);
			// FileUtils.writeLines (new File (localPath + fileName), lines);

			FileUtils.copyInputStreamToFile(in2, new File(localPath + fileName));
			Thread.sleep(60000);
			count++;
			System.out.println("getNextUpdate count =" + count);
			if (count == 720) {
				exit = true;
			}
		}
	}

	public static void getInitSub(DefaultHttpClient httpclient, String subscriptionId) throws Exception {
		System.out.println(". 2 getInitSub");
		boolean isComplete = false;
		while (!isComplete) {
			// 3.Client sends to Sports Engine a get-next-initial-data-request
			// SDQL construct.
			HttpGet httpGet2 = new HttpGet(GET_NEXT_INIT_DATA_REQ + subscriptionId);
			// 4.Sports Engine sends to Client a get-next-initial-data-response
			// SDQL construct.
			HttpResponse response2 = httpclient.execute(httpGet2);
			HttpEntity entity2 = response2.getEntity();
			InputStream in2 = entity2.getContent();
			String xmlStr = unGZip(in2);
			List<String> lines = new ArrayList<String>();
			lines.add(xmlStr);
			FileUtils.writeLines(new File(localPath + "/ InitSub_" + System.currentTimeMillis() + ". Xml"), lines);
			System.out.println("getInitSub the xmlStr =" + xmlStr);
			String dumpComplete = parseXMLAndGetDumpComplete(xmlStr);
			System.out.print("dumpComplete =" + dumpComplete);
			if ("true".equals(dumpComplete)) {
				isComplete = true;
			}
			Thread.sleep(35000);
		}
	}

	public static DefaultHttpClient getLongConnect() {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		httpclient.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				// Honor 'keep-alive' header
				HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					if (value != null && param.equalsIgnoreCase("timeout")) {
						try {
							return Long.parseLong(value) * 1000;
						} catch (NumberFormatException ignore) {
						}
					}
				}
				HttpHost target = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
				if ("seps.betbrain.com".equalsIgnoreCase(target.getHostName())) {
					// Keep alive for 5 seconds only
					return 5 * 1000;
				} else {
					// Otherwise keep alive for 30 seconds
					return 30 * 1000;
				}
			}
		});

		return httpclient;
	}

	public static String getSubscriptionId(DefaultHttpClient httpclient) throws Exception {
		// 1.Client sends to Sports Engine a subscribe-request SDQL construct.
		HttpGet httpGet = new HttpGet(SUB_REQ);
		HttpResponse response1 = httpclient.execute(httpGet);
		// 2.Sports Engine sends to Client a subscribe-response SDQL construct.
		HttpEntity entity1 = response1.getEntity();
		InputStream in = entity1.getContent();
		String xmlStr = unGZip(in);
		System.out.println("getSubscriptionId the xmlStr =" + xmlStr);
		List<String> lines = new ArrayList<String>();
		lines.add(xmlStr);
		FileUtils.writeLines(new File(localPath + "/ SubscriptionId.xml"), lines);

		String subscriptionId = parseXMLAndGetSubscriptionId(xmlStr.toString());
		System.out.println("1 subscriptionId =." + subscriptionId);

		return subscriptionId;
	}

	private static String parseXMLAndGetSubscriptionId(String xmlStr) {
		if (xmlStr == null) {
			return null;
		}
		String subscriptionId = null;
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
			Element root = document.getRootElement();
			Iterator<Element> iter = root.elementIterator("SubscribeResponse");
			while (iter.hasNext()) {
				Element ele = iter.next();
				Attribute attr = ele.attribute("subscriptionId");
				subscriptionId = attr.getStringValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subscriptionId;
	}

	private static String parseXMLAndGetDumpComplete(String xmlStr) {
		if (xmlStr == null) {
			return null;
		}
		String dumpComplete = null;
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
			Element root = document.getRootElement();
			Iterator<Element> iter = root.elementIterator("GetNextInitialDataResponse");
			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				Iterator<Element> it = recordEle.elementIterator("InitialData");
				while (it.hasNext()) {
					Element ele = (Element) it.next();
					Attribute attr = ele.attribute("dumpComplete");
					dumpComplete = attr.getStringValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dumpComplete;
	}

	private static String unGZip(InputStream inputStream) {
		byte[] b = null;
		String s = null;
		try {
			GZIPInputStream gzip = new GZIPInputStream(inputStream);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			s = baos.toString();
			baos.flush();
			baos.close();
			gzip.close();
			// Bis.close ();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}
}