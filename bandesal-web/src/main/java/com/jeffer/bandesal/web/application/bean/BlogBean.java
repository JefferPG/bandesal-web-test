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
import com.jeffer.bandesal.web.application.model.Blog;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@SessionScoped
@ManagedBean
public class BlogBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{blogDao}")
	private BlogDao blogDao;

	private List<Blog> blogList;
	private Blog blog;

	@PostConstruct
	public void init() {
		validateSession();

		cleanObjects();
	}

	private void cleanObjects() {
		blog = new Blog();
		blogList = blogDao.getAll();
	}

	public void newBlog() {
		blog = new Blog();
	}

	public void saveBlog() {
		boolean isNew = (blog.getId() == null);

		if (isNew) {
			blogDao.save(blog);
			blogList.add(blog);
		} else {
			blogDao.update(blog);
		}

		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Blog " + blog.getTittle() + (isNew ? " added" : " updated")));

		PrimeFaces.current().executeScript("PF('wvdSaveBlog').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:dtBlogs");
	}

	public void deleteBlog() {
		try {
			blogDao.deleteById(blog.getId());

			blogList.remove(blog);

			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "The blog " + blog.getTittle() + " deleted"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "The blog " + blog.getTittle() + " could not be deleted"));
		}

		PrimeFaces.current().ajax().update("form:messages", "form:dtBlogs");
	}

}
