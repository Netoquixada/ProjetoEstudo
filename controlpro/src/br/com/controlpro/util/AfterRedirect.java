package br.com.controlpro.util;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

@SuppressWarnings("unchecked")
public class AfterRedirect {

	/**
	 * Usado para recuperar um objeto inserido no escopo flassh. Deve-se fornecer a chave do objeto a ser recuperado.
	 * 
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static <T> T recuperarObjetoDoFlash(Class<T> clazz, String key) {
		return (T) flashScope().get(key);
	}

	/**
	 * Recupera um Flash
	 * 
	 * @return
	 */
	public static Flash flashScope() {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash();
	}
	
	/**
	 * M�todo utilizado para inserir um objeto no escopo flash.
	 * 
	 * @param key
	 * @param value
	 */
	public static void inserirObjetoNoFlash(String key, Object value){
		flashScope().put(key, value);
	}
	
	/**
	 * M�todo utilizado para permitir que a mensagem seja enviada ap�s o redirecionamento.
	 * 
	 */
	public static void manterMensagem(){
		flashScope().setKeepMessages(true);
	}

}
