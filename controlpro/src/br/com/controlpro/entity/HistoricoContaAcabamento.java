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

@Entity
@Table(name = "CONTROL_HISTORICO_CONTA_ACABAMENTO")
public class HistoricoContaAcabamento extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;
	
	public HistoricoContaAcabamento() {
		// TODO Auto-generated constructor stub
	}
	public HistoricoContaAcabamento(Faccao faccao, Date inicio, Date fim) {
		this.faccao = faccao;
		this.dataPesquisaInicio = inicio;
		this.dataPesquisaFim = fim;
	}
	
	@ManyToOne
	@JoinColumn(name = "id_conta_pagar_acabamento")
	private ContaPagarAcabamento contaPagarAcabamento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_pagamento")
	private Date dataPagamento = new Date();
	
	@ManyToOne
	private Faccao faccao;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Column(name = "valor_juros", columnDefinition="numeric", scale = 2, precision = 9)
	private BigDecimal valorJuros = new BigDecimal(0);
	
	@Column(name = "valor_desconto", columnDefinition="numeric", scale = 2, precision = 9)
	private BigDecimal valorDesconto = new BigDecimal(0);
	
	@Column(name = "valor_pago", columnDefinition="numeric", scale = 2, precision = 9)
	private BigDecimal valorPago = new BigDecimal(0);
	
	@Enumerated(EnumType.STRING)
	@Column(name = "opcao_pagamento")
	private OpcoesPagamento opcaoPagamento;
	
	@Column(name = "numero_cheque")
	private String numeroCheque;
	
	@Column(name = "banco_cheque")
	private String bancoCheque;
	
	@Transient
	private Date dataPesquisaInicio = new Date();

	@Transient
	private Date dataPesquisaFim = new Date();

	public ContaPagarAcabamento getContaPagarAcabamento() {
		return contaPagarAcabamento;
	}
	
	public void setContaPagarAcabamento(ContaPagarAcabamento contaPagarAcabamento) {
		this.contaPagarAcabamento = contaPagarAcabamento;
	}
	
	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(BigDecimal valorJuros) {
		this.valorJuros = valorJuros;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public OpcoesPagamento getOpcaoPagamento() {
		return opcaoPagamento;
	}

	public void setOpcaoPagamento(OpcoesPagamento opcaoPagamento) {
		this.opcaoPagamento = opcaoPagamento;
	}

	public String getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public String getBancoCheque() {
		return bancoCheque;
	}

	public void setBancoCheque(String bancoCheque) {
		this.bancoCheque = bancoCheque;
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
	
	public Faccao getFaccao() {
		return faccao;
	}
	
	public void setFaccao(Faccao faccao) {
		this.faccao = faccao;
	}

}