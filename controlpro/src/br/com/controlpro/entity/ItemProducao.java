package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import br.com.controlpro.constants.TipoItemProducao;
import br.com.controlpro.constants.TipoMateriaPrima;
import br.com.controlpro.entity.consultas.Produto;

@Entity
@Table(name = "CONTROL_ITEM_PRODUCAO")
public class ItemProducao extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;

	@Type(type = "true_false")
	@Column(name = "item_reenviado")
	private Boolean itemReenviado = false;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

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
	
	@Column (name = "qtdReenviada")
	private Integer reenviado = 0;

	@Transient
	private Integer defeitoAux = 0;

	@Transient
	private Integer prontasAux = 0;

	@Transient
	private BigDecimal bonusAux = new BigDecimal(0);

	@Transient
	private Integer faltaAux = 0;

	@Transient
	private Integer qtdEnviarAux = 0;

	@Column(name="justificativa_aux")
	private String justificativaAux;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_item_producao")
	private TipoItemProducao tipoItemProducao;
	
	@ManyToOne
	@JoinColumn(name = "ordem_producao_id")
	private OrdemProducao ordemProducao;

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

	public Integer getDefeitoAux() {
		return defeitoAux;
	}

	public void setDefeitoAux(Integer defeitoAux) {
		this.defeitoAux = defeitoAux;
	}

	public Integer getProntasAux() {
		return prontasAux;
	}

	public void setProntasAux(Integer prontasAux) {
		this.prontasAux = prontasAux;
	}

	public Integer getFaltaAux() {
		faltaAux = quantidade - prontas - getDefeito();
		return faltaAux;
	}

	public void setFaltaAux(Integer faltaAux) {
		this.faltaAux = faltaAux;
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

	public BigDecimal getBonusAux() {
		return bonusAux;
	}

	public void setBonusAux(BigDecimal bonusAux) {
		this.bonusAux = bonusAux;
	}

	public Boolean getItemReenviado() {
		return itemReenviado;
	}

	public void setItemReenviado(Boolean itemReenviado) {
		this.itemReenviado = itemReenviado;
	}
	
	public Integer getQtdEnviarAux() {
		if(qtdEnviarAux == null) {
			qtdEnviarAux = 0;
		}
		return qtdEnviarAux;
	}
	
	public void setQtdEnviarAux(Integer qtdEnviarAux) {
		this.qtdEnviarAux = qtdEnviarAux;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getJustificativaAux() {
		if(justificativaAux == null) {
			justificativaAux = "";
		}
		return justificativaAux;
	}
	
	public void setJustificativaAux(String justificativaAux) {
		this.justificativaAux = justificativaAux;
	}

	public TipoItemProducao getTipoItemProducao() {
		return tipoItemProducao;
	}

	public void setTipoItemProducao(TipoItemProducao tipoItemProducao) {
		this.tipoItemProducao = tipoItemProducao;
	}

	public Integer getReenviado() {
		return reenviado;
	}

	public void setReenviado(Integer reenviado) {
		this.reenviado = reenviado;
	}
	
	

}
