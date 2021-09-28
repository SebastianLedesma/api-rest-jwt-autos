package com.springboot.backend.security.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.springboot.backend.entities.Base;
import com.springboot.backend.security.enums.ERolNombre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rol extends Base{

	@NotNull
	@Column(name="rol_nombre")
	@Enumerated(EnumType.STRING)
	private ERolNombre rolNombre;
}
