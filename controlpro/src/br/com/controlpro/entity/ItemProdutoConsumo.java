package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CONTROL_ITEM_PRODUTO_CONUSMO")
public class ItemProdutoConsumo extends EntidadeGenerica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;
	
	@ManyToOne
	@JoinColumn(name = "materia_prima_id")
	private MateriaPrima materiaPrima;

	private double quantidade = 0L;
	
	@ManyToOne
	@JoinColumn(name="produto_consumo_id")
	private ProdutoConsumo produtoConsumo;
	
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

	public ProdutoConsumo getProdutoConsumo() {
		return produtoConsumo;
	}

	public void setProdutoConsumo(ProdutoConsumo produtoConsumo) {
		this.produtoConsumo = produtoConsumo;
	}

	public BigDecimal getTotal() {
		total = materiaPrima.getValor().multiply(new BigDecimal(this.quantidade));
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
