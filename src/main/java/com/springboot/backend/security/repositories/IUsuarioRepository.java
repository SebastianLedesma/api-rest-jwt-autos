package com.springboot.backend.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.backend.security.entities.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("select u from Usuario u where u.nombreUsuario = :filtro")
	Optional<Usuario> findByNombreUsuario(@Param("filtro") String nombreUsuario);
	
	//@Query("select u from Usuario u where u.nombreUsuario = :filtro")
	boolean existsByNombreUsuario(String nombreUsuario);
	
	//@Query("select u from Usuario u where u.email = :filtro")
	boolean existsByEmail(String email);
}
