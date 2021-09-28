package com.springboot.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="autos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Auto extends Base{

	@NotEmpty(message="no puede estar vacio.")
	@Column(name="marca",nullable=false)
	private String marca;
	
	@NotEmpty(message="no puede estar vacio.")
	@Column(name="modelo",nullable=false)
	private String modelo;
	
	
	@NotNull(message="debe tener un precio")
	@Range(min=90000,max=600000,message="debe tener un precio entre $90.000 y $600.000")
	@Column(name="precio",nullable=false)
	private Integer precio;
	
	@NotEmpty(message="debe tener un color.")
	@Column(name="color",nullable=false)
	private String color;
}
