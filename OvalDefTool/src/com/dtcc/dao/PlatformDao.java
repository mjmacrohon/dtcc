package com.dtcc.dao;

import java.util.List;

import com.dtcc.model.Platform;

public interface PlatformDao {
	abstract String save(Platform platform);
	abstract String delete(Platform platform);
	abstract List<Platform> findByFamily(String family);
}
