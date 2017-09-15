package com.dtcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dtcc.model.Product;
import com.dtcc.service.ProductService;

@Controller
@RequestMapping("/db")
public class DbAjax {

	@Autowired
	ProductService ps;
	
	@RequestMapping("/readprod.do")
	public @ResponseBody List<Product> readProduct(@RequestParam("family") String family){
		List<Product> products=ps.findByFamily(family);
		return products;
	}
	
	
}
