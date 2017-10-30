package com.dtcc.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JSoupTest {

	public static void main(String[] args) throws IOException {
		Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress("gateway.zscaler.net", 80));
	
		
		// HTTP
		//System.setProperty("http.proxyHost", "http://pac.zscaler.net/ivoclarvivadent.com/proxy.pac");
		//System.setProperty("http.proxyPort", "80");
		//System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");

		//// HTTPS
		//System.setProperty("https.proxyHost", "https://webproxy.ivcorp.net");
		//System.setProperty("https.proxyPort", "9400");
		//mitre
		
		String sUrl="http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2017-8736";
		Jsoup.connect(sUrl).get();
		//sUrl="http://lisc-ordmgrd01/order-manager-ui/";
		Document jSoupDoc=Jsoup.connect(sUrl).proxy(proxy).get();
		_log.info("" + jSoupDoc.title());
		_log.info("" + jSoupDoc.select("td").get(8).html());
		
		//nvd
		sUrl="http://nvd.nist.gov/vuln/detail/CVE-2017-8552";	
		jSoupDoc=Jsoup.connect(sUrl).get();
		_log.info("" + jSoupDoc.title());
		_log.info("" + jSoupDoc.select("p[data-testid='vuln-description']").html());
		
		//msrc
		sUrl="https://portal.msrc.microsoft.com/api/security-guidance/en-us/CVE/CVE-2017-8551";
		jSoupDoc=Jsoup.connect(sUrl).get();
		_log.info("" + jSoupDoc.title());
		_log.info("" + jSoupDoc);
	}
	static Logger _log=Logger.getLogger("AA ");

}
