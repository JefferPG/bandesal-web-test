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

import com.jeffer.bandesal.web.application.dao.BlogDao;
import com.jeffer.bandesal.web.application.dao.BlogReaderDao;
import com.jeffer.bandesal.web.application.dao.ReaderDao;
import com.jeffer.bandesal.web.application.model.Blog;
import com.jeffer.bandesal.web.application.model.BlogReader;
import com.jeffer.bandesal.web.application.model.Reader;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@SessionScoped
@ManagedBean
public class BlogReaderBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{blogDao}")
	private BlogDao blogDao;

	@ManagedProperty(value = "#{readerDao}")
	private ReaderDao readerDao;

	@ManagedProperty(value = "#{blogReaderDao}")
	private BlogReaderDao blogReaderDao;

	private BlogReader blogReader;

	private List<Blog> blogList;
	private List<Reader> readerList;
	private List<BlogReader> blogReaderList;

	@PostConstruct
	public void init() {
		validateSession();

		cleanObjects();
	}

	private void cleanObjects() {
		blogList = blogDao.getAll();
		readerList = readerDao.getAll();
		blogReaderList = blogReaderDao.getAll();

		blogReader = new BlogReader();
	}

	public void newBlogReader() {
		blogReader = new BlogReader();
	}

	public void saveBlogReader() {
		boolean isNew = (blogReader.getId() == null);

		blogReader = blogReaderDao.save(blogReader);

		if (isNew) {
			blogReaderDao.save(blogReader);
			blogReaderList.add(blogReader);
		} else {
			blogReaderDao.update(blogReader);
		}

		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "BlogReader " +  (isNew ? " added" : " updated")));

		PrimeFaces.current().executeScript("PF('wvdSaveBlogReader').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:dtBlogReaders");
	}

	public void deleteBlogReader() {
		try {
			blogReaderDao.deleteById(blogReader.getId());

			blogReaderList.remove(blogReader);

			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "The BlogReader deleted"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "The BlogReader could not be deleted"));
		}

		PrimeFaces.current().ajax().update("form:messages", "form:dtBlogReaders");
	}

}
