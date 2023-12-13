package com.jeffer.bandesal.web.application.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.jeffer.bandesal.web.application.model.Blog;
import com.jeffer.bandesal.web.utils.JpaUtil;

@ApplicationScoped
@ManagedBean
public class BlogDao {

	EntityManager entity = JpaUtil.getEntityManagerFactory().createEntityManager();

	public List<Blog> getAll() {
		List<Blog> listaClientes = new ArrayList<>();
		TypedQuery<Blog> query = entity.createQuery("SELECT b FROM Blog b", Blog.class);
		listaClientes = query.getResultList();
		return listaClientes;
	}

	public Blog findById(Integer id) {
		return entity.find(Blog.class, id);
	}

	public Blog findByTittle(String tittle) {
		TypedQuery<Blog> query = entity.createQuery("SELECT b FROM Blog b WHERE r.tittle = :tittle", Blog.class);
		query.setParameter("tittle", tittle);
		return query.getSingleResult();
	}

	public Blog save(Blog blog) {
		entity.getTransaction().begin();
		entity.persist(blog);
		entity.flush();
		entity.getTransaction().commit();
		return blog;
	}

	public Blog update(Blog blog) {
		entity.getTransaction().begin();
		entity.merge(blog);
		entity.getTransaction().commit();
		return blog;
	}

	public void deleteById(Integer id) {
		Blog blog = new Blog();
		blog = findById(id);
		entity.getTransaction().begin();
		entity.remove(blog);
		entity.getTransaction().commit();
	}

}
