package com.dtcc.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonNodeTest {

	public static void main(String[] args) {
		String everything="";
		try(FileInputStream inputStream = new FileInputStream("C:\\Temp\\nvdcve-1.0-2017.json")) {     
		    everything = IOUtils.toString(inputStream);

		    JsonNode rootNode=new ObjectMapper().readTree(everything);		   
		    JsonNode arrayNode=rootNode.get("CVE_Items");
		    
		    for(JsonNode cveItem : arrayNode){
		    	_log.info(cveItem.get("cve").get("CVE_data_meta").get("ID").asText());
		    }
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static Logger _log=Logger.getLogger("JsonNodeTreeTest");
}
