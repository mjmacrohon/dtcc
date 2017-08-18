package com.dtcc.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dtcc.model.Cve;

@Controller
@RequestMapping("/jsonrest")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB 
maxFileSize=1024*1024*10,      // 50MB
maxRequestSize=1024*1024*50)   // 50MB
public class JsonRest {

	@Autowired
	Properties appProp;
	
	String everything="";
	
	public JsonRest() {
		super();
		try(FileInputStream inputStream = new FileInputStream("C:\\Temp\\nvdcve-1.0-2017.json")) {     
		    everything = IOUtils.toString(inputStream);
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	@RequestMapping("/cves.do")
	public @ResponseBody String cves(HttpServletResponse res){
		return everything;		
	}
	
	
	
	
	@RequestMapping("/cvemeta.do")
	public @ResponseBody List<Cve> read_cve_meta(@RequestParam("cveId") String cveId){
		List<Cve> cves=new ArrayList<Cve>();
		String sUrl="";
		Document jSoupDoc;
		try {
			
			
			/**mitre**/	
			Cve mitre=new Cve();
			mitre.setSource("MITRE");
			sUrl=appProp.getProperty("mitre.url").replace("${cveid}", cveId);	
			_log.info(sUrl);
			
			mitre.setCveNumber(cveId);	
			mitre.setUrl(sUrl);	
			Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress("gateway.zscaler.net", 80));
			jSoupDoc = Jsoup.connect(sUrl).proxy(proxy).get();
			_log.info(jSoupDoc);
			//mitre.setDescription(jSoupDoc.select("td").get(8).html());
			cves.add(mitre);
			
			/**nvd nvd.url**/
			Cve nvd=new Cve();
			nvd.setSource("NDV");
			sUrl=appProp.getProperty("nvd.url").replace("${cveid}", cveId);	
			nvd.setCveNumber(cveId);
			nvd.setUrl(sUrl);
			jSoupDoc=Jsoup.connect(sUrl).proxy(proxy).get();
			nvd.setDescription(jSoupDoc.select("p[data-testid='vuln-description']").html());
			cves.add(nvd);
		
			/**msrc**/
			//separate request method due to cross-origin
			//read_cve_msrc()
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cves;
	}
	
	
	@RequestMapping("/cvemsrcmeta.do")
	public @ResponseBody String read_cve_msrc(@RequestParam("cveId") String cveId) {
		URL url;
		String result="";
		try {
			//Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress("gateway.zscaler.net", 80));
			_log.info(cveId);
			url = new URL("https://portal.msrc.microsoft.com/api/security-guidance/en-us/CVE/"+cveId);
		    InputStream is = url.openConnection().getInputStream();
		    BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );
		    String line = null;		   
		    while( ( line = reader.readLine() ) != null )  {
		       result=result+line;
		    }
		    
		    reader.close();
		} catch (IOException e) {
			result="";
			e.printStackTrace();
		}
	    return result;
	}
	
	@RequestMapping("/cvemitrenvdmeta.do")
	public @ResponseBody List<Cve> read_cve_mitre(@RequestParam("cveId") String cveId) {
		List<Cve> cves=new ArrayList<Cve>();
		URL url;
		String result="";
		Document jSoupDoc=null;
		String sUrl=null;
		try {
			Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress("gateway.zscaler.net", 80));
			sUrl=appProp.getProperty("mitre.url").replace("${cveid}", cveId);	
			url = new URL(sUrl);
			//URLConnection con=url.openConnection(proxy);
			//InputStream is=con.getInputStream();
		    InputStream is = url.openConnection(proxy).getInputStream();
		    BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );
		    String line = null;		   
		    while( ( line = reader.readLine() ) != null )  {
		    	result=result+line;
		    }
		    reader.close();
		    jSoupDoc=Jsoup.parse(result);
		    
		    
			/**mitre**/	
			Cve mitre=new Cve();
			mitre.setSource("MITRE");
			mitre.setCveNumber(cveId);	
			mitre.setUrl(sUrl);	
			mitre.setDescription(jSoupDoc.select("td").get(8).html());
			cves.add(mitre);
			
			
			sUrl=appProp.getProperty("nvd.url").replace("${cveid}", cveId);	
			url = new URL(sUrl);
		    is = url.openConnection(proxy).getInputStream();
		    reader = new BufferedReader( new InputStreamReader( is )  );
		    line = null;		   
		    while( ( line = reader.readLine() ) != null )  {
		    	result=result+line;
		    }
		    reader.close();
		    jSoupDoc=Jsoup.parse(result);			
			
			/**nvd nvd.url**/
			Cve nvd=new Cve();
			nvd.setSource("NDV");			
			nvd.setCveNumber(cveId);
			nvd.setUrl(sUrl);
			jSoupDoc=Jsoup.connect(sUrl).proxy(proxy).get();
			nvd.setDescription(jSoupDoc.select("p[data-testid='vuln-description']").html());
			cves.add(nvd);				
		} catch (IOException e) {
			result="";
			//e.printStackTrace();
		}
	    return cves;
	}
	
	
	
	Logger _log=Logger.getLogger(getClass().getName());
}
	