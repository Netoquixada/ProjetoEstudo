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
@Table(name = "CONTROL_CONTA_PAGAR_ACABAMENTO")
public class ContaPagarAcabamento extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	public ContaPagarAcabamento() {
		// TODO Auto-generated constructor stub
	}

	public ContaPagarAcabamento(SituacaoConta situacao, Faccao faccao, Date dataPesquisaInicio, Date dataPesquisaFim) {
		this.situacao = situacao;
		this.getItemAcabamento().getAcabamento().setFaccao(faccao);
		this.dataInicio = dataPesquisaInicio;
		this.dataFim = dataPesquisaFim;
	}

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_item_acabamento")
	private ItemAcabamento itemAcabamento;

	@Temporal(TemporalType.DATE)
	private Date dataRecebimento = new Date();

	@Column(name = "valor_pagar", scale = 2, precision = 9)
	private BigDecimal valor = new BigDecimal(0);

	@Column(name = "valor_pago", scale = 2, precision = 9)
	private BigDecimal valorPago = new BigDecimal(0);;

	@Column(name = "valor_devedor", scale = 2, precision = 9)
	private BigDecimal valorDevedor = new BigDecimal(0);;

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

	public ItemAcabamento getItemAcabamento() {
		if(itemAcabamento == null) {
			itemAcabamento = new ItemAcabamento();
		}
		return itemAcabamento;
	}

	public void setItemAcabamento(ItemAcabamento itemAcabamento) {
		this.itemAcabamento = itemAcabamento;
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

}