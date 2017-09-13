package com.dtcc.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Properties;
import java.net.Proxy.Type;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class UrlOpen {

	public static void main(String[] args) throws IOException {
		Properties systemProps = System.getProperties();
	    //systemProps.put("javax.net.ssl.keyStorePassword","changeit");
	   // systemProps.put("javax.net.ssl.keyStore","C:/Program Files/Java/jdk1.8.0_112/jre/lib/security/jssecacerts");
	    systemProps.put("javax.net.ssl.trustStore", "C:/Program Files/Java/jdk1.8.0_102/jre/lib/security/cacerts");
	    systemProps.put("javax.net.ssl.trustStorePassword","changeit");
	    System.setProperties(systemProps);

		System.out.println(System.getProperty("java.home"));
		
		//URL url = new URL("https://portal.msrc.microsoft.com/api/security-guidance/en-us/CVE/CVE-2017-8552");
		URL url = new URL("https://www.google.com");
		Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress("gateway.zscaler.net", 80));

		HttpsURLConnection conn=(HttpsURLConnection) url.openConnection(proxy);
		//conn.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
		InputStream is = conn.getInputStream();
	
		//InputStream is = url.openConnection(proxy).getInputStream();
		
		
	    BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );

	    String line = null;
	    while( ( line = reader.readLine() ) != null )  {
	       System.out.println(line);
	    }
	    reader.close();

	}

}
