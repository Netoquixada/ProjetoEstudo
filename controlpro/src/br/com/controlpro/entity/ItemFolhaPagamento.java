package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.controlpro.constants.TipoFolhaPagamento;

@Entity
@Table(name = "CONTROL_ITEM_FOLHA_PAGAMENTO")
public class ItemFolhaPagamento extends EntidadeGenerica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;
	
	@ManyToOne
	@JoinColumn(name = "funcionario")
	private FuncionarioControlPro funcionario;
	
	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor = new BigDecimal(0);

	@Column(name = "hora_extra", scale = 2, precision = 9)
	private BigDecimal horaExtra = new BigDecimal(0);

	@Column(name = "inss", scale = 2, precision = 9)
	private BigDecimal inss = new BigDecimal(0);
	
	@Column(name = "salarioFamilia", scale = 2, precision = 9)
	private BigDecimal salarioFamilia = new BigDecimal(0);

	@Column(name = "outros_descontos", scale = 2, precision = 9)
	private BigDecimal outrosDescontos = new BigDecimal(0);

	@Column(name = "salarioReceber", scale = 2, precision = 9)
	private BigDecimal salarioReceber = new BigDecimal(0);

	@ManyToOne
	@JoinColumn(name="folha_pagamento")
	private FolhaPagamento folhaPagamento;

	public FuncionarioControlPro getFuncionario() {
		if(funcionario == null) {
			funcionario = new FuncionarioControlPro();
		}
		return funcionario;
	}

	public void setFuncionario(FuncionarioControlPro funcionario) {
		this.funcionario = funcionario;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getHoraExtra() {
		return horaExtra;
	}

	public void setHoraExtra(BigDecimal horaExtra) {
		this.horaExtra = horaExtra;
	}

	public BigDecimal getInss() {
		return inss;
	}

	public void setInss(BigDecimal inss) {
		this.inss = inss;
	}

	public BigDecimal getOutrosDescontos() {
		return outrosDescontos;
	}

	public void setOutrosDescontos(BigDecimal outrosDescontos) {
		this.outrosDescontos = outrosDescontos;
	}

	public BigDecimal getSalarioReceber() {
		return salarioReceber;
	}

	public void setSalarioReceber(BigDecimal salarioReceber) {
		this.salarioReceber = salarioReceber;
	}

	public FolhaPagamento getFolhaPagamento() {
		return folhaPagamento;
	}

	public void setFolhaPagamento(FolhaPagamento folhaPagamento) {
		this.folhaPagamento = folhaPagamento;
	}
	
	public BigDecimal getSalarioFamilia() {
		return salarioFamilia;
	}
	
	public void setSalarioFamilia(BigDecimal salarioFamilia) {
		this.salarioFamilia = salarioFamilia;
	}
	
}
