package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class HistoricoPagamentoResumo extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;
	
	public HistoricoPagamentoResumo(String faccao, BigDecimal bonus, BigDecimal acrescimo, BigDecimal total, BigDecimal totalConta) {
		this.faccao = faccao;
		this.bonus = bonus;
		this.acrescimo = acrescimo;
		this.total = total;
		this.totalConta = totalConta;
	}
	

	private String faccao;
	private BigDecimal bonus;
	private BigDecimal acrescimo;
	private BigDecimal total;
	private BigDecimal totalConta;
	
	public String getFaccao() {
		return faccao;
	}
	public void setFaccao(String faccao) {
		this.faccao = faccao;
	}
	public BigDecimal getBonus() {
		return bonus;
	}
	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}
	public BigDecimal getAcrescimo() {
		return acrescimo;
	}
	public void setAcrescimo(BigDecimal acrescimo) {
		this.acrescimo = acrescimo;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public BigDecimal getTotalConta() {
		return totalConta;
	}
	
	public void setTotalConta(BigDecimal totalConta) {
		this.totalConta = totalConta;
	}
	

}