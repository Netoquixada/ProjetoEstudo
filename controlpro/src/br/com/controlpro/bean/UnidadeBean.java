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

import br.com.controlpro.bo.UnidadeBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.entity.Unidade;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.util.AfterRedirect;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@RequestScoped
public class UnidadeBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Unidade unidade;
	private Unidade unidadeFilter;
	private LazyEntityDataModel<Unidade> lazy;
	private List<Unidade> unidades;

	@PostConstruct
	public void init() {
		unidade = new Unidade();
		unidadeFilter = new Unidade();
		lazy = new LazyEntityDataModel<Unidade>(Unidade.class);
		unidades = new ArrayList<Unidade>();
	}
	
	public void save() {
		try {
			UnidadeBO.getInstance().save(unidade);
			addInfoMessage("Cadastrado com sucesso!", "Unidade " + unidade.getNome());
			unidade = new Unidade();
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
			UnidadeBO.getInstance().update(unidade);
			addInfoMessage("Editado com sucesso!", "Unidade " + unidade.getNome());
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
		unidade = new Unidade();
		AfterRedirect.manterMensagem();
		return "/private/control/controlUnidade.xhtml?faces-redirect=true";
	}

	public String list() {
		lazy = UnidadeBO.getInstance().unidadesLazy(unidadeFilter);
		return null;
	}

	public String updateStatus() {
		try {
			if (unidade.getStatus()) {
				unidade.setStatus(false);
				UnidadeBO.getInstance().update(unidade);
			} else {
				unidade.setStatus(true);
				UnidadeBO.getInstance().update(unidade);
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
		String status = unidade.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O unidade está " + status);
		return null;
	}
	
	public Estados[] getEstados(){
		return Estados.values();
	}
	
	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Unidade getUnidadeFilter() {
		return unidadeFilter;
	}

	public void setUnidadeFilter(Unidade unidadeFilter) {
		this.unidadeFilter = unidadeFilter;
	}

	public LazyEntityDataModel<Unidade> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Unidade> lazy) {
		this.lazy = lazy;
	}

	public List<Unidade> getUnidades() {
		try {
			unidades = UnidadeBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}
}
