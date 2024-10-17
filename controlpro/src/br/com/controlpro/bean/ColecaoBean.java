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

import br.com.controlpro.bo.ColecaoBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.entity.Colecao;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.util.AfterRedirect;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@RequestScoped
public class ColecaoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Colecao colecao;
	private Colecao colecaoFilter;
	private LazyEntityDataModel<Colecao> lazy;
	private List<Colecao> colecaos;

	@PostConstruct
	public void init() {
		colecao = new Colecao();
		colecaoFilter = new Colecao();
		lazy = new LazyEntityDataModel<Colecao>(Colecao.class);
		colecaos = new ArrayList<Colecao>();
	}
	
	public void save() {
		try {
			ColecaoBO.getInstance().save(colecao);
			addInfoMessage("Cadastrado com sucesso!", "Colecao " + colecao.getNome());
			colecao = new Colecao();
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
			ColecaoBO.getInstance().update(colecao);
			addInfoMessage("Editado com sucesso!", "Colecao " + colecao.getNome());
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
		colecao = new Colecao();
		AfterRedirect.manterMensagem();
		return "/private/control/controlColecao.xhtml?faces-redirect=true";
	}

	public String list() {
		lazy = ColecaoBO.getInstance().colecaosLazy(colecaoFilter);
		return null;
	}

	public String updateStatus() {
		try {
			if (colecao.getStatus()) {
				colecao.setStatus(false);
				ColecaoBO.getInstance().update(colecao);
			} else {
				colecao.setStatus(true);
				ColecaoBO.getInstance().update(colecao);
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
		String status = colecao.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O colecao est� " + status);
		return null;
	}
	
	public Estados[] getEstados(){
		return Estados.values();
	}
	
	public Colecao getColecao() {
		return colecao;
	}

	public void setColecao(Colecao colecao) {
		this.colecao = colecao;
	}

	public Colecao getColecaoFilter() {
		return colecaoFilter;
	}

	public void setColecaoFilter(Colecao colecaoFilter) {
		this.colecaoFilter = colecaoFilter;
	}

	public LazyEntityDataModel<Colecao> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Colecao> lazy) {
		this.lazy = lazy;
	}

	public List<Colecao> getColecaos() {
		try {
			colecaos = ColecaoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return colecaos;
	}

	public void setColecaos(List<Colecao> colecaos) {
		this.colecaos = colecaos;
	}
}
