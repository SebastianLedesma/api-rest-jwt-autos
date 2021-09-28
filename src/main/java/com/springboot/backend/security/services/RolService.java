package com.springboot.backend.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.security.entities.Rol;
import com.springboot.backend.security.enums.ERolNombre;
import com.springboot.backend.security.repositories.IRolRepository;

@Service
@Transactional
public class RolService {

	@Autowired
	private IRolRepository rolRepo;
	
	public Optional<Rol> getByNombre(ERolNombre rolNombre){
		return this.rolRepo.findByRol(rolNombre);
	}
	
	public void save(Rol rol) {
		this.rolRepo.save(rol);
	}
}
