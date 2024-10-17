package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTROL_ITEM_AVIAMENTO")
public class ItemAviamentoProducao extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;

	@ManyToOne
	@JoinColumn(name = "materia_prima")
	private MateriaPrima materiaPrima;

	@Column(name = "quantidade", scale = 2, precision = 9)
	private BigDecimal quantidade = new BigDecimal(0);

	@Column(name = "total", scale = 2, precision = 9)
	private Double total = 0.0;
	
	@Column(name = "valor_total", scale = 2, precision = 9)
	private BigDecimal valorTotal = new BigDecimal(0);

	@ManyToOne
	@JoinColumn(name = "ordem_producao_id")
	private OrdemProducao ordemProducao;

	public MateriaPrima getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(MateriaPrima materiaPrima) {
		this.materiaPrima = materiaPrima;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public OrdemProducao getOrdemProducao() {
		return ordemProducao;
	}

	public void setOrdemProducao(OrdemProducao ordemProducao) {
		this.ordemProducao = ordemProducao;
	}
	
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

}
