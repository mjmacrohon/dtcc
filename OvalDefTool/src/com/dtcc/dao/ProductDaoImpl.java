package com.dtcc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dtcc.model.Product;

@Component
public class ProductDaoImpl implements ProductDao {

	@Override
	public String save(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByFamily(String family) {
		List<Product> products = new ArrayList<Product>();
		Product product;
		
		if (family.equalsIgnoreCase("windows")){
			product=new Product("Windows 7", "Windows", "platform", ""); 	products.add(product);		
			product=new Product("Windows 2000", "Windows", "platform", "");			products.add(product);
			product=new Product("Windows 2007", "Windows", "platform", "");			products.add(product);
			product=new Product("Windows 2008 Server", "Windows", "platform", "");			products.add(product);
			product=new Product("Windows 2008 Server R2", "Windows", "platform", "");			products.add(product);
		}else if(family.equalsIgnoreCase("unix")){
			product=new Product("Unix 1", "unix", "platform", ""); 	products.add(product);		
			product=new Product("Unix 2", "unix", "platform", ""); 	products.add(product);	
			product=new Product("Unix 3", "unix", "platform", ""); 	products.add(product);	
		}
		return products;
	}

}
