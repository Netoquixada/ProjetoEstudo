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
import javax.persistence.Transient;

import br.com.controlpro.constants.OrigemOcorrencia;

@Entity
@Table(name = "CONTROL_OCORRENCIA_ORDEM_PRODUCAO")
public class OcorrenciaOrdemProducao extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;

	@ManyToOne
	@JoinColumn(name = "ordem_producao_id")
	private OrdemProducao ordemProducao;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();

	@Temporal(TemporalType.DATE)
	@Column(name = "data_ocorrencia")
	private Date dataOcorrencia = new Date();

	@Enumerated(EnumType.STRING)
	@Column(name = "origem_ocorrencia")
	private OrigemOcorrencia origemOcorrencia;
	
	@Column(columnDefinition = "text")
	private String descricao;

	private String protocolo;
	
	@Transient
	private Integer statusOcorrenciaFilter; 

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataOcorrencia() {
		return dataOcorrencia;
	}

	public void setDataOcorrencia(Date dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public OrdemProducao getOrdemProducao() {
		if(ordemProducao == null) {
			ordemProducao = new OrdemProducao();
		}
		return ordemProducao;
	}

	public void setOrdemProducao(OrdemProducao ordemProducao) {
		this.ordemProducao = ordemProducao;
	}
	
	public OrigemOcorrencia getOrigemOcorrencia() {
		return origemOcorrencia;
	}
	
	public void setOrigemOcorrencia(OrigemOcorrencia origemOcorrencia) {
		this.origemOcorrencia = origemOcorrencia;
	}
	
	public Integer getStatusOcorrenciaFilter() {
		return statusOcorrenciaFilter;
	}
	
	public void setStatusOcorrenciaFilter(Integer statusOcorrenciaFilter) {
		this.statusOcorrenciaFilter = statusOcorrenciaFilter;
	}

}
