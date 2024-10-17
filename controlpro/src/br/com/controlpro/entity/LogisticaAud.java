package br.com.controlpro.entity;

import java.io.Serializable;
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

import br.com.controlpro.constants.LocalLogistica;
import br.com.controlpro.constants.StatusLogistica;
import br.com.controlpro.constants.TipoEnvioLogistica;

@Entity
@Table(name = "CONTROL_LOGISTICA_AUD")
public class LogisticaAud extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4983331588533261636L;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();

	private String pedido;

	@Enumerated(EnumType.STRING)
	@Column(name = "local_logistica")
	private LocalLogistica localLogistica; 
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status_logistica")
	private StatusLogistica statusLogistica;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_envio_logistica")
	private TipoEnvioLogistica tipoEnvioLogistica;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_envio")
	private Date dataEnvio;
	
	@ManyToOne
	@JoinColumn(name = "motoboy_id")
	private Motoboy motoboy;
	
	@ManyToOne
	@JoinColumn(name = "excursao_id")
	private Excursao excursao;
	
	
	@Column(columnDefinition = "text")
	private String observacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_alteracao")
	private Date dataAlteracao = new Date();
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "logistica_id")
	private Integer logistica;

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public LocalLogistica getLocalLogistica() {
		return localLogistica;
	}

	public void setLocalLogistica(LocalLogistica localLogistica) {
		this.localLogistica = localLogistica;
	}

	public StatusLogistica getStatusLogistica() {
		return statusLogistica;
	}

	public void setStatusLogistica(StatusLogistica statusLogistica) {
		this.statusLogistica = statusLogistica;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Motoboy getMotoboy() {
		return motoboy;
	}

	public void setMotoboy(Motoboy motoboy) {
		this.motoboy = motoboy;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getLogistica() {
		return logistica;
	}

	public void setLogistica(Integer logistica) {
		this.logistica = logistica;
	}
	
	public TipoEnvioLogistica getTipoEnvioLogistica() {
		return tipoEnvioLogistica;
	}
	
	public void setTipoEnvioLogistica(TipoEnvioLogistica tipoEnvioLogistica) {
		this.tipoEnvioLogistica = tipoEnvioLogistica;
	}
	
	public Excursao getExcursao() {
		return excursao;
	}
	
	public void setExcursao(Excursao excursao) {
		this.excursao = excursao;
	}
	
}
