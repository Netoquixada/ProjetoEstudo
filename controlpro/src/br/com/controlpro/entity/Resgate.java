package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.controlpro.entity.consultas.Cliente;


@Entity
@Table(name = "CONTROL_RESGATE")
public class Resgate extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@Column(name = "total_vendas", scale = 2, precision = 9)
	private BigDecimal totalVendas = new BigDecimal(0.0);
	
	@Column(name = "total_resgate", scale = 2, precision = 9)
	private BigDecimal totalResgate = new BigDecimal(0.0);

	@Column(name = "saldo", scale = 2, precision = 9)
	private BigDecimal saldo = new BigDecimal(0.0);
	
	@Column(name = "valor_resgate", scale = 2, precision = 9)
	private BigDecimal valorResgate = new BigDecimal(0.0);
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new Date();
	
	@ManyToOne
	private Cliente cliente;
	
	public BigDecimal getTotalVendas() {
		return totalVendas;
	}
	
	public void setTotalVendas(BigDecimal totalVendas) {
		this.totalVendas = totalVendas;
	}

	public BigDecimal getTotalResgate() {
		return totalResgate;
	}

	public void setTotalResgate(BigDecimal totalResgate) {
		this.totalResgate = totalResgate;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public BigDecimal getValorResgate() {
		return valorResgate;
	}
	
	public void setValorResgate(BigDecimal valorResgate) {
		this.valorResgate = valorResgate;
	}
	
}