package br.com.controlpro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.bo.ProdutosMovimentadosBO;
import br.com.controlpro.entity.consultas.ProdutosMovimentados;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class ProdutosMovimentadosMB implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;


	private ProdutosMovimentados produtosMovimentados;
	private List<ProdutosMovimentados> produtosMovimentadosList;

	@PostConstruct
	public void init() {
		produtosMovimentados = new ProdutosMovimentados();
		produtosMovimentadosList = new ArrayList<>();
	}

	public String list() {
		try {
			produtosMovimentadosList = ProdutosMovimentadosBO.getInstance().produtosMovimentados(produtosMovimentados);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public Integer getTotalQuantidade() {
//		Integer valor = 0;
//		for (ProdutosMovimentados item : produtosMovimentadosList) {
//			valor = valor + item.getQuantidade();
//		}
//		return valor;
//	}

	public ProdutosMovimentados getProdutosMovimentados() {
		return produtosMovimentados;
	}

	public void setProdutosMovimentados(ProdutosMovimentados produtosMovimentados) {
		this.produtosMovimentados = produtosMovimentados;
	}
	
	public List<ProdutosMovimentados> getProdutosMovimentadosList() {
		return produtosMovimentadosList;
	}

	public void setProdutosMovimentadosList(List<ProdutosMovimentados> produtosMovimentadosList) {
		this.produtosMovimentadosList = produtosMovimentadosList;
	}

}