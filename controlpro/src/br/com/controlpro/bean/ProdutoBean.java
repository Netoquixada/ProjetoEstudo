package br.com.controlpro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.bo.ProdutoBO;
import br.com.controlpro.entity.consultas.Produto;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508477123324402389L;

	private Produto produto;
	private Produto produtoFilter;
	private List<Produto> listaProdutos;
	private LazyEntityDataModel<Produto> lazy;

	@PostConstruct
	public void init() {
		produto = new Produto();
		produtoFilter = new Produto();
		listaProdutos = new ArrayList<Produto>();
		lazy = new LazyEntityDataModel<Produto>(Produto.class);
	}

	public String list() {
		lazy = ProdutoBO.getInstance().produtosLazy(produtoFilter);
		return null;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getListaProdutos() {
		try {
			listaProdutos = ProdutoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return listaProdutos;
	}

	public List<Produto> getProdutoAutoCompletePorFornecedor(String codFornecedor) {

		List<Produto> produtosComplete = new ArrayList<Produto>();

		try {
			produtosComplete = ProdutoBO.getInstance()
					.produtosCompleteCodFornecedor(codFornecedor);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return produtosComplete;
	}
	public List<Produto> getProdutoAutoCompletePorProduto(String codProduto) {
		
		List<Produto> produtosComplete = new ArrayList<Produto>();
		
		try {
			produtosComplete = ProdutoBO.getInstance()
					.produtosCompleteCodProduto(codProduto);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return produtosComplete;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Produto getProdutoFilter() {
		return produtoFilter;
	}

	public void setProdutoFilter(Produto produtoFilter) {
		this.produtoFilter = produtoFilter;
	}

	public LazyEntityDataModel<Produto> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Produto> lazy) {
		this.lazy = lazy;
	}

}
