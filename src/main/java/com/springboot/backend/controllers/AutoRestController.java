package com.springboot.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.entities.Auto;
import com.springboot.backend.services.IAutoService;

import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin(origins="*")
@RequestMapping(path="/api/v2/autos")
public class AutoRestController{
	
	@Autowired
	private IAutoService autoService;
	
	
	@ApiOperation("Muestra todos los autos de la BD.")
	@GetMapping("")
	public ResponseEntity<?> getAll() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.autoService.getAll());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error\":\"no se pudo recuperar los autos.\"}");
		}
	}
	
	
	@ApiOperation("Muestra el auto según el id.")
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Auto auto = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			 auto = this.autoService.getOne(id);
		}catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(auto == null) {
			response.put("mensaje", "El auto con ID ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Auto>(auto,HttpStatus.OK);
	}
	
	
	@ApiOperation("Envía un auto para insertar en la BD(solo ADMIN).")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("")
	public ResponseEntity<?> save(@Valid @RequestBody Auto auto,BindingResult result) {
		Auto autoNew = null;
		Map<String,Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors =result.getFieldErrors()
										.stream()
										.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
										.collect(Collectors.toList());
			
			response.put("errors",errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			autoNew =  this.autoService.save(auto);
		}catch(DataAccessException ex) {
			response.put("mensaje", "Error al insertar el auto en la base de datos.");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El auto ha sido creado con  éxito.");
		response.put("registro", autoNew);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	
	@ApiOperation("Envía los campos de un auto para actualizar en la BD(solo ADMIN)")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Auto auto,BindingResult result,@PathVariable Long id) {
		Auto autoActual = this.autoService.getOne(id);
		Auto autoUpdate = null;
		Map<String,Object> response = new HashMap<>();
		
		if(autoActual == null) {
			response.put("mensaje", "Error,no se pudo editar.El auto con ID ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		if(result.hasErrors()) {
			List<String> errors =result.getFieldErrors()
										.stream()
										.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
										.collect(Collectors.toList());
			
			response.put("errors",errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		
		try {
			auto.setId(autoActual.getId());
			autoUpdate = this.autoService.update(id,auto);
		}catch(DataAccessException ex) {
			response.put("mensaje", "Error al actualizar el auto en la base de datos.");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El auto ha sido modificado con éxito.");
		response.put("registro", autoUpdate);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	
	@ApiOperation("Elimina un auto de la BD según el id(solo ADMIN).")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String,Object> response = new HashMap<>();
		
		try {
			this.autoService.delete(id);
		}catch(DataAccessException ex) {
			response.put("mensaje", "Error al eliminar el auto en la base de datos.");
			response.put("error", "El auto con ID "+ id +" no existe en la base de datos .");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El auto se ha eliminado de la base de datos.");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	
	
	
	
	
	
}
