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


@Entity
@Table(name = "CONTROL_RESERVA")
public class Reserva extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@Temporal(TemporalType.DATE)
	private Date dataLanacamento = new Date();

	@Temporal(TemporalType.DATE)
	private Date data = new Date();
	
	@Column(name = "valor_vendas", scale = 2, precision = 9)
	private BigDecimal valorVendas = new BigDecimal(0);

	@Column(name = "valor_reserva", scale = 2, precision = 9)
	private BigDecimal valorReserva= new BigDecimal(0);
	
	@Enumerated(EnumType.STRING)
	private Loja loja;
	
	private String tipo;
	
	private String os;

	@Transient
	private Date dataInicio;

	@Transient
	private Date dataFim;

	public Date getDataLanacamento() {
		return dataLanacamento;
	}

	public void setDataLanacamento(Date dataLanacamento) {
		this.dataLanacamento = dataLanacamento;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValorVendas() {
		return valorVendas;
	}

	public void setValorVendas(BigDecimal valorVendas) {
		this.valorVendas = valorVendas;
	}

	public BigDecimal getValorReserva() {
		return valorReserva;
	}

	public void setValorReserva(BigDecimal valorReserva) {
		this.valorReserva = valorReserva;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}