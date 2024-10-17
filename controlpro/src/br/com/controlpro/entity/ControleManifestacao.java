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

import br.com.controlpro.constants.Envio;
import br.com.controlpro.constants.LocalControleInterno;

@Entity
@Table(name = "CONTROL_CONTROLE_MANIFESTACAO")
public class ControleManifestacao extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4983331588533261636L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();

	@Column(name = "status_ocorrencia")
	private Integer statusOcorrencia = 0;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_envio")
	private Envio tipoEnvio;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "canal")
	private LocalControleInterno canal;
	
	
	@Column(columnDefinition = "text")
	private String observacao;
	
	private String protocolo;

	@Column(name = "numero_pedido")
	private String numeroPedido;

	@Column(name = "codigo_rastreio")
	private String codigoRastreio;
	
	@Column(name = "codigo_manifesto")
	private String codigoManifesto;
	
	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor = new BigDecimal(0);

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getStatusOcorrencia() {
		return statusOcorrencia;
	}

	public void setStatusOcorrencia(Integer statusOcorrencia) {
		this.statusOcorrencia = statusOcorrencia;
	}

	public Envio getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(Envio tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	public LocalControleInterno getCanal() {
		return canal;
	}

	public void setCanal(LocalControleInterno canal) {
		this.canal = canal;
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

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getCodigoRastreio() {
		return codigoRastreio;
	}

	public void setCodigoRastreio(String codigoRastreio) {
		this.codigoRastreio = codigoRastreio;
	}

	public String getCodigoManifesto() {
		return codigoManifesto;
	}

	public void setCodigoManifesto(String codigoManifesto) {
		this.codigoManifesto = codigoManifesto;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	

}
