package br.com.controlpro.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagens implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 681078823418731136L;

	public static void addErrorMessage(String sumary, String detail) {
		FacesContext instance = FacesContext.getCurrentInstance();
		instance.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				sumary, detail));
	}

	public static void addInfoMessage(String sumary, String detail) {
		FacesContext instance = FacesContext.getCurrentInstance();
		instance.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				sumary, detail));
	}

	public static void addWarnMessage(String sumary, String detail) {
		FacesContext instance = FacesContext.getCurrentInstance();
		instance.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
				sumary, detail));
	}

	public static void addFatalMessage(String sumary, String detail) {
		FacesContext instance = FacesContext.getCurrentInstance();
		instance.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
				sumary, detail));
	}

}
