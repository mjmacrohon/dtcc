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

import com.dtcc.model.Platform;

@Component
public class PlatformDaoImpl implements PlatformDao {

	@Override
	public String save(Platform platform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Platform platform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Platform> findByFamily(String family) {
		// TODO Auto-generated method stub
		return null;
	}
/*
	@Autowired
	DriverManagerDataSource dataSource;
	
	
	@Override
	public String save(Platform platform) {
		String result="OK";
		Connection con;
		try {
			con = dataSource.getConnection();
			Statement stmt=con.createStatement();
			stmt.execute("insert into platform(name,family,description) values('"+platform.getName()+"','"+platform.getFamily()+"','"+platform.getDescription()+"')");
			
		} catch (SQLException e) {
			result="NOK";
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String delete(Platform platform) {
		String result="OK";
		Connection con;
		try {
			con = dataSource.getConnection();
			Statement stmt=con.createStatement();
			stmt.execute("delete from platform where name='"+platform.getName()+"',family='"+platform.getFamily()+"'");
			
		} catch (SQLException e) {
			result="NOK";
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<Platform> findByFamily(String family) {
		List<Platform> platforms=new ArrayList<>();
		Connection con;
		try {
			con = dataSource.getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from platform where family='"+family+"' order by name");
			while(rs.next()){
				Platform p=new Platform();
				p.setName(rs.getString("name"));
				p.setFamily(rs.getString("family"));
				p.setDescription(rs.getString("description"));
				platforms.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return platforms;
	}
*/
}
