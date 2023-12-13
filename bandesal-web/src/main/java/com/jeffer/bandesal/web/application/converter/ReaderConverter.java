package com.jeffer.bandesal.web.application.converter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.jeffer.bandesal.web.application.dao.ReaderDao;
import com.jeffer.bandesal.web.application.model.Reader;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ManagedBean
public class ReaderConverter implements Converter {

	@ManagedProperty(value = "#{readerDao}")
    private ReaderDao readerDao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	if (value == null || value.isEmpty() || value.equals("null"))
            return null;

        return readerDao.findById(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
    	if (value == null || String.valueOf(value).equals(""))
			return "";

    	if (!(value instanceof Reader))
			throw new ConverterException(new FacesMessage(value + " no es un Reader válido"));
    	
    	return String.valueOf(((Reader) value).getId());
    }
}
