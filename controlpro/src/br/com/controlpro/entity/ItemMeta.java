package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CONTROL_ITEM_META")
public class ItemMeta extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@ManyToOne
	@JoinColumn(name = "id_meta")
	private Meta meta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_recebimento")
	private Date dataRecebimento = new Date();

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor;

	@Column(name = "qtd_itens")
	private Integer qtdItens;
	
	@Column(columnDefinition = "text")
	private String observacao;

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Integer getQtdItens() {
		return qtdItens;
	}
	
	public void setQtdItens(Integer qtdItens) {
		this.qtdItens = qtdItens;
	}

}