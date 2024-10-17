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

import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.TipoImobilizado;


@Entity
@Table(name = "CONTROL_IMOBILIZADO")
public class Imobilizado extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	private String nome;
	private String tombamento;
	private String ano;
	
	@Enumerated(EnumType.STRING)
	private TipoImobilizado tipo;
	
	@Enumerated(EnumType.STRING)
	private Loja loja;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();
	
	private BigDecimal valor = new BigDecimal(0);

	@Column( columnDefinition = "text")
	private String observacao;
	
	@Transient
	private Date dataPesquisaInicio;
	
	@Transient
	private Date dataPesquisaFim;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTombamento() {
		return tombamento;
	}

	public void setTombamento(String tombamento) {
		this.tombamento = tombamento;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public TipoImobilizado getTipo() {
		return tipo;
	}

	public void setTipo(TipoImobilizado tipo) {
		this.tipo = tipo;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
	
	public Loja getLoja() {
		return loja;
	}
	
	public void setLoja(Loja loja) {
		this.loja = loja;
	}

}