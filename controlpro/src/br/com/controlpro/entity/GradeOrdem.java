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
import javax.persistence.Transient;

import br.com.controlpro.constants.TipoItemProducao;
import br.com.controlpro.entity.consultas.Funcionario;
import br.com.controlpro.entity.consultas.Produto;

@Entity
@Table(name = "CONTROL_GRADE_ORDEM")
public class GradeOrdem extends EntidadeGenerica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;
	
	@ManyToOne
	@JoinColumn(name = "grade_id")
	private Grade grade;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "cortador_id")
	private Funcionario cortador;
	
	private Long quantidade = 0L;
	
	private Integer partes;
	
	private Long prontas = 0L;
	
	@Transient
	private Long prontasAux = 0L;
	
	@Transient
	private Long faltaAux = 0L;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "parte")
	private TipoItemProducao parte;
	
	@ManyToOne
	@JoinColumn(name="ordem_corte_id")
	private OrdemCorte ordemCorte;
	
	@Transient
	private Date dataCorteAux;
	
	@Transient
	private String justificativaAux;
	
	public Date getDataCorteAux() {
		return dataCorteAux;
	}
	
	public void setDataCorteAux(Date dataCorteAux) {
		this.dataCorteAux = dataCorteAux;
	}
	
	
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	public OrdemCorte getOrdemCorte() {
		return ordemCorte;
	}
	
	public void setOrdemCorte(OrdemCorte ordemCorte) {
		this.ordemCorte = ordemCorte;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Funcionario getCortador() {
		return cortador;
	}
	
	public void setCortador(Funcionario cortador) {
		this.cortador = cortador;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Long getProntas() {
		return prontas;
	}

	public void setProntas(Long prontas) {
		this.prontas = prontas;
	}

	public Long getProntasAux() {
		return prontasAux;
	}

	public void setProntasAux(Long prontasAux) {
		this.prontasAux = prontasAux;
	}

	public Long getFaltaAux() {
		faltaAux = quantidade - prontas;
		return faltaAux;
	}

	public void setFaltaAux(Long faltaAux) {
		this.faltaAux = faltaAux;
	}
	
	public String getJustificativaAux() {
		if(justificativaAux == null) {
			justificativaAux = "";
		}
		return justificativaAux;
	}
	
	public void setJustificativaAux(String justificativaAux) {
		this.justificativaAux = justificativaAux;
	}

	public Integer getPartes() {
		return partes;
	}

	public void setPartes(Integer partes) {
		this.partes = partes;
	}

	public TipoItemProducao getParte() {
		return parte;
	}

	public void setParte(TipoItemProducao parte) {
		this.parte = parte;
	}
	
}
