package com.jeffer.bandesal.web.application.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.jeffer.bandesal.web.application.model.User;
import com.jeffer.bandesal.web.utils.JpaUtil;

@ApplicationScoped
@ManagedBean
public class UserDao {

	EntityManager entity = JpaUtil.getEntityManagerFactory().createEntityManager();

	public List<User> getAll() {
		List<User> listaClientes = new ArrayList<>();
		TypedQuery<User> query = entity.createQuery("SELECT u.id, u.pass FROM User u", User.class);
		listaClientes = query.getResultList();
		return listaClientes;
	}

	public User findById(Integer id) {
		return entity.find(User.class, id);
	}

	public boolean findByUserAndPass(String username, String password) {
		TypedQuery<User> query = entity.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);

		query.setParameter("username", username);

		try {
			User user = query.getSingleResult();

			return BCrypt.checkpw(password, user.getPassword());
		} catch (NoResultException nre) {
			return false;
		}
	}

	public void save(User user) {
		String salt = BCrypt.gensalt(7);
		
		user.setPassword(BCrypt.hashpw(user.getPassword(), salt));

		entity.getTransaction().begin();
		entity.persist(user);
		entity.flush();
		entity.getTransaction().commit();
	}

}
