package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

import br.com.controlpro.bo.ConferenciaBO;
import br.com.controlpro.bo.ValeBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.StatusConferencia;
import br.com.controlpro.constants.Tipo;
import br.com.controlpro.entity.Conferencia;
import br.com.controlpro.entity.Vale;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.com.controlpro.util.DownloadArquivoUtil;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@RequestScoped
public class ConferenciaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarConferencia.xhtml";
	private final String LIST = "/private/lista/listarConferencia.xhtml?faces-redirect=true";

	private Conferencia conferencia;
	private Conferencia conferenciaFilter;
	private LazyEntityDataModel<Conferencia> lazy;
	private List<Conferencia> conferencias;

	private StreamedContent arquivo;

	@PostConstruct
	public void init() {
		conferencia = new Conferencia();
		conferenciaFilter = new Conferencia();
		lazy = new LazyEntityDataModel<Conferencia>(Conferencia.class);
		conferencias = new ArrayList<Conferencia>();
	}

	public String save() {
		try {
			ConferenciaBO.getInstance().save(conferencia);
			addInfoMessage("Cadastrado com sucesso!", "");
			conferencia = new Conferencia();
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario", "erro.salvar.entidade.campo.existente");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
		return CAD_EDIT;
	}

	public String update() {
		try {
			ConferenciaBO.getInstance().update(conferencia);
			addInfoMessage("Editado com sucesso!", "");
		} catch (ViolacaoChaveHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		}
		conferencia = new Conferencia();
		AfterRedirect.manterMensagem();
		return LIST;
	}
	
	public void gerarPDF(ActionEvent ev) {
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();

			List<Conferencia> conferencias = ConferenciaBO.getInstance().conferenciaListReport(conferenciaFilter);

			conferenciaFilter = new Conferencia();
			mapa.put("filtro", ConferenciaBO.getInstance().dadosFiltro());

			GenericReport
					.gerarPdfComJRBeanCollectionDataSource(
							mapa,
							conferencias,
							"conferencia",
							"Relatório de Conferências - "
									+ DataUtil.formatarData(new Date()), true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	// Upload do arquivo
	public void uploadFile(FileUploadEvent event) {
		try {
			this.conferencia.setExtensao(getExtensao(event.getFile().getFileName()));
			this.conferencia.setArquivo(event.getFile().getContents());
			FacesMessage message = new FacesMessage("Enviado com sucesso!", event.getFile().getFileName());
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			e.printStackTrace();
			addWarnMessage("Erro: " + e.getMessage(), "");
		}
	}

	public StreamedContent downloadArquivo() {
		try {
			arquivo = DownloadArquivoUtil.getInstance().downloadArquivo(
					ConferenciaBO.getInstance().buscarArquivo(conferencia.getId()), arquivo, "Comprovante",
					conferencia.getExtensao());
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
		}
		return arquivo;
	}

	public String getExtensao(String nomeArquivo) {
		if (nomeArquivo.isEmpty() || nomeArquivo == null)
			return null;
		return nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length()).toLowerCase();
	}

	public Loja[] getLojas() {
		return Loja.values();
	}

	public Tipo[] getTipos() {
		return Tipo.values();
	}

	public StatusConferencia[] getStatusConferencias() {
		return StatusConferencia.values();
	}

	public String list() {
		lazy = ConferenciaBO.getInstance().conferenciasLazy(conferenciaFilter);
		return null;
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		conferencia = new Conferencia();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (conferencia.getStatus()) {
				conferencia.setStatus(false);
				ConferenciaBO.getInstance().update(conferencia);
			} else {
				conferencia.setStatus(true);
				ConferenciaBO.getInstance().update(conferencia);
			}
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro!", "Erro: " + e.getMessage());
			e.printStackTrace();
		}
		String status = conferencia.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O conferencia est� " + status);
		return null;
	}

	public Conferencia getConferencia() {
		return conferencia;
	}

	public void setConferencia(Conferencia conferencia) {
		this.conferencia = conferencia;
	}

	public Conferencia getConferenciaFilter() {
		return conferenciaFilter;
	}

	public void setConferenciaFilter(Conferencia conferenciaFilter) {
		this.conferenciaFilter = conferenciaFilter;
	}

	public LazyEntityDataModel<Conferencia> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Conferencia> lazy) {
		this.lazy = lazy;
	}

	public List<Conferencia> getConferencias() {
		return conferencias;
	}

	public void setConferencias(List<Conferencia> conferencias) {
		this.conferencias = conferencias;
	}

	public StreamedContent getArquivo() {
		return arquivo;
	}

	public void setArquivo(StreamedContent arquivo) {
		this.arquivo = arquivo;
	}

}
