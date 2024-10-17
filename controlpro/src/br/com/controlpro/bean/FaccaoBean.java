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
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.bo.CidadeBO;
import br.com.controlpro.bo.FaccaoBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.entity.Faccao;
import br.com.controlpro.entity.consultas.Cidade;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@RequestScoped
public class FaccaoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarFaccao.xhtml";
	private final String LIST = "/private/lista/listarFaccao.xhtml?faces-redirect=true";

	private Faccao faccao;
	private Faccao faccaoFilter;
	private List<Faccao> faccaos;
	private List<Cidade> cidades;

	@PostConstruct
	public void init() {
		faccao = new Faccao();
		faccaoFilter = new Faccao();
		faccaos = new ArrayList<Faccao>();
		cidades = new ArrayList<Cidade>();
	}

	public String save() {
		try {
			faccao.setStatus(true);
			FaccaoBO.getInstance().save(faccao);
			addInfoMessage("Cadastrado com sucesso!", "Faccao " + faccao.getNome());
			faccao = new Faccao();
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
			FaccaoBO.getInstance().update(faccao);
			addInfoMessage("Editado com sucesso!", "Faccao " + faccao.getNome());
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
		faccao = new Faccao();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public String list() {
		try {
			faccaos = FaccaoBO.getInstance().faccaoListReport(faccaoFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, faccaos, "faccoes",
				"Relatório de facções - " + DataUtil.formatarData(new Date()), true);
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		faccao = new Faccao();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (faccao.getStatus()) {
				faccao.setStatus(false);
				FaccaoBO.getInstance().update(faccao);
			} else {
				faccao.setStatus(true);
				FaccaoBO.getInstance().update(faccao);
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
		String status = faccao.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O faccao est� " + status);
		return null;
	}
	
	public List<Faccao> getFaccaoAutoComplete(String nomeFaccao) {

		List<Faccao> faccoesComplete = new ArrayList<Faccao>();

		try {
			faccoesComplete = FaccaoBO.getInstance().faccaosComplete(nomeFaccao);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return faccoesComplete;
	}
	
	public List<Faccao> getListaFaccoesList() {
		try {
			faccaos  = FaccaoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return faccaos;
	}
	
	public Estados[] getEstados(){
		return Estados.values();
	}
	
	public Faccao getFaccao() {
		return faccao;
	}

	public void setFaccao(Faccao faccao) {
		this.faccao = faccao;
	}

	public Faccao getFaccaoFilter() {
		return faccaoFilter;
	}

	public void setFaccaoFilter(Faccao faccaoFilter) {
		this.faccaoFilter = faccaoFilter;
	}


	public List<Faccao> getFaccaos() {
		return faccaos;
	}

	public void setFaccaos(List<Faccao> faccaos) {
		this.faccaos = faccaos;
	}
	
	public List<Cidade> getCidades() {
		try {
			cidades = CidadeBO.getInstance().cidadesPorEstado(faccao.getEstado());
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return cidades;
	}
	
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
}
