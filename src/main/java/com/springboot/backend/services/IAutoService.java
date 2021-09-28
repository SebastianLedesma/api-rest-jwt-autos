package com.springboot.backend.services;

import java.util.List;

import com.springboot.backend.entities.Auto;

public interface IAutoService {

	List<Auto> getAll();
	
	Auto getOne(Long id);
	
	Auto save(Auto auto);
	
	Auto update(Long id,Auto auto);
	
	void delete(Long id);
}
