package com.dtcc.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;

public class URLHttps {

	public static void main(String[] args) throws IOException {
		URL url = new URL("https://portal.msrc.microsoft.com/api/security-guidance/en-us/CVE/CVE-2017-8552");
		Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress("gateway.zscaler.net", 80));

		InputStream is = url.openConnection().getInputStream();

	    BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );

	    String line = null;
	    while( ( line = reader.readLine() ) != null )  {
	       System.out.println(line);
	    }
	    reader.close();

	}

}
