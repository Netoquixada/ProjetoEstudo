package br.com.controlpro.entity.consultas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.controlpro.bo.ProdutoBO;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@Entity
@Table(name = "Estoque_Atual")
public class Estoque implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 303600955063832761L;

	@Id
	@Column(name = "Ordem")
	private String id;
	
	@Column(name = "Produto")
	private String produto;

	@Transient
	private Produto produtoObj;
	
	@Column(name = "Estoque_Atual")
	private Long estoqueAtual;
	
	@Column(name = "Ultima_Data")
	private Date data;
	
	@Transient
	private Date dataPesquisaInicio;

	@Transient
	private Date dataPesquisaFim;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Produto getProdutoObj(){
		try {
			produtoObj = ProdutoBO.getInstance().findById(this.produto);
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return produtoObj;
	}

	public void setProdutoObj(Produto produtoObj) {
		this.produtoObj = produtoObj;
	}

	public Long getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(Long estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataPesquisaInicio() {
		return dataPesquisaInicio;
	}

	public void setDataPesquisaInicio(Date dataPesquisaInicio) {
		this.dataPesquisaInicio = dataPesquisaInicio;
	}

	public Date getDataPesquisaFim() {
		return dataPesquisaFim;
	}

	public void setDataPesquisaFim(Date dataPesquisaFim) {
		this.dataPesquisaFim = dataPesquisaFim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estoque other = (Estoque) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (estoqueAtual == null) {
			if (other.estoqueAtual != null)
				return false;
		} else if (!estoqueAtual.equals(other.estoqueAtual))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
