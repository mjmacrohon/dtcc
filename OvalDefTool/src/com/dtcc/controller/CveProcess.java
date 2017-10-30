package com.dtcc.controller;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dtcc.model.OvaDef;
import com.dtcc.model.Product;
import com.dtcc.model.oval.Criteria;
import com.dtcc.model.oval.Generator;
import com.dtcc.model.oval.State;
import com.dtcc.model.oval.Test;
import com.dtcc.service.FamilyService;
import com.dtcc.service.PlatformService;
import com.security.UserInfo;

@Controller
@RequestMapping("/cve")
@SessionAttributes({"def_ctr","extended_ctr","testref_ctr"})
public class CveProcess {
	
	DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
	
	DocumentBuilder db;
	Document ovaldbf;
	
	String xmlResult;
	
	@Autowired
	FamilyService familyService;
	
	@Autowired
	PlatformService platformService;
	
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
	public @ResponseBody String generate_oval(@RequestBody Generator gentr, Authentication auth){
		String result="oval";
		try {
			UserInfo ui=(UserInfo) auth.getPrincipal();
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
			
			//definitions
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
			contributor.setTextContent(ui.getFullName());
			
			//status
			Element status=ovaldbf.createElement("oval-def:status");
			oval_repository.appendChild(status);
			status.setTextContent("INITIAL SUBMISSION");
			
			//criteria
			generateCriteria(ovaldbf,definition, gentr.getCriterias());
			
			//tests
			Element tests=ovaldbf.createElement("tests");
			generator.appendChild(tests);
			generateTest(ovaldbf, tests, gentr.getTests());
			
			
			
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
			
			_log.info("SIZE: " + gentr.getCriterias().size());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		return xmlResult;
	}
	
	private void generateCriteria(Document ovaldbf, Element current,  List<Criteria> criterias){
		
		for(Criteria c:criterias){	
			Element criteria=null;
			if (c.getType().equalsIgnoreCase("criteria")){//criteria
				criteria=ovaldbf.createElement("oval-def:criteria");
				criteria.setAttribute("operator", c.getOperator());
			}else if (c.getType().equalsIgnoreCase("criterion")){//criterion
				criteria=ovaldbf.createElement("oval-def:criterion");
				criteria.setAttribute("test_ref", c.getReferenceId());
			}else{												//extend_definition
				criteria=ovaldbf.createElement("oval-def:extend_definition");
				criteria.setAttribute("definition_ref", c.getReferenceId());
			}			
			criteria.setAttribute("comment",c.getComment());			
			current.appendChild(criteria);
			generateCriteria(ovaldbf, criteria, c.getCriterias());
		}		
	}
	
    private void generateTest(Document ovaldbf, Element current,  List<Test> tests){
    	for(Test t:tests){
    		Element test=ovaldbf.createElement("file_test");
    		test.setAttribute("xmlns", "http://oval.mitre.org/XMLSchema/oval-definitions-5#windows");
    		test.setAttribute("comment", t.getComment());
    		test.setAttribute("id", t.getTestId());
    		test.setAttribute("version", "0");
    		
    		Element objectRef=ovaldbf.createElement("object");
    		objectRef.setAttribute("object_ref", t.getObjectReference());

    		Element stateRef=ovaldbf.createElement("state");
    		stateRef.setAttribute("state_ref", t.getStateReference());
    		
    		test.appendChild(objectRef);
    		test.appendChild(stateRef);
    		current.appendChild(test);
    	}
    }
	
	
	private void generateState(Document ovaldbf, Element current, List<State> states){
		for(State s: states){
			Element state=ovaldbf.createElement("file_state");
			state.setAttribute("xmlns", "http://oval.mitre.org/XMLSchema/oval-definitions-5#windows");
			state.setAttribute("comment", s.getComment());
			state.setAttribute("id", s.getStateId());
			state.setAttribute("version", "0");

			Element version=ovaldbf.createElement("version");
			version.setAttribute("datatype", s.getVersion());
			version.setAttribute("operation", s.getOperation());
			version.setTextContent(s.getOperationValue());
			state.appendChild(version);
			current.appendChild(state);
		}
	}
	
	@RequestMapping("/generateovaldef.do")
	public @ResponseBody String generate_oval_def(@RequestBody List<OvaDef> ovaldefs, Authentication auth){
		String result="OK";
		xmlResult="";
		
		try {
			OvaDef odGentr=ovaldefs.get(0);
			//UserInfo ui=(UserInfo) auth.getPrincipal();
			String name=auth.getName();
			db = dbFactory.newDocumentBuilder();
			ovaldbf=db.newDocument();
			Element oval_definitions=ovaldbf.createElement("oval_definitions");
			oval_definitions.setAttribute("xmlns", "http://oval.mitre.org/XMLSchema/oval-definitions-5");
			oval_definitions.setAttribute("xmlns:oval", "http://oval.mitre.org/XMLSchema/oval-common-5");
			oval_definitions.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			oval_definitions.setAttribute("xsi:schemaLocation", "http://oval.mitre.org/XMLSchema/oval-common-5 oval-common-schema.xsd http://oval.mitre.org/XMLSchema/oval-definitions-5 oval-definitions-schema.xsd");
			
			//generator
			Element generator=ovaldbf.createElement("generator");
			oval_definitions.appendChild(generator);

			//product name
			Element product_name=ovaldbf.createElement("oval:product_name");
			product_name.setTextContent(odGentr.getProductName());
			generator.appendChild(product_name);
			
			//product version
			Element product_version=ovaldbf.createElement("oval:product_version");
			product_version.setTextContent(odGentr.getProductVersion());
			generator.appendChild(product_version);
			
			//schema version
			Element schema_version=ovaldbf.createElement("oval:schema_version");
			schema_version.setTextContent(odGentr.getSchemaVersion());
			generator.appendChild(schema_version);
			
			//timestamp
			Element timestamp=ovaldbf.createElement("oval:timestamp");
			timestamp.setTextContent(odGentr.getTimestamp());
			generator.appendChild(timestamp);
			
			//definitions
			Element definitions=ovaldbf.createElement("definitions");
			oval_definitions.appendChild(definitions);
			
			int ovalIdCtr=0;
			for(OvaDef odItem: ovaldefs){
				//definition
				Element definition=ovaldbf.createElement("oval-def:definition");
				definitions.appendChild(definition);
				definition.setAttribute("xmlns:oval-def", "http://oval.mitre.org/XMLSchema/oval-definitions-5");
				definition.setAttribute("class", odItem.getOvalClass());
				
				odItem.setOvalId("oval:com.xxxx:def:"+ (ovalIdCtr++));
				definition.setAttribute("id", odItem.getOvalId());
				definition.setAttribute("version", odItem.getOvalVersion());
				
				//meta
				Element metadata=ovaldbf.createElement("oval-def:metadata");
				definition.appendChild(metadata);
				
				//title
				Element title=ovaldbf.createElement("oval-def:title");
				metadata.appendChild(title);
				title.setTextContent(odItem.getTitle());
				
				//affected family
				Element family=ovaldbf.createElement("oval-def:affected");
				metadata.appendChild(family);
				family.setAttribute("family", odItem.getFamily());
				
				//platform
				for(String pform:odItem.getPlatform().split(",")){
					Element platform=ovaldbf.createElement("oval-def:platform");
					family.appendChild(platform);
					platform.setTextContent(pform);
				}
				
				//product
				for(String pform:odItem.getProduct().split(",")){
					Element product=ovaldbf.createElement("product");
					family.appendChild(product);
					product.setTextContent(pform);
				}
				
				//reference
				Element reference=ovaldbf.createElement("oval-def-reference");
				metadata.appendChild(reference);
				reference.setAttribute("ref_id", odItem.getRefId());
				reference.setAttribute("ref_url", odItem.getRefUrl());
				reference.setAttribute("source", odItem.getSource());
				
				//description
				Element description=ovaldbf.createElement("oval-def:description");
				metadata.appendChild(description);
				description.setTextContent(odItem.getDescription());
				
				//oval_repository
				Element oval_repository=ovaldbf.createElement("oval-def:oval_repository");
				metadata.appendChild(oval_repository);
				
				//dates
				Element dates=ovaldbf.createElement("oval-def:dates");
				oval_repository.appendChild(dates);
				
				//submitted
				Element submitted=ovaldbf.createElement("oval-def:submitted");
				dates.appendChild(submitted);
				submitted.setAttribute("date", odItem.getTimestamp());
				
				//contributor
				Element contributor=ovaldbf.createElement("oval-def:contributor");
				submitted.appendChild(contributor);
				contributor.setAttribute("organization", "DTCC");
				contributor.setTextContent(name);
				
				//status
				Element status=ovaldbf.createElement("oval-def:status");
				oval_repository.appendChild(status);
				status.setTextContent("INITIAL SUBMISSION");
				
				//criteria
				generateCriteria(ovaldbf,definition, odItem.getCriterias());
				
				
			}//for loop
			
			
			//tests
			Element tests=ovaldbf.createElement("tests");
			oval_definitions.appendChild(tests);
			generateTest(ovaldbf, tests, odGentr.getTests());
			
			//state
			Element states=ovaldbf.createElement("states");
			oval_definitions.appendChild(states);
			generateState(ovaldbf, states, odGentr.getStates());
			
			DOMSource source=new DOMSource(oval_definitions);			
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			Transformer transformer=transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			String sFolder="C:/Temp/Oval";
			java.io.File ioFolder=new File(sFolder);
			if (!ioFolder.exists()){
				ioFolder.mkdir();
			}
			StreamResult streamResult=new StreamResult(new File(sFolder+"/OVALDef_"+odGentr.getTimestamp().split("T")[0]+".xml"));
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

		return result;
	}
	

	
	@RequestMapping(value="/xmlresult.do",method=RequestMethod.GET,
            produces=MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody String xmlresult( Authentication auth){
		//UserInfo ui=(UserInfo) auth.getPrincipal();
		return xmlResult;
	}
	
	Logger _log=Logger.getLogger(getClass().getName());
}
