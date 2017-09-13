package com.dtcc.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/test")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB 
maxFileSize=1024*1024*10,      // 50MB
maxRequestSize=1024*1024*50)   // 50MB
public class Test {	

	
	@RequestMapping("/cves.do")
	public @ResponseBody String read_cves(HttpServletResponse res){
		//File file=new File("C:\\Temp\\nvdcve-1.0-2017.json");
		//res.getOutputStream().
		String everything="";
		try(FileInputStream inputStream = new FileInputStream("C:\\Temp\\nvdcve-1.0-2017.json")) {     
		    everything = IOUtils.toString(inputStream);

		    JsonNode rootNode=new ObjectMapper().readTree(inputStream);
		    rootNode.get("CVE_data_format").asText();
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return everything;
	}

}
