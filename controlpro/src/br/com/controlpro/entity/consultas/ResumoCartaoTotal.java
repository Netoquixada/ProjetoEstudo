package br.com.controlpro.entity.consultas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ResumoCartaoTotal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;
	
	public ResumoCartaoTotal() {
		
	}
	
	public ResumoCartaoTotal(String administradora, BigDecimal valor) {
		this.administradora = administradora;
		this.valor = valor;
	}
	
	private String administradora;
	private BigDecimal valor;
	
	private Date dataInicioFilter = new Date();
	private Date dataFimFilter = new Date();

	public String getAdministradora() {
		return administradora;
	}

	public void setAdministradora(String administradora) {
		this.administradora = administradora;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataInicioFilter() {
		return dataInicioFilter;
	}

	public void setDataInicioFilter(Date dataInicioFilter) {
		this.dataInicioFilter = dataInicioFilter;
	}

	public Date getDataFimFilter() {
		return dataFimFilter;
	}

	public void setDataFimFilter(Date dataFimFilter) {
		this.dataFimFilter = dataFimFilter;
	}

}