package com.dtcc.test;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonTest {

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper(); 

		
				
				URL url;
				String result="";
				InputStream is;
				try {
					url = new URL("https://portal.msrc.microsoft.com/api/security-guidance/en-us/CVE/CVE-2017-8552");
				    
					is = url.openConnection().getInputStream();
							
				    BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );
				    String line = null;		   
				    while( ( line = reader.readLine() ) != null )  {
				       result=result+line;
				    }	
				    reader.close();
				    
				    JsonNode root=mapper.readTree(result);
				    System.out.println("Hello: " +root.get("_x003C_AffectedProducts_x003E_k__BackingField"));
				    
				}catch (FileNotFoundException fnfe){
					result="";
				} catch (IOException e) {
					result="";
					e.printStackTrace();
				}

	}

}
