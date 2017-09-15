package com.dtcc.controller;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dtcc.model.Product;
import com.dtcc.model.oval.Generator;
import com.dtcc.service.FamilyService;

@Controller
@RequestMapping("/cve")
public class CveProcess {
	
	DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
	
	DocumentBuilder db;
	Document ovaldbf;
	
	String xmlResult;
	
	@Autowired
	FamilyService familyService;
	
	@RequestMapping("/read_cve_meta.do")
	public String read_cve_meta(@RequestParam("cve_id") String cveId){
		_log.info("CVE ID: " + cveId);
		return "cve/read_cve_meta";
	}
	
	@RequestMapping("/generator.do")
	public String generator(Model model){
		model.addAttribute("families",familyService.getAll());
		return "cve/generator";
	}
	
	@RequestMapping("/generate_oval.do")
	public @ResponseBody String generate_oval(@RequestBody Generator gentr){
		String result="oval";
		_log.info(gentr);
		try {
			db = dbFactory.newDocumentBuilder();
			ovaldbf=db.newDocument();
			Element oval_definitions=ovaldbf.createElement("oval_definitions");
			oval_definitions.setAttribute("xmlns", "http://oval.mitre.org/XMLSchema/oval-definitions-5");
			oval_definitions.setAttribute("xmlns:oval", "http://oval.mitre.org/XMLSchema/oval-common-5");
			oval_definitions.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			oval_definitions.setAttribute("xsi:schemaLocation", "http://oval.mitre.org/XMLSchema/oval-common-5 oval-common-schema.xsd http://oval.mitre.org/XMLSchema/oval-definitions-5 oval-definitions-schema.xsd");
			
			Element generator=ovaldbf.createElement("generator");
			oval_definitions.appendChild(generator);
			
			//product name
			Element product_name=ovaldbf.createElement("oval:product_name");
			product_name.setTextContent(gentr.getProduct_name());
			generator.appendChild(product_name);
			
			//product version
			Element product_version=ovaldbf.createElement("oval:product_version");
			product_version.setTextContent(gentr.getProduct_version());
			generator.appendChild(product_version);
			
			//schema version
			Element schema_version=ovaldbf.createElement("oval:schema_version");
			schema_version.setTextContent(gentr.getSchema_version());
			generator.appendChild(schema_version);
			
			//timestamp
			Element timestamp=ovaldbf.createElement("oval:timestamp");
			timestamp.setTextContent(gentr.getTimestamp());
			generator.appendChild(timestamp);
			
			//defiitions
			Element definitions=ovaldbf.createElement("definitions");
			generator.appendChild(definitions);
			
			//definition
			Element definition=ovaldbf.createElement("oval-def:definition");
			definitions.appendChild(definition);
			definition.setAttribute("xmlns:oval-def", "http://oval.mitre.org/XMLSchema/oval-definitions-5");
			definition.setAttribute("class", gentr.getDefinition().getDefinitionClass());
			definition.setAttribute("id", gentr.getDefinition().getDefinitionId());
			definition.setAttribute("version", gentr.getDefinition().getVersion());
			
			//meta
			Element metadata=ovaldbf.createElement("oval-def:metadata");
			definition.appendChild(metadata);
			
			//title
			Element title=ovaldbf.createElement("oval-def:title");
			metadata.appendChild(title);
			title.setTextContent(gentr.getDefinition().getTitle());
			
			//affected family
			Element family=ovaldbf.createElement("oval-def:affected");
			metadata.appendChild(family);
			family.setAttribute("family", gentr.getFamily());
			
			for(Product pform:gentr.getAffected()){
				Element platform=ovaldbf.createElement("oval-def:platform");
				family.appendChild(platform);
				platform.setTextContent(pform.getName());
			}
			
			//reference
			Element reference=ovaldbf.createElement("oval-def-reference");
			metadata.appendChild(reference);
			reference.setAttribute("ref_id", gentr.getDefinition().getReference().getRef_id());
			reference.setAttribute("ref_url", gentr.getDefinition().getReference().getRef_url());
			reference.setAttribute("source", gentr.getDefinition().getReference().getSource());
			
			
			//description
			Element description=ovaldbf.createElement("oval-def:description");
			metadata.appendChild(description);
			description.setTextContent(gentr.getDefinition().getDescription());
			
			//oval_repository
			Element oval_repository=ovaldbf.createElement("oval-def:oval_repository");
			metadata.appendChild(oval_repository);
			
			//dates
			Element dates=ovaldbf.createElement("oval-def:dates");
			oval_repository.appendChild(dates);
			
			//submitted
			Element submitted=ovaldbf.createElement("oval-def:submitted");
			dates.appendChild(submitted);
			submitted.setAttribute("date", gentr.getTimestamp());
			
			//contributor
			Element contributor=ovaldbf.createElement("oval-def:contributor");
			submitted.appendChild(contributor);
			contributor.setAttribute("organization", "DTCC");
			contributor.setTextContent("Michael John Macrohon");
			
			//status
			Element status=ovaldbf.createElement("oval-def:status");
			oval_repository.appendChild(status);
			status.setTextContent("INITIAL SUBMISSION");
			
			
			DOMSource source=new DOMSource(oval_definitions);			
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			Transformer transformer=transformerFactory.newTransformer();
			StreamResult streamResult=new StreamResult(new File("c:/Temp/text.xml"));
			transformer.transform(source, streamResult);
			StreamResult consoleResult=new StreamResult(System.out);
			transformer.transform(source, consoleResult);
			
			StringWriter stringWriter=new StringWriter();
			StreamResult stringResult=new StreamResult(stringWriter);
			transformer.transform(source, stringResult);
			String strResult=stringWriter.toString();
			
			xmlResult=strResult;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		
		return xmlResult;
	}
	
	@RequestMapping(value="/xmlresult.do",method=RequestMethod.GET,
            produces=MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody String xmlresult(){
		return xmlResult;
	}
	
	Logger _log=Logger.getLogger(getClass().getName());
}
