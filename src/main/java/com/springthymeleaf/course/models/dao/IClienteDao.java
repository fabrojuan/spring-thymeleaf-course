package com.springthymeleaf.course.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springthymeleaf.course.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {

//	List<Cliente> findAll();
//	
//	void save(Cliente cliente);
//	
//	Cliente findOne(Long id);
//	
//	void delete(Long id);
}
