package com.dtcc.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dtcc.model.Family;
import com.dtcc.model.Platform;
import com.dtcc.service.FamilyService;
import com.dtcc.service.PlatformService;

@Controller
@RequestMapping("/db")
public class DbAjax {

	@Autowired
	FamilyService familyService;
	
	@Autowired
	PlatformService platformService;
		
	
	@RequestMapping("ajaxSaveFamily.do")
	public @ResponseBody String ajaxSaveFamily(@RequestBody Family family){
		_log.info(family.getName());
		return familyService.save(family);
	}
	
	@RequestMapping("ajaxSavePlatform.do")
	public @ResponseBody String ajaxSavePlatform(@RequestBody Platform platform){
		_log.info(platform.getName());
		platformService.save(platform);
		return "OK";
		
	}	
	
	@RequestMapping("/ajaxReadPlatform.do")
	public @ResponseBody List<Platform> readProduct(@RequestParam("family") String family){
		List<Platform> platforms=platformService.findByFamily(family);
		return platforms;
	}
	
	Logger _log=Logger.getLogger(getClass().getName());
	
}
