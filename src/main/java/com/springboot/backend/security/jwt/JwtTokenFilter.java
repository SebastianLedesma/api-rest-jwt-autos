package com.springboot.backend.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.backend.security.services.UserDetailServiceImpl;

/**
 * Clase que se encarga de verificar que el token es valido.
 * @author ricardo
 *
 */
public class JwtTokenFilter extends OncePerRequestFilter{
	
	private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserDetailServiceImpl userDetailService;

	//Este medoto se ejecutara por cada peticion que se haga.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String token = this.getToken(request);
			
			if(token != null && this.jwtProvider.validateToken(token)) {//comprueba si el tokenes valido.
				String nombreUsuario = this.jwtProvider.getNombreUsuarioFromToken(token);//obtien el nombre del usuario.
				UserDetails userDetails = this.userDetailService.loadUserByUsername(nombreUsuario);//creamos un serdateials con ese nombre
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(auth);//se pasa el usuario al contexto de autentication.
			}
		}catch(Exception e) {
			logger.error("fail en el metodo doFilter."+e.getMessage());
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	private String getToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		
		if(header != null && header.startsWith("Bearer")) {
			return header.replace("Bearer", "");
		}
		
		return null;
	}

}
