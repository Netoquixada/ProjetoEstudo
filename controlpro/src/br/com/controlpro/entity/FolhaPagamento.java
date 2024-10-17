package br.com.controlpro.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.TipoFolhaPagamento;

@Entity
@Table(name = "CONTROL_FOLHA_PAGAMENTO")
public class FolhaPagamento extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4983331588533261636L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();
	
	@Column(name = "protocolo")
	private String protocolo;
	
	@Column(name = "referencia")
	private String referencia;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_folha_pagamento")
	private TipoFolhaPagamento TipoFolhaPagamento;

	@Enumerated(EnumType.STRING)
	@Column(name = "loja")
	private Loja loja;
	
	@Column(columnDefinition = "text")
	private String observacao;

	@OneToMany(mappedBy = "folhaPagamento", fetch = FetchType.LAZY)
	private List<ItemFolhaPagamento> itensFolhaPagamento;

	@Transient
	private Date dataCadastroInicio;

	@Transient
	private Date dataCadastroFim;

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public TipoFolhaPagamento getTipoFolhaPagamento() {
		return TipoFolhaPagamento;
	}

	public void setTipoFolhaPagamento(TipoFolhaPagamento tipoFolhaPagamento) {
		TipoFolhaPagamento = tipoFolhaPagamento;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<ItemFolhaPagamento> getItensFolhaPagamento() {
		return itensFolhaPagamento;
	}

	public void setItensFolhaPagamento(List<ItemFolhaPagamento> itensFolhaPagamento) {
		this.itensFolhaPagamento = itensFolhaPagamento;
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
	
}
