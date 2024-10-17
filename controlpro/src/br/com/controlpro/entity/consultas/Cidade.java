package br.com.controlpro.entity.consultas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Cidades")
public class Cidade implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 303600955063832761L;
	
	@Id
	@Column(name = "Ordem")
	private Long id;

	@Column(name = "Cidade")
	private String cidadeNome;

	@Column(name = "Estado")
	private String estado;

	public String getCidadeNome() {
		return cidadeNome;
	}

	public void setCidadeNome(String cidade) {
		this.cidadeNome = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
}
