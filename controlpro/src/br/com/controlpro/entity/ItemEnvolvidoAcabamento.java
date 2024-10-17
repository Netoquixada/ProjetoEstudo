package br.com.controlpro.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.controlpro.entity.consultas.Produto;

@Entity
@Table(name = "CONTROL_ITEM_ENVOLVIDO_ACABAMENTO")
public class ItemEnvolvidoAcabamento extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "materia_prima")
	private MateriaPrima materiaPrima;

	private Integer quantidade = 0;
	
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

	public Acabamento getAcabamento() {
		return acabamento;
	}

	public void setAcabamento(Acabamento acabamento) {
		this.acabamento = acabamento;
	}

	public MateriaPrima getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(MateriaPrima materiaPrima) {
		this.materiaPrima = materiaPrima;
	}
	
	

}
