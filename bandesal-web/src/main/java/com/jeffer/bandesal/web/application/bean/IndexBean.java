package com.jeffer.bandesal.web.application.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@SessionScoped
@ManagedBean
public class IndexBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String selected;

	@PostConstruct
	public void init() {
		validateSession();
	}
	
}
