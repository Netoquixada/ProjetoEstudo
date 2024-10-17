package br.com.controlpro.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CONTROL_GRADE")
public class Grade extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	private String nome;

	@Column(columnDefinition = "text")
	private String observacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}