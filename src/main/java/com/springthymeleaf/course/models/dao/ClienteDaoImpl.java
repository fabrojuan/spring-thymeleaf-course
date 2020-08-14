package com.springthymeleaf.course.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.springthymeleaf.course.models.entity.Cliente;

@Repository
public class ClienteDaoImpl {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	//@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();
	}

	//@Override
	public void save(Cliente cliente) {
		if(cliente.getId() != null && cliente.getId() > 0) {
			em.merge(cliente);
		} else {
			em.persist(cliente);
		}
	}

	//@Override
	public Cliente findOne(Long id) {
		Cliente cliente = em.find(Cliente.class, id);
		return cliente;
	}

	//@Override
	public void delete(Long id) {
		Cliente cliente = findOne(id);
		em.remove(cliente);		
	}
}
