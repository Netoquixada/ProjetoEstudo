package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.controlpro.entity.consultas.Funcionario;

@Entity
@Table(name = "CONTROL_SANGRIA")
public class Sangria extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_sangria")
	private Date dataSangria = new Date();

	private String protocolo;
	
	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor;

	@ManyToOne
	@JoinColumn(name = "id_funcionario")
	private Funcionario favorecido;

	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario responsavel;
	
	@Transient
	private Date dataPesquisaInicio;

	@Transient
	private Date dataPesquisaFim;

	public Date getDataSangria() {
		return dataSangria;
	}

	public void setDataSangria(Date dataSangria) {
		this.dataSangria = dataSangria;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Funcionario getFavorecido() {
		return favorecido;
	}

	public void setFavorecido(Funcionario favorecido) {
		this.favorecido = favorecido;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
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
	
	
	
}