package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import br.com.controlpro.constants.TipoAuditoria;
import br.com.controlpro.entity.consultas.Produto;

@Entity
@Table(name = "CONTROL_ITEM_PRODUCAO_AUD")
public class ItemProducaoAud extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;

	@Type(type = "true_false")
	@Column(name = "item_reenviado")
	private Boolean itemReenviado = false;
	
	@Column(name = "id_item")
	private Integer idItem;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;

	@ManyToOne
	@JoinColumn(name = "faccao_id")
	private Faccao faccao;

	@Column(name = "valor_unitario", scale = 2, precision = 9)
	private BigDecimal valor;

	@Transient
	private BigDecimal valorTotal = new BigDecimal(0.0);

	private Integer quantidade = 0;

	private Integer prontas = 0;
	private Integer defeito = 0;

	@ManyToOne
	@JoinColumn(name = "ordem_producao_id")
	private OrdemProducao ordemProducao;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_registro")
	private Date dataRegistro = new Date();
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_auditoria")
	private TipoAuditoria tipoAuditoria;

	public Produto getProduto() {
		if (produto == null) {
			produto = new Produto();
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Faccao getFaccao() {
		return faccao;
	}

	public void setFaccao(Faccao faccao) {
		this.faccao = faccao;
	}

	public Integer getQuantidade() {
		if(quantidade == null) {
			quantidade = 0;
		}
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getProntas() {
		return prontas;
	}
	
	public Integer getDefeito() {
		if(defeito == null) {
			defeito = 0;
		}
		return defeito;
	}
	
	public void setDefeito(Integer defeito) {
		this.defeito = defeito;
	}

	public void setProntas(Integer prontas) {
		this.prontas = prontas;
	}

	public OrdemProducao getOrdemProducao() {
		if (ordemProducao == null) {
			ordemProducao = new OrdemProducao();
		}
		return ordemProducao;
	}

	public void setOrdemProducao(OrdemProducao ordemProducao) {
		this.ordemProducao = ordemProducao;
	}

	public BigDecimal getValor() {
		if(valor == null) {
			valor = new BigDecimal(0);
		}
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorTotal() {
		valorTotal = getValor().multiply(new BigDecimal(quantidade));
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Boolean getItemReenviado() {
		return itemReenviado;
	}

	public void setItemReenviado(Boolean itemReenviado) {
		this.itemReenviado = itemReenviado;
	}

	public Integer getIdItem() {
		return idItem;
	}

	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public TipoAuditoria getTipoAuditoria() {
		return tipoAuditoria;
	}

	public void setTipoAuditoria(TipoAuditoria tipoAuditoria) {
		this.tipoAuditoria = tipoAuditoria;
	}
	
}
