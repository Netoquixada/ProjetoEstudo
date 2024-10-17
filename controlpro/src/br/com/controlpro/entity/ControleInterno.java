package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.controlpro.constants.LocalControleInterno;
import br.com.controlpro.constants.RelatoControleInterno;
import br.com.controlpro.constants.TipoControleInterno;

@Entity
@Table(name = "CONTROL_CONTROLE_INTERNO")
public class ControleInterno extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();
	
	@Enumerated(EnumType.STRING)
	@Column(name = "local_controle_interno")
	private LocalControleInterno localControleInterno;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_controle_interno")
	private TipoControleInterno tipoControleInterno;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "relato_controle_interno")
	private RelatoControleInterno relatoControleInterno;
	
	@Column(name = "pedido")
	private String pedido;
	
	@Column(name = "observacao", columnDefinition = "text")
	private String observacao;
	
	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor;

	@Transient
	private Date dataPesquisaInicio;

	@Transient
	private Date dataPesquisaFim;

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalControleInterno getLocalControleInterno() {
		return localControleInterno;
	}

	public void setLocalControleInterno(LocalControleInterno localControleInterno) {
		this.localControleInterno = localControleInterno;
	}

	public TipoControleInterno getTipoControleInterno() {
		return tipoControleInterno;
	}

	public void setTipoControleInterno(TipoControleInterno tipoControleInterno) {
		this.tipoControleInterno = tipoControleInterno;
	}

	public RelatoControleInterno getRelatoControleInterno() {
		return relatoControleInterno;
	}

	public void setRelatoControleInterno(RelatoControleInterno relatoControleInterno) {
		this.relatoControleInterno = relatoControleInterno;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	
}