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

import org.hibernate.annotations.Type;

import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.OpcoesPagamento;
import br.com.controlpro.constants.StatusVale;
import br.com.controlpro.entity.consultas.Funcionario;

@Entity
@Table(name = "CONTROL_VALE")
public class Vale extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;
	
	@Type(type = "true_false")
	private Boolean pago;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status_vale")
	private StatusVale statusVale;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "loja")
	private Loja loja;
	
	@ManyToOne
	@JoinColumn(name = "id_funcionario")
	private Funcionario funcionario;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_vale")
	private Date dataVale = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_pagamento")
	private Date dataPagamento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "opcoes_pagamento")
	private OpcoesPagamento opcoesPagamento;
	
	@Column(name = "observacao", columnDefinition = "text")
	private String observacao;
	
	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor;

	@Transient
	private Date dataPesquisaInicio;

	@Transient
	private Date dataPesquisaFim;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDataVale() {
		return dataVale;
	}

	public void setDataVale(Date dataVale) {
		this.dataVale = dataVale;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Boolean getPago() {
		return pago;
	}
	
	public void setPago(Boolean pago) {
		this.pago = pago;
	}
	
	public StatusVale getStatusVale() {
		return statusVale;
	}
	
	public void setStatusVale(StatusVale statusVale) {
		this.statusVale = statusVale;
	}
	
	public Date getDataPagamento() {
		return dataPagamento;
	}
	
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	public OpcoesPagamento getOpcoesPagamento() {
		return opcoesPagamento;
	}
	
	public void setOpcoesPagamento(OpcoesPagamento opcoesPagamento) {
		this.opcoesPagamento = opcoesPagamento;
	}
	
	public Loja getLoja() {
		return loja;
	}
	
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	
}