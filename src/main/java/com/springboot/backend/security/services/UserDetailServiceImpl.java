package com.springboot.backend.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.backend.security.entities.Usuario;
import com.springboot.backend.security.entities.UsuarioPrincipal;

/**
 * Clase que convierte al usuario en usuario principal.
 * @author ricardo
 *
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UsuarioService usuarioService;
	
	//Obitnene el usuariode la BD y lo convierte en usuario principal.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = this.usuarioService.getByNombreUsuario(username).get();
		return UsuarioPrincipal.build(usuario);
	}

}
