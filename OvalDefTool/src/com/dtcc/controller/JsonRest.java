package com.dtcc.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dtcc.model.Article;
import com.dtcc.model.Cve;
import com.dtcc.model.OvalDefinition;
import com.dtcc.model.OvalParam;
import com.dtcc.model.Product;

@Controller
@RequestMapping("/jsonrest")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB 
maxFileSize=1024*1024*10,      // 50MB
maxRequestSize=1024*1024*50)   // 50MB
public class JsonRest {

	@Autowired
	Properties appProp;
	
	@Value("${platform}")
	private String defaultPlatform;
	
	String everything="";
	List<String> listOfCve=new ArrayList<>();
	
	public JsonRest() {
		super();
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
	
	@RequestMapping(value="/readmicrosofturl.do", produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String read_microsoft_api(@RequestParam String kbNumber){
		String result="";
		try {
			result=Jsoup.connect("https://support.microsoft.com/app/content/api/content/help/en-gb/"+kbNumber).ignoreContentType(true).execute().body();
			_log.info(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping("/ovaldefids.do")
	public @ResponseBody List<OvalDefinition> searchOvalDefinition(@RequestBody OvalParam op, HttpServletRequest req){
		List<OvalDefinition> ods=new ArrayList<OvalDefinition>();
		Map<String, OvalDefinition> mapOds=new HashMap<String,OvalDefinition>();
		Document jSoupDoc=null;
		String result="";
		URL url;
		String sUrl=appProp.getProperty("oval.org.mitre");
		String tempUrl=appProp.getProperty("oval.org.mitre")+"page=page_count&definition_id="+op.getDefinition_id()+"&title="+op.getTitle()+"&description="+op.getDescription()+"&reference_id=" +op.getReference_id()
				+ "&platform="+op.getPlatform()+"&product="+op.getProduct()+"&contributor=0&organization=0"
				+ "&class="+op.getAclass()+"&family="+op.getFamily()+"&status=" + op.getStatus();
		_log.info("tempUrl: " + tempUrl);
		sUrl=tempUrl.replace("page_count", "1").replace(" ", "+");
		
		
		Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress(appProp.getProperty("proxy.host"),new Integer(appProp.getProperty("proxy.port"))));
		try {
			url = new URL(sUrl);
			_log.info(sUrl);
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
		    Element elemPage=jSoupDoc.select(".search-results-last").first();
		    String sPageCount="1";
		    if (elemPage!=null){
		    	sPageCount=elemPage.attr("last-page");
			    if (sPageCount.isEmpty()){
			    	sPageCount="1";
			    }
		    }
		   _log.info("last page: " + sPageCount);
		    int pageCounter=new Integer(sPageCount);
		   
		   
		    for(int ctr=1;ctr<=pageCounter;ctr++){
		    	sUrl=tempUrl.replace("page_count", ctr+"").replace(" ", "+");;
				url = new URL(sUrl);

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
						mapOds.put(od.getDefinitionId(), od);
					}
				}
				ods=new ArrayList<>(mapOds.values());
				
		    }//for
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ods;
	}
	
	
	@RequestMapping("/extractproducts.do")
	public @ResponseBody List<Product> extractPlatformProduct(@RequestBody List<Product> products){
		String p="for|For|version|Version|\\(";
		Pattern pattern=Pattern.compile(p, Pattern.CASE_INSENSITIVE);
		List<Product> processProducts=new ArrayList<Product>();
		Map<String, String> mapProducts=new HashMap<String, String>();
		Map<String, String> mapPlatforms=new HashMap<String, String>();

		for(Product prod : products){
			String[] sArr;
			String pf;
			if (prod.getPlatform() == null){
				sArr=pattern.split(prod.getName());
				pf=sArr[0].replace("RT", "").replaceAll("\\s*$", "").replaceAll("^\\s*", "").trim().replaceAll(" +", " ");
				if (pf.contains("indow")){
					mapPlatforms.put(pf, pf);
				}else{
					mapProducts.put(prod.getName(), prod.getName());
				}
			}else{
				sArr=pattern.split(prod.getPlatform());
				pf=sArr[0].replace("RT", "").replaceAll("\\s*$", "").replaceAll("^\\s*", "").trim().replaceAll(" +", " ");
				mapPlatforms.put(pf, pf);				
				mapProducts.put(prod.getName(), prod.getName());
			}
		}
		
		for(String s: mapPlatforms.values()){
			Product prod=new Product();
			prod.setType("platform");
			prod.setName(s);
			processProducts.add(prod);
		}
		
		for(String s: mapProducts.values()){
			Product prod=new Product();
			prod.setType("product");
			prod.setName(s);
			processProducts.add(prod);
		}
		
		if (mapPlatforms.size()==0){
			String[] sArr=defaultPlatform.split(",");
			for(String s: sArr){
				Product prod=new Product();
				prod.setName(s);
				prod.setType("platform");
				processProducts.add(prod);
			}
		}		
		
		return processProducts;
	}
	

	@RequestMapping("/cleansearticles.do")
	public @ResponseBody List<Article> cleanse_article(@RequestBody List<Article> articles){
		List<Article> locArticles=new ArrayList<Article>();
		String p="for|For|version|Version|\\(";
		Pattern pattern=Pattern.compile(p, Pattern.CASE_INSENSITIVE);
		_log.info(articles);
		for(Article a:articles){
			_log.info(a.getProduct() + "  " + a.getPlatform());
			String[] sArr;
			if (a.getPlatform()==null){
				sArr=pattern.split(a.getProduct());
				if (a.getProduct().contains("indows")){				
					a.setPlatform(sArr[0].replace("RT", "").replaceAll("\\s*$", "").replaceAll("^\\s*", "").trim().replaceAll(" +", " "));
				}
			}else{
				sArr=pattern.split(a.getPlatform());
				a.setPlatform(sArr[0].replace("RT", "").replaceAll("\\s*$", "").replaceAll("^\\s*", "").trim().replaceAll(" +", " "));
			}
			
			locArticles.add(a);
		}		
		return locArticles;
	}
	
	
	
	
	
//	https://oval.cisecurity.org/repository/search/results/download?page=1&definition_id=&title=&description=&reference_id=CVE-2017-8552&platform=&product=0&contributor=0&organization=0&class=0&family=0&status=0
	
	@RequestMapping("/ovalrepo.do")
	public @ResponseBody String ovalrepo(@RequestParam("cveId") String cveId) {
		URL url;
		String result="";
		try {
			Proxy proxy=new Proxy(Type.HTTP, new InetSocketAddress(appProp.getProperty("proxy.host"),new Integer(appProp.getProperty("proxy.port"))));

			url = new URL("https://oval.cisecurity.org/repository/search/results/download?page=1&definition_id=&title=&description=&reference_id="+cveId+"&platform=&product=0&contributor=0&organization=0&class=0&family=0&status=0");
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
		    
		    is=new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8.name()));
		    
		    DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
		    DocumentBuilder dbuilder=dbFactory.newDocumentBuilder();
		    org.w3c.dom.Document doc = dbuilder.parse(is);
		    doc.getDocumentElement().normalize();

		    _log.info("Root Element: " + doc.getDocumentElement().getNodeName());
		    
		    //Generator gentr=new Generator();
		   // gentr.setProduct_name(product_name);
		    
		    //product name
		    NodeList nodeList=doc.getElementsByTagName("oval:product_name");
		    _log.info(nodeList.getLength());
		    Node node=nodeList.item(0);
		    org.w3c.dom.Element element=(org.w3c.dom.Element)node;
		    _log.info("productName: " + element.getTextContent());
		    
		    //product version
		    nodeList=doc.getElementsByTagName("oval:product_version");
		    _log.info(nodeList.getLength());
		    node=nodeList.item(0);
		    element=(org.w3c.dom.Element)node;
		    _log.info("productVersion: " + element.getTextContent());
		    
		    //schema version
		    nodeList=doc.getElementsByTagName("oval:schema_version");
		    _log.info(nodeList.getLength());
		    node=nodeList.item(0);
		    element=(org.w3c.dom.Element)node;
		    _log.info("schemaVersion: " + element.getTextContent());
		    
		  //schema version
		    nodeList=doc.getElementsByTagName("oval:timestamp");
		    _log.info(nodeList.getLength());
		    node=nodeList.item(0);
		    element=(org.w3c.dom.Element)node;
		    _log.info("timeStamp: " + element.getTextContent());
		    
		   
		    nodeList=doc.getElementsByTagName("definition");
		    for(int ctr=0; ctr<nodeList.getLength();ctr++){
		    	_log.info("**********************def "+ (ctr+1) +"*******************");
		    	 node=nodeList.item(ctr);
		    	 element=(org.w3c.dom.Element) node;
		    	_log.info("Class: "+ element.getAttribute("class"));
		    	_log.info("version: "+ element.getAttribute("version"));
		    
		    	_log.info("title: " + element.getElementsByTagName("title").item(0).getTextContent());
		    	
		    	Node nodeFamily= element.getElementsByTagName("affected").item(0);
		    	org.w3c.dom.Element familyElement= (org.w3c.dom.Element) nodeFamily;
		    	_log.info("family: " + familyElement.getAttribute("family"));
		    	
		    	//platform
		    	NodeList pfNodeList=familyElement.getElementsByTagName("platform");
		    	for(int ctr1=0;ctr1<pfNodeList.getLength();ctr1++){
		    		org.w3c.dom.Element pfElement=(org.w3c.dom.Element)pfNodeList.item(ctr1);
		    		_log.info(pfElement.getTextContent());
		    	}
		    	//product
		    	NodeList pNodeList=familyElement.getElementsByTagName("product");
		    	for(int ctr1=0;ctr1<pNodeList.getLength();ctr1++){
		    		org.w3c.dom.Element pElement=(org.w3c.dom.Element)pNodeList.item(ctr1);
		    		_log.info(pElement.getTextContent());
		    	}
		    	
		    	
		    	//description
		    	Node nodeDesc= element.getElementsByTagName("description").item(0);
		    	org.w3c.dom.Element nodeDescElement=(org.w3c.dom.Element)nodeDesc;
		    	_log.info(nodeDescElement.getTextContent());
		
		    }
		    
		}catch (FileNotFoundException fnfe){
			result="";
		} catch (IOException e) {
			result="";
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return result;
	}

	
	Logger _log=Logger.getLogger(getClass().getName());
}
	