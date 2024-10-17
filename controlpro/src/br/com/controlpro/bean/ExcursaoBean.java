package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.controlpro.bo.ExcursaoBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.entity.Excursao;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.util.AfterRedirect;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@RequestScoped
public class ExcursaoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Excursao excursao;
	private Excursao excursaoFilter;
	private LazyEntityDataModel<Excursao> lazy;
	private List<Excursao> excursaos;

	@PostConstruct
	public void init() {
		excursao = new Excursao();
		excursaoFilter = new Excursao();
		lazy = new LazyEntityDataModel<Excursao>(Excursao.class);
		excursaos = new ArrayList<Excursao>();
	}
	
	public void save() {
		try {
			ExcursaoBO.getInstance().save(excursao);
			addInfoMessage("Cadastrado com sucesso!", "Excursao " + excursao.getNome());
			excursao = new Excursao();
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
	}

	public String update() {
		try {
			ExcursaoBO.getInstance().update(excursao);
			addInfoMessage("Editado com sucesso!", "Excursao " + excursao.getNome());
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
		excursao = new Excursao();
		AfterRedirect.manterMensagem();
		return "/private/control/controlExcursao.xhtml?faces-redirect=true";
	}

	public String list() {
		lazy = ExcursaoBO.getInstance().excursaosLazy(excursaoFilter);
		return null;
	}

	public String updateStatus() {
		try {
			if (excursao.getStatus()) {
				excursao.setStatus(false);
				ExcursaoBO.getInstance().update(excursao);
			} else {
				excursao.setStatus(true);
				ExcursaoBO.getInstance().update(excursao);
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
		String status = excursao.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O excursao est� " + status);
		return null;
	}
	
	public List<Excursao> getExcursaoAutoComplete(String nome) {

		List<Excursao> faccoesComplete = new ArrayList<Excursao>();

		try {
			faccoesComplete = ExcursaoBO.getInstance().excursaosComplete(nome);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return faccoesComplete;
	}
	
	public Estados[] getEstados(){
		return Estados.values();
	}
	
	public Excursao getExcursao() {
		return excursao;
	}

	public void setExcursao(Excursao excursao) {
		this.excursao = excursao;
	}

	public Excursao getExcursaoFilter() {
		return excursaoFilter;
	}

	public void setExcursaoFilter(Excursao excursaoFilter) {
		this.excursaoFilter = excursaoFilter;
	}

	public LazyEntityDataModel<Excursao> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Excursao> lazy) {
		this.lazy = lazy;
	}

	public List<Excursao> getExcursaos() {
		try {
			excursaos = ExcursaoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return excursaos;
	}

	public void setExcursaos(List<Excursao> excursaos) {
		this.excursaos = excursaos;
	}
}
