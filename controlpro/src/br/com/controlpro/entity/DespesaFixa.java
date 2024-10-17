package br.com.controlpro.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.controlpro.constants.Loja;

@Entity
@Table(name = "CONTROL_DESPESA_FIXA")
public class DespesaFixa extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4983331588533261636L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();

	@OneToMany(mappedBy = "despesaFixa",fetch=FetchType.LAZY)
	private List<ItemDespesaFixa> itensDespesaFixa;
	
	@Enumerated(EnumType.STRING)
	private Loja loja;
	
	@Column(name = "quantidade_producao")
	private Integer quantidadeProducao;
	
	@Column(columnDefinition = "text")
	private String observacao;
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public List<ItemDespesaFixa> getItensDespesaFixa() {
		return itensDespesaFixa;
	}

	public void setItensDespesaFixa(List<ItemDespesaFixa> itensDespesaFixa) {
		this.itensDespesaFixa = itensDespesaFixa;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Integer getQuantidadeProducao() {
		return quantidadeProducao;
	}

	public void setQuantidadeProducao(Integer quantidadeProducao) {
		this.quantidadeProducao = quantidadeProducao;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
