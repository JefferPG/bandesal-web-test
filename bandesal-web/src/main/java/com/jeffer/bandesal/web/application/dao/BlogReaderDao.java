package com.jeffer.bandesal.web.application.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.jeffer.bandesal.web.application.model.BlogReader;
import com.jeffer.bandesal.web.utils.JpaUtil;

@ApplicationScoped
@ManagedBean
public class BlogReaderDao {

	EntityManager entity = JpaUtil.getEntityManagerFactory().createEntityManager();

	public List<BlogReader> getAll() {
		List<BlogReader> listaClientes = new ArrayList<>();
		TypedQuery<BlogReader> query = entity.createQuery("SELECT br FROM BlogReader br", BlogReader.class);
		listaClientes = query.getResultList();
		return listaClientes;
	}

	public BlogReader findById(Integer id) {
		return entity.find(BlogReader.class, id);
	}

	public BlogReader save(BlogReader blogReader) {
		entity.getTransaction().begin();
		entity.persist(blogReader);
		entity.flush();
		entity.getTransaction().commit();
		return blogReader;
	}

	public BlogReader update(BlogReader blogReader) {
		entity.getTransaction().begin();
		entity.merge(blogReader);
		entity.getTransaction().commit();
		return blogReader;
	}

	public void deleteById(Integer id) {
		BlogReader blogReader = new BlogReader();
		blogReader = findById(id);
		entity.getTransaction().begin();
		entity.remove(blogReader);
		entity.getTransaction().commit();
	}

}
