package br.com.controlpro.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.SituacaoOrdem;

@Entity
@Table(name = "CONTROL_ORDEM_CORTE")
public class OrdemCorte extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4983331588533261636L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cancelamento")
	private Date dataCancelamento = new Date();

	@Column(name = "qtd_dias")
	private Integer qtdDias;

	@Temporal(TemporalType.DATE)
	@Column(name = "previsao_termino")
	private Date previsaoTermino;

	@Column(name = "numero_pedido")
	private Long numeroPedido;

	@Column(columnDefinition = "text")
	private String observacao;

	@Enumerated(EnumType.STRING)
	private SituacaoOrdem situacao;

	private String protocolo;

	@OneToMany(mappedBy = "ordemCorte", fetch = FetchType.LAZY)
	private List<GradeOrdem> grades;
	
	@OneToMany(mappedBy = "ordemCorte", fetch = FetchType.LAZY)
	private List<ItemEnfestoCorte> enfestos;

	@Transient
	private Date dataCadastroInicio;

	@Transient
	private Date dataCadastroFim;
	
	@Transient
	private Long quantidadeEnviado;
	
	private Loja loja;

	public Date getDataCancelamento() {
		return dataCancelamento;
	}
	
	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}
	
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

	public List<GradeOrdem> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeOrdem> grades) {
		this.grades = grades;
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

	public Long getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Long numeroPedido) {
		this.numeroPedido = numeroPedido;
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

	public List<ItemEnfestoCorte> getEnfestos() {
		return enfestos;
	}

	public void setEnfestos(List<ItemEnfestoCorte> enfestos) {
		this.enfestos = enfestos;
	}
	
	public Long getQuantidadeEnviado() {
		return quantidadeEnviado;
	}
	
	public void setQuantidadeEnviado(Long quantidadeEnviado) {
		this.quantidadeEnviado = quantidadeEnviado;
	}

	public Loja getLoja() {
		return loja;
	}
	
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	
}
