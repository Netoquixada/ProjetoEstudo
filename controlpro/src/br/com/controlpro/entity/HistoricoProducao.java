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
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import br.com.controlpro.entity.consultas.Produto;

@Entity
@Table(name = "CONTROL_HISTORICO_PRODUCAO")
public class HistoricoProducao extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;
	
	
	@ManyToOne
	@JoinColumn(name = "id_ordem_producao")
	private OrdemProducao ordemProducao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_recebimento")
	private Date dataRecebimento = new Date();

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_faccao")
	private Faccao faccao;
	
	@Type(type = "true_false")
	@Column(name = "item_reenviado")
	private Boolean itemReenviado = false;

	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
	private Integer qtdRecebido = 0;
	
	private Integer qtdDefeito = 0;
	
	@Column(name = "valor_bonus")
	private BigDecimal valorBonus = new BigDecimal(0);
	
	private String justificativa;
	
	@Transient
	private Date dataPesquisaInicio;

	@Transient
	private Date dataPesquisaFim;

	public void setOrdemProducao(OrdemProducao ordemProducao) {
		this.ordemProducao = ordemProducao;
	}
	
	public OrdemProducao getOrdemProducao() {
		return ordemProducao;
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

	public Faccao getFaccao() {
		return faccao;
	}

	public void setFaccao(Faccao faccao) {
		this.faccao = faccao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQtdRecebido() {
		if(qtdRecebido == null){
			qtdRecebido = 0;
		}
		return qtdRecebido;
	}

	public void setQtdRecebido(Integer qtdRecebido) {
		this.qtdRecebido = qtdRecebido;
	}
	
	public Integer getQtdDefeito() {
		if(qtdDefeito == null) {
			qtdDefeito = 0;
		}
		return qtdDefeito;
	}
	
	public void setQtdDefeito(Integer qtdDefeito) {
		this.qtdDefeito = qtdDefeito;
	}

	public Date getDataPesquisaInicio() {
		return dataPesquisaInicio;
	}

	public void setDataPesquisaInicio(Date dataPesquisaInicio) {
		this.dataPesquisaInicio = dataPesquisaInicio;
	}

	public Date getDataPesquisaFim() {
		return dataPesquisaFim;
	}

	public void setDataPesquisaFim(Date dataPesquisaFim) {
		this.dataPesquisaFim = dataPesquisaFim;
	}
	
	public BigDecimal getValorBonus() {
		return valorBonus;
	}
	
	public void setValorBonus(BigDecimal valorBonus) {
		this.valorBonus = valorBonus;
	}
	
	public Boolean getItemReenviado() {
		return itemReenviado;
	}
	
	public void setItemReenviado(Boolean itemReenviado) {
		this.itemReenviado = itemReenviado;
	}
	
	public String getJustificativa() {
		return justificativa;
	}
	
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

}