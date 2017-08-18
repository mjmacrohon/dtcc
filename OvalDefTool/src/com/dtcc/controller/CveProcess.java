package com.dtcc.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cve")
public class CveProcess {
	
	@RequestMapping("/read_cve_meta.do")
	public String read_cve_meta(@RequestParam("cve_id") String cveId){
		_log.info("CVE ID: " + cveId);
		return "cve/read_cve_meta";
	}
	
	@RequestMapping("/generator.do")
	public String generator(){
		return "cve/generator";
	}
	
	Logger _log=Logger.getLogger(getClass().getName());
}
