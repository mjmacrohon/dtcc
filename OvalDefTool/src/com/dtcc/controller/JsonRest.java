package com.dtcc.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dtcc.model.Cve;
import com.dtcc.model.OvalDefinition;
import com.dtcc.model.OvalParam;
import com.dtcc.model.Registrations;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/jsonrest")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB 
maxFileSize=1024*1024*10,      // 50MB
maxRequestSize=1024*1024*50)   // 50MB
public class JsonRest {

	@Autowired
	Properties appProp;
	
	String everything="";
	List<String> listOfCve=new ArrayList<>();
	
	public JsonRest() {
		super();
		try(FileInputStream inputStream = new FileInputStream("C:\\Temp\\nvdcve-1.0-2017.json")) {     
		    everything = IOUtils.toString(inputStream);
		    _log.info("Reading cves......."); 
		    JsonNode rootNode=new ObjectMapper().readTree(everything);		   
		    JsonNode arrayNode=rootNode.get("CVE_Items");
		    
		    for(JsonNode cveItem : arrayNode){
		    	listOfCve.add(cveItem.get("cve").get("CVE_data_meta").get("ID").asText());
		    }
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
	
	@RequestMapping("/readcves.do")
	public @ResponseBody List<String> readAllCves(){
		//_log.info("CVE Count: " + listOfCve.size());
		return listOfCve;
	}
	
	
	@RequestMapping("/cvemeta.do")
	public @ResponseBody List<Cve> read_cve_meta(@RequestParam("cveId") String cveId){
		List<Cve> cves=new ArrayList<Cve>();
		String sUrl="";
		Document jSoupDoc;
		try {
			Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress(appProp.getProperty("proxy.host"),new Integer(appProp.getProperty("proxy.port"))));

			/**mitre**/	
			Cve mitre=new Cve();
			mitre.setSource("MITRE");
			sUrl=appProp.getProperty("mitre.url").replace("${cveid}", cveId);	
			_log.info(sUrl);
			
			mitre.setCveNumber(cveId);	
			mitre.setUrl(sUrl);	
			if (appProp.getProperty("proxy.enabled").equals("1")){
				jSoupDoc = Jsoup.connect(sUrl).proxy(proxy).get();
			}else{
				jSoupDoc = Jsoup.connect(sUrl).get();
			}
			
			_log.info(jSoupDoc);
			//mitre.setDescription(jSoupDoc.select("td").get(8).html());
			cves.add(mitre);
			
			/**nvd nvd.url**/
			Cve nvd=new Cve();
			nvd.setSource("NDV");
			sUrl=appProp.getProperty("nvd.url").replace("${cveid}", cveId);	
			nvd.setCveNumber(cveId);
			nvd.setUrl(sUrl);
			if (appProp.getProperty("proxy.enabled").equals("1")){
				jSoupDoc=Jsoup.connect(sUrl).proxy(proxy).get();
			}else{
				jSoupDoc=Jsoup.connect(sUrl).get();
			}
			
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
			Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress(appProp.getProperty("proxy.host"),new Integer(appProp.getProperty("proxy.port"))));

			url = new URL("https://portal.msrc.microsoft.com/api/security-guidance/en-us/CVE/"+cveId);
		    InputStream is;
		    if (appProp.getProperty("proxy.enabled").equals("1")){
		    	is = url.openConnection(proxy).getInputStream();
		    }else{
		    	is = url.openConnection().getInputStream();
		    }
		    BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );
		    String line = null;		   
		    while( ( line = reader.readLine() ) != null )  {
		       result=result+line;
		    }
		    
		    reader.close();
		}catch (FileNotFoundException fnfe){
			result="";
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
			Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress(appProp.getProperty("proxy.host"),new Integer(appProp.getProperty("proxy.port"))));
			sUrl=appProp.getProperty("mitre.url").replace("${cveid}", cveId);	
			url = new URL(sUrl);
			//URLConnection con=url.openConnection(proxy);
			//InputStream is=con.getInputStream();
		    InputStream is;
		    if (appProp.getProperty("proxy.enabled").equals("1")){
		    	is = url.openConnection(proxy).getInputStream();
		    }else{
		    	is = url.openConnection().getInputStream();
		    }
		    
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
			 if (appProp.getProperty("proxy.enabled").equals("1")){
				 jSoupDoc=Jsoup.connect(sUrl).proxy(proxy).get();
			 }else{
				 jSoupDoc=Jsoup.connect(sUrl).get();
			 }
			
			nvd.setDescription(jSoupDoc.select("p[data-testid='vuln-description']").html());
			cves.add(nvd);				
		} catch (IOException e) {
			result="";
			//e.printStackTrace();
		}
	    return cves;
	}
	
	
	@RequestMapping("/ovaldefids.do")
	public @ResponseBody List<OvalDefinition> searchOvalDefinition(@RequestBody OvalParam op, HttpServletRequest req){
		List<OvalDefinition> ods=new ArrayList<OvalDefinition>();
		Document jSoupDoc=null;
		String result="";
		URL url;
		String sUrl=appProp.getProperty("oval.org.mitre");
		String tempUrl=appProp.getProperty("oval.org.mitre")+"?page=page_count&definition_id="+op.getDefinition_id()+"&title"+op.getTitle()+"=&description="+op.getDescription()+"&reference_id=" +op.getReference_id()
				+ "&platform="+op.getPlatform()+"&product="+op.getProduct()+"&contributor=0&organization=0"
				+ "&class="+op.getAclass()+"&family="+op.getFamily()+"&status=" + op.getStatus();
		sUrl=tempUrl.replace("page_count", "1");
		Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress(appProp.getProperty("proxy.host"),new Integer(appProp.getProperty("proxy.port"))));
		try {
			url = new URL(sUrl);
			//URLConnection con=url.openConnection(proxy);
			//InputStream is=con.getInputStream();
		    InputStream is;
		    if (appProp.getProperty("proxy.enabled").equals("1")){
		    	is = url.openConnection(proxy).getInputStream();
		    }else{
		    	is = url.openConnection().getInputStream();
		    }
		    
		    BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );
		    String line = null;		   
		    while( ( line = reader.readLine() ) != null )  {
		    	result=result+line;
		    }
		    reader.close();
		    jSoupDoc=Jsoup.parse(result);
		    String sPageCount=jSoupDoc.select(".search-results-last").attr("last-page");
		    if (sPageCount.isEmpty()){
		    	sPageCount="0";
		    }
		   int pageCounter=new Integer(sPageCount);
		   
		    for(int ctr=1;ctr<pageCounter;ctr++)
		    	sUrl=tempUrl.replace("page_count", ctr+"");
				url = new URL(sUrl);
				//URLConnection con=url.openConnection(proxy);
				//InputStream is=con.getInputStream();
			    if (appProp.getProperty("proxy.enabled").equals("1")){
			    	is = url.openConnection(proxy).getInputStream();
			    }else{
			    	is = url.openConnection().getInputStream();
			    }
			    
			    reader = new BufferedReader( new InputStreamReader( is )  );
			    line = null;		   
			    while( ( line = reader.readLine() ) != null )  {
			    	result=result+line;
			    }
			    reader.close();
			    jSoupDoc=Jsoup.parse(result);		    	
		    	
				for(Element trElem: jSoupDoc.select("table tbody tr")){
					if (trElem.select("td").size()>0){
						//_log.info(trElem.select("td a").get(0).html()); //definition id		
						//_log.info(trElem.select("td").get(0).html()); //definition id	
						//_log.info(trElem.select("td").get(1).html()); //Class
						//_log.info(trElem.select("td").get(2).html()); //Title
						//_log.info(trElem.select("td").get(3).html()); //Last Modified
						OvalDefinition od=new OvalDefinition();
						od.setDefinitionId(trElem.select("td a").get(0).html());
						od.setDefinitionUrl(trElem.select("td").get(0).html());
						od.setDefinitionClass(trElem.select("td").get(1).html());
						od.setTitle(trElem.select("td").get(2).html());
						od.setLastModified(trElem.select("td").get(3).html());
						ods.add(od);
					}
				}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ods;
	}
	
	
	@RequestMapping("/getregistration.do")
	public @ResponseBody Registrations getRegistrations(){
		Registrations reg=new Registrations();
		reg.setLastName("De la Cruz");
		reg.setFirstName("Jose");
		reg.setDeliveryCountry("Germany");
		reg.setDeliveryCountryCode("DE");
		reg.setTitle("Mr");
		return reg;
	}
	
	Logger _log=Logger.getLogger(getClass().getName());
}
	