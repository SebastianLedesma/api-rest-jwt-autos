package com.springboot.backend.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.backend.security.entities.Rol;
import com.springboot.backend.security.enums.ERolNombre;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long>{

	@Query("select r from Rol r where r.rolNombre = :filtro")
	Optional<Rol> findByRol(@Param("filtro") ERolNombre rolNombre);
}
