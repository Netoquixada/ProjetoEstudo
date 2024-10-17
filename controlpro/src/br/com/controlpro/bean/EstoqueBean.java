package br.com.controlpro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.bo.EstoqueBO;
import br.com.controlpro.entity.consultas.Estoque;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class EstoqueBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508477123324402389L;

	private Estoque estoque;
	private Estoque estoqueFilter;
	private List<Estoque> listaEstoques;

	@PostConstruct
	public void init() {
		estoque = new Estoque();
		estoqueFilter = new Estoque();
		listaEstoques = new ArrayList<Estoque>();
	}

	public String list() {
		try {
			listaEstoques = EstoqueBO.getInstance().estoqueListReport(estoqueFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public List<Estoque> getListaEstoques() {
		return listaEstoques;
	}

	public void setListaEstoques(List<Estoque> listaEstoques) {
		this.listaEstoques = listaEstoques;
	}

	public Estoque getEstoqueFilter() {
		return estoqueFilter;
	}

	public void setEstoqueFilter(Estoque estoqueFilter) {
		this.estoqueFilter = estoqueFilter;
	}

}
