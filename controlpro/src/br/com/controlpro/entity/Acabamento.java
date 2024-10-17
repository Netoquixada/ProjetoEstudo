package br.com.controlpro.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.OpcoesPagamento;
import br.com.controlpro.constants.OrigemAcabamento;
import br.com.controlpro.constants.SituacaoOrdem;
import br.com.controlpro.constants.StatusAcabamento;
import br.com.controlpro.constants.TipoCostura;


@Entity
@Table(name = "CONTROL_ACABAMENTO")
public class Acabamento extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4983331588533261636L;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();

	@Column(name = "qtd_dias")
	private Integer qtdDias;
	
	@ManyToOne
	@JoinColumn(name = "faccao_id")
	private Faccao faccao;
	
	@Enumerated(EnumType.STRING)
	private Loja loja;
	
	@Enumerated(EnumType.STRING)
	private OrigemAcabamento origem;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "previsao_termino")
	private Date previsaoTermino;
	
	@Column(columnDefinition = "text")
	private String observacao;
	
	
	private String protocolo;
	
	@OneToMany(mappedBy = "acabamento",fetch=FetchType.LAZY)
	private List<ItemAcabamento> itensAcabamento;
	
	@OneToMany(mappedBy = "acabamento",fetch=FetchType.LAZY)
	private List<ItemEnvolvidoAcabamento> itensEnvolvidosAcabamento;
	
	@Transient
	private Date dataCadastroInicio;

	
	@Transient
	private Date dataCadastroFim;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_pagamento")
	private Date dataPagamento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "opcoes_pagamento")
	private OpcoesPagamento opcoesPagamento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status_acabamento")
	private StatusAcabamento statusAcabamento = StatusAcabamento.ABERTO;

	@Enumerated(EnumType.STRING)
	private SituacaoOrdem situacao;
	
	@Enumerated(EnumType.STRING)
	private TipoCostura tipoCostura;
	
	
	/**Aqui abaixo fica os Get e Sets**/
	
	
	

	public Loja getLoja() {
		return loja;
	}

	public TipoCostura getTipoCostura() {
		return tipoCostura;
	}

	public void setTipoCostura(TipoCostura tipoCostura) {
		this.tipoCostura = tipoCostura;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}

	public Date getPrevisaoTermino() {
		return previsaoTermino;
	}

	public void setPrevisaoTermino(Date previsaoTermino) {
		this.previsaoTermino = previsaoTermino;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public Date getDataCadastroInicio() {
		return dataCadastroInicio;
	}

	public void setDataCadastroInicio(Date dataCadastroInicio) {
		this.dataCadastroInicio = dataCadastroInicio;
	}

	public Date getDataCadastroFim() {
		return dataCadastroFim;
	}

	public void setDataCadastroFim(Date dataCadastroFim) {
		this.dataCadastroFim = dataCadastroFim;
	}
	
	public Faccao getFaccao() {
		return faccao;
	}
	
	public void setFaccao(Faccao faccao) {
		this.faccao = faccao;
	}

	public List<ItemAcabamento> getItensAcabamento() {
		return itensAcabamento;
	}

	public void setItensAcabamento(List<ItemAcabamento> itensAcabamento) {
		this.itensAcabamento = itensAcabamento;
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

	public StatusAcabamento getStatusAcabamento() {
		return statusAcabamento;
	}

	public void setStatusAcabamento(StatusAcabamento statusAcabamento) {
		this.statusAcabamento = statusAcabamento;
	}
	
	public SituacaoOrdem getSituacao() {
		return situacao;
	}
	
	public void setSituacao(SituacaoOrdem situacao) {
		this.situacao = situacao;
	}

	public OrigemAcabamento getOrigem() {
		return origem;
	}
	
	public void setOrigem(OrigemAcabamento origem) {
		this.origem = origem;
	}
	
	public List<ItemEnvolvidoAcabamento> getItensEnvolvidosAcabamento() {
		return itensEnvolvidosAcabamento;
	}
	
	public void setItensEnvolvidosAcabamento(List<ItemEnvolvidoAcabamento> itensEnvolvidosAcabamento) {
		this.itensEnvolvidosAcabamento = itensEnvolvidosAcabamento;
	} 
	
}
