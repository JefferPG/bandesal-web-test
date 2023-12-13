package com.jeffer.bandesal.web.application.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.jeffer.bandesal.web.application.dao.ReaderDao;
import com.jeffer.bandesal.web.application.model.Reader;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@SessionScoped
@ManagedBean
public class ReaderBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{readerDao}")
	private ReaderDao readerDao;

	private List<Reader> readerList;
	private Reader reader;

	@PostConstruct
	public void init() {
		validateSession();

		cleanObjects();
	}

	private void cleanObjects() {
		reader = new Reader();
		readerList = readerDao.getAll();
	}

	public void newReader() {
		reader = new Reader();
	}

	public void saveReader() {
		boolean isNew = (reader.getId() == null);

		if (isNew) {
			readerDao.save(reader);
			readerList.add(reader);
		} else {
			readerDao.update(reader);
		}

		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Lector " + reader.getName() + (isNew ? " added" : " updated")));

		PrimeFaces.current().executeScript("PF('wvdSaveReader').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:dtReaders");
	}

	public void deleteReader() {
		try {
			readerDao.deleteById(reader.getId());

			readerList.remove(reader);

			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "The reader " + reader.getName() + " deleted"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "The reader " + reader.getName() + " could not be deleted"));
		}

		PrimeFaces.current().ajax().update("form:messages", "form:dtReaders");
	}

}
