package com.dtcc.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

public class URLHttps {

	public static void main(String[] args) throws IOException {
		//URL url = new URL("https://portal.msrc.microsoft.com/api/security-guidance/en-us/CVE/CVE-2017-8552");
		
		//URL url = new URL("https://cve.mitre.org/data/downloads/allitems.html");
		//URL url = new URL("https://ovaldb.altx-soft.ru/Definitions.aspx?title=Microsoft%20Internet%20Explorer&platform=Microsoft%20Windows%2010");
		
		//Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress("gateway.zscaler.net", 80));

		URL url = new URL("http://download.microsoft.com/download/9/9/c/99c5efe6-f2c5-428d-a1ad-ab103de5ca9c/4038782.csv");
		InputStream is = url.openConnection().getInputStream();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp);
		
//		IOUtils.toString(is,StandardCharsets.UTF_8);
	//	System.out.println(IOUtils.toString(is,StandardCharsets.UTF_8));
		timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp);
		
		
		LineIterator li=IOUtils.lineIterator(is,StandardCharsets.UTF_8);
		System.out.println(li.hasNext());
		while(li.hasNext()){
			String s=li.nextLine();
			if (s.toLowerCase().contains("\"Mshtml.dll\"".toLowerCase()))
				System.out.println(s);
			
			
			
		}
		
		
/*
	    BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );

	    String line = null;
	    while( ( line = reader.readLine() ) != null )  {
	       System.out.println(line);
	    }
	    reader.close();
*/
	}

}
