package br.com.controlpro.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTROL_AQUISICAO_SAIDA_ACABAMENTO")
public class AquisicaoSaidaAcabamento extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;

	@ManyToOne
	@JoinColumn(name = "materia_prima")
	private MateriaPrima materiaPrima;

	@Column(name = "quantidade", scale = 2, precision = 9)
	private Double quantidade = 0.0;

	@Column(name = "total", scale = 2, precision = 9)
	private Double total = 0.0;

	@ManyToOne
	@JoinColumn(name = "acabamento_id")
	private Acabamento acabamento;

	public MateriaPrima getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(MateriaPrima materiaPrima) {
		this.materiaPrima = materiaPrima;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Acabamento getAcabamento() {
		return acabamento;
	}

	public void setAcabamento(Acabamento acabamento) {
		this.acabamento = acabamento;
	}
}
