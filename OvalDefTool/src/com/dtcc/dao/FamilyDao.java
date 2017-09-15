package com.dtcc.dao;

import java.util.List;

import com.dtcc.model.Family;

public interface FamilyDao {
	abstract String save(Family family);
	abstract String delete(Family family);
	abstract List<Family> getAll();	
}
