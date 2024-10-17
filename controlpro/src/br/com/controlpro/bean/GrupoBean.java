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

import br.com.controlpro.bo.GrupoBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.entity.Grupo;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.util.AfterRedirect;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@RequestScoped
public class GrupoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Grupo grupo;
	private Grupo grupoFilter;
	private LazyEntityDataModel<Grupo> lazy;
	private List<Grupo> grupos;

	@PostConstruct
	public void init() {
		grupo = new Grupo();
		grupoFilter = new Grupo();
		lazy = new LazyEntityDataModel<Grupo>(Grupo.class);
		grupos = new ArrayList<Grupo>();
	}
	
	public void save() {
		try {
			GrupoBO.getInstance().save(grupo);
			addInfoMessage("Cadastrado com sucesso!", "Grupo " + grupo.getNome());
			grupo = new Grupo();
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
			GrupoBO.getInstance().update(grupo);
			addInfoMessage("Editado com sucesso!", "Grupo " + grupo.getNome());
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
		grupo = new Grupo();
		AfterRedirect.manterMensagem();
		return "/private/control/controlGrupo.xhtml?faces-redirect=true";
	}

	public String list() {
		lazy = GrupoBO.getInstance().gruposLazy(grupoFilter);
		return null;
	}

	public String updateStatus() {
		try {
			if (grupo.getStatus()) {
				grupo.setStatus(false);
				GrupoBO.getInstance().update(grupo);
			} else {
				grupo.setStatus(true);
				GrupoBO.getInstance().update(grupo);
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
		String status = grupo.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O grupo está " + status);
		return null;
	}
	
	public Estados[] getEstados(){
		return Estados.values();
	}
	
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Grupo getGrupoFilter() {
		return grupoFilter;
	}

	public void setGrupoFilter(Grupo grupoFilter) {
		this.grupoFilter = grupoFilter;
	}

	public LazyEntityDataModel<Grupo> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Grupo> lazy) {
		this.lazy = lazy;
	}

	public List<Grupo> getGrupos() {
		try {
			grupos = GrupoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
}
