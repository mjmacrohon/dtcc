package com.dtcc.test;

import java.io.IOException;
import java.sql.Timestamp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.sun.jna.platform.win32.Sspi.TimeStamp;

public class JsoupCVEList {

	public static void main(String[] args) {
		Timestamp ttStart = new Timestamp(System.currentTimeMillis());
		Timestamp ttEnd;
		int ctr=0;
			
		String sUrl="https://cve.mitre.org/data/downloads/allitems.html";
		//sUrl="http://lisc-ordmgrd01/order-manager-ui/";
		try {
			Document jSoupDoc=Jsoup.connect(sUrl).maxBodySize(0).get();
			for(Element e: jSoupDoc.select("font b")){
				System.out.println(e.html()  + " " + ctr++);
			}
			ttEnd = new Timestamp(System.currentTimeMillis());
			System.out.println(ttStart);
			System.out.println(ttEnd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
