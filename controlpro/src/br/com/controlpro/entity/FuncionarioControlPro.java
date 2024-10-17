package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.controlpro.constants.Loja;

@Entity
@Table(name = "CONTROL_FUNCIONARIO")
public class FuncionarioControlPro extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	private String nome;

	@ManyToOne
	private Cargo cargo;

	@Column(name = "salario_ct", scale = 2, precision = 9)
	private BigDecimal salarioCt = new BigDecimal(0);

	@Column(name = "salario_pf", scale = 2, precision = 9)
	private BigDecimal salarioPf = new BigDecimal(0);

	@Column(name = "salario_familia", scale = 2, precision = 9)
	private BigDecimal salarioFamilia = new BigDecimal(0);

	@Column(name = "inss", scale = 2, precision = 9)
	private BigDecimal inss = new BigDecimal(0);

	@Column(name = "salario_total", scale = 2, precision = 9)
	private BigDecimal salarioTotal = new BigDecimal(0);

	@Enumerated(EnumType.STRING)
	private Loja loja;

	@Column(columnDefinition = "text")
	private String observacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getSalarioCt() {
		return salarioCt;
	}

	public void setSalarioCt(BigDecimal salarioCt) {
		this.salarioCt = salarioCt;
	}

	public BigDecimal getSalarioPf() {
		return salarioPf;
	}

	public void setSalarioPf(BigDecimal salarioPf) {
		this.salarioPf = salarioPf;
	}

	public BigDecimal getSalarioFamilia() {
		return salarioFamilia;
	}

	public void setSalarioFamilia(BigDecimal salarioFamilia) {
		if (salarioFamilia == null) {
			salarioFamilia = new BigDecimal(0);
		}
		this.salarioFamilia = salarioFamilia;
	}

	public BigDecimal getInss() {
		if (inss == null) {
			inss = new BigDecimal(0);
		}
		return inss;
	}

	public void setInss(BigDecimal inss) {
		this.inss = inss;
	}

	public BigDecimal getSalarioTotal() {
		return salarioTotal;
	}

	public void setSalarioTotal(BigDecimal salarioTotal) {
		this.salarioTotal = salarioTotal;
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

}