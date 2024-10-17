package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CONTROL_ITEM_ENTRADA_MP")
public class ItemEntradaMateriaPrima extends EntidadeGenerica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;
	
	@ManyToOne
	@JoinColumn(name = "materia_prima_id")
	private MateriaPrima materiaPrima;

	private double quantidade = 0L;
	
	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor = new BigDecimal(0);
	
	@ManyToOne
	@JoinColumn(name="entrada_mp_id")
	private EntradaMateriaPrima entradaMateriaPrima;
	
	@Transient
	private BigDecimal total = new BigDecimal(0);

	public MateriaPrima getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(MateriaPrima materiaPrima) {
		this.materiaPrima = materiaPrima;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public EntradaMateriaPrima getEntradaMateriaPrima() {
		return entradaMateriaPrima;
	}

	public void setEntradaMateriaPrima(EntradaMateriaPrima entradaMateriaPrima) {
		this.entradaMateriaPrima = entradaMateriaPrima;
	}

	public BigDecimal getTotal() {
		total = this.valor.multiply(new BigDecimal(this.quantidade));
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
