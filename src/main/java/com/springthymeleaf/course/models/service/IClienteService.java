package com.springthymeleaf.course.models.service;

import java.util.List;

import com.springthymeleaf.course.models.entity.Cliente;

public interface IClienteService {

	List<Cliente> findAll();
	
	void save(Cliente cliente);
	
	Cliente findOne(Long id);
	
	void delete(Long id);
}
