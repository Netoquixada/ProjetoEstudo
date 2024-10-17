package br.com.controlpro.entity.consultas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ResumoCartao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;
	
	public ResumoCartao() {
		
	}
	
	public ResumoCartao(String administradora, BigDecimal valor, Date data, int administradoraId) {
		this.administradora = administradora;
		this.valor = valor;
		this.data = data;
		this.adminstratoraId = administradoraId;
	}
	
	private String administradora;
	private BigDecimal valor;
	private Date data;
	private int adminstratoraId;
	
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getAdminstratoraId() {
		return adminstratoraId;
	}

	public void setAdminstratoraId(int adminstratoraId) {
		this.adminstratoraId = adminstratoraId;
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