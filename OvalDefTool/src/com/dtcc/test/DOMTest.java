package com.dtcc.test;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DOMTest {
	public static void main(String[] args) {
		DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db;
		
		try {
			db = dbFactory.newDocumentBuilder();
			Document doc=db.newDocument();
			Element oval_definitions=doc.createElement("oval_definitions");
			oval_definitions.setAttribute("xmlns", "http://oval.mitre.org/XMLSchema/oval-definitions-5");
			oval_definitions.setAttribute("xmlns:oval", "http://oval.mitre.org/XMLSchema/oval-common-5");
			oval_definitions.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			oval_definitions.setAttribute("xsi:schemaLocation", "http://oval.mitre.org/XMLSchema/oval-common-5 oval-common-schema.xsd http://oval.mitre.org/XMLSchema/oval-definitions-5 oval-definitions-schema.xsd");
			
			Element generator=doc.createElement("generator");
			oval_definitions.appendChild(generator);
			
			Element product_name=doc.createElement("oval-product_name");
			product_name.setTextContent("CIS OVAL Repository");
			generator.appendChild(product_name);
			
			
			
			
			
			
			
			
			DOMSource source=new DOMSource(oval_definitions);
			
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			Transformer transformer=transformerFactory.newTransformer();
			StreamResult result=new StreamResult(new File("c:/Temp/text.xml"));
			transformer.transform(source, result);
			StreamResult consoleResult=new StreamResult(System.out);
			transformer.transform(source, consoleResult);
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
