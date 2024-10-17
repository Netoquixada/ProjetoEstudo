package br.com.controlpro.entity.consultas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Saidas_Produtos")
public class MovimentacaoProduto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 303600955063832761L;

	@Id
	@GeneratedValue
	@Column(name = "Ordem")
	private Integer id;
	
//	@Temporal(TemporalType.DATE)
//	@Column(name = "Data")
//	private Date data;
	
//	@Temporal(TemporalType.TIME)
//	@Column(name = "Hora")
//	private Date hora;
//	
	@Column(name = "Sequencia")
	private String sequencia;

	@ManyToOne
	@JoinColumn(name = "Codigo_Sem_Grade")
	private Produto produto;

	@Column(name = "Linha")
	private Integer linha;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public Date getData() {
//		return data;
//	}
//
//	public void setData(Date data) {
//		this.data = data;
//	}
//
//	public Date getHora() {
//		return hora;
//	}
//
//	public void setHora(Date hora) {
//		this.hora = hora;
//	}

	public String getSequencia() {
		return sequencia;
	}

	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Integer getLinha() {
		return linha;
	}
	
	public void setLinha(Integer linha) {
		this.linha = linha;
	}
	
}
