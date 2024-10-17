package br.com.controlpro.util;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class RecuperaMensagemComFlash {
	
   public static Flash flashScope (){
	return FacesContext.getCurrentInstance().getExternalContext().getFlash();
   }
}
