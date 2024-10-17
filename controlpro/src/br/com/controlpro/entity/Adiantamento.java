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

import br.com.controlpro.constants.OpcoesPagamento;
import br.com.controlpro.constants.StatusVale;
import br.com.controlpro.constants.TipoAdiantamento;

@Entity
@Table(name = "CONTROL_ADIANTAMENTO")
public class Adiantamento extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;
	
	@Type(type = "true_false")
	private Boolean pago;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status_adiantamento")
	private StatusVale statusAdiantamento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoAdiantamento tipoAdiantamento;
	
	@ManyToOne
	@JoinColumn(name = "id_faccao")
	private Faccao faccao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data")
	private Date data = new Date();
	
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

	public TipoAdiantamento getTipoAdiantamento() {
		return tipoAdiantamento;
	}

	public void setTipoAdiantamento(TipoAdiantamento tipoAdiantamento) {
		this.tipoAdiantamento = tipoAdiantamento;
	}

	public Faccao getFaccao() {
		return faccao;
	}

	public void setFaccao(Faccao faccao) {
		this.faccao = faccao;
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
	
	public StatusVale getStatusAdiantamento() {
		return statusAdiantamento;
	}
	
	public void setStatusAdiantamento(StatusVale statusAdiantamento) {
		this.statusAdiantamento = statusAdiantamento;
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
	
	
}