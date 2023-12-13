package com.jeffer.bandesal.web.application.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.jeffer.bandesal.web.application.dao.UserDao;
import com.jeffer.bandesal.web.application.model.User;
import com.jeffer.bandesal.web.utils.SessionUtil;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@SessionScoped
@ManagedBean
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userDao}")
	private UserDao userDao;

	private User user;
	private boolean isLogged = false;

	@PostConstruct
	public void init() {
		user = new User();
	}

	public void loginUser() throws IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		boolean isValid = userDao.findByUserAndPass(user.getUsername(), user.getPassword());

		if (isValid) {
			HttpSession session = SessionUtil.getSession();
			session.setAttribute("user", user);

		    context.redirect("/bandesal-web/pages/index.xhtml");
		} else {
		    context.redirect("/bandesal-web/pages/logout.xhtml");
		}

	}

}
