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
import br.com.controlpro.bo.MotoboyBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.entity.Motoboy;
import br.com.controlpro.entity.consultas.Cidade;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@RequestScoped
public class MotoboyBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String LIST = "/private/control/controlMotoboy.xhtml?faces-redirect=true";

	private Motoboy motoboy;
	private Motoboy motoboyFilter;
	private List<Motoboy> motoboys;

	@PostConstruct
	public void init() {
		motoboy = new Motoboy();
		motoboyFilter = new Motoboy();
		motoboys = new ArrayList<Motoboy>();
	}

	public void save() {
		try {
			MotoboyBO.getInstance().save(motoboy);
			addInfoMessage("Cadastrado com sucesso!", "Motoboy " + motoboy.getNome());
			motoboy = new Motoboy();
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
			MotoboyBO.getInstance().update(motoboy);
			addInfoMessage("Editado com sucesso!", "Motoboy " + motoboy.getNome());
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
		motoboy = new Motoboy();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public String list() {
		try {
			motoboys = MotoboyBO.getInstance().motoboyListReport(motoboyFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String goToList() {
		return LIST;
	}

	public String updateStatus() {
		try {
			if (motoboy.getStatus()) {
				motoboy.setStatus(false);
				MotoboyBO.getInstance().update(motoboy);
			} else {
				motoboy.setStatus(true);
				MotoboyBO.getInstance().update(motoboy);
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
		String status = motoboy.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O motoboy est� " + status);
		return null;
	}
	
	public List<Motoboy> getMotoboyAutoComplete(String nomeMotoboy) {

		List<Motoboy> faccoesComplete = new ArrayList<Motoboy>();

		try {
			faccoesComplete = MotoboyBO.getInstance().motoboysComplete(nomeMotoboy);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return faccoesComplete;
	}
	
	public Motoboy getMotoboy() {
		return motoboy;
	}

	public void setMotoboy(Motoboy motoboy) {
		this.motoboy = motoboy;
	}

	public Motoboy getMotoboyFilter() {
		return motoboyFilter;
	}

	public void setMotoboyFilter(Motoboy motoboyFilter) {
		this.motoboyFilter = motoboyFilter;
	}


	public List<Motoboy> getMotoboys() {
		try {
			motoboys = MotoboyBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return motoboys;
	}

	public void setMotoboys(List<Motoboy> motoboys) {
		this.motoboys = motoboys;
	}
	
}
