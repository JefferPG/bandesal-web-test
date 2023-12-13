package com.jeffer.bandesal.web.application.bean;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.jeffer.bandesal.web.utils.SessionUtil;

public abstract class AbstractBean {

	protected void validateSession() {
		try {
			HttpSession session = SessionUtil.getSession();

			if (session.getAttribute("user") == null) {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				context.redirect("/bandesal-web/pages/logout.xhtml");
			}
		} catch (IOException e) {
			throw new NullPointerException(e.getLocalizedMessage());
		}
	}

}
