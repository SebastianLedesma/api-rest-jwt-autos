package com.springboot.backend.security.entities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que se usará para implementar los privilegios de usuario.
 * @author ricardo
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioPrincipal implements UserDetails{

	@NotNull
	@Column(name="nombre")
	private String nombre;
	
	@NotNull
	@Column(name="nombreUsuario",unique=true)
	private String nombreUsuario;
	
	@NotNull
	@Column(name="email")
	private String email;
	
	@NotNull
	@Column(name="password")
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;

	public static UsuarioPrincipal build(Usuario usuario) {
		List<GrantedAuthority> authorities = usuario.getRoles()
											.stream()
											.map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name()))
											.collect(Collectors.toList());
	
	
		return new UsuarioPrincipal(usuario.getNombre(),usuario.getNombreUsuario(),usuario.getEmail(),usuario.getPassword(),authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.nombreUsuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
