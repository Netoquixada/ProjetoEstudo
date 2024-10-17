package br.com.controlpro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.bo.FuncionarioBO;
import br.com.controlpro.entity.consultas.Funcionario;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508477123324402389L;

	
	private Funcionario funcionario;
	private Funcionario funcionarioFilter;
	private List<Funcionario> listaFuncionarios;
	private LazyEntityDataModel<Funcionario> lazy;
	
	@PostConstruct
	public void init(){
		funcionario = new Funcionario();
		funcionarioFilter = new Funcionario();
		listaFuncionarios = new ArrayList<Funcionario>();
		lazy = new LazyEntityDataModel<Funcionario>(Funcionario.class);
	}

	public String list() {
		lazy = FuncionarioBO.getInstance().funcionariosLazy(funcionarioFilter);
		return null;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getListaFuncionarios() {
		try {
			listaFuncionarios = FuncionarioBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return listaFuncionarios;
	}
	
	public List<Funcionario> getFuncionarioAutoComplete(String funcionarioName) {

		List<Funcionario> funcionariosComplete = new ArrayList<Funcionario>();

		try {
			funcionariosComplete = FuncionarioBO.getInstance()
					.funcionariosComplete(funcionarioName);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return funcionariosComplete;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public Funcionario getFuncionarioFilter() {
		return funcionarioFilter;
	}

	public void setFuncionarioFilter(Funcionario funcionarioFilter) {
		this.funcionarioFilter = funcionarioFilter;
	}

	public LazyEntityDataModel<Funcionario> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Funcionario> lazy) {
		this.lazy = lazy;
	}
	
}
