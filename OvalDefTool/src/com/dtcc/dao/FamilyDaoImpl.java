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

import com.dtcc.model.Family;

@Component
public class FamilyDaoImpl implements FamilyDao {

	@Override
	public String save(Family family) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Family family) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Family> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
/*	
	@Autowired
	DriverManagerDataSource dataSource;
	
	@Override
	public String save(Family family) {
		String result="OK";
		try {
			Connection con=dataSource.getConnection();
			Statement stmt=con.createStatement();
			stmt.execute("insert into family(name, description) values('"+family.getName()+"','"+family.getDescription()+"')");
		} catch (SQLException e) {
			result="NOK";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String delete(Family family) {
		String result="OK";
		try {
			Connection con=dataSource.getConnection();
			Statement stmt=con.createStatement();
			stmt.executeQuery("delete from family where name='"+family.getName()+"' and description='"+family.getDescription()+"')");
		} catch (SQLException e) {
			result="NOK";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Family> getAll() {
		List<Family> families=new ArrayList<Family>();
		try {
			Connection con=dataSource.getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from family");
			while(rs.next()){
				Family f=new Family();
				f.setName(rs.getString("name"));
				f.setDescription(rs.getString("description"));
				families.add(f);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return families;
	}
*/
}
