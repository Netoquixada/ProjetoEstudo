package br.com.controlpro.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

import br.com.controlpro.bo.VendasBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.entity.consultas.Vendas;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@Entity
@Table(name = "CONTROL_CONTROLE_AJUSTE")
public class ControleAjuste extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	private String protocolo;

	private String protocoloControle;

	@Enumerated(EnumType.STRING)
	private Loja loja;

	@Column(name = "sequencia_venda")
	private String sequenciaVenda;

	@ManyToOne
	@JoinColumn(name = "faccao_id")
	private Faccao faccao;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();

	@Temporal(TemporalType.DATE)
	@Column(name = "data_pronto")
	private Date dataPronto;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_entrega")
	private Date dataEntrega;

	@Column(columnDefinition = "text")
	private String observacao;

	@Transient
	private Date dataInicio;

	@Transient
	private Date dataFim;

	@Transient
	private Vendas venda;

	public String getSequenciaVenda() {
		return sequenciaVenda;
	}

	public void setSequenciaVenda(String sequenciaVenda) {
		this.sequenciaVenda = sequenciaVenda;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Vendas getVenda() {

		try {
			List<Vendas> lista = VendasBO.getInstance().vendaPorSequencia(this.sequenciaVenda);
			if (lista.size() == 0) {
				venda = new Vendas();
			} else {
				venda = lista.get(0);
			}

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}

		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getProtocoloControle() {
		return protocoloControle;
	}

	public void setProtocoloControle(String protocoloControle) {
		this.protocoloControle = protocoloControle;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Faccao getFaccao() {
		return faccao;
	}

	public void setFaccao(Faccao faccao) {
		this.faccao = faccao;
	}

	public Date getDataPronto() {
		return dataPronto;
	}

	public void setDataPronto(Date dataPronto) {
		this.dataPronto = dataPronto;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

}