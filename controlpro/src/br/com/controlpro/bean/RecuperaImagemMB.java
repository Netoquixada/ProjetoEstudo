package br.com.controlpro.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.controlpro.dao.ProdutoConsumoDao;

@ManagedBean
@RequestScoped
public class RecuperaImagemMB implements Serializable {

	private static final long serialVersionUID = 1L;

	public StreamedContent getImage() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		byte[] photoData = ProdutoConsumoDao.getInstance().getById(Integer.valueOf(id)).getArquivo();
		return new DefaultStreamedContent(new ByteArrayInputStream(photoData));
	}
	
}
