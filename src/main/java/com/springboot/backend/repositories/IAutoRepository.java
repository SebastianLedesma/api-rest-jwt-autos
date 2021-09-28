package com.springboot.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.backend.entities.Auto;

@Repository
public interface IAutoRepository extends JpaRepository<Auto, Long>{

}
