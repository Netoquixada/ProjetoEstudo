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

import br.com.controlpro.bo.SubGrupoBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.entity.SubGrupo;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.util.AfterRedirect;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@RequestScoped
public class SubGrupoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private SubGrupo subSubGrupo;
	private SubGrupo subSubGrupoFilter;
	private LazyEntityDataModel<SubGrupo> lazy;
	private List<SubGrupo> subSubGrupos;

	@PostConstruct
	public void init() {
		subSubGrupo = new SubGrupo();
		subSubGrupoFilter = new SubGrupo();
		lazy = new LazyEntityDataModel<SubGrupo>(SubGrupo.class);
		subSubGrupos = new ArrayList<SubGrupo>();
	}
	
	public void save() {
		try {
			SubGrupoBO.getInstance().save(subSubGrupo);
			addInfoMessage("Cadastrado com sucesso!", "SubGrupo " + subSubGrupo.getNome());
			subSubGrupo = new SubGrupo();
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
			SubGrupoBO.getInstance().update(subSubGrupo);
			addInfoMessage("Editado com sucesso!", "SubGrupo " + subSubGrupo.getNome());
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
		subSubGrupo = new SubGrupo();
		AfterRedirect.manterMensagem();
		return "/private/control/controlSubGrupo.xhtml?faces-redirect=true";
	}

	public String list() {
		lazy = SubGrupoBO.getInstance().subGruposLazy(subSubGrupoFilter);
		return null;
	}

	public String updateStatus() {
		try {
			if (subSubGrupo.getStatus()) {
				subSubGrupo.setStatus(false);
				SubGrupoBO.getInstance().update(subSubGrupo);
			} else {
				subSubGrupo.setStatus(true);
				SubGrupoBO.getInstance().update(subSubGrupo);
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
		String status = subSubGrupo.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O subSubGrupo est� " + status);
		return null;
	}
	
	public Estados[] getEstados(){
		return Estados.values();
	}
	
	public SubGrupo getSubGrupo() {
		return subSubGrupo;
	}

	public void setSubGrupo(SubGrupo subSubGrupo) {
		this.subSubGrupo = subSubGrupo;
	}

	public SubGrupo getSubGrupoFilter() {
		return subSubGrupoFilter;
	}

	public void setSubGrupoFilter(SubGrupo subSubGrupoFilter) {
		this.subSubGrupoFilter = subSubGrupoFilter;
	}

	public LazyEntityDataModel<SubGrupo> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<SubGrupo> lazy) {
		this.lazy = lazy;
	}

	public List<SubGrupo> getSubGrupos() {
		try {
			subSubGrupos = SubGrupoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return subSubGrupos;
	}

	public void setSubGrupos(List<SubGrupo> subSubGrupos) {
		this.subSubGrupos = subSubGrupos;
	}
}
