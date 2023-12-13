package com.jeffer.bandesal.web.application.converter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.jeffer.bandesal.web.application.dao.BlogDao;
import com.jeffer.bandesal.web.application.model.Blog;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ManagedBean
public class BlogConverter implements Converter {

	@ManagedProperty(value = "#{blogDao}")
	private BlogDao blogDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty() || value.equals("null"))
			return null;

		return blogDao.findById(Integer.valueOf(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || String.valueOf(value).equals(""))
			return "";

		if (!(value instanceof Blog))
			throw new ConverterException(new FacesMessage(value + " no es un Blog válido"));

		return String.valueOf(((Blog) value).getId());
	}

}
