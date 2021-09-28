package com.springboot.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.entities.Auto;
import com.springboot.backend.repositories.IAutoRepository;


@Service
public class AutoServiceImpl implements IAutoService{

	@Autowired
	private IAutoRepository autoRepository;
	
	@Override
	@Transactional(readOnly=true)
	public List<Auto> getAll() {
		return this.autoRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Auto getOne(Long id) {
		return this.autoRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Auto save(Auto auto) {
		return this.autoRepository.save(auto);
	}

	@Override
	@Transactional
	public Auto update(Long id, Auto auto) {
		Optional<Auto> autoOptional= this.autoRepository.findById(id);
		Auto autoNew = autoOptional.get();
		autoNew = this.autoRepository.save(auto);
		return autoNew;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.autoRepository.deleteById(id);
	}
	
}
