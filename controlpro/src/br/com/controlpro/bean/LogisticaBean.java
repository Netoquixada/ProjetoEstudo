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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.LogisticaBO;
import br.com.controlpro.constants.LocalLogistica;
import br.com.controlpro.constants.StatusLogistica;
import br.com.controlpro.constants.TipoEnvioLogistica;
import br.com.controlpro.entity.Logistica;
import br.com.controlpro.entity.LogisticaAud;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.com.controlpro.util.ManagedBeanHelper;
import br.com.controlpro.util.RecuperarObjetoViaRequisicao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@ViewScoped
public class LogisticaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarLogistica.xhtml";
	private final String LIST = "/private/lista/listarLogistica.xhtml?faces-redirect=true";

	private Usuario usuarioLogado;

	private Logistica logistica;
	private Logistica logisticaFilter;
	private List<Logistica> logisticas = new ArrayList<Logistica>();

	private LogisticaAud logisticaAudFilter;
	private List<LogisticaAud> logisticasAud = new ArrayList<LogisticaAud>();

	@PostConstruct
	public void init() {

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		logistica = new Logistica();
		logisticaFilter = new Logistica();
		
		logisticaAudFilter = new LogisticaAud();
		logisticasAud = new ArrayList<>();

		Logistica aux = RecuperarObjetoViaRequisicao.getObjeto(Logistica.class, "id");
		logistica = aux != null ? aux : new Logistica();

		// CASO SEJA EDI��O DE DADOS
		if (logistica.getId() != null) {
		}

	}

	public String save() {
		try {
			LogisticaBO.getInstance().save(logistica);
			
			LogisticaBO.getInstance().saveAud(insertAuditoria(logistica));
			
			addInfoMessage("Cadastrado com sucesso!", "");
			logistica = new Logistica();
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
			LogisticaBO.getInstance().update(logistica);

			LogisticaBO.getInstance().saveAud(insertAuditoria(logistica));

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
		logistica = new Logistica();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public String list() {
		try {
			logisticas = LogisticaBO.getInstance().logisticaListReport(logisticaFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		list();
		
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, logisticas, "logistica",
				"Relatório de Logisticas - " + DataUtil.formatarData(new Date()), true);
	}


	public String listAuitoria() {
		try {
			logisticasAud = LogisticaBO.getInstance().logisticaListAuditoria(logisticaAudFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public LogisticaAud insertAuditoria(Logistica logistica) {
		LogisticaAud aud = new LogisticaAud();
		aud.setDataCadastro(logistica.getDataCadastro());
		aud.setDataEnvio(logistica.getDataEnvio());
		aud.setLocalLogistica(logistica.getLocalLogistica());
		aud.setMotoboy(logistica.getMotoboy());
		aud.setObservacao(logistica.getObservacao());
		aud.setPedido(logistica.getPedido());
		aud.setStatusLogistica(logistica.getStatusLogistica());
		aud.setDataAlteracao(new Date());
		aud.setUsuario(usuarioLogado);
		aud.setLogistica(logistica.getId());
		aud.setTipoEnvioLogistica(logistica.getTipoEnvioLogistica());
		aud.setExcursao(logistica.getExcursao());
		return aud;
	}

	public LocalLogistica[] getLocaisLogistica() {
		return LocalLogistica.values();
	}

	public StatusLogistica[] getStatusLogistica() {
		return StatusLogistica.values();
	}

	public TipoEnvioLogistica[] getTipoEnvioLogistica() {
		return TipoEnvioLogistica.values();
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		logistica = new Logistica();
		return CAD_EDIT;
	}

	public Logistica getLogistica() {
		return logistica;
	}

	public void setLogistica(Logistica logistica) {
		this.logistica = logistica;
	}

	public Logistica getLogisticaFilter() {
		return logisticaFilter;
	}

	public void setLogisticaFilter(Logistica logisticaFilter) {
		this.logisticaFilter = logisticaFilter;
	}

	public List<Logistica> getLogisticas() {
		return logisticas;
	}

	public void setLogisticas(List<Logistica> logisticas) {
		this.logisticas = logisticas;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public LogisticaAud getLogisticaAudFilter() {
		return logisticaAudFilter;
	}

	public void setLogisticaAudFilter(LogisticaAud logisticaAudFilter) {
		this.logisticaAudFilter = logisticaAudFilter;
	}

	public List<LogisticaAud> getLogisticasAud() {
		return logisticasAud;
	}

	public void setLogisticasAud(List<LogisticaAud> logisticasAud) {
		this.logisticasAud = logisticasAud;
	}

}
