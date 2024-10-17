package br.com.controlpro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.bo.BancoBO;
import br.com.controlpro.entity.consultas.Banco;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@ViewScoped
public class BancoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508477123324402389L;

	
	private Banco banco;
	private Banco bancoFilter;
	private List<Banco> listaBancos;
	private LazyEntityDataModel<Banco> lazy;
	
	@PostConstruct
	public void init(){
		banco = new Banco();
		bancoFilter = new Banco();
		listaBancos = new ArrayList<Banco>();
		lazy = new LazyEntityDataModel<Banco>(Banco.class);
	}

	public String list() {
		lazy = BancoBO.getInstance().bancosLazy(bancoFilter);
		return null;
	}
	
	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public List<Banco> getListaBancos() {
		try {
			listaBancos = BancoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return listaBancos;
	}
	
	public void setListaBancos(List<Banco> listaBancos) {
		this.listaBancos = listaBancos;
	}

	public Banco getBancoFilter() {
		return bancoFilter;
	}

	public void setBancoFilter(Banco bancoFilter) {
		this.bancoFilter = bancoFilter;
	}

	public LazyEntityDataModel<Banco> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Banco> lazy) {
		this.lazy = lazy;
	}
	
}
