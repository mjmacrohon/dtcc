package com.dtcc.dao;

import java.util.ArrayList;
import java.util.List;

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
		List<Family> families=new ArrayList<Family>();
		Family f= new Family();
		f.setName("0");
		families.add(f);
		f= new Family();
		f.setName("none");
		families.add(f);
		f= new Family();
		f.setName("ios");
		families.add(f);
		f= new Family();
		f.setName("macos");
		families.add(f);
		f= new Family();
		f.setName("pixos");
		families.add(f);
		f= new Family();
		f.setName("unix");
		families.add(f);
		f= new Family();
		f.setName("windows");
		families.add(f);
		return families;
	}

}
