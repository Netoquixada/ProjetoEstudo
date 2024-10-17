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

import br.com.controlpro.constants.StatusDespesaFixa;

@Entity
@Table(name = "CONTROL_ITEM_DESPESA")
public class ItemDespesaFixa extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;
	
	private String descricao;

	@Column(name = "dia_pagamento")
	private Integer diaPagamento;
	
	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor = new BigDecimal(0);
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status_despesa_fixa")
	private StatusDespesaFixa statusDespesaFixa;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_confirmacao")
	private Date dataConfirmacao;

	@ManyToOne
	@JoinColumn(name = "despesa_fixa_id")
	private DespesaFixa despesaFixa;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getDiaPagamento() {
		return diaPagamento;
	}

	public void setDiaPagamento(Integer diaPagamento) {
		this.diaPagamento = diaPagamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public DespesaFixa getDespesaFixa() {
		return despesaFixa;
	}

	public void setDespesaFixa(DespesaFixa despesaFixa) {
		this.despesaFixa = despesaFixa;
	}

	public StatusDespesaFixa getStatusDespesaFixa() {
		return statusDespesaFixa;
	}

	public void setStatusDespesaFixa(StatusDespesaFixa statusDespesaFixa) {
		this.statusDespesaFixa = statusDespesaFixa;
	}

	public Date getDataConfirmacao() {
		return dataConfirmacao;
	}

	public void setDataConfirmacao(Date dataConfirmacao) {
		this.dataConfirmacao = dataConfirmacao;
	}
	
}
