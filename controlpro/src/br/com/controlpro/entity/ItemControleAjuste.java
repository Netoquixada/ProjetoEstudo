package br.com.controlpro.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTROL_ITEM_AJUSTE")
public class ItemControleAjuste extends EntidadeGenerica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;
	

	@ManyToOne
	@JoinColumn(name="controle_ajuste_id")
	private ControleAjuste controleAjuste;
	
	private String sequencia;
	private Integer linha;
	private String observacao;
	
	public ControleAjuste getControleAjuste() {
		return controleAjuste;
	}
	public void setControleAjuste(ControleAjuste controleAjuste) {
		this.controleAjuste = controleAjuste;
	}
	public String getSequencia() {
		return sequencia;
	}
	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Integer getLinha() {
		return linha;
	}
	public void setLinha(Integer linha) {
		this.linha = linha;
	}
	
	
	

}
