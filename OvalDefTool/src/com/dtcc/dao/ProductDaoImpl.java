package com.dtcc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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
		// TODO Auto-generated method stub
		return null;
	}
/*
	@Autowired
	DriverManagerDataSource dataSource;
	
	@Override
	public String save(Product product) {
		String result="OK";
		try {
			Connection con=dataSource.getConnection();
			Statement stmt=con.createStatement();
			stmt.execute("insert into product(name, family, type, description) values('"
					+ product.getName()+"','"
					+ product.getFamily()+"','"
					+ product.getType()+"','"
					+ product.getDescription()+"')");
		} catch (SQLException e) {
			result="NOK";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String delete(Product product) {
		String result="OK";
		try {
			Connection con=dataSource.getConnection();
			Statement stmt=con.createStatement();
			stmt.execute("delete from product where name='"
					+ product.getName()+"' and family='"
					+ product.getFamily()+"' and type='"
					+ product.getType()+"' and description='"
					+ product.getDescription()+"')");
		} catch (SQLException e) {
			result="NOK";
			e.printStackTrace();
		}
		return result;	}

	@Override
	public String getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByFamily(String family) {
		List<Product> products = new ArrayList<Product>();

		try {
			Connection con=dataSource.getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from product where family='"+family+"' order by name");
			while(rs.next()){
				Product p=new Product();
				p.setName(rs.getString("name"));
				p.setFamily(family);
				p.setType(rs.getString("type"));
				p.setDescription(rs.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return products;
	}
*/
}
