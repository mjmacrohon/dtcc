package com.dtcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dtcc.dao.ProductDao;
import com.dtcc.model.Product;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;

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
		return productDao.findByFamily(family);
	}
	
	
}
