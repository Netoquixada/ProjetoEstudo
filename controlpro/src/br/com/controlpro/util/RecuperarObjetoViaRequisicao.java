package br.com.controlpro.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.hibernateutil.core.GenericDao;

public class RecuperarObjetoViaRequisicao {

	public static <T> T getObjeto(Class<T> t, String parameterName) {

		T aux = null;
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		if (request.getParameter(parameterName) != null) {
			try {
				Integer id = Integer.valueOf(request
						.getParameter(parameterName));
				aux = (T) GenericDao.findById(t, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return aux;
	}
}
