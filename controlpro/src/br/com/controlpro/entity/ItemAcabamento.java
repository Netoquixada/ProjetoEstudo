package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.controlpro.entity.consultas.Produto;

@Entity
@Table(name = "CONTROL_ITEM_ACABAMENTO")
public class ItemAcabamento extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;

	@Column(name = "valor_unitario", scale = 2, precision = 9)
	private BigDecimal valor;

	@Transient
	private BigDecimal valorTotal = new BigDecimal(0.0);

	private Integer quantidade = 0;
	
	private Integer prontas = 0;

	@Transient
	private Integer prontasAux = 0;

	@Transient
	private Integer faltaAux = 0;

	@ManyToOne
	@JoinColumn(name = "acabamento_id")
	private Acabamento acabamento;

	public Produto getProduto() {
		if (produto == null) {
			produto = new Produto();
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorTotal() {
		valorTotal = valor.multiply(new BigDecimal(quantidade));
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Acabamento getAcabamento() {
		if(acabamento == null) {
			acabamento = new Acabamento();
		}
		return acabamento;
	}

	public void setAcabamento(Acabamento acabamento) {
		this.acabamento = acabamento;
	}

	public Integer getProntas() {
		return prontas;
	}

	public void setProntas(Integer prontas) {
		this.prontas = prontas;
	}

	public Integer getProntasAux() {
		return prontasAux;
	}

	public void setProntasAux(Integer prontasAux) {
		this.prontasAux = prontasAux;
	}

	public Integer getFaltaAux() {
		faltaAux = quantidade - prontas;
		return faltaAux;
	}

	public void setFaltaAux(Integer faltaAux) {
		this.faltaAux = faltaAux;
	}
	
	

}
