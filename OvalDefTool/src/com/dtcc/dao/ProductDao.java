package com.dtcc.dao;

import java.util.List;

import com.dtcc.model.Product;

public interface ProductDao {
	abstract String save(Product product);
	abstract String delete(Product product);
	abstract String getAll();
	abstract List<Product> findByFamily(String family);
}
