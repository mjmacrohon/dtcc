package com.dtcc.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nvd.cve.json.CVE;
import nvd.cve.json.CVE_Item;
import nvd.cve.json.CVE_data_meta;
import nvd.cve.json.ComVulEx;

public class CVEJSON {

	public static void main(String[] args) {		
		ObjectMapper mapper = new ObjectMapper();
		
		ComVulEx cvule=new ComVulEx();
		cvule.setCVE_data_type("CVE");
		cvule.setCVE_data_format("MITRE");
		cvule.setCVE_version("4.0");
		cvule.setCVE_data_numberOfCVEs("5142");
		cvule.setCVE_data_timestamp("2017-07-24T07:01Z");
		List<CVE> cves=new ArrayList<CVE>();	
		
		
		CVE_Item CVE_Items = new CVE_Item();	
		CVE cve=new CVE();
		CVE_data_meta cve_data_meta=new CVE_data_meta();
		cve_data_meta.setID("CVE-2017-0001");
		cve.setCve_data_meta(cve_data_meta);
		cves.add(cve);
		
		CVE_Items.setCve(cves);
		
		cvule.setCVE_Items(CVE_Items);
		
		try {
			String jsonString=mapper.writeValueAsString(cvule);
			System.out.println(jsonString);
			
			File file=new File("C:\\Temp\\nvdcve-1.0-2017.json");
			
			ComVulEx comVulEx=mapper.readValue(file, ComVulEx.class);
			
			
			
			
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static Logger _log=Logger.getLogger("AA ");
}
