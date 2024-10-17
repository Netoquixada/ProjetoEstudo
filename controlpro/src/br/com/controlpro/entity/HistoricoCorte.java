package br.com.controlpro.entity;

import java.io.Serializable;
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
import br.com.controlpro.entity.consultas.Produto;

@Entity
@Table(name = "CONTROL_HISTORICO_CORTE")
public class HistoricoCorte extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@ManyToOne
	@JoinColumn(name = "id_ordem_corte")
	private OrdemCorte ordemCorte;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_recebimento")
	private Date dataRecebimento = new Date();

	@Temporal(TemporalType.DATE)
	@Column(name = "data_corte")
	private Date dataCorte;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_cortador")
	private Funcionario cortador;

	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;

	@ManyToOne
	@JoinColumn(name = "id_grade")
	private Grade grade;

	private Long qtdRecebido = 0L;

	@Transient
	private Date dataPesquisaInicio;

	@Transient
	private Date dataPesquisaFim;
	
	private String justificativa;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Funcionario getCortador() {
		return cortador;
	}

	public void setCortador(Funcionario cortador) {
		this.cortador = cortador;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public Long getQtdRecebido() {
		return qtdRecebido;
	}

	public void setQtdRecebido(Long qtdRecebido) {
		this.qtdRecebido = qtdRecebido;
	}

	public OrdemCorte getOrdemCorte() {
		return ordemCorte;
	}

	public void setOrdemCorte(OrdemCorte ordemCorte) {
		this.ordemCorte = ordemCorte;
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
	
	public Date getDataCorte() {
		return dataCorte;
	}
	
	public void setDataCorte(Date dataCorte) {
		this.dataCorte = dataCorte;
	}
	
	public String getJustificativa() {
		return justificativa;
	}
	
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

}