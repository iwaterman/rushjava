package org.xman.http;

/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * This example demonstrates the recommended way of using API to make sure the
 * underlying connection gets released back to the connection manager.
 */
public class ClientConnectionRelease {

	public final static void main(String[] args) throws Exception {
		for (int i = 0; i < 1; i++) {

			Thread thread = new Thread(new Runnable() {

				public void run() {

					HttpClient httpclient = new DefaultHttpClient();
					try {
						HttpGet httpget = new HttpGet("http://localhost:8080/s3/bb.hello");
						// HttpGet httpget = new
						// HttpGet("http://10.129.148.50:8106/wasmanager/mysleep.jsp");
						// HttpGet httpget = new
						// HttpGet("http://10.129.148.50:8106/mysleep.jsp");

						// Execute HTTP request
						System.out.println("executing request " + httpget.getURI());
						HttpResponse response = httpclient.execute(httpget);

						System.out.println("----------------------------------------");
						System.out.println(response.getStatusLine());
						System.out.println("----------------------------------------");

						// Get hold of the response entity
						HttpEntity entity = response.getEntity();

						// If the response does not enclose an entity, there is
						// no need
						// to bother about connection release

						if (entity != null) {

							InputStream instream = entity.getContent();
							BufferedReader bReader = null;
							try {
								// instream.read();

								bReader = new BufferedReader(new InputStreamReader(instream));
								//
								String msg;
								while ((msg = bReader.readLine()) != null) {
									System.out.println(msg);
								}

								System.out.println(entity);
								// String msg = EntityUtils.toString(entity);
								// System.out.println("==================" +
								// msg);
								// do something useful with the response
							} catch (RuntimeException ex) {
								// In case of an unexpected exception you may
								// want to abort
								// the HTTP request in order to shut down the
								// underlying
								// connection immediately.
								httpget.abort();
								throw ex;
							} finally {
								// Closing the input stream will trigger
								// connection release
								try {
									instream.close();
								} catch (Exception ignore) {
								}
								try {
									bReader.close();
								} catch (Exception ignore) {
								}
							}
						}

					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						// When HttpClient instance is no longer needed,
						// shut down the connection manager to ensure
						// immediate deallocation of all system resources
						httpclient.getConnectionManager().shutdown();
					}
				}
			});

			thread.start();

		}
	}

}
