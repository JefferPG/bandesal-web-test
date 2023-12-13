package com.jeffer.bandesal.web.utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@RequestScoped
@ManagedBean
public class ErrorHandler {

	public String getStatusCode() {
		return String.valueOf((Integer) FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.status_code"));
	}

	public String getMessage() {
		return String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.message"));
	}

	public String getExceptionType() {
		return String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.exception_type"));
	}

	public String getException() {
		String val = String.valueOf(((Exception) FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.exception")));
		return val;
	}

	public String getRequestURI() {
		return String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.request_uri"));
	}

	public String getServletName() {
		return String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.servlet_name"));
	}

}