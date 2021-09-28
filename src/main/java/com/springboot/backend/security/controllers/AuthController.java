package com.springboot.backend.security.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.security.dto.JwtDto;
import com.springboot.backend.security.dto.LoginUsuario;
import com.springboot.backend.security.dto.NuevoUsuario;
import com.springboot.backend.security.entities.Rol;
import com.springboot.backend.security.entities.Usuario;
import com.springboot.backend.security.enums.ERolNombre;
import com.springboot.backend.security.jwt.JwtProvider;
import com.springboot.backend.security.services.RolService;
import com.springboot.backend.security.services.UsuarioService;

@RestController
@RequestMapping(path="/auth")
@CrossOrigin(origins="*")
public class AuthController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	RolService rolService;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@PostMapping("/nuevo")
	public ResponseEntity<?> crearUsuario(@Valid @RequestBody NuevoUsuario nuevoUsuario,BindingResult results){
		Map<String,Object> response = new HashMap<>();
		if(results.hasErrors()) {
			List<String> errors =results.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors",errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(this.usuarioService.existByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
			response.put("errors","Ya existe un usuario con ese nombre.");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(this.usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
			response.put("errors","Ya existe un usuario con ese email.");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = new Usuario(nuevoUsuario.getNombre(),nuevoUsuario.getNombreUsuario(),nuevoUsuario.getEmail(),this.passwordEncoder.encode(nuevoUsuario.getPassword()));
		
		Set<Rol> roles = new HashSet<>();
		roles.add(this.rolService.getByNombre(ERolNombre.ROLE_USER).get());
		
		if(nuevoUsuario.getRoles().contains("admin")) {
			roles.add(this.rolService.getByNombre(ERolNombre.ROLE_ADMIN).get());
		}
		
		usuario.setRoles(roles);
		this.usuarioService.save(usuario);
		
		response.put("mensaje", "usuario guardado");
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario,BindingResult results){
		Map<String,Object> response = new HashMap<>();
		if(results.hasErrors()) {
			List<String> errors =results.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors",errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(),loginUsuario.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = this.jwtProvider.generateToken(authentication);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		JwtDto jwtDto = new JwtDto(jwt,userDetails.getUsername(),userDetails.getAuthorities());
		
		response.put("jwt",jwtDto);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
}
