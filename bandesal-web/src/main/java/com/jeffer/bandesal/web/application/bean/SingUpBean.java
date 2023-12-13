package com.jeffer.bandesal.web.application.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.jeffer.bandesal.web.application.dao.UserDao;
import com.jeffer.bandesal.web.application.model.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@SessionScoped
@ManagedBean
public class SingUpBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userDao}")
	private UserDao userDao;

	private User user;

	@PostConstruct
	public void init() {
		cleanObjects();
	}

	private void cleanObjects() {
		user = new User();
	}

	public void saveUser() {
		userDao.save(user);

		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "User " + user.getUsername() + " added"));

		cleanObjects();

		PrimeFaces.current().ajax().update("form:messages", "form");
	}

}
