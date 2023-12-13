package com.jeffer.bandesal.web.application.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.jeffer.bandesal.web.application.model.Reader;
import com.jeffer.bandesal.web.utils.JpaUtil;

@ApplicationScoped
@ManagedBean
public class ReaderDao {

	EntityManager entity = JpaUtil.getEntityManagerFactory().createEntityManager();

	public List<Reader> getAll() {
		List<Reader> listaClientes = new ArrayList<>();
		TypedQuery<Reader> query = entity.createQuery("SELECT r FROM Reader r", Reader.class);
		listaClientes = query.getResultList();
		return listaClientes;
	}

	public Reader findById(Integer id) {
		return entity.find(Reader.class, id);
	}

	public Reader findByName(String name) {
		TypedQuery<Reader> query = entity.createQuery("SELECT r FROM Reader r WHERE r.name = :name", Reader.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}

	public Reader save(Reader reader) {
		entity.getTransaction().begin();
		entity.persist(reader);
		entity.flush();
		entity.getTransaction().commit();
		return reader;
	}

	public Reader update(Reader reader) {
		entity.getTransaction().begin();
		entity.merge(reader);
		entity.getTransaction().commit();
		return reader;
	}

	public void deleteById(Integer id) {
		Reader reader = new Reader();
		reader = findById(id);
		entity.getTransaction().begin();
		entity.remove(reader);
		entity.getTransaction().commit();
	}

}
