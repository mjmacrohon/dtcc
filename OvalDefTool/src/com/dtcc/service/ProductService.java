package com.dtcc.service;

import java.util.List;

import com.dtcc.model.Product;

public interface ProductService {
	abstract String save(Product product);
	abstract String delete(Product product);
	abstract String getAll();
	abstract List<Product> findByFamily(String family);

}
