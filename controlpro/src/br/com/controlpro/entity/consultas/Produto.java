package br.com.controlpro.entity.consultas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Produtos")
public class Produto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 303600955063832761L;

	@Id
	@Column(name = "Codigo")
	private String id;
	
	@Column(name = "Nome")
	private String nome;

	@Column(name = "Unidade_Venda")
	private String unidadeVenda;
	
	@Column(name = "Codigo_Fornecedor1")
	private String codFornecedor;
	
	@Column(name = "Preco1" )
	private String Valor1;
	
	@Column(name = "Preco2")
	private String Valor2;
	
	public String getValor1() {
		return Valor1;
	}

	public void setValor1(String valor1) {
		Valor1 = valor1;
	}

	public String getValor2() {
		return Valor2;
	}

	public void setValor2(String valor2) {
		Valor2 = valor2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUnidadeVenda() {
		return unidadeVenda;
	}

	public void setUnidadeVenda(String unidadeVenda) {
		this.unidadeVenda = unidadeVenda;
	}
	
	public String getCodFornecedor() {
		return codFornecedor;
	}

	public void setCodFornecedor(String codFornecedor) {
		this.codFornecedor = codFornecedor;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
