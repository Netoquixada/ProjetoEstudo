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

import br.com.controlpro.constants.SituacaoConta;


@Entity
@Table(name = "CONTROL_CONTA_PAGAR")
public class ContaPagar extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_item_producao")
	private ItemProducao itemProducao;

	@Temporal(TemporalType.DATE)
	private Date dataRecebimento = new Date();
	
	@Column(name = "valor_pagar", scale = 2, precision = 9)
	private BigDecimal valor = new BigDecimal(0);

	@Column(name = "valor_pago", scale = 2, precision = 9)
	private BigDecimal valorPago  = new BigDecimal(0);;
	
	@Column(name = "valor_devedor", scale = 2, precision = 9)
	private BigDecimal valorDevedor  = new BigDecimal(0);
	
	@Column(name = "valor_bonus", scale = 2, precision = 9)
	private BigDecimal valorBonus = new BigDecimal(0);
	
	@Column(name = "qtd_recebido")
	private Integer qtdRecebido;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private SituacaoConta situacao = SituacaoConta.NAO_QUITADA;
	
	@Transient
	private Date dataInicio;

	@Transient
	private Date dataFim;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ItemProducao getItemProducao() {
		if(itemProducao == null){
			itemProducao = new ItemProducao();
		}
		return itemProducao;
	}

	public void setItemProducao(ItemProducao itemProducao) {
		this.itemProducao = itemProducao;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public Integer getQtdRecebido() {
		return qtdRecebido;
	}
	
	public void setQtdRecebido(Integer qtdRecebido) {
		this.qtdRecebido = qtdRecebido;
	}
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public BigDecimal getValorDevedor() {
		return valorDevedor;
	}

	public void setValorDevedor(BigDecimal valorDevedor) {
		this.valorDevedor = valorDevedor;
	}

	public SituacaoConta getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoConta situacao) {
		this.situacao = situacao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public BigDecimal getValorBonus() {
		return valorBonus;
	}
	
	public void setValorBonus(BigDecimal valorBonus) {
		this.valorBonus = valorBonus;
	}
	
}