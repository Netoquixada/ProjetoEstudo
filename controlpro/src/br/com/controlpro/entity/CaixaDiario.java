package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.controlpro.constants.OpcoesPagamento;
import br.com.controlpro.entity.consultas.AdmCartao;
import br.com.controlpro.entity.consultas.ContaBancaria;

@Entity
@Table(name = "CONTROL_CAIXA_DIARIO")
public class CaixaDiario extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	
	private String tipo;
	
	private String venda;
	
	@Column(name = "numero_pedido")
	private String numeroPedido;	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data")
	private Date data = new Date();

	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor;

	@Enumerated(EnumType.STRING)
	@Column(name = "opcoes_pagamento")
	private OpcoesPagamento opcoesPagamento;
	
	@ManyToOne
	@JoinColumn(name = "conta_bancaria")
	private ContaBancaria contaBAncaria;

	@ManyToOne
	@JoinColumn(name = "adm_cartao")
	private AdmCartao admCartao;

	
	private String descricao;
	
	@Transient
	private Date dataPesquisaInicio;

	@Transient
	private Date dataPesquisaFim;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getVenda() {
		return venda;
	}

	public void setVenda(String venda) {
		this.venda = venda;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public OpcoesPagamento getOpcoesPagamento() {
		return opcoesPagamento;
	}

	public void setOpcoesPagamento(OpcoesPagamento opcoesPagamento) {
		this.opcoesPagamento = opcoesPagamento;
	}

	public ContaBancaria getContaBAncaria() {
		return contaBAncaria;
	}

	public void setContaBAncaria(ContaBancaria contaBAncaria) {
		this.contaBAncaria = contaBAncaria;
	}

	public AdmCartao getAdmCartao() {
		return admCartao;
	}

	public void setAdmCartao(AdmCartao admCartao) {
		this.admCartao = admCartao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataPesquisaInicio() {
		return dataPesquisaInicio;
	}

	public void setDataPesquisaInicio(Date dataPesquisaInicio) {
		this.dataPesquisaInicio = dataPesquisaInicio;
	}

	public Date getDataPesquisaFim() {
		return dataPesquisaFim;
	}

	public void setDataPesquisaFim(Date dataPesquisaFim) {
		this.dataPesquisaFim = dataPesquisaFim;
	}
	
}