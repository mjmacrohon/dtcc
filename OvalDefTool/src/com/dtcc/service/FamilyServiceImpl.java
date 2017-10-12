package com.dtcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dtcc.dao.FamilyDao;
import com.dtcc.model.Family;

@Service
@Transactional
public class FamilyServiceImpl implements FamilyService {

	@Autowired
	FamilyDao familyDao;
	
	@Override
	public String save(Family family) {
		return familyDao.save(family);
	}

	@Override
	public String delete(Family family) {
		return familyDao.delete(family);
	}

	@Override
	public List<Family> getAll() {
		return familyDao.getAll();
	}

}
