package com.dtcc.service;

import java.util.List;

import com.dtcc.model.Family;

public interface FamilyService {
	abstract String save(Family family);
	abstract String delete(Family family);
	abstract List<Family> getAll();	
}
