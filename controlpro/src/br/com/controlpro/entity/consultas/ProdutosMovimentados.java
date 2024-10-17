package br.com.controlpro.entity.consultas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdutosMovimentados implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;
	
	public ProdutosMovimentados() {
		
	}
	
	public ProdutosMovimentados(String produto, String nome, BigDecimal vendido, BigDecimal saida) {
		this.produto = produto;
		this.nome = nome;
		this.vendido = vendido;
		this.saida = saida;
	}

	private String produto;
	private String nome;
	private BigDecimal vendido;
	private BigDecimal saida;
	
	
	private Date dataInicioFilter = new Date();
	private Date dataFimFilter = new Date();

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getVendido() {
		return vendido;
	}

	public void setVendido(BigDecimal vendido) {
		this.vendido = vendido;
	}

	public BigDecimal getSaida() {
		return saida;
	}

	public void setSaida(BigDecimal saida) {
		this.saida = saida;
	}

	public Date getDataInicioFilter() {
		return dataInicioFilter;
	}

	public void setDataInicioFilter(Date dataInicioFilter) {
		this.dataInicioFilter = dataInicioFilter;
	}

	public Date getDataFimFilter() {
		return dataFimFilter;
	}

	public void setDataFimFilter(Date dataFimFilter) {
		this.dataFimFilter = dataFimFilter;
	}


	
}