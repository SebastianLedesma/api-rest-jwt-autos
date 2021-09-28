package com.springboot.backend.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.security.entities.Usuario;
import com.springboot.backend.security.repositories.IUsuarioRepository;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepo;
	
	
	public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
		return this.usuarioRepo.findByNombreUsuario(nombreUsuario);
	}
	
	
	public boolean existByNombreUsuario(String nombreUsuario) {
		return this.usuarioRepo.existsByNombreUsuario(nombreUsuario);
	}
	
	
	public boolean existsByEmail(String email) {
		return this.usuarioRepo.existsByEmail(email);
	}
	
	public void save(Usuario usuario) {
		this.usuarioRepo.save(usuario);
	}
}
