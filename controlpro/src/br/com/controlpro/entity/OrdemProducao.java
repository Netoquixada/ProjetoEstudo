package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.SituacaoOrdem;
import br.com.controlpro.constants.TipoCostura;

@Entity
@Table(name = "CONTROL_ORDEM_PRODUCAO")
public class OrdemProducao extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4983331588533261636L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();

	@Column(name = "qtd_dias")
	private Integer qtdDias;
	
	@ManyToOne
	@JoinColumn(name = "faccao_id")
	private Faccao faccao;
	
	@Column(name = "status_ocorrencia")
	private Integer statusOcorrencia = 0;
	
	@ManyToOne
	@JoinColumn(name = "colecao_id")
	private Colecao colecao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "previsao_termino")
	private Date previsaoTermino;
	
	@Column(columnDefinition = "text")
	private String observacao;
	
	@Enumerated(EnumType.STRING)
	private SituacaoOrdem situacao;
	
	@Enumerated(EnumType.STRING)
	private TipoCostura tipoCostura;
	
	@Transient
	private Boolean finalizarOrdem = false;

	private String protocolo;
	
	@Column(name = "protocolo_corte")
	private String protocoloCorte;
	
	private Loja loja;
	
	@OneToMany(mappedBy = "ordemProducao",fetch=FetchType.LAZY)
	private List<ItemProducao> itensProducao;
	
	@OneToMany(mappedBy = "ordemProducao",fetch=FetchType.LAZY)
	private List<ItemAviamentoProducao> itensAviamento;
	
	@Transient
	private BigDecimal valorTotalOrdem = new BigDecimal(0);
	
	@Transient
	private Date dataCadastroInicio;

	@Transient
	private Date dataCadastroFim;


	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}

	public Date getPrevisaoTermino() {
		return previsaoTermino;
	}

	public void setPrevisaoTermino(Date previsaoTermino) {
		this.previsaoTermino = previsaoTermino;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public SituacaoOrdem getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoOrdem situacao) {
		this.situacao = situacao;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public List<ItemProducao> getItensProducao() {
		return itensProducao;
	}

	public void setItensProducao(List<ItemProducao> itensProducao) {
		this.itensProducao = itensProducao;
	}

	public Date getDataCadastroInicio() {
		return dataCadastroInicio;
	}

	public void setDataCadastroInicio(Date dataCadastroInicio) {
		this.dataCadastroInicio = dataCadastroInicio;
	}

	public Date getDataCadastroFim() {
		return dataCadastroFim;
	}

	public void setDataCadastroFim(Date dataCadastroFim) {
		this.dataCadastroFim = dataCadastroFim;
	}
	
	public Faccao getFaccao() {
		return faccao;
	}
	
	public void setFaccao(Faccao faccao) {
		this.faccao = faccao;
	}

	public List<ItemAviamentoProducao> getItensAviamento() {
		return itensAviamento;
	}

	public void setItensAviamento(List<ItemAviamentoProducao> itensAviamento) {
		this.itensAviamento = itensAviamento;
	}
	
	public TipoCostura getTipoCostura() {
		return tipoCostura;
	}
	
	public void setTipoCostura(TipoCostura tipoCostura) {
		this.tipoCostura = tipoCostura;
	}
	
	public Colecao getColecao() {
		return colecao;
	}
	
	public void setColecao(Colecao colecao) {
		this.colecao = colecao;
	}
	
	public Boolean getFinalizarOrdem() {
		return finalizarOrdem;
	}
	
	public void setFinalizarOrdem(Boolean finalizarOrdem) {
		this.finalizarOrdem = finalizarOrdem;
	}
	
	public Loja getLoja() {
		return loja;
	}
	
	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Integer getStatusOcorrencia() {
		return statusOcorrencia;
	}
	
	public void setStatusOcorrencia(Integer statusOcorrencia) {
		this.statusOcorrencia = statusOcorrencia;
	}
	
	public String getProtocoloCorte() {
		return protocoloCorte;
	}
	
	public void setProtocoloCorte(String protocoloCorte) {
		this.protocoloCorte = protocoloCorte;
	}
	
	public BigDecimal getValorTotalOrdem() {
		
		return valorTotalOrdem;
	}
	
	public void setValorTotalOrdem(BigDecimal valorTotalOrdem) {
		this.valorTotalOrdem = valorTotalOrdem;
	}
	
}
