package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.controlpro.entity.consultas.Funcionario;
import br.com.controlpro.entity.consultas.Produto;

@Entity
@Table(name = "CONTROL_PRODUTO_CONSUMO")
public class ProdutoConsumo extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4983331588533261636L;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;

	@Column(columnDefinition = "text")
	private String observacao;

	@Column(name = "venda_varejo", scale = 2, precision = 9)
	private BigDecimal vendaVarejo = new BigDecimal(0.0);

	@Column(name = "venda_atacado", scale = 2, precision = 9)
	private BigDecimal vendaAtacado = new BigDecimal(0.0);

	@Column(name = "costura", scale = 2, precision = 9)
	private BigDecimal costura = new BigDecimal(0.0);

	@Column(name = "outros", scale = 2, precision = 9)
	private BigDecimal outros = new BigDecimal(0.0);

	@Column(name = "outros_producao", scale = 2, precision = 9)
	private BigDecimal outrosProducao = new BigDecimal(0.0);

	@Column(name = "percentual_imposto", scale = 2, precision = 9)
	private BigDecimal percentualImpostos = new BigDecimal(0.0);

	@Column(name = "percentual_outros", scale = 2, precision = 9)
	private BigDecimal percentualoutros = new BigDecimal(0.0);

	@Column(name = "percentual_cartao", scale = 2, precision = 9)
	private BigDecimal percentualCartao = new BigDecimal(0.0);

	@Column(name = "percentual_comissao", scale = 2, precision = 9)
	private BigDecimal percentualComissao = new BigDecimal(0.0);

	@Column(name = "loja", scale = 2, precision = 9)
	private BigDecimal loja = new BigDecimal(0);

	@Column(name = "percentual_financeiro", scale = 2, precision = 9)
	private BigDecimal percentualFinanceiro = new BigDecimal(0);

	@Column(name = "percentual_marketing", scale = 2, precision = 9)
	private BigDecimal percentualMarketing = new BigDecimal(0);

	@OneToMany(mappedBy = "produtoConsumo", fetch = FetchType.LAZY)
	private List<ItemProdutoConsumo> itensProdutoConsumo;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] arquivo;

	private String extensao;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<ItemProdutoConsumo> getItensProdutoConsumo() {
		return itensProdutoConsumo;
	}

	public void setItensProdutoConsumo(List<ItemProdutoConsumo> itensProdutoConsumo) {
		this.itensProdutoConsumo = itensProdutoConsumo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public BigDecimal getVendaVarejo() {
		if (vendaVarejo == null) {
			vendaVarejo = new BigDecimal(0.0);
		}
		return vendaVarejo;
	}

	public void setVendaVarejo(BigDecimal vendaVarejo) {
		this.vendaVarejo = vendaVarejo;
	}

	public BigDecimal getVendaAtacado() {
		if (vendaAtacado == null) {
			vendaAtacado = new BigDecimal(0.0);
		}
		return vendaAtacado;
	}

	public void setVendaAtacado(BigDecimal vendaAtacado) {
		this.vendaAtacado = vendaAtacado;
	}

	public BigDecimal getCostura() {
		if (costura == null) {
			costura = new BigDecimal(0.0);
		}
		return costura;
	}

	public BigDecimal getOutros() {
		if (outros == null) {
			outros = new BigDecimal(0.0);
		}
		return outros;
	}

	public BigDecimal getLoja() {
		if (loja == null) {
			loja = new BigDecimal(0.0);
		}
		return loja;
	}

	public void setLoja(BigDecimal loja) {
		this.loja = loja;
	}

	public void setCostura(BigDecimal costura) {
		this.costura = costura;
	}

	public void setOutros(BigDecimal outros) {
		this.outros = outros;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public BigDecimal getPercentualImpostos() {
		if (percentualImpostos == null) {
			percentualImpostos = new BigDecimal(0);
		}
		return percentualImpostos;
	}

	public void setPercentualImpostos(BigDecimal percentualImpostos) {
		this.percentualImpostos = percentualImpostos;
	}

	public BigDecimal getPercentualCartao() {
		if (this.percentualCartao == null) {
			percentualCartao = new BigDecimal(0.0);
		}
		return percentualCartao;
	}

	public void setPercentualCartao(BigDecimal percentualCartao) {
		this.percentualCartao = percentualCartao;
	}

	public BigDecimal getPercentualComissao() {
		if (percentualComissao == null) {
			percentualComissao = new BigDecimal(0.0);
		}
		return percentualComissao;
	}

	public void setPercentualComissao(BigDecimal percentualComissao) {
		this.percentualComissao = percentualComissao;
	}

	public BigDecimal getOutrosProducao() {
		if (this.outrosProducao == null) {
			outrosProducao = new BigDecimal(0.0);
		}
		return outrosProducao;
	}

	public void setOutrosProducao(BigDecimal outrosProducao) {
		this.outrosProducao = outrosProducao;
	}

	public BigDecimal getPercentualFinanceiro() {
		if (percentualFinanceiro == null) {
			percentualFinanceiro = new BigDecimal(0.0);
		}
		return percentualFinanceiro;
	}

	public void setPercentualFinanceiro(BigDecimal percentualFinanceiro) {
		this.percentualFinanceiro = percentualFinanceiro;
	}

	public BigDecimal getPercentualMarketing() {
		if (percentualMarketing == null) {
			percentualMarketing = new BigDecimal(0.0);
		}
		return percentualMarketing;
	}

	public void setPercentualMarketing(BigDecimal percentualMarketing) {
		this.percentualMarketing = percentualMarketing;
	}

}
